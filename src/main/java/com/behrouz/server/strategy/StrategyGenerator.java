package com.behrouz.server.strategy;

import com.behrouz.server.utils.StringUtil;

import java.time.LocalDateTime;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.strategy
 * Project xima
 * 13 September 2018 10:55
 **/
public class StrategyGenerator {

    private static final int ACCOUNTING_NUMBER_LENGTH = 18;



    public static String generateAccountingNumber() {

        return StringGenerator.generateDigit( ACCOUNTING_NUMBER_LENGTH );

    }


    public static String generateInvitingCode( String mobile ) {

        if ( !StringUtil.isNullOrEmpty( mobile ) ) {

            long numericMobile = Long.parseLong( mobile );

            return Long.toHexString( numericMobile );

        }

        return null;

    }


    public static String generateCustomerApplicationToken() {

        return StringGenerator.generateToken(32);

    }


    public static String generateOperatorToken() {

        return StringGenerator.generateToken(32);

    }


    public static String generateInvitingCodeLink( String invitingCode ) {

        return String.format( "http://domain.ir/invite?code=%s", invitingCode );

    }


    public static String generateDownloadLink() {

        return "http://shop.behrouz.com";

    }


    public static String generateBillTrackingCode( long customerId ) {

        //aaMMYCHHRRD
        LocalDateTime date = LocalDateTime.now();

        int    aa = randomInRange(11 , 99);
        String mm = String.format("%d%d", date.getMinute() / 10, date.getMinute() % 10 );
        String c = String.format("%d", customerId % 97);
        String y  = String.format("%d", (date.getYear() * 12) % 10);
        String hh = String.format("%02d", (date.getHour() * 87) % 100);
        int    r  = randomInRange(9 , 99);

        int   sum = aa + Integer.parseInt(mm) + Integer.parseInt(c) + Integer.parseInt(y) + Integer.parseInt(hh) + r;

        int   checkDigit  = (10 - (sum % 10)) % 10;

//        System.out.println(
//                "BillTracking Code: \n" +
//                "\t\t\taa: " + aa + "\n" +
//                "\t\t\tmm: " + mm + "\n" +
//                "\t\t\tc: " + c + "\n" +
//                "\t\t\ty: " + y + "\n" +
//                "\t\t\thh: " + hh + "\n" +
//                "\t\t\tr: " + r + "\n" +
//                "\t\t\tcheckDigit: " + checkDigit + "\n\n"
//        );

        return aa + mm + c + y + hh + r + checkDigit;

    }

    public static String generateSpecialTrackingCode(int id) {

        //aaMMYCHHRD
        LocalDateTime date = LocalDateTime.now();

        int    aa = randomInRange(12 , 99);
        String mm = String.format("%d%d", date.getMinute() % 10, date.getMinute() / 10);
        String c  = String.format("%d", id % 10);
        String y  = String.format("%d", date.getYear() % 10);
        String hh = String.format("%02d", date.getHour());
        int    r  = randomInRange(1 , 9);

        int   sum = aa + Integer.parseInt(mm) + Integer.parseInt(c) + Integer.parseInt(y) + Integer.parseInt(hh) + r;

        int   checkDigit  = (10 - (sum % 10)) % 10;

        return aa + mm + c + y + hh + r + checkDigit;

    }


    private static final int randomInRange(int high) {
        return randomInRange(0 , high);
    }
    private static final int randomInRange(int low , int high){
        int cur = (int) (Math.random() * (high - low));

        cur += low;

        return cur;
    }


    public static String generateCallSupportTrackingCode () {

        return StringGenerator.generateDigit(12);

    }


    public static String generateOffCode () {

        return StringGenerator.generateToken(5);

    }
}
