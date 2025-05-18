package com.behrouz.server.api.customer.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * created by: Hapi
 **/

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApplicationUpdateResponse {

    private int versionCode;

    private String downloadLink;

    private String versionName;

    private boolean forced;


    public ApplicationUpdateResponse ( int versionCode, String downloadLink, String versionName, boolean forced ) {
        this.versionCode = versionCode;
        this.downloadLink = downloadLink;
        this.versionName = versionName;
        this.forced = forced;
    }

    public ApplicationUpdateResponse () {
    }


    public int getVersionCode() {
        return versionCode;
    }
    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getDownloadLink() {
        return downloadLink;
    }
    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getVersionName() {
        return versionName;
    }
    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public boolean isForced() {
        return forced;
    }
    public void setForced(boolean forced) {
        this.forced = forced;
    }
}
