package com.behrouz.server.model.bill;

import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.model.CandidateTimeEntity;
import com.behrouz.server.model.OffCodeEntity;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.global.AddressEntity;
import com.behrouz.server.modelOption.MyEnumConverter;
import com.behrouz.server.modelOption.PaymentMethodOption;
import com.behrouz.server.strategy.StrategyGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 15:19
 **/

/**
 * Sefaresh
 *
 * When a user has some order and click on "Sefarsh" we generate a BillEntity
 *
 * When a Bill is ready to send to customer, we make another Bill that is modified version of previous Bill
 *
 */
@Entity
@Table(name = "bill", schema = "public")
public class BillEntity extends BaseEntity {


    private CustomerEntity customer;

    private long realAmount; // Real Value of products
    private long discountAmount;
    private long offCodeAmount;
    private long distanceAmount;
    private long payableAmount; // Value that user should pay
    private long taxAmount;

    @Convert(converter = MyEnumConverter.class)
    private PaymentMethodOption paymentMethod;

    private AddressEntity address;
    private String  description;

    private OffCodeEntity offCode;

    private Date orderDate;

    private CandidateTimeEntity orderTime;

    private String trackingCode;

    @OneToMany(mappedBy = "bill")
    private Set< BillBillStatusEntity > billBillStatus;


    @OneToMany(mappedBy = "bill")
    private Set<BillProductProviderEntity> order;




    public BillEntity() {
    }


    public BillEntity(
            CustomerEntity customer,
            long realAmount,
            long discountAmount,
            long offCodeAmount,
            long distanceAmount,
            long taxAmount,
            long payableAmount,
            AddressEntity address,
            OffCodeEntity offCode,
            Date orderDate,
            CandidateTimeEntity orderTime,
            PaymentMethodOption paymentMethod
    ) {
        this.customer = customer;
        this.realAmount = realAmount;
        this.discountAmount = discountAmount;
        this.offCodeAmount = offCodeAmount;
        this.distanceAmount = distanceAmount;
        this.taxAmount = taxAmount;
        this.address = address;
        this.offCode = offCode;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.payableAmount = payableAmount;

        // TODO CHECK THIS WHY SET 1 always
        this.paymentMethod = paymentMethod;
    }


    public void toToman(){
        this.realAmount /= 10;
        this.discountAmount /= 10;
        this.offCodeAmount /= 10;
        this.distanceAmount /= 10;
        this.payableAmount /= 10;
        this.taxAmount /= 10;
    }


    @Basic
    public String getTrackingCode() {
        return trackingCode;
    }
    public void setTrackingCode( String trackingCode ) {
        this.trackingCode = trackingCode;
    }

    public long getRealAmount () {
        return realAmount;
    }
    public void setRealAmount ( long realAmount ) {
        this.realAmount = realAmount;
    }


    public long getDiscountAmount () {
        return discountAmount;
    }
    public void setDiscountAmount ( long discountAmount ) {
        this.discountAmount = discountAmount;
    }


    public long getOffCodeAmount () {
        return offCodeAmount;
    }
    public void setOffCodeAmount ( long offCodeAmount ) {
        this.offCodeAmount = offCodeAmount;
    }


    public long getDistanceAmount () {
        return distanceAmount;
    }
    public void setDistanceAmount ( long distanceAmount ) {
        this.distanceAmount = distanceAmount;
    }


    public long getPayableAmount () {
        return payableAmount;
    }
    public void setPayableAmount ( long payableAmount ) {
        this.payableAmount = payableAmount;
    }


    public String getDescription () {
        return description;
    }
    public void setDescription ( String description ) {
        this.description = description;
    }



    @Column(columnDefinition = "bigint default 0")
    public long getTaxAmount () {
        return taxAmount;
    }
    public void setTaxAmount ( long taxAmount ) {
        this.taxAmount = taxAmount;
    }



    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    public CustomerEntity getCustomer() {
        return customer;
    }
    public void setCustomer( CustomerEntity customer ) {
        this.customer = customer;
    }




    @ManyToOne
    @JoinColumn(name = "off_code_id")
    public OffCodeEntity getOffCode () {
        return offCode;
    }
    public void setOffCode ( OffCodeEntity offCode ) {
        this.offCode = offCode;
    }


    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    public AddressEntity getAddress () {
        return address;
    }
    public void setAddress ( AddressEntity address ) {
        this.address = address;
    }



    @Basic
    public Date getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }


    @ManyToOne
    @JoinColumn(name = "order_time_id", nullable = false)
    public CandidateTimeEntity getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(CandidateTimeEntity orderTime) {
        this.orderTime = orderTime;
    }

    public PaymentMethodOption getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(PaymentMethodOption paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    @PrePersist
    void prePersist() {
        this.trackingCode = StrategyGenerator.generateBillTrackingCode( customer.getId() );
    }


}
