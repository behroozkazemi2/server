package com.behrouz.server.model;

import com.behrouz.server.api.customer.request.CommentRequest;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.base.BaseEntity;
import com.behrouz.server.model.product.ProductProviderEntity;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Hapi KZM
 * Edited by HapiJ
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 11:47
 **/

@Entity
@Table(name = "comment", schema = "public")
public class CommentEntity extends BaseEntity {

    private String text; // original text
    private String editedText; // edit text by panel user
    private float rate;
    private float commentOrder;
    private Date changeStatusDate;
    private String email;
    private String commenter;
    private CustomerEntity customer;
    private ProductProviderEntity productProvider;
    private CommentStatusEntity status;


    public CommentEntity ( String text,
                           CustomerEntity customer,
                           ProductProviderEntity productProvider,
                           CommentStatusEntity status ) {

        this.text = text;

        this.customer = customer;

        this.productProvider = productProvider;

        this.status = status;

    }

    public CommentEntity(CustomerEntity customer,CommentStatusEntity commentStatusEntity, CommentRequest request) {
        this.text = request.getText();
        this.editedText = request.getText();
        this.customer = customer;
        this.status = commentStatusEntity;
        this.rate = request.getRate();
        this.commenter = request.getCommenter();
        this.email = request.getEmail();
    }

    @Basic
    public String getText() {
        return text;
    }
    public void setText( String text ) {
        this.text = text;
    }


    @Basic
    public float getRate() {
        return rate;
    }
    public void setRate( float rate ) {
        this.rate = rate;
    }

    @Basic
    public float getCommentOrder() {
        return commentOrder;
    }
    public void setCommentOrder( float order ) {
        this.commentOrder = order;
    }


    @Basic
    @CreationTimestamp
    @Temporal( TemporalType.TIMESTAMP )
    public Date getChangeStatusDate () {
        return changeStatusDate;
    }
    public void setChangeStatusDate ( Date changeStatusDate ) {
        this.changeStatusDate = changeStatusDate;
    }


    @ManyToOne
    @JoinColumn(name = "customer_id")
    public CustomerEntity getCustomer() {
        return customer;
    }
    public void setCustomer( CustomerEntity customer ) {
        this.customer = customer;
    }


    @ManyToOne
    @JoinColumn(name = "product_provider_id")
    public ProductProviderEntity getProductProvider() {
        return productProvider;
    }
    public void setProductProvider( ProductProviderEntity productProvider ) {
        this.productProvider = productProvider;
    }


    @ManyToOne
    @JoinColumn(name = "status_id", nullable = false)
    public CommentStatusEntity getStatus () {
        return status;
    }
    public void setStatus ( CommentStatusEntity status ) {
        this.status = status;
    }


    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getCommenter() {
        return commenter;
    }

    public void setCommenter(String commenter) {
        this.commenter = commenter;
    }

    public String getEditedText() {
        return editedText;
    }
    public void setEditedText(String editedText) {
        this.editedText = editedText;
    }

    public CommentEntity() {
    }
}
