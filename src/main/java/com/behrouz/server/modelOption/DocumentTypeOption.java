package com.behrouz.server.modelOption;

import io.netty.util.internal.StringUtil;

public enum DocumentTypeOption {

    PNG (1,"PNG"),
    JPG (2,"JPG"),
    JPEG (3,"JPEG"),
    PDF (4,"PDF"),
    DWG (5,"DWG");

    private final int id;
    private final String name;


    public int getId () {
        return id;
    }
    public String getName () {
        return name;
    }

    DocumentTypeOption(int id, String name ) {
        this.id = id;
        this.name = name;
    }

    public static DocumentTypeOption getById(int id){
        for ( DocumentTypeOption option : DocumentTypeOption.values() ){
            if ( option.id == id ){
                return option;
            }
        }
        return PDF;
    }

    public static DocumentTypeOption getType(String name){
        if(StringUtil.isNullOrEmpty(name)){
            return PDF;
        }
        for ( DocumentTypeOption option : DocumentTypeOption.values() ){
            if (name.toLowerCase().contains(("."+option.name).toLowerCase()) ){
                return option;
            }
        }
        return PDF;
    }


}