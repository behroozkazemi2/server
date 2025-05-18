package com.behrouz.server.bank.saman;

import com.behrouz.server.bank.saman.response.MelliCallBackResponse;
import com.behrouz.server.component.ProductProviderComponent;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.model.balance.BalanceEntity;
import com.behrouz.server.model.balance.BalanceHistoryEntity;
import com.behrouz.server.model.bank.BankTransactionEntity;
import com.behrouz.server.model.bank.melli.MelliVerifyResponseEntity;
import com.behrouz.server.model.bill.BillBillStatusEntity;
import com.behrouz.server.model.bill.BillStatusEntity;
import com.behrouz.server.modelOption.BillStatusOption;
import com.behrouz.server.repository.AccountRepository;
import com.behrouz.server.repository.CustomerRepository;
import com.behrouz.server.repository.balance.BalanceHistoryRepository;
import com.behrouz.server.repository.balance.BalanceRepository;
import com.behrouz.server.repository.bank.BankTransactionRepository;
import com.behrouz.server.repository.bill.BillBillStatusRepository;
import com.behrouz.server.utils.ArraysUtil;
import com.behrouz.server.utils.StringUtil;
import com.behrouz.server.values.BankValuesConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;

@Controller
@RequestMapping("/payment/melli")
public class BankMelliController {


    @Autowired
    private MelliBankComponent melliBankComponent;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private BalanceHistoryRepository balanceHistoryRepository;

    @Autowired
    private BillBillStatusRepository billBillStatusRepository;

    @Autowired
    private ProductProviderComponent productProviderComponent;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BankTransactionRepository bankTransactionRepository;


    @RequestMapping("/{token}")
    public String pay(Model model, @PathVariable("token") String token){

        System.out.println("received token: " + token);
        model.addAttribute("url", BankValuesConfiguration.SADAD_SADAD_PAY_URL);
        model.addAttribute("token", token);

        return "bank/payment";

    }


    @RequestMapping("/sadadpsp/{token}")
    public String paymentConfirm(
            Model model,
            @PathVariable("token") String token,
            @ModelAttribute("data") MelliCallBackResponse data,
            BindingResult bindingResult
            ) throws HttpServerErrorException {

        if(data == null){
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }

        System.out.println("\n\nreceived data: " + data.toString() + "\n\n");

        if(StringUtil.isNullOrEmpty(data.getToken())){
            throw new HttpServerErrorException(HttpStatus.NOT_FOUND);
        }


        String refCode;
        boolean success;
        String applicationLink = "https://8tagmarket.com/pay/error";

        try {
            if (StringUtil.isNullOrEmpty(token)) {
                throw new BehtaShopException(" تایید تراکنش ناموفق! پارامتر های ورودی دچار ایراد می‌باشد.");
            }

            BankTransactionEntity bankTransaction = bankTransactionRepository.findFirstByToken(token);
            if (bankTransaction == null) {
                throw new BehtaShopException(" تایید تراکنش ناموفق! تراکنشی با این مشخصات پیدا نشد.");
            }

            //خرید موفق
            AccountEntity account =
                    customerRepository.findFirstByIdAndBannedIsFalseAndDeletedIsFalse( bankTransaction.getAccountId() );


            MelliVerifyResponseEntity melliResponse =
                    melliBankComponent.verifyTransaction(data, bankTransaction);

            success = melliResponse.getResCode() == 0;
            refCode = melliResponse.getRetrivalRefNo();

            // charge
            if(success){
                successPaymentConfirm(melliResponse, refCode);

                if (melliResponse.getBankTransaction().getRefId() == 0) {
                    //شارژ موفق
                    applicationLink =
                            melliBankComponent.successChargedAccount(account, melliResponse.getAmount());
                }else {
                    //پرداخت

                    BillBillStatusEntity billBillStatus =
                            billBillStatusRepository.findFirstByBill_IdAndDeletedIsFalseOrderByIdDesc(bankTransaction.getRefId());
                    if (billBillStatus == null) {
                        throw new BehtaShopException("خطا در پیدا کردن فاکتور");
                    }
                    if(billBillStatus.getBill().getCustomer().getId() != account.getId()){
                        throw new BehtaShopException("خطا دردسترسی به فاکتور");
                    }
                    if(billBillStatus.getStatus().getId() != BillStatusOption.WAIT_FOR_PAY.getId()){
                        throw new BehtaShopException("خطا! فاکتور منتظر پرداخت ‌نمی‌باشد.");
                    }

                    applicationLink =
                            melliBankComponent.successBillPayment(billBillStatus.getBill(), account, refCode, melliResponse.getAmount());

                    // TODO CHECK THIS IS TRUE ?? (for clear in cart product)
                    productProviderComponent.clearInCartProduct(melliResponse.getBankTransaction().getAccountId());
                }
            }else{
                return "redirect:https://8tagmarket.com/pay/error";

            }

        } catch (BehtaShopException e) {
            return "redirect:https://8tagmarket.com/pay/error";

        }


        return "redirect:" + applicationLink;

    }



    private void successPaymentConfirm(MelliVerifyResponseEntity verifyResponseEntity, String trackingCode) {

        assert verifyResponseEntity != null;

        BankTransactionEntity transaction =
                verifyResponseEntity.getBankTransaction();

        AccountEntity account =
                accountRepository.findFirstByIdAndDeletedIsFalse(transaction.getAccountId());

        if(account == null){
            System.err.println("Account is null");
            return;
        }

        updateAccountBalance(
                account,
                transaction.getAmount(),
                transaction.getRefId(),
                trackingCode,
                "شارژ حساب از طریق درگاه بانک ملی به شماره پیگیری " + trackingCode
        );


        if(transaction.getRefId() != 0){
            visitPay(transaction, account, trackingCode);
        }

        //todo uncomment after edit sms
//        new Thread(() -> {
//            if(transaction.getRefId() == 0){
//                SmsSender.charge(account.getMobile(), String.format("%,d",transaction.getAmount()), trackingCode);
//            } else{
//                SmsSender.pay(account.getMobile(), trackingCode);
//            }
//        }).start();


    }

    private void visitPay(BankTransactionEntity transaction, AccountEntity account, String trackingCode) {
        BillBillStatusEntity billBillStatus = billBillStatusRepository.findFirstByBill_IdAndDeletedIsFalseOrderByIdDesc(
                transaction.getRefId()
        );

        if(billBillStatus == null){
            System.err.println("Payment-Error: visit not-found!");
            return;
        }

        if(billBillStatus.getStatus().getId() != BillStatusOption.WAIT_FOR_PAY.getId()){
            System.err.println("Payment-Error: visit status-id " + billBillStatus.getStatus().getId());
            return;
        }

        long balance = getAccountBalance(account.getId());

        if(balance < billBillStatus.getBill().getPayableAmount()){
            System.err.println("Payment-Error: balance is not enough " + billBillStatus.getStatus().getId());
            return;
        }

        billBillStatusRepository.save(
                new BillBillStatusEntity(
                        billBillStatus.getBill(),
                        new BillStatusEntity(){{setId(BillStatusOption.PAYED.getId());}},
                        account

                )
        );

        updateAccountBalance(
                account,
                -billBillStatus.getBill().getPayableAmount(),
                billBillStatus.getBill().getId(),
                trackingCode,
                "پرداخت فاکتور با کد پیگیری " + billBillStatus.getBill().getTrackingCode() + " از طریق درگاه بانک ملی با کد پیگیری" + trackingCode
        );





    }

    private void updateAccountBalance(AccountEntity account, long amount, long refId, String trackingCode, String description) {

        long balance = getAccountBalance(account.getId());

        balance += amount;



        BalanceEntity nBalance =
                new BalanceEntity(
                        account,
                        balance,
                        description
                );

        BalanceHistoryEntity nBalanceTransaction =
                new BalanceHistoryEntity(
                        account,
                        amount,
                        0,
                        refId,
                        trackingCode,
                        description
                );

        balanceRepository.save(nBalance);
        balanceHistoryRepository.save(nBalanceTransaction);

    }


    private long getAccountBalance(long account){
        String query = "" +
                "SELECT bl.amount amount " +
                "FROM balance_last bl " +
                "WHERE bl.account_id = :aid ";

        List<Long> balances = jdbcTemplate.query(
                query,
                new MapSqlParameterSource("aid", account),
                (res, rowId) -> res.getLong("amount")
        );
        return ArraysUtil.isNullOrEmpty(balances) ? 0 : balances.get(0);
    }
}
