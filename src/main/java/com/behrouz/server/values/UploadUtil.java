package com.behrouz.server.values;

import java.nio.file.Paths;


public class UploadUtil {

    public static String calculateFilename(String directory, String originalFilename) {

        String name = originalFilename.split("\\.")[0];
        String extension = originalFilename.split("\\.")[1];

        int num = 2;
        String tryName = name + '.' + extension;
        while( Paths.get(directory).resolve(tryName).toFile().exists() ){
            tryName = (name + "("  + num + ")." + extension);
            num++;
        }

        return tryName;
    }



}
