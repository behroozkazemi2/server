package com.behrouz.server.model.melliCharge;//package com.behrouz.server.model.melliCharge;
//
//import com.behrouz.server.model.bill.BillChargeEntity;
//import com.behrouz.server.rest.paymentMelli.MelliPaymentResponse;
//import org.hibernate.annotations.CreationTimestamp;
//
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Date;
//
///**
// * Created by Hapi KZM
// * Package com.behrouz.server.model
// * Project xima
// * 13 September 2018 09:29
// **/
//
//@Entity
//@Table(name = "melli_charge_response", schema = "public")
//public class MelliChargeResponseEntity implements Serializable {
//
//    private int id;
//    private int responseCode;
//    private String token;
//    private String description;
//    private Date insertDate;
//
//    private BillChargeEntity billCharge;
//
//    public MelliChargeResponseEntity ( BillChargeEntity billCharge, MelliPaymentResponse bandResponse ) {
//        this.responseCode = bandResponse.getResCode();
//        this.token        = bandResponse.getToken();
//        this.description  = bandResponse.getDescription();
//        this.billCharge   = billCharge;
//    }
//
//    public MelliChargeResponseEntity ( BillChargeEntity billCharge, String description ) {
//        this.description = description;
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
//    public String getToken() {
//        return token;
//    }
//    public void setToken( String token ) {
//        this.token = token;
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
//    public MelliChargeResponseEntity () {
//    }
//}
