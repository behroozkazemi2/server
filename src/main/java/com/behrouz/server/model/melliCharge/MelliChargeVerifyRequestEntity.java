package com.behrouz.server.model.melliCharge;//package com.behrouz.server.model.melliCharge;
//
//import com.behrouz.server.model.bill.BillChargeEntity;
//import com.behrouz.server.rest.paymentMelli.MelliVerifyRequest;
//import org.hibernate.annotations.CreationTimestamp;
//import javax.persistence.*;
//import java.util.Date;
//
///**
// * Created by Hapi KZM
// * Package com.behrouz.server.model
// * Project xima
// * 13 September 2018 11:19
// **/
//
//@Entity
//@Table(name = "melli_charge_verify_request", schema = "public")
//public class MelliChargeVerifyRequestEntity {
//
//    private int id;
//    private String token;
//    private String signData;
//    private Date insertDate;
//
//    private BillChargeEntity billCharge;
//
//    public MelliChargeVerifyRequestEntity ( MelliVerifyRequest melliVerifyRequest,
//                                            BillChargeEntity billCharge ) {
//
//        this.token = melliVerifyRequest.getToken();
//        this.signData = melliVerifyRequest.getSignData();
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
//    public String getToken() {
//        return token;
//    }
//    public void setToken( String token ) {
//        this.token = token;
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
//    public MelliChargeVerifyRequestEntity () {
//    }
//}
