package com.behrouz.server.model.account;

import com.behrouz.server.model.bill.BillEntity;
import com.behrouz.server.api.customer.request.RegisterRequest;
import com.behrouz.server.api.customer.response.ProfileRequestResponse;
import com.behrouz.server.model.CallSupportEntity;
import com.behrouz.server.model.CommentEntity;
import com.behrouz.server.model.MessageEntity;
import com.behrouz.server.model.global.ImageEntity;
import com.behrouz.server.model.order.CustomerOrderEntity;
import com.behrouz.server.model.product.ProductViewEntity;
import com.behrouz.server.strategy.StrategyGenerator;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 10:30
 **/

@Entity
@Inheritance
public class CustomerEntity extends AccountEntity{

    private boolean man;

    private String firstName;

    private String lastName;
//    private String mobile;

    private String invitingCode;

    private String nationalCode;

    private CustomerEntity invitedBy; // az taraf ye nafar davat shode

    private ImageEntity avatar;


    @OneToMany(mappedBy = "invitedBy")
    private Set< CustomerEntity > inviting; // mitune ye seri adamo davat kone


    @OneToMany(mappedBy = "customer")
    private Set<CustomerOrderEntity> customerOrder;


    @OneToMany(mappedBy = "customer")
    private Set<ProductViewEntity> productViews;

    @OneToMany(mappedBy = "customer")
    private Set<CommentEntity> comments;

    @OneToMany(mappedBy = "customer")
    private Set<BillEntity> bills;


    @OneToMany(mappedBy = "customer")
    private Set<CallSupportEntity> callSupports;

    @OneToMany(mappedBy = "customer")
    private Set<MessageEntity> messages;


    public CustomerEntity() {
    }

    public CustomerEntity( String mobile ) {
        this.mobile = mobile;
    }

    public CustomerEntity(RegisterRequest registerRequest  ) {
        this.firstName = registerRequest.getFirstName();
        this.lastName = registerRequest.getLastName();
        this.mobile = registerRequest.getMobile();
        this.man = registerRequest.isGenderMen();
    }

    public CustomerEntity(RegisterRequest registerRequest, CustomerEntity invitedBy  ) {
        this.firstName = registerRequest.getFirstName();
        this.lastName = registerRequest.getLastName();
        this.mobile = registerRequest.getMobile();
        this.nationalCode = registerRequest.getNationalCode();
        this.man = registerRequest.isGenderMen();
        this.invitedBy = invitedBy;
    }


    public boolean isMan() {
        return man;
    }
    public void setMan(boolean man) {
        this.man = man;
    }

    @Basic
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }


    @Basic
    public String getLastName() {
        return lastName;
    }
    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    @Basic
    public String getInvitingCode() {
        return invitingCode;
    }
    public void setInvitingCode( String invitingCode ) {
        this.invitingCode = invitingCode;
    }



    @ManyToOne
    @JoinColumn(name = "inviter_id")
    public CustomerEntity getInvitedBy() {
        return invitedBy;
    }
    public void setInvitedBy( CustomerEntity invitedBy ) {
        this.invitedBy = invitedBy;
    }


    @ManyToOne
    @JoinColumn(name = "avatar_id")
    public ImageEntity getAvatar() {
        return avatar;
    }
    public void setAvatar( ImageEntity avatar ) {
        this.avatar = avatar;
    }



    @PrePersist
    void prePersist() {
        super.prePersist();
        this.invitingCode = StrategyGenerator.generateInvitingCode(this.mobile);
    }


    public String getNationalCode() {
        return nationalCode;
    }
    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public void update (ProfileRequestResponse request ) {
        this.firstName = request.getFirstName();
        this.lastName = request.getLastName();
    }
}
