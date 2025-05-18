package com.behrouz.server;

import com.behrouz.server.values.AndroidValues;
import com.behrouz.server.values.DebugValues;
import com.behrouz.server.values.Links;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TimeZone;

@SpringBootApplication
public class BehrouzServerApplication {



    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Tehran"));
        SpringApplication.run(BehrouzServerApplication.class, args);
        showStartServer();
    }

    private static void showStartServer() {

        System.out.println(_8tagMarketContent());
        System.out.println(
                "\n\n\t\t\t\t\t\t" + "Debug(Sms Activate): "      + DebugValues.DEBUG_MODE                +
                        (!DebugValues.DEBUG_MODE ? "" :
                                "\n\n\t\t\t\t\t\t" + "SMS Code:            "      + DebugValues.SMS_CODE)                 +
                        "\n\n\t\t\t\t\t\t" + "Response Delay:      "      + DebugValues.DELAY_RESPONSE            +
                        "\n\n\t\t\t\t\t\t" + "Log Length:          "      + DebugValues.LOG_LENGTH_BYTE           +
//                "\n\n\t\t\t\t\t\t" + "Auth Link:           "      + Links.USER_AND_ACCOUNT                +
//                "\n\n\t\t\t\t\t\t" + "Financial Link:      "      + Links.XIMA_FINANCIAL                  +
                        "\n\n\n"
        );
        System.out.println();
    }


    //<editor-fold desc="Debug">

    @Value("${api-debug.mode}")
    public void setDebugMode(Boolean debugMode){
        DebugValues.DEBUG_MODE = debugMode;
    }

    @Value("${api-debug.log-byte-length}")
    public void setLogByteLength(int longByteLength){
        DebugValues.LOG_LENGTH_BYTE = longByteLength;
    }

    @Value("${api-debug.delay-response}")
    public void setDelayResponse(Boolean delayResponse){
        DebugValues.DELAY_RESPONSE = delayResponse;
    }

    @Value("${api-debug.sms-code}")
    public void setSmsCode(String delayResponse){
        DebugValues.SMS_CODE = delayResponse;
    }
    //</editor-fold>


    //<editor-fold desc="Links">
    @Value("${api.link.auth}")
    public void setUserAccountLink(String value){
        Links.USER_AND_ACCOUNT = value;
    }

    @Value("${api.link.xima-financial}")
    public void setXimaFinancial(String value){
        Links.XIMA_FINANCIAL = value;
    }
    //</editor-fold>


    //<editor-fold desc="Android">
    @Value("${android.version.name}")
    public void setAndroidVersionName(String value){
        AndroidValues.APK_VERSION_NAME = value;
    }

    @Value("${android.force-update}")
    public void setAndroidForceUpdate(boolean value){
        AndroidValues.APK_FORCE_UPDATE = value;
    }

    @Value("${android.version.code}")
    public void setAndroidVersionCode(int value){
        AndroidValues.APK_VERSION_CODE = value;
    }

    @Value("${android.link}")
    public void setAndroidApkLink(String value){
        AndroidValues.APK_LINK = value;
    }
    //</editor-fold>




    public static String _8tagMarketContent(){
        try {
            File file = new ClassPathResource("static/assets/raw/_shopbehtatavie.txt").getFile();
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                StringBuilder builder = new StringBuilder(  );
                String line;
                while ((line = br.readLine()) != null) {
                    builder.append( line + "\n");
                }
                return builder.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "xima";
    }

}
