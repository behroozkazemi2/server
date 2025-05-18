package com.behrouz.server.base.annotation;

import com.behrouz.server.base.exception.ApiActionWrongDataException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * created by: Hapi
 **/


@Retention(RetentionPolicy.RUNTIME)
@Target( ElementType.METHOD )
public @interface ApiAction {

    String value() default "";

    Version []versions() default {Version.ALL};

    boolean tokenRequired() default true;

    boolean log() default true;

    boolean forceRegisteredToken() default true;



    enum Version{

        ALL(0),

        V1(1),

        V2(2),

        V3(3),

        V4(4),

        V5(5),

        V6(6),

        V7(7);


        private int version;
        Version(int version) {

            this.version = version;
        }

        public int getVersion() {
            return version;
        }


        public static Version getByVersion(int version) throws ApiActionWrongDataException {

            for(Version v : Version.values()){
                if(v.getVersion() == version ){
                    return v;
                }
            }

            throw new ApiActionWrongDataException( "version code not found: " + version );
        }
    }




}
