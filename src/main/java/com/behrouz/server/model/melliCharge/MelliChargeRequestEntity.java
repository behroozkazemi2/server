package com.behrouz.server.model.melliCharge;//package com.behrouz.server.model.melliCharge;
//
//import com.behrouz.server.model.bill.BillChargeEntity;
//import com.behrouz.server.rest.paymentMelli.MelliPaymentRequest;
//import org.hibernate.annotations.CreationTimestamp;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Date;
//
///**
// * Created by Hapi KZM
// * Package com.behrouz.server.model
// * Project xima
// * 13 September 2018 09:22
// **/
//
//@Entity
//@Table(name = "melli_charge_request", schema = "public")
//public class MelliChargeRequestEntity implements Serializable {
//
//    private int id;
//    private String merchantId;
//    private String terminalId;
//    private double amount;
//    private Date insertDate;
//    private String returnUrl;
//    private String signData;
//    private String additionalData;
//    private long orderId;
//
//    private BillChargeEntity billCharge;
//
//    public MelliChargeRequestEntity ( BillChargeEntity billCharge, MelliPaymentRequest request ) {
//
//        this.merchantId = request.getMerchantId();
//        this.terminalId = request.getTerminalId();;
//        this.amount = request.getAmount(); // TODO CHECK THIS LATER
//        this.returnUrl = request.getReturnUrl();
//
//        if ( billCharge != null ){
//
//            this.billCharge = billCharge;
//
//            this.orderId = billCharge.getId();
//        }
//
//    }
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    public int getId() {
//        return id;
//    }
//    public void setId( int id ) {
//        this.id = id;
//    }
//
//
//    @Basic
//    public String getMerchantId() {
//        return merchantId;
//    }
//    public void setMerchantId( String merchantId ) {
//        this.merchantId = merchantId;
//    }
//
//
//    @Basic
//    public String getTerminalId() {
//        return terminalId;
//    }
//    public void setTerminalId( String terminalId ) {
//        this.terminalId = terminalId;
//    }
//
//
//    @Basic
//    public double getAmount() {
//        return amount;
//    }
//    public void setAmount( double amount ) {
//        this.amount = amount;
//    }
//
//
//    @Basic
//    @CreationTimestamp
//    @Temporal( TemporalType.TIMESTAMP )
//    public Date getInsertDate() {
//        return insertDate;
//    }
//    public void setInsertDate( Date insertDate ) {
//        this.insertDate = insertDate;
//    }
//
//
//    @Basic
//    public String getReturnUrl() {
//        return returnUrl;
//    }
//    public void setReturnUrl( String returnUrl ) {
//        this.returnUrl = returnUrl;
//    }
//
//
//    @Basic
//    public String getSignData() {
//        return signData;
//    }
//    public void setSignData( String signData ) {
//        this.signData = signData;
//    }
//
//
//    @Basic
//    public String getAdditionalData() {
//        return additionalData;
//    }
//    public void setAdditionalData( String additionalData ) {
//        this.additionalData = additionalData;
//    }
//
//    @Basic
//    public long getOrderId () {
//        return orderId;
//    }
//    public void setOrderId ( long orderId ) {
//        this.orderId = orderId;
//    }
//
//
//    @OneToOne
//    @JoinColumn(name = "bill_charge_id", nullable = false)
//    public BillChargeEntity getBillCharge() {
//        return billCharge;
//    }
//    public void setBillCharge( BillChargeEntity billCharge ) {
//        this.billCharge = billCharge;
//    }
//
//    public MelliChargeRequestEntity (  ) {
//    }
//}
