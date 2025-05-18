package com.behrouz.server.modelOption;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MyEnumConverter implements AttributeConverter<PaymentMethodOption, Integer> {

    @Override
    public Integer convertToDatabaseColumn(PaymentMethodOption myEnum) {
        return myEnum == null ? null : myEnum.getId();
    }

    @Override
    public PaymentMethodOption convertToEntityAttribute(Integer id) {
        return id == null ? null : PaymentMethodOption.getById(id);
    }
}