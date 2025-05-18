package com.behrouz.server.base.util;

import java.util.logging.Logger;

/**
 * Created by Hapi KZM on 2017/06/20.
 * Project: taxi8
 * package : com.sepehr.mobin.taxi8.utils
 */
public class XimaServerEncryption extends Encryption {

    protected static final Logger logger = Logger.getLogger( XimaServerEncryption.class.getName());

    private String sharedKey    = "Hapi8J8888888";

    private String sharedVI     = "HapiKazemi888888";


    @Override
    protected String getSharedKey() {
        return sharedKey;
    }

    @Override
    protected String getSharedVI() {
        return sharedVI;
    }
}
