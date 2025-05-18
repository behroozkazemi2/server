package com.behrouz.server.utils;

/**
 * Created by: Reza
 * Company: reza
 * Package: com.behrouz.server.utils
 * Project Name: behta-server
 * 24 January 2019
 **/
public class NumberUtil {

    public static boolean isEqualToZero( float number ){

        return Math.abs(number) < 1e-9;

    }

    public static boolean isGreaterThanOrEqual ( float number1, float number2 ) {

        return number1 > number2 || number1 - number2 < 1e-9;

    }


}
