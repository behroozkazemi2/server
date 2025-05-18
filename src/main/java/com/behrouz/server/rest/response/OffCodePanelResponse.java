package com.behrouz.server.rest.response;

import com.behrouz.server.model.OffCodeEntity;
import com.behrouz.server.utils.LocalDateTimeUtil;
import com.behrouz.server.utils.date.PersianDateUtil;

import java.util.Date;
import java.util.List;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.rest.response
 * Project Name: behta-server
 * 27 March 2019
 **/
public class OffCodePanelResponse {

    private long id;
    private String code;
    private boolean forAll;
    private Date insertDate;
    private Date startDate;
    private Date expireDate;
    private String start;
    private String expire;
    private float percent;
    private float minAmount;
    private float maxAmount;
    private String description;
    private long maxUsageCount;
    private boolean deleted;

    private boolean forCustomer;
    private String customerFirstName;
    private String customerLastName;
    private String customerMobile;

    private boolean forProvider;
    private String providerName;
    private long providerImageId;

    private boolean forProductProvider;
    private String productName;
    private long productProviderImageId;

    private List<Integer> customerIds;
    private List<Integer> productProviderIds;

    public OffCodePanelResponse() {
    }

    public OffCodePanelResponse (OffCodeEntity e ) {

        this.id = e.getId();
        this.code = e.getCode();
        this.forAll = e.isForAll();
        this.insertDate = LocalDateTimeUtil.localDateTimeToDate(e.getInsertDate());
        this.startDate = e.getStartDate();
        this.expireDate = e.getExpireDate();
        this.start = (e.getStartDate() != null)? PersianDateUtil.getPersianDate(startDate.getTime()) :"";
        this.expire = (e.getExpireDate() != null)? PersianDateUtil.getPersianDate(expireDate.getTime()) :"";

        this.percent = e.getPercent();
        this.minAmount = e.getMinAmount();
        this.maxAmount = e.getMaxAmount();
        this.description = e.getDescription();
        this.maxUsageCount = e.getUsageCount();
        this.deleted = e.isDeleted();

        if ( e.getCustomer() != null ){
            this.forCustomer = true;
            this.customerFirstName = e.getCustomer().getFirstName();
            this.customerLastName = e.getCustomer().getLastName();
            this.customerMobile = e.getCustomer().getMobile();
        }

        if ( e.getProvider() != null ) {
            this.forProvider = true;
            this.providerName = e.getProvider().getName();
//            this.providerImageId =
//                    providerImages == null || providerImages.isEmpty()
//                            ? 0
//                            : providerImages.get( 0 ).getImage().getId();
        }

        if ( e.getProductProvider() != null ){
            this.forProductProvider = true;
            this.productName = e.getProductProvider().getProduct().getName();
//            this.productProviderImageId =
//                    productProviderImages == null || productProviderImages.isEmpty()
//                            ? 0
//                            : productProviderImages.get( 0 ).getImage().getId();
        }

    }


    public long getId () {
        return id;
    }
    public void setId ( long id ) {
        this.id = id;
    }

    public String getCode () {
        return code;
    }
    public void setCode ( String code ) {
        this.code = code;
    }

    public boolean isForAll () {
        return forAll;
    }
    public void setForAll ( boolean forAll ) {
        this.forAll = forAll;
    }

    public Date getInsertDate () {
        return insertDate;
    }
    public void setInsertDate ( Date insertDate ) {
        this.insertDate = insertDate;
    }

    public Date getStartDate () {
        return startDate;
    }
    public void setStartDate ( Date startDate ) {
        this.startDate = startDate;
    }

    public Date getExpireDate () {
        return expireDate;
    }
    public void setExpireDate ( Date expireDate ) {
        this.expireDate = expireDate;
    }

    public float getPercent () {
        return percent;
    }
    public void setPercent ( float percent ) {
        this.percent = percent;
    }

    public float getMinAmount () {
        return minAmount;
    }
    public void setMinAmount ( float minAmount ) {
        this.minAmount = minAmount;
    }

    public float getMaxAmount () {
        return maxAmount;
    }
    public void setMaxAmount ( float maxAmount ) {
        this.maxAmount = maxAmount;
    }

    public String getDescription () {
        return description;
    }
    public void setDescription ( String description ) {
        this.description = description;
    }

    public long getMaxUsageCount () {
        return maxUsageCount;
    }
    public void setMaxUsageCount ( long maxUsageCount ) {
        this.maxUsageCount = maxUsageCount;
    }

    public boolean isDeleted () {
        return deleted;
    }
    public void setDeleted ( boolean deleted ) {
        this.deleted = deleted;
    }

    public boolean isForCustomer () {
        return forCustomer;
    }
    public void setForCustomer ( boolean forCustomer ) {
        this.forCustomer = forCustomer;
    }

    public String getCustomerFirstName () {
        return customerFirstName;
    }
    public void setCustomerFirstName ( String customerFirstName ) {
        this.customerFirstName = customerFirstName;
    }

    public String getCustomerLastName () {
        return customerLastName;
    }
    public void setCustomerLastName ( String customerLastName ) {
        this.customerLastName = customerLastName;
    }

    public String getCustomerMobile () {
        return customerMobile;
    }
    public void setCustomerMobile ( String customerMobile ) {
        this.customerMobile = customerMobile;
    }

    public boolean isForProvider () {
        return forProvider;
    }
    public void setForProvider ( boolean forProvider ) {
        this.forProvider = forProvider;
    }

    public String getProviderName () {
        return providerName;
    }
    public void setProviderName ( String providerName ) {
        this.providerName = providerName;
    }

    public long getProviderImageId () {
        return providerImageId;
    }
    public void setProviderImageId ( long providerImageId ) {
        this.providerImageId = providerImageId;
    }

    public boolean isForProductProvider () {
        return forProductProvider;
    }
    public void setForProductProvider ( boolean forProductProvider ) {
        this.forProductProvider = forProductProvider;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getProductProviderImageId () {
        return productProviderImageId;
    }
    public void setProductProviderImageId ( long productProviderImageId ) {
        this.productProviderImageId = productProviderImageId;
    }

    public String getStart() {
        return start;
    }
    public void setStart(String start) {
        this.start = start;



    }


    public String getExpire() {
        return expire;
    }
    public void setExpire(String expire) {
        this.expire = expire;
    }


    public List<Integer> getCustomerIds() {
        return customerIds;
    }
    public void setCustomerIds(List<Integer> customerIds) {
        this.customerIds = customerIds;
    }


    public List<Integer> getProductProviderIds() {
        return productProviderIds;
    }
    public void setProductProviderIds(List<Integer> productProviderIds) {
        this.productProviderIds = productProviderIds;
    }
}