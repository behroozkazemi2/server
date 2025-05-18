package com.behrouz.server.model.melliCharge;//package com.behrouz.server.model.melliCharge;
//
//import com.behrouz.server.model.bill.BillChargeEntity;
//import com.behrouz.server.rest.paymentMelli.MelliVerifyResponse;
//import org.hibernate.annotations.CreationTimestamp;
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Date;
//
///**
// * Created by Hapi KZM
// * Package com.behrouz.server.model
// * Project xima
// * 13 September 2018 09:41
// **/
//
//@Entity
//@Table(name = "melli_charge_verify_response", schema = "public")
//public class MelliChargeVerifyResponseEntity implements Serializable {
//
//    private int id;
//    private int responseCode;
//    private float amount;
//    private String description;
//    private String retrivalRefNo;
//    private String systemTraceNo;
//    private int orderId;
//    private Date insertDate;
//
//    private BillChargeEntity billCharge;
//
//    public MelliChargeVerifyResponseEntity ( BillChargeEntity billCharge,
//                                             String description ) {
//
//        this.billCharge = billCharge;
//        this.description = description;
//
//    }
//
//    public MelliChargeVerifyResponseEntity ( BillChargeEntity billCharge,
//                                             MelliVerifyResponse melliVerifyResponse ) {
//
//        this.responseCode = (int) melliVerifyResponse.getResCode();
//        this.amount = melliVerifyResponse.getAmount();
//        this.description = melliVerifyResponse.getDescription();
//        this.retrivalRefNo = melliVerifyResponse.getRetrivalRefNo();
//        this.systemTraceNo = melliVerifyResponse.getSystemTraceNo();
//
//        this.orderId = (int) melliVerifyResponse.getOrderId(); //TODO Check this later
//
//        this.billCharge = billCharge;
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
//    public int getResponseCode() {
//        return responseCode;
//    }
//    public void setResponseCode( int responseCode ) {
//        this.responseCode = responseCode;
//    }
//
//
//    @Basic
//    public float getAmount() {
//        return amount;
//    }
//    public void setAmount( float amount ) {
//        this.amount = amount;
//    }
//
//
//    @Basic
//    public String getDescription() {
//        return description;
//    }
//    public void setDescription( String description ) {
//        this.description = description;
//    }
//
//
//    @Basic
//    public String getRetrivalRefNo() {
//        return retrivalRefNo;
//    }
//    public void setRetrivalRefNo( String retrivalRefNo ) {
//        this.retrivalRefNo = retrivalRefNo;
//    }
//
//
//    @Basic
//    public String getSystemTraceNo() {
//        return systemTraceNo;
//    }
//    public void setSystemTraceNo( String systemTraceNo ) {
//        this.systemTraceNo = systemTraceNo;
//    }
//
//
//    @Basic
//    public int getOrderId() {
//        return orderId;
//    }
//    public void setOrderId( int orderId ) {
//        this.orderId = orderId;
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
//    @OneToOne
//    @JoinColumn(name = "bill_charge_id", nullable = false)
//    public BillChargeEntity getBillCharge() {
//        return billCharge;
//    }
//    public void setBillCharge( BillChargeEntity billCharge ) {
//        this.billCharge = billCharge;
//    }
//
//    public MelliChargeVerifyResponseEntity () {
//    }
//}
