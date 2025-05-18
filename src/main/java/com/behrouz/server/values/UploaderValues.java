package com.behrouz.server.values;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class UploaderValues {

    public static String SAVE_PATH;

    @Value("${uploader.save-path}")
    public void setSavePath(String savePath){
        UploaderValues.SAVE_PATH = savePath;
    }


}