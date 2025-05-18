package com.behrouz.server.utils.thymeleaf;

import com.behrouz.server.utils.date.PersianDate;
import com.behrouz.server.utils.date.PersianDateUtil;

import java.util.Date;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.utils.thymeleaf
 * Project Koala Server
 * 09 September 2018 13:19
 **/
public interface ThymeleafPersianDateModel {

    default String dateToPersianString(Date date){
        return PersianDateUtil.getPersianDateString(date);
    }

    default String dateToPersianDate(Date date){
        return String.valueOf(PersianDateUtil.getPersianDate(date));
    }

    default PersianDate getPersianDate(Date date){
        return PersianDateUtil.getPersianDate(date);
    }

}

