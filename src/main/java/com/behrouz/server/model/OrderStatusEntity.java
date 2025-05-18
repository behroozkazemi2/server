package com.behrouz.server.model;

import com.behrouz.server.model.base.BaseIdNameEntity;

import javax.persistence.*;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.model
 * Project xima
 * 12 September 2018 14:07
 **/

@Entity
@Table(name = "order_status", schema = "public")
public class OrderStatusEntity extends BaseIdNameEntity {

    public OrderStatusEntity() {
    }
}
