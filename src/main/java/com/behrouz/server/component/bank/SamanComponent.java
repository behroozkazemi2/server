package com.behrouz.server.component.bank;

import com.behrouz.server.bank.saman.MelliBankComponent;
import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.model.balance.BalanceEntity;
import com.behrouz.server.model.balance.BalanceHistoryEntity;
import com.behrouz.server.model.bank.*;
import com.behrouz.server.model.bill.BillBillStatusEntity;
import com.behrouz.server.model.bill.BillEntity;
import com.behrouz.server.model.bill.BillStatusEntity;
import com.behrouz.server.repository.bank.*;
import com.behrouz.server.component.PaymentComponent;
import com.behrouz.server.component.ProductProviderComponent;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.model.bank.*;
import com.behrouz.server.model.bank.melli.MelliVerifyResponseEntity;
import com.behrouz.server.modelOption.BillStatusOption;
import com.behrouz.server.repository.AccountRepository;
import com.behrouz.server.repository.CustomerRepository;
import com.behrouz.server.repository.balance.BalanceHistoryRepository;
import com.behrouz.server.repository.balance.BalanceRepository;
import com.behrouz.server.repository.bank.*;
import com.behrouz.server.repository.bill.BillBillStatusRepository;
import com.behrouz.server.rest.request.bank.SamanReverseTransactionRestRequest;
import com.behrouz.server.rest.request.bank.SamanTokenRestRequest;
import com.behrouz.server.rest.request.bank.SamanVerifyTransactionRestRequest;
import com.behrouz.server.rest.response.bank.SamanRedirectUrlRestResponse;
import com.behrouz.server.rest.response.bank.SamanReverseTransactionRestResponse;
import com.behrouz.server.rest.response.bank.SamanTokenRestResponse;
import com.behrouz.server.rest.response.bank.SamanVerifyTransactionRestResponse;
import com.behrouz.server.retrofit.SamanBankService;
import com.behrouz.server.strategy.StringGenerator;
import com.behrouz.server.utils.ArraysUtil;
import com.behrouz.server.utils.StringUtil;
import com.behrouz.server.values.BankValuesConfiguration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.util.List;

@Component
public class SamanComponent {

    @Autowired
    private SamanTokenResponseRepository samanTokenResponseRepository;

    @Autowired
    private SamanTokenRequestRepository samanTokenRequestRepository;

    @Autowired
    private SamanVerifyTransactionRequestRepository samanVerifyTransactionRequestRepository;

    @Autowired
    private SamanVerifyTransactionResponseRepository samanVerifyTransactionResponseRepository;

    @Autowired
    private SamanRedirectUrlResponseRepository samanRedirectUrlResponseRepository;


    @Autowired
    private PaymentComponent paymentComponent;

    @Autowired
    private SamanVerifyTransactionInfoResponseRepository samanVerifyTransactionInfoResponseRepository;

    @Autowired
    private SamanReverseTransactionInfoResponseRepository samanReverseTransactionInfoResponseRepository;

    @Autowired
    private SamanReverseTransactionResponseRepository samanReverseTransactionResponseRepository;

    @Autowired
    private SamanReverseTransactionRequestRepository samanReverseTransactionRequestRepository;

    @Autowired
    private BankTransactionRepository bankTransactionRepository;

    @Autowired
    private MelliBankComponent melliBankComponent;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private BalanceHistoryRepository balanceHistoryRepository;

    @Autowired
    private BillBillStatusRepository billBillStatusRepository;

    @Autowired
    private ProductProviderComponent productProviderComponent;

    public SamanTokenRestResponse requestToSamanBankForCreateToken(BillEntity bill, long shouldPayWithBank) throws IOException {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        String token =
                createToken(bill.getCustomer().getId(), bill.getId());

        SamanTokenRestRequest parameters =
                new SamanTokenRestRequest(
                        "token",
                        BankValuesConfiguration.SAMAN_TERMINAL_ID,
                        shouldPayWithBank,
                        bill.getTrackingCode(),
                        BankValuesConfiguration.SAMAN_CALL_BACK_URL + token ,
                        bill.getCustomer().getMobile()
                );

        samanTokenRequestRepository.save(
                new SamanTokenRequestEntity(parameters)
        );

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sep.shaparak.ir")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        SamanBankService gerritAPI = retrofit.create(SamanBankService.class);

        Call<SamanTokenRestResponse> call =
                gerritAPI.getBankToken(
                        parameters
                );


        SamanTokenRestResponse bankRes = new SamanTokenRestResponse();
        bankRes.setBillId(bill.getId());

        Response<SamanTokenRestResponse> response = call.execute();
        if (response.isSuccessful()) {
            bankRes = response.body();
        } else {
            bankRes = response.body();
        }

        samanTokenResponseRepository.save(
                new SamanTokenResponseEntity(bankRes, bill.getTrackingCode())
        );

        saveBankTransaction(bill, shouldPayWithBank, token);

        System.out.println("SAMAN BANK RES" + bankRes.toString());
        return bankRes;
    }

    private void saveBankTransaction(BillEntity bill, long shouldPayWithBank, String  token) {

        String redirectUrl =
                BankValuesConfiguration.SAMAN_CALL_BACK_URL + token;

        long userMobile = 0;
        try {
            userMobile = StringUtil.isNullOrEmpty(bill.getCustomer().getMobile()) ? 0 : Long.parseLong(bill.getCustomer().getMobile());
        } catch (Exception e) {
            e.printStackTrace();
        }

        BankTransactionEntity bankTransaction =
                new BankTransactionEntity(
                        bill.getCustomer().getId(),
                        userMobile,
                        bill.getId(),
                        shouldPayWithBank,
                        token,
                        redirectUrl,
                        null,
                        null
                );

        bankTransactionRepository.save(bankTransaction);
    }


    public String verifyBankTransaction(SamanRedirectUrlRestResponse request, String token) throws IOException, BehtaShopException {

        // بررسی شود که دو تا refNum تکراری استفاده نشده باششد برای خرید
        SamanVerifyTransactionRequestEntity lastVerifiedResponse =
                samanVerifyTransactionRequestRepository.findFirstByRefNum(request.getRefNum());
        System.out.println(" ----- verifyBankTransaction -----  ");
        System.out.println(request.getRefNum());
        System.out.println(( lastVerifiedResponse == null ? "NULL" : lastVerifiedResponse.toString()));
        if (lastVerifiedResponse != null) {
            throw new BehtaShopException(" تایید تراکنش ناموفق! تراکنش تکراری.");
        }

        samanRedirectUrlResponseRepository.save(
                new SamanRedirectUrlResponseEntity(request)
        );

        SamanVerifyTransactionRestRequest parameters =
                new SamanVerifyTransactionRestRequest(request.getRefNum(), request.getTerminalId());

        samanVerifyTransactionRequestRepository.save(
                new SamanVerifyTransactionRequestEntity(
                        parameters
                )
        );


        String refCode;
        boolean success;
        String applicationLink = "https://shop.behrouz.com/pay/error";

        try {
            if (StringUtil.isNullOrEmpty(token)) {
                throw new BehtaShopException(" تایید تراکنش ناموفق! پارامتر های ورودی دچار ایراد می‌باشد.");
            }

            BankTransactionEntity bankTransaction = bankTransactionRepository.findFirstByToken(token);
            if (bankTransaction == null) {
                throw new BehtaShopException(" تایید تراکنش ناموفق! تراکنشی با این مشخصات پیدا نشد.");
            }


            Response<SamanVerifyTransactionRestResponse> response =
                    callForVerifySamanTransaction(parameters);

            System.out.println( "----------------  verifyBankTransaction ---------------- " );
            System.out.println("\n\n\n" + response.raw());

            SamanVerifyTransactionResponseEntity samanResponse =
                    savingSamanVerifyResponse(response.body(), bankTransaction);

            //خرید موفق
            AccountEntity account =
                    customerRepository.findFirstByIdAndBannedIsFalseAndDeletedIsFalse(bankTransaction.getAccountId());


//            MelliVerifyResponseEntity melliResponse =
//                    melliBankComponent.verifyTransaction(data, bankTransaction);

            success = response.isSuccessful();
            refCode = samanResponse.getResultCode() + "";


            System.out.println( "----------------  samanResponse ---------------- " );
            System.out.println("\n\n\n" + samanResponse);
            System.out.println("\n" + refCode);
            // charge
            if (success) {
                successSamanPaymentConfirm(samanResponse, refCode);

                if (samanResponse.getBankTransaction().getRefId() == 0) {
                    //شارژ موفق
                    applicationLink =
                            melliBankComponent.successChargedAccount(account, samanResponse.getVerifyInfo().getAffectiveAmount());
                } else {
                    //پرداخت

                    BillBillStatusEntity billBillStatus =
                            billBillStatusRepository.findFirstByBill_IdAndDeletedIsFalseOrderByIdDesc(bankTransaction.getRefId());
                    if (billBillStatus == null) {
                        throw new BehtaShopException("خطا در پیدا کردن فاکتور");
                    }
                    if (billBillStatus.getBill().getCustomer().getId() != account.getId()) {
                        throw new BehtaShopException("خطا دردسترسی به فاکتور");
                    }
                    if (billBillStatus.getStatus().getId() != BillStatusOption.WAIT_FOR_PAY.getId()) {
                        throw new BehtaShopException("خطا! فاکتور منتظر پرداخت ‌نمی‌باشد.");
                    }

                    applicationLink =
                            melliBankComponent.successBillPayment(billBillStatus.getBill(), account, refCode, samanResponse.getVerifyInfo().getAffectiveAmount());

                    productProviderComponent.clearInCartProduct(samanResponse.getBankTransaction().getAccountId());
                }
            } else {
                reverseTransaction(
                        new SamanReverseTransactionRestRequest(parameters)
                );
                return "redirect:https://shop.behrouz.com/pay/error";

            }

        } catch (BehtaShopException e) {
            reverseTransaction(
                    new SamanReverseTransactionRestRequest(parameters)
            );
            return "redirect:https://shop.behrouz.com/pay/error";
        }

        return applicationLink;
    }

    private Response<SamanVerifyTransactionRestResponse> callForVerifySamanTransaction(SamanVerifyTransactionRestRequest parameters) throws IOException {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sep.shaparak.ir")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SamanBankService gerritAPI = retrofit.create(SamanBankService.class);

        Call<SamanVerifyTransactionRestResponse> call =
                gerritAPI.verifyTransaction(
                        parameters
                );

        System.out.println("\n\n\n" + call.request().toString());

        // TODO ERROR
        Response<SamanVerifyTransactionRestResponse> response = call.execute();
        return response;
    }


    public void reverseTransaction(SamanReverseTransactionRestRequest request) throws IOException, BehtaShopException {

        samanReverseTransactionRequestRepository.save(new SamanReverseTransactionRequestEntity(request));

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://sep.shaparak.ir")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SamanBankService gerritAPI = retrofit.create(SamanBankService.class);
        Call<SamanReverseTransactionRestResponse> call =
                gerritAPI.reverseTransaction(
                        request
                );


        Response<SamanReverseTransactionRestResponse> response = call.execute();

        savingSamanReserveResponse(response.body());
    }

    private void successPaymentConfirm(MelliVerifyResponseEntity verifyResponseEntity, String trackingCode) {

        assert verifyResponseEntity != null;

        BankTransactionEntity transaction =
                verifyResponseEntity.getBankTransaction();

        AccountEntity account =
                accountRepository.findFirstByIdAndDeletedIsFalse(transaction.getAccountId());

        if (account == null) {
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


        if (transaction.getRefId() != 0) {
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
    private void successSamanPaymentConfirm(SamanVerifyTransactionResponseEntity verifyResponseEntity, String trackingCode) {

        assert verifyResponseEntity != null;

        BankTransactionEntity transaction =
                verifyResponseEntity.getBankTransaction();

        AccountEntity account =
                accountRepository.findFirstByIdAndDeletedIsFalse(transaction.getAccountId());

        if (account == null) {
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


        if (transaction.getRefId() != 0) {
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

        if (billBillStatus == null) {
            System.err.println("Payment-Error: visit not-found!");
            return;
        }

        if (billBillStatus.getStatus().getId() != BillStatusOption.WAIT_FOR_PAY.getId()) {
            System.err.println("Payment-Error: visit status-id " + billBillStatus.getStatus().getId());
            return;
        }

        long balance = getAccountBalance(account.getId());

        if (balance < billBillStatus.getBill().getPayableAmount()) {
            System.err.println("Payment-Error: balance is not enough " + billBillStatus.getStatus().getId());
            return;
        }

        billBillStatusRepository.save(
                new BillBillStatusEntity(
                        billBillStatus.getBill(),
                        new BillStatusEntity() {{
                            setId(BillStatusOption.PAYED.getId());
                        }},
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

    private long getAccountBalance(long account) {
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

    private SamanVerifyTransactionResponseEntity savingSamanVerifyResponse(SamanVerifyTransactionRestResponse verifyResponse, BankTransactionEntity bankTransaction) {

        SamanVerifyTransactionInfoResponseEntity samanVerifyInfoResponse = null;

        if (verifyResponse.getTransactionDetail() != null) {
            samanVerifyInfoResponse = new SamanVerifyTransactionInfoResponseEntity(verifyResponse.getTransactionDetail());
            samanVerifyTransactionInfoResponseRepository.save(samanVerifyInfoResponse);
        }

        SamanVerifyTransactionResponseEntity samanVerifyTransactionResponse =  new SamanVerifyTransactionResponseEntity(
                verifyResponse,
                samanVerifyInfoResponse,
                bankTransaction
        );

        samanVerifyTransactionResponseRepository.save(samanVerifyTransactionResponse);

        return samanVerifyTransactionResponse;
    }

    private void savingSamanReserveResponse(SamanReverseTransactionRestResponse verifyResponse) {

        SamanReverseTransactionInfoResponseEntity samanVerifyInfoResponse = new SamanReverseTransactionInfoResponseEntity(verifyResponse.getVerifyInfo());
        samanReverseTransactionInfoResponseRepository.save(samanVerifyInfoResponse);

        samanReverseTransactionResponseRepository.save(new SamanReverseTransactionResponseEntity(
                verifyResponse,
                samanVerifyInfoResponse
        ));
    }

    private String createToken(long accountId, long refId) {
        String token =
                StringGenerator.generateDigit(8) + System.currentTimeMillis() + StringGenerator.generateToken(8);

        return String.format("%04d%06d%s", accountId, refId, token);
    }


}
