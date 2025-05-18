package com.behrouz.server.component;


import com.behrouz.server.api.customer.request.MoneyRequestResponse;
import com.behrouz.server.bank.saman.MelliBankComponent;
import com.behrouz.server.component.bank.SamanComponent;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.account.ProviderEntity;
import com.behrouz.server.model.bank.BankTransactionEntity;
import com.behrouz.server.model.bill.BillEntity;
import com.behrouz.server.model.bill.BillProductProviderEntity;
import com.behrouz.server.repository.balance.BalanceHistoryRepository;
import com.behrouz.server.repository.balance.BalanceRepository;
import com.behrouz.server.repository.bill.BillBillStatusRepository;
import com.behrouz.server.repository.bill.BillProductProviderRepository;
import com.behrouz.server.repository.bill.BillStatusRepository;
import com.behrouz.server.rest.response.bank.SamanTokenRestResponse;
import com.behrouz.server.utils.NiazPardazandehSendSmsUtil;
import com.behrouz.server.utils.StringUtil;
import com.behrouz.server.utils.date.PersianDateUtil;
import com.behrouz.server.exception.BehtaShopException;
import com.behrouz.server.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.component
 * Project Name: behta-server
 * 06 December 2018
 **/

@Component
public class PaymentComponent {

    private static final Logger logger = Logger.getLogger( PaymentComponent.class.getSimpleName() );


    @Autowired
    private CustomerRepository customerRepository;


    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private BalanceHistoryRepository balanceHistoryRepository;

    @Autowired
    private BillStatusRepository billStatusRepository;

    @Autowired
    private FactorComponent factorComponent;

    @Autowired
    private OrderComponent orderComponent;


    @Autowired
    private BillBillStatusRepository billBillStatusRepository;


    @Autowired
    private BillProductProviderRepository billProductProviderRepository;

    @Autowired
    private MelliBankComponent melliBankComponent;

    @Autowired
    private NiazPardazandehSendSmsUtil niazPardazandehSendSmsUtil;


    @Autowired
    private SamanComponent samanComponent;


    public String charge(MoneyRequestResponse chargeRequest, int customerId) throws BehtaShopException {


        CustomerEntity customer = customerRepository.findFirstByIdAndDeletedIsFalse(customerId);
        if(customer == null){
            throw new BehtaShopException("کاربر نامعتبر");
        }


        BankTransactionEntity transaction = melliBankComponent.createTransaction(
                customerId,
                customer.getMobile(),
                0,
                chargeRequest.getAmountRial()
        );

        //        redisBank.generate(bankToken, chargeRequest.getAmountRial(), 0, customerId, true);

        return transaction.getUserPayUrl();

    }



    public void sendSms(BillEntity bill){
        System.err.println("!!!!TODO SEND SMS FOR MODIR AND CUSTOMER!!!!");
        sendBillCustomerSms(bill);
        sendMeReceived(bill);
        sendProviderBillSms(bill);

    }

    private void sendMeReceived(BillEntity bill) {
//        com.behrouz.server.kavenegar.SmsSender.providerBillAccepted(
//                "09029090868",
//                "حسن کاظمی",
//                bill.getTrackingCode(),
//                PersianDateUtil.getPersianDate(bill.getOrderDate().getTime())
//        );
//        com.behrouz.server.kavenegar.SmsSender.providerBillAccepted(
//                "09105501184",
//                "محمد حسین",
//                bill.getTrackingCode(),
//                PersianDateUtil.getPersianDate(bill.getOrderDate().getTime())
//        );

        niazPardazandehSendSmsUtil.sendNewProviderBill(
                "حسن کاظمی",
                "09029090868",
                bill.getTrackingCode(),
                PersianDateUtil.getPersianDate(bill.getOrderDate().getTime())

                );

        niazPardazandehSendSmsUtil.sendNewProviderBill(
                "بهتا‌تیویه‌قشم",
                "09105501184",
                bill.getTrackingCode(),
                PersianDateUtil.getPersianDate(bill.getOrderDate().getTime())
        );
    }

    private void sendProviderBillSms(BillEntity bill) {

        List<BillProductProviderEntity> orders =
                billProductProviderRepository.findAllByBill_IdAndDeletedIsFalse(bill.getId());

        List<ProviderEntity> providers =
                new ArrayList<>(orders.stream().collect(Collectors.groupingBy(g ->
//                        g.getOrder().getSpecialProduct() == null ?
                                g.getProductProvider().getProvider()//                                : g.getOrder().getSpecialProduct().getAcceptedProvider()
                        )
                ).keySet());

        for (ProviderEntity provider : providers) {
            if(!StringUtil.isNullOrEmpty(provider.getMobile())){
                niazPardazandehSendSmsUtil.sendNewProviderBill(
                        provider.getName(),
                        provider.getMobile(),
                        bill.getTrackingCode(),
                        PersianDateUtil.getPersianDate(bill.getOrderDate().getTime())
                );
//                com.behrouz.server.kavenegar.SmsSender.providerBillAccepted(
//                        provider.getMobile(),
//                        provider.getName(),
//                        bill.getTrackingCode(),
//                        PersianDateUtil.getPersianDate(bill.getOrderDate().getTime())
//                );
            }
        }
    }

    private void sendBillCustomerSms(BillEntity bill) {
        niazPardazandehSendSmsUtil.sendTransactionCompeleteSms(
                bill.getCustomer().getFirstName() + " " + bill.getCustomer().getLastName(),
                bill.getCustomer().getMobile(),
                bill.getTrackingCode()
        );
//        SmsSender.customerBillAccepted(
//                bill.getCustomer().getMobile(),
//                bill.getCustomer().getFirstName(),
//                bill.getTrackingCode()
//        );
    }


    public String createLinkForFactor(BillEntity newBill, long shouldPayWithBank) throws BehtaShopException {

        assert newBill != null;
        SamanTokenRestResponse res = new SamanTokenRestResponse();
        try {
            res = samanComponent.requestToSamanBankForCreateToken(newBill, shouldPayWithBank);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return res.getToken();

    }
}
