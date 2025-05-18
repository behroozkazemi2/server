package com.behrouz.server.utils;

public class ValidationUtil {

    public static boolean isMobileValid(String mobile){
        return
                !StringUtil.isNullOrEmpty(mobile) &&
                        StringUtil.isAllInteger(mobile) &&
                        mobile.length() == "09391661481".length() &&
                        mobile.startsWith("09");
    }

    public static boolean isNationalCodeValid(String code){

        if(StringUtil.isNullOrEmpty(code) || "0923650989".length() != code.length()){
            return false;
        }

        long nationalCode = Long.parseLong(code);
        short []arrayNationalCode = new short[10];

        //extract digits from number
        for (int i = 0; i < 10 ; i++) {
            arrayNationalCode[i] = (short) (nationalCode % 10);
            nationalCode = nationalCode / 10;
        }
        int sum = 0;
        for (int i = 9; i > 0 ; i--) {
            sum += arrayNationalCode[i] * (i + 1);
        }

        int temp = sum % 11;
        if (temp < 2) {
            return arrayNationalCode[0] == temp;
        }else {
            return arrayNationalCode[0] == 11 - temp;
        }
    }

    private static boolean validationNationalCode(String code){
        //check length
        if (code.length() != 10)
            return false;

        long nationalCode = Long.parseLong(code);
        byte[] arrayNationalCode = new byte[10];

        //extract digits from number
        for (int i = 0; i < 10 ; i++) {
            arrayNationalCode[i] = (byte) (nationalCode % 10);
            nationalCode = nationalCode / 10;
        }

        //Checking the control digit
        int sum = 0;
        for (int i = 9; i > 0 ; i--)
            sum += arrayNationalCode[i] * (i+1);
        int temp = sum % 11;
        if (temp < 2)
            return arrayNationalCode[0] == temp;
        else
            return arrayNationalCode[0] == 11 - temp;
    }
}
