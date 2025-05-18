package com.behrouz.server.model.melliCharge;//package com.behrouz.server.model.melliCharge;
//
//
//import com.behrouz.server.model.bill.BillChargeEntity;
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
// * 13 September 2018 09:32
// **/
//
//@Entity
//@Table(name = "melli_charge_callback", schema = "public")
//public class MelliChargeCallbackEntity implements Serializable {
//
//    private int id;
//    private int orderId; // BillChargeId
//    private int responseCode;
//    private String token;
//    private Date insertDate;
//
//    private BillChargeEntity billCharge;
//
//    public MelliChargeCallbackEntity ( String token, int resCode, int orderId ) {
//
//        this.orderId = orderId;
//        this.responseCode = resCode;
//        this.token = token;
//
////        this.billCharge
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
//    public int getOrderId() {
//        return orderId;
//    }
//    public void setOrderId( int orderId ) {
//        this.orderId = orderId;
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
//    public String getToken() {
//        return token;
//    }
//    public void setToken( String token ) {
//        this.token = token;
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
//    public MelliChargeCallbackEntity () {
//    }
//}
