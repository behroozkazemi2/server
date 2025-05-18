package com.behrouz.server.api.customer.response;

import com.behrouz.server.api.customer.data.LatLngData;
import com.behrouz.server.model.bill.BillEntity;
import com.behrouz.server.model.bill.BillStatusEntity;
import com.behrouz.server.rest.constant.RequestDetailResponse;
import com.behrouz.server.utils.date.PersianDateUtil;
import com.behrouz.server.modelOption.BillStatusOption;
import com.behrouz.server.modelOption.PaymentMethodOption;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.api.customer.response
 * Project Name: behta-server
 * 13 January 2019
 **/
public class FactorDetailResponse {

    private List<FactorProductResponse> products;

    private List<RequestDetailResponse> steps;

    private long currentStepId;
    private PaymentMethodOption paymentMethod;

    private boolean lastPayActive;


    private long realAmount;

    private long discountAmount;

    private long offCodeAmount;

    private long distanceAmount;

    private long payableAmount;

    private long taxAmount;

    private String address;
    private LatLngData location;

    private String customerName;
    private String customerNumber;

//    private Date supposedToDeliverDate;
//
//    private RequestDetailResponse supposedToDeliverTime;
//
//    private Date deliveredDate;


    private String trackingCode;
    private String insertDate;
    private String offCodeCode;
    private String description;


    public FactorDetailResponse(List<BillStatusEntity> allStatus,
                                BillEntity bill,
                                long status,
                                List<FactorProductResponse> products) {

        this.realAmount = bill.getRealAmount();
        this.discountAmount = bill.getDiscountAmount();
        this.offCodeAmount = bill.getOffCodeAmount();
        this.distanceAmount = bill.getDistanceAmount();
        this.payableAmount = bill.getPayableAmount();
        this.taxAmount = bill.getTaxAmount();
        this.insertDate = PersianDateUtil.getPersianDate(bill.getOrderDate()) + " - " + bill.getOrderTime().getName();
        this.address = bill.getAddress().getAddress();
        this.description = bill.getDescription();
        this.products = products;
        this.paymentMethod = bill.getPaymentMethod() == null ? PaymentMethodOption.INTERNETI : bill.getPaymentMethod();

        this.steps =
                allStatus.stream()
                        .map(e -> {
                            String name = e.getName();
                            if (e.getId() == BillStatusOption.WAIT_FOR_PAY.getId() ) {
                                name = "پرداخت ناموفق";
                            }
                            return new RequestDetailResponse(e.getId(), name);
                        })
                        .collect(Collectors.toList());

        this.currentStepId = status;

        if (this.currentStepId >= BillStatusOption.PAYED.getId()) {
            this.lastPayActive = true;
        }
//
//        this.supposedToDeliverDate = bill.getOrderDate();
//        this.supposedToDeliverTime = new RequestDetailResponse(bill.getOrderTime().getId(),bill.getOrderTime().getName());
//        this.deliveredDate = bill.getStatus().getId() == BillStatusOption.FINISHED.getId() ? bill.getOrderDate() : null;

        this.trackingCode = bill.getTrackingCode();
        this.customerName = bill.getCustomer().getFirstName() + " " + bill.getCustomer().getLastName();
        this.customerNumber = bill.getCustomer().getMobile();
        this.location = new LatLngData(bill.getAddress());
        this.offCodeCode = bill.getOffCode() == null ? "-" : bill.getOffCode().getCode();

    }

    public FactorDetailResponse toToman() {
        this.realAmount /= 10;
        this.discountAmount /= 10;
        this.offCodeAmount /= 10;
        this.distanceAmount /= 10;
        this.payableAmount /= 10;
        this.taxAmount /= 10;

        System.out.println("realAmount " + realAmount);
        System.out.println("discountAmount " + discountAmount);
        System.out.println("offCodeAmount " + offCodeAmount);
        System.out.println("distanceAmount " + distanceAmount);
        System.out.println("payableAmount " + payableAmount);
        System.out.println("taxAmount " + taxAmount);
        return this;
    }


    public long getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(long taxAmount) {
        this.taxAmount = taxAmount;
    }


    public long getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(long realAmount) {
        this.realAmount = realAmount;
    }


    public long getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(long discountAmount) {
        this.discountAmount = discountAmount;
    }


    public long getOffCodeAmount() {
        return offCodeAmount;
    }

    public void setOffCodeAmount(long offCodeAmount) {
        this.offCodeAmount = offCodeAmount;
    }


    public long getDistanceAmount() {
        return distanceAmount;
    }

    public void setDistanceAmount(long distanceAmount) {
        this.distanceAmount = distanceAmount;
    }


    public long getPayableAmount() {
        return payableAmount;
    }

    public void setPayableAmount(long payableAmount) {
        this.payableAmount = payableAmount;
    }


    public List<FactorProductResponse> getProducts() {
        return products;
    }

    public void setProducts(List<FactorProductResponse> products) {
        this.products = products;
    }


    public List<RequestDetailResponse> getSteps() {
        return steps;
    }

    public void setSteps(List<RequestDetailResponse> steps) {
        this.steps = steps;
    }


    public long getCurrentStepId() {
        return currentStepId;
    }

    public void setCurrentStepId(long currentStepId) {
        this.currentStepId = currentStepId;
    }


    public String getTrackingCode() {
        return trackingCode;
    }

    public void setTrackingCode(String trackingCode) {
        this.trackingCode = trackingCode;
    }


    public boolean isLastPayActive() {
        return lastPayActive;
    }

    public void setLastPayActive(boolean lastPayActive) {
        this.lastPayActive = lastPayActive;
    }

    public String getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(String insertDate) {
        this.insertDate = insertDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public LatLngData getLocation() {
        return location;
    }

    public void setLocation(LatLngData location) {
        this.location = location;
    }


    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }


    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }


    public String getOffCodeCode() {
        return offCodeCode;
    }

    public void setOffCodeCode(String offCodeCode) {
        this.offCodeCode = offCodeCode;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PaymentMethodOption getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(PaymentMethodOption paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    //    public Date getSupposedToDeliverDate() {
//        return supposedToDeliverDate;
//    }
//    public void setSupposedToDeliverDate(Date supposedToDeliverDate) {
//        this.supposedToDeliverDate = supposedToDeliverDate;
//    }
//
//
//    public RequestDetailResponse getSupposedToDeliverTime() {
//        return supposedToDeliverTime;
//    }
//    public void setSupposedToDeliverTime(RequestDetailResponse supposedToDeliverTime) {
//        this.supposedToDeliverTime = supposedToDeliverTime;
//    }
//
//
//    public Date getDeliveredDate() {
//        return deliveredDate;
//    }
//    public void setDeliveredDate(Date deliveredDate) {
//        this.deliveredDate = deliveredDate;
//    }
}
