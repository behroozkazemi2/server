package com.behrouz.server.bank.saman;

import com.behrouz.server.model.bank.melli.MelliPaymentRequestEntity;
import com.behrouz.server.model.bank.melli.MelliPaymentResponseEntity;
import com.behrouz.server.model.bank.melli.MelliVerifyRequestEntity;
import com.behrouz.server.model.bank.melli.MelliVerifyResponseEntity;
import com.behrouz.server.repository.bank.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.behrouz.server.bank.saman.request.MelliPaymentRequest;
import com.behrouz.server.bank.saman.request.MelliVerifyRequest;
import com.behrouz.server.bank.saman.response.MelliCallBackResponse;
import com.behrouz.server.bank.saman.response.MelliPaymentResponse;
import com.behrouz.server.bank.saman.response.MelliVerifyResponse;
import com.behrouz.server.base.util.Encryption;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.model.balance.BalanceEntity;
import com.behrouz.server.model.balance.BalanceHistoryEntity;
import com.behrouz.server.model.bank.BankTransactionEntity;
import com.behrouz.server.model.bank.melli.*;
import com.behrouz.server.model.bill.BillBillStatusEntity;
import com.behrouz.server.model.bill.BillEntity;
import com.behrouz.server.model.bill.BillStatusEntity;
import com.behrouz.server.modelOption.BillStatusOption;
import com.behrouz.server.repository.AccountRepository;
import com.behrouz.server.repository.balance.BalanceHistoryRepository;
import com.behrouz.server.repository.balance.BalanceRepository;
import com.behrouz.server.repository.bank.*;
import com.behrouz.server.repository.bill.BillBillStatusRepository;
import com.behrouz.server.repository.bill.BillRepository;
import com.behrouz.server.repository.bill.BillStatusRepository;
import com.behrouz.server.utils.StringGenerator;
import com.behrouz.server.utils.StringUtil;
import com.behrouz.server.values.BankValuesConfiguration;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Date;

/**
 * Created by thunderbolt on 9/16/17.
 */


@Component
public class MelliBankComponent {


    private final static String REQUEST_URL = "https://sadad.shaparak.ir/VPG/api/v0/Request/PaymentRequest";

    private final static String VERIFY_URL = "https://sadad.shaparak.ir/VPG/api/v0/Advice/Verify";


    private RestTemplate rest = new RestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    @Autowired
    private BankTransactionRepository bankTransactionRepository;

    @Autowired
    private MelliPaymentRequestRepository melliPaymentRequestRepository;

    @Autowired
    private MelliPaymentResponseRepository melliPaymentResponseRepository;

    @Autowired
    private MelliVerifyResponseRepository melliVerifyResponseRepository;

    @Autowired
    private MelliCallBackResponseRepository melliCallBackResponseRepository;

    @Autowired
    private MelliVerifyRequestRepository melliVerifyRequestRepository;

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private BillStatusRepository billStatusRepository;

    @Autowired
    private BillBillStatusRepository billBillStatusRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private BalanceHistoryRepository balanceHistoryRepository;


    public MelliBankComponent() {
        //headers.add("Content-Type", "application/x-www-form-urlencoded");
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
    }


    public BankTransactionEntity createTransaction(long accountId, String mobile, long refId, long amount) throws BehtaShopException {


        String token = createToken(accountId, refId);
        String redirectUrl = BankValuesConfiguration.SAMAN_CALL_BACK_URL + token;

        long userMobile = 0;
        try {
            userMobile = StringUtil.isNullOrEmpty(mobile) ? 0 : Long.parseLong(mobile);
        } catch (Exception e) {
            e.printStackTrace();
        }

        BankTransactionEntity bankTransaction =
                new BankTransactionEntity(
                        accountId,
                        userMobile,
                        refId,
                        amount,
                        token,
                        redirectUrl,
                        null,
                        null
                );

        bankTransactionRepository.save(bankTransaction);


        MelliPaymentResponseEntity melliPaymentResponse =
                createMelliPaymentResponse(bankTransaction);

        if (melliPaymentResponse == null) {
            throw new BehtaShopException("خطا در ایجاد تراکنش بانکی");
        }

        if (melliPaymentResponse.getResCode() != 0) {
            throw new BehtaShopException("خطا در بوجود آوردن تراکنش بانکی");
        }


        String userPayUrl = BankValuesConfiguration.SAMAN_USER_PAY_URL + melliPaymentResponse.getToken();

        bankTransaction.setUserPayUrl(userPayUrl);
        bankTransaction.setBankToken(melliPaymentResponse.getToken());
        bankTransactionRepository.save(bankTransaction);

        return bankTransaction;

    }


    public MelliVerifyResponseEntity verifyTransaction(MelliCallBackResponse callBackResponse, BankTransactionEntity bankTransaction) throws BehtaShopException {


        MelliVerifyResponseEntity lastVerifiedResponse =
                melliVerifyResponseRepository.findFirstByBankTransaction_Id(bankTransaction.getId());

        if (lastVerifiedResponse != null) {
            throw new BehtaShopException(" تایید تراکنش ناموفق! تراکنش تکراری.");
        }


        MelliVerifyResponseEntity melliVerifyResponse = null;
        try {
            melliVerifyResponse = createMelliVerifyResponse(
                    callBackResponse,
                    bankTransaction
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new BehtaShopException(" خطا در برقراری ارتباط با بانک");
        }

        if (melliVerifyResponse == null) {
            throw new BehtaShopException(" خطا در تایید تراکنش");
        }

        return melliVerifyResponse;
    }

    private MelliPaymentResponseEntity createMelliPaymentResponse(final BankTransactionEntity bankTransaction) {


        try {
            MelliPaymentRequest paymentRequest = new MelliPaymentRequest(
                    BankValuesConfiguration.SADAD_MERCHANT_ID,
                    BankValuesConfiguration.SADAD_TERMINAL_ID,
                    bankTransaction.getAmount(),
                    bankTransaction.getId(),
                    new Date(),
                    bankTransaction.getRedirectUrl(),
                    bankTransaction.getMobile(),
                    generateSignData(bankTransaction.getId(), bankTransaction.getAmount())
            );
            MelliPaymentRequestEntity melliPaymentRequestEntity =
                    new MelliPaymentRequestEntity(paymentRequest, bankTransaction);


            melliPaymentRequestRepository.save(melliPaymentRequestEntity);


            MelliPaymentResponseEntity melliPaymentResponseEntity = null;

            try {

                ResponseEntity<String> melliResponseString = rest.exchange(REQUEST_URL, HttpMethod.POST,
                        new HttpEntity<>(paymentRequest, headers), String.class);


                if (melliResponseString.getStatusCode() == HttpStatus.OK) {

                    MelliPaymentResponse melliResponseBody =
                            new ObjectMapper().readValue(melliResponseString.getBody(), MelliPaymentResponse.class);

                    melliPaymentResponseEntity =
                            new MelliPaymentResponseEntity(
                                    melliResponseBody,
                                    bankTransaction,
                                    melliPaymentRequestEntity
                            );
                } else {
                    melliPaymentResponseEntity =
                            new MelliPaymentResponseEntity(
                                    null,
                                    bankTransaction,
                                    melliPaymentRequestEntity
                            );
                    melliPaymentResponseEntity.setResCode(-1000 * melliResponseString.getStatusCode().value());
                    melliPaymentResponseEntity.setDescription("Sadad Failed: " + melliResponseString.getStatusCode().value());
                }

            } catch (Exception e) {
                e.printStackTrace();
                melliPaymentResponseEntity =
                        new MelliPaymentResponseEntity(
                                null,
                                bankTransaction,
                                melliPaymentRequestEntity
                        );
                melliPaymentResponseEntity.setResCode(-1);
                melliPaymentResponseEntity.setDescription("Error: " + e.getMessage());

            } finally {
                if (melliPaymentResponseEntity == null) {
                    System.err.println("\n\n\n\n\n\t\t\t Hapi !!!!!!!!!!!!1 \n\n\n");
                } else {
                    melliPaymentResponseRepository.save(melliPaymentResponseEntity);
                }
            }

            return melliPaymentResponseEntity;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String createToken(long accountId, long refId) {
        String token =
                StringGenerator.generateDigit(8) + System.currentTimeMillis() + StringGenerator.generateToken(8);

        return String.format("%04d%06d%s", accountId, refId, token);
    }

    private String generateSignData(long orderId, long amount) throws Exception {


        byte[] key = Base64.decodeBase64(BankValuesConfiguration.SADAD_EPG_KEY.getBytes("UTF-8"));

        String value = String.format("%s;%s;%s", BankValuesConfiguration.SADAD_TERMINAL_ID, String.valueOf(orderId), String.valueOf(amount));

        return Encryption.TripleDESEncrypt(key, value);

    }


    private MelliVerifyResponseEntity createMelliVerifyResponse(MelliCallBackResponse callBackResponse, BankTransactionEntity bankTransaction) throws Exception {

        MelliVerifyRequest verifyRequest =
                new MelliVerifyRequest(
                        callBackResponse.getToken(),
                        verifySignData(callBackResponse.getToken())
                );
        MelliVerifyRequestEntity melliVerifyRequestEntity =
                new MelliVerifyRequestEntity(verifyRequest, bankTransaction);

        melliVerifyRequestRepository.save(melliVerifyRequestEntity);

        MelliVerifyResponseEntity melliVerifyResponseEntity = null;


        try {


            ResponseEntity<String> melliResponseString = rest.exchange(VERIFY_URL, HttpMethod.POST,
                    new HttpEntity<>(verifyRequest, headers), String.class);


            if (melliResponseString.getStatusCode() == HttpStatus.OK) {
                MelliVerifyResponse melliVerifyResponse =
                        new ObjectMapper().readValue(
                                melliResponseString.getBody(),
                                MelliVerifyResponse.class
                        );

                melliVerifyResponseEntity = new MelliVerifyResponseEntity(
                        melliVerifyResponse,
                        bankTransaction,
                        melliVerifyRequestEntity
                );

            } else {
                melliVerifyResponseEntity = new MelliVerifyResponseEntity(
                        null,
                        bankTransaction,
                        melliVerifyRequestEntity
                );
                melliVerifyResponseEntity.setResCode(-1000 * melliResponseString.getStatusCodeValue());
                melliVerifyResponseEntity.setDescription("Http Failed: " + melliResponseString.getStatusCodeValue());
            }


        } catch (Exception e) {
            e.printStackTrace();
            melliVerifyResponseEntity = new MelliVerifyResponseEntity(
                    null,
                    bankTransaction,
                    melliVerifyRequestEntity
            );
            melliVerifyResponseEntity.setResCode(-5);
            melliVerifyResponseEntity.setDescription("Exception : " + e.getMessage());
        } finally {
            melliVerifyResponseRepository.save(melliVerifyResponseEntity);
        }

        return melliVerifyResponseEntity;
    }

    public String verifySignData(String token) throws Exception {
        byte[] key = Base64.decodeBase64(BankValuesConfiguration.SADAD_EPG_KEY.getBytes("UTF-8"));

        return Encryption.TripleDESEncrypt(key, token);
    }

    public String successChargedAccount(AccountEntity acc, long amountBank) {
        // TODO Hapi SEND SMS
        BalanceEntity balance =
                balanceRepository.findFirstByAccount_IdAndDeletedIsFalseOrderByIdDesc(acc.getId());

        long balanceAmount = balance == null ? 0L : balance.getAmount();

        balanceAmount += amountBank;

        BalanceEntity newBalanceAfterBank =
                new BalanceEntity(
                        acc,
                        balanceAmount,
                        "شارژ حساب کاربری از طریق درگاه بانکی"
                );
        balanceRepository.save(newBalanceAfterBank);
        sendSmsForCustomer(acc);


        return "https://shop.behrouz.com/UserPanel?type=0&walletCharge=true&failed=false";

    }

    public String successBillPayment(BillEntity billEntity, AccountEntity acc, String refCode, long amountBank) {

        billBillStatusRepository.save(new BillBillStatusEntity(
                billEntity,
                new BillStatusEntity()
                        .id(BillStatusOption.PAYED.getId()),
                acc
        ));


        BalanceEntity balance =
                balanceRepository.findFirstByAccount_IdAndDeletedIsFalseOrderByIdDesc(acc.getId());

        long balanceAmount = balance == null ? 0L : balance.getAmount();

        balanceAmount += amountBank;

        BalanceEntity newBalanceAfterBank =
                new BalanceEntity(
                        acc,
                        balanceAmount,
                        "شارژ حساب کاربری از طریق درگاه بانکی با کد پیگیری " + refCode
                );
        balanceRepository.save(newBalanceAfterBank);

        BalanceHistoryEntity newBalanceAfterBankHistory =
                new BalanceHistoryEntity(
                        acc,
                        balanceAmount * 10,
                        0,
                        billEntity.getId(),
                        null,
                        "شارژ حساب کاربری از طریق درگاه بانکی با کد پیگیری " + refCode
                );

        balanceHistoryRepository.save(newBalanceAfterBankHistory);




        balanceAmount -= billEntity.getPayableAmount();
        BalanceEntity newBalanceAfterPayBill =
                new BalanceEntity(
                acc,
                balanceAmount,
                " پرداخت اعتبار برای سفارش " + refCode
        );
        balanceRepository.save(newBalanceAfterPayBill);

        BalanceHistoryEntity newBalanceAfterPayBillHistory =
                new BalanceHistoryEntity(
                        acc,
                        - balanceAmount * 10,
                        0,
                        billEntity.getId(),
                        null,
                        " پرداخت اعتبار برای سفارش " + refCode
                );
        balanceHistoryRepository.save(newBalanceAfterPayBillHistory);

        // TODO Hapi SEND SMS
        sendSmsForCustomer(acc);
        sendSmsForProvider(acc);
        sendSmsForAdmin(acc);

        // کد پیگیری کدومه refCode || trackingCode
        return "https://shop.behrouz.com/pay/success/" + refCode + '/' + billEntity.getId();

    }

    private void sendSmsForAdmin(AccountEntity customer) {

    }

    private void sendSmsForProvider(AccountEntity customer) {

    }

    private void sendSmsForCustomer(AccountEntity customer) {
    }

}
