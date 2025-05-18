package com.behrouz.server.utils;

import java.util.Collection;

/**
 * created by: Hapi
 **/


public class ArraysUtil {

    public static boolean isNullOrEmpty(Collection< ? > array) {
        return array == null || array.isEmpty();
    }
}
