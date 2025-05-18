package com.behrouz.server.utils;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Morteza
 * Package ir.mobintabaran.xima.server.utils
 * Project Koala Server
 * 09 September 2018 13:17
 **/

public class StringsUtil {

    public static final boolean isNullOrEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static final boolean isAllInteger(String str) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        boolean allInteger = true;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                allInteger = false;
            }
        }
        return allInteger;
    }

    public static boolean checkFileExtensionImage(MultipartFile multipartFile) {
        if (multipartFile == null || multipartFile.isEmpty() || isNullOrEmpty(multipartFile.getOriginalFilename())){
            return true;
        }

        String safeExtensions = ".png, .jpg, .jpeg, .PNG, .JPG, .JPEG, .pdf, .xlsx, .doc, .csv, .mp4, .webm, .mkv, .flv, .vob, .ogv, .ogg, .avi, .m4p, .m4v, .3gp, .rar ,.zip ";

        String fileExtension = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

        return fileExtension != null && safeExtensions.contains(fileExtension);
    }


}
