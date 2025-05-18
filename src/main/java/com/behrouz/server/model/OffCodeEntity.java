package com.behrouz.server.model;

import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.account.ProviderEntity;
import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.model.bill.BillEntity;
import com.behrouz.server.model.product.ProductProviderEntity;
import com.behrouz.server.rest.request.DiscountRequest;
import com.behrouz.server.rest.response.OffCodePanelResponse;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.behrouz.server.strategy.StrategyGenerator;
import com.behrouz.server.utils.StringUtil;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Set;

/**
 * Created by Hapi KZM
 **/

@Entity
@Table(name = "off_code", schema = "public")
public class OffCodeEntity extends BaseEntity {

    private String code;
    private boolean forAll;
    private Date startDate;
    private Date expireDate;
    private float percent;
    private float minAmount;
    private float maxAmount;
    private String description;
    private long usageCount;

    private CustomerEntity customer;
    private ProviderEntity provider;
    private ProductProviderEntity productProvider;

    private Set<BillEntity> bills;


    public OffCodeEntity ( float percent, ProductProviderEntity productProvider ) {
        this.startDate = createStartDate();
        this.expireDate = createExpireDate();

        this.percent = percent;

        this.description = "Added By PathVariable";
        this.productProvider = productProvider;
    }

    public OffCodeEntity(DiscountRequest discountRequest, ProductProviderEntity productProvider) {
        this.startDate = discountRequest.getStartDate();
        this.expireDate = discountRequest.getExpireDate();

        this.percent = discountRequest.getDiscountPercent();
        this.productProvider = productProvider;
    }


    public OffCodeEntity ( float percent, ProviderEntity provider ) {
        this.startDate = createStartDate();
        this.expireDate = createExpireDate();

        this.percent = percent;

        this.description = "Added By PathVariable";
        this.provider = provider;
    }

    public OffCodeEntity ( float percent, CustomerEntity customer ) {
        this.startDate = createStartDate();
        this.expireDate = createExpireDate();

        this.percent = percent;

        this.description = "Added By PathVariable";
        this.customer = customer;
    }

    public OffCodeEntity(CustomerEntity customer, OffCodePanelResponse offCode) {
        this.code = offCode.getCode();
        this.usageCount = offCode.getMaxUsageCount();
        this.percent = offCode.getPercent();
        this.maxAmount = offCode.getMaxAmount();
        this.startDate = offCode.getStartDate();
        this.expireDate = offCode.getExpireDate();
        this.customer = customer;
        this.description = offCode.getDescription();
    }

    public OffCodeEntity( OffCodePanelResponse offCode) {
        this.code = offCode.getCode();
        this.usageCount = offCode.getMaxUsageCount();
        this.percent = offCode.getPercent();
        this.maxAmount = offCode.getMaxAmount();
        this.startDate = offCode.getStartDate();
        this.expireDate = offCode.getExpireDate();
        this.description = offCode.getDescription();
    }

    public OffCodeEntity(ProductProviderEntity productProvider, OffCodePanelResponse offCode) {
        this.code = offCode.getCode();
        this.usageCount = offCode.getMaxUsageCount();
        this.percent = offCode.getPercent();
        this.maxAmount = offCode.getMaxAmount();
        this.startDate = offCode.getStartDate();
        this.expireDate = offCode.getExpireDate();
        this.productProvider = productProvider;
        this.description = offCode.getDescription();

    }

    public OffCodeEntity ( ProviderEntity provider, OffCodePanelResponse offCode ) {
        this.provider = provider;
        this.code = offCode.getCode();
        this.usageCount = offCode.getMaxUsageCount();
        this.percent = offCode.getPercent();
        this.maxAmount = offCode.getMaxAmount();
        this.startDate = offCode.getStartDate();
        this.expireDate = offCode.getExpireDate();
        this.description = offCode.getDescription();
    }


    private Date createExpireDate () {
        Calendar calendar = Calendar.getInstance();
        calendar.add( Calendar.DAY_OF_MONTH, 2 );
        return calendar.getTime();
    }

    private Date createStartDate () {
        Calendar calendar = Calendar.getInstance();
        calendar.add( Calendar.MINUTE, 1 );
        return calendar.getTime();
    }


    @Basic
    public String getCode() {
        return code;
    }
    public void setCode( String code ) {
        this.code = code;
    }


    @Basic
    public boolean isForAll() {
        return forAll;
    }
    public void setForAll( boolean forAll ) {
        this.forAll = forAll;
    }


    @Basic
    @Temporal( TemporalType.TIMESTAMP )
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate( Date startDate ) {
        this.startDate = startDate;
    }


    @Basic
    @Temporal( TemporalType.TIMESTAMP )
    public Date getExpireDate() {
        return expireDate;
    }
    public void setExpireDate( Date expireDate ) {
        this.expireDate = expireDate;
    }


    @Basic
    public float getPercent() {
        return percent;
    }
    public void setPercent( float percent ) {
        this.percent = percent;
    }


    @Basic
    public float getMinAmount() {
        return minAmount;
    }
    public void setMinAmount( float minAmount ) {
        this.minAmount = minAmount;
    }


    @Basic
    public float getMaxAmount() {
        return maxAmount;
    }
    public void setMaxAmount( float maxAmount ) {
        this.maxAmount = maxAmount;
    }


    @Basic
    public String getDescription () {
        return description;
    }
    public void setDescription ( String description ) {
        this.description = description;
    }


    @Basic
    public long getUsageCount () {
        return usageCount;
    }
    public void setUsageCount ( long usageCount ) {
        this.usageCount = usageCount;
    }



    @ManyToOne
    @JoinColumn(name = "customer_id")
    public CustomerEntity getCustomer () {
        return customer;
    }
    public void setCustomer ( CustomerEntity customer ) {
        this.customer = customer;
    }


    @ManyToOne
    @JoinColumn(name = "provider_id")
    public ProviderEntity getProvider () {
        return provider;
    }
    public void setProvider ( ProviderEntity provider ) {
        this.provider = provider;
    }


    @ManyToOne
    @JoinColumn(name = "product_provider_id")
    public ProductProviderEntity getProductProvider () {
        return productProvider;
    }
    public void setProductProvider ( ProductProviderEntity productProvider ) {
        this.productProvider = productProvider;
    }


    @OneToMany(mappedBy = "offCode")
    @JsonBackReference
    public Set <BillEntity> getBills () {
        return bills;
    }
    public void setBills ( Set < BillEntity > bills ) {
        this.bills = bills;
    }


    public OffCodeEntity() {
    }


    @PrePersist
    void prePersist() {
        if ( StringUtil.isNullOrEmpty( this.code ) ) {
            this.code = StrategyGenerator.generateOffCode();
        }
    }

}
