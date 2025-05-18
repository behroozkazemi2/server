package com.behrouz.server.api.customer;

import com.behrouz.server.api.BaseApi;
import com.behrouz.server.api.customer.request.LoginRequest;
import com.behrouz.server.api.customer.request.RegisterRequest;
import com.behrouz.server.api.customer.request.VerifyRequest;
import com.behrouz.server.api.customer.response.ApiVerifyResponse;
import com.behrouz.server.base.ApiActionRequest;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.annotation.ApiActionParam;
import com.behrouz.server.base.exception.ApiActionException;
import com.behrouz.server.base.exception.ApiActionWrongDataException;
import com.behrouz.server.model.SmsHistoryEntity;
import com.behrouz.server.model.account.AccountEntity;
import com.behrouz.server.model.account.CustomerEntity;
import com.behrouz.server.model.account.CustomerLoginEntity;
import com.behrouz.server.redis.RedisSms;
import com.behrouz.server.redis.RedisSmsMobile;
import com.behrouz.server.redis.RedisSmsProtected;
import com.behrouz.server.redis.RedisToken;
import com.behrouz.server.repository.*;
import com.behrouz.server.utils.*;
import com.behrouz.server.repository.*;
import com.behrouz.server.utils.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.api
 * Project server
 * 16 September 2018 12:35
 **/

/**
 * First Page :
 *
 *      1 ) Login -> Input : mobile, then gotoVerifyMobile
 *      {@link #login(LoginRequest)}
 *
 *              1-2-1 ) VerifyMobile -> Input : smsCode, then gotoMain
 *              {@link #verify(VerifyRequest, ApiActionRequest)}
 *
 *
 *      2 ) CreateAccount -> Input : some information, then gotoVerifyMobile
 *      {@link #register(RegisterRequest, ApiActionRequest)}
 *
 *              2-1 ) VerifyMobile -> Input : smsCode, then gotoMain | {@link #verify(VerifyRequest, ApiActionRequest)}
 *
 */
public class LoginApi extends BaseApi {


    private static final List<String> BehtaShopMobilen = Arrays.asList(
            "09105501184",
            "09029090869",
            "09029090868"
            );



    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private SmsHistoryRepository smsHistoryRepository;


    @Autowired
    private RedisToken redisToken;

    @Autowired
    private RedisSmsMobile redisSmsMobile;

    @Autowired
    private RedisSms redisSms;

    @Autowired
    private RedisSmsProtected redisSmsProtected;


    @Autowired
    private ApplicationVersionRepository applicationVersionRepository;

    @Autowired
    private MobilePlatformRepository mobilePlatformRepository;

    @Autowired
    private CustomerLoginRepository customerLoginRepository;


    @Autowired
    private AccountRepository accountRepository;


    @Autowired
    private NiazPardazandehSendSmsUtil niazPardazandehSendSmsUtil;

    private static final int MAX_NUMBER_OF_SMS_PER_DAY = 50;

    @ApiAction( value = "app.customer.auth.login", tokenRequired = false )
    public ApiResponseBody login(@ApiActionParam(nullable = false) LoginRequest request ) throws ApiActionException {

        if(!ValidationUtil.isMobileValid(request.getMobile())){
            throw new ApiActionWrongDataException(
                    "شماره تلفن همراه خود را به درستی وارد کنید."
            );
        }

        if(StringUtil.isNullOrEmpty(request.getImei())){
            throw new ApiActionWrongDataException(
                    "درخواست غیر مجاز، لطفا مجددا تلاش کنید."
            );
        }


        if(!redisSmsProtected.permitSendSms()){
            throw new ApiActionWrongDataException(
                    "مرکز در حال محاسبات می‌باشد. لطفا کمی صبر کنید و مجددا تلاش کنید."
            );
        }
//
//        if(!redisSms.canSend(request.getImei()) || !redisSmsMobile.permitted(request.getMobile())){
//            throw new ApiActionWrongDataException(
//                    "تعداد دفعات درخواست پیامک برای شما بیش از حد مجاز است."
//            );
//        }
        generateAndSendSms(request);
        return new ApiResponseBody().ok();
    }

    private void generateAndSendSms( LoginRequest request) {
        String generatedCode = "12345";
//                StringGenerator.generateDigit(5);
//        if (BehtaShopMobilen.contains(request.getMobile())) {
//            generatedCode = "12345";
//        }else {
//            String finalGeneratedCode = generatedCode;
////            if(false) {//don't delete this for debugging process
//                new Thread(() -> {
////                    SendResult resultSms = SmsSender.login(request.getMobile(), finalGeneratedCode);
//                    SmsResponse resultSms =
//                            niazPardazandehSendSmsUtil.sendLoginVerifyCode(request.getMobile(), finalGeneratedCode);
//                    System.out.println("Successful Send SMS " + request.getMobile() + " -> Verify -> " + finalGeneratedCode);
//                }).start();
////            }
//        }
        logger.info("login sms: " + generatedCode);
        redisSms.saveSmsVerifyCode(request.getImei(), request.getMobile() , generatedCode);
        redisSmsMobile.saveLog(request.getMobile());
        redisSmsProtected.saveLog(request.getMobile(), request.getImei());

        SmsHistoryEntity history =
                new SmsHistoryEntity(
                        generatedCode,
                        request.getMobile(),
                        "کد فعال سازی",
                        null
                );
        smsHistoryRepository.save(history);
    }


    @ApiAction( value = "app.customer.auth.verify", tokenRequired = false )
    public ApiResponseBody verify(VerifyRequest request, ApiActionRequest apiActionRequest ) throws ApiActionException {

        if(StringUtil.isNullOrEmpty(request.getSmsCode())){
            throw new ApiActionWrongDataException(
                    "کد فعال سازی را وارد کنید."
            );
        }

        if(!ValidationUtil.isMobileValid(request.getMobile())){
            throw new ApiActionWrongDataException(
                    "شماره تلفن همراه خود را به درستی وارد کنید."
            );
        }

        if(StringUtil.isNullOrEmpty(request.getImei())){
            throw new ApiActionWrongDataException(


            );
        }

        if( !redisSms.isCorrect(request.getImei(), request.getMobile(), request.getSmsCode()) ){
            throw new ApiActionWrongDataException(
                    "کد فعال سازی وارد شده اشتباه است."
            );
        }

        String generateToken =
                StringGenerator.generateToken();


        AccountEntity user =
                accountRepository.findFirstByMobileAndBannedIsFalseAndDeletedIsFalse(
                        request.getMobile()
                );

        LocalDateTime expire = redisToken.login(
                generateToken,
                user != null ? user.getId() : 0,
                request.getMobile(),
                request.getNotificationId() ,
                request.getImei(),
                user == null
        );

        if(user != null){
            String ip = IpUtil.getClientIpAddress();
            saveLoginLog(user, generateToken, request.getNotificationId(), ip, request.getImei());
        }


        return new ApiResponseBody<>().ok(
                new ApiVerifyResponse(
                        generateToken,
                        user == null
                )
        );
    }

    private boolean recentlyRequested( String mobile ) {
        return smsHistoryRepository.findAllRecentRequestForMobile( mobile );
    }

    private boolean tooMuchVerifyMobileRequest( String mobile ) {
        List< SmsHistoryEntity > todaySentSms =
                smsHistoryRepository.findAllByMobileNumberAndInsertDateIsToday(
                        mobile
                );

        return todaySentSms != null && todaySentSms.size() >= MAX_NUMBER_OF_SMS_PER_DAY;
    }
//
//    private String sendVerifyMobileCode( String mobile, String smsCode ) throws ApiActionException {
//        return sendVerifyMobileCode(mobile, smsCode, false);
//    }

//    private String sendVerifyMobileCode( String mobile, String smsCode, boolean dontSendSms ) throws ApiActionException {
//

//        boolean debug = DebugValues.DEBUG_MODE || dontSendSms;
//
//        if(!debug  ) {
//            SendMessageRequest request = new SendMessageRequest(mobile, smsCode, 0, SendMessageRequest.Template.XIMA.getKavenegarName());
//            com.behrouz.server.okhttp.api.ApiResponseBody<IdRequest> smsResponse =
//                    OkHttpHelper.sendVerifyMessage( request );
//
//            if ( !smsResponse.successful() ) {
//                throw new ApiActionException(
//                        HttpCode.REQUEST_REJECT,
//                        "پیامک ارسال نشد. لطفا دوباره تلاش کنید!"
//                );
//            }
//
//        }else {
//            smsCode = DebugValues.SMS_CODE;
//        }
//
//
//        smsHistoryRepository.save(
//                new SmsHistoryEntity(
//                        smsCode,
//                        mobile,
//                        "لاگین",
//                        customerRepository.findFirstByMobileAndBannedIsFalse( mobile )
//                )
//        );
//
//        redisSms.saveSmsVerifyCode(mobile, smsCode);
//
//        System.out.println(String.format( "\n\n *** SMS Sent -> mobile : %s, content : %s *** \n\n",  mobile, smsCode));
//
//
//        return smsCode;
//    }

    @ApiAction( value = "app.customer.auth.resend", tokenRequired = false )
    public ApiResponseBody resend(LoginRequest request ) throws ApiActionException {
//
//        if ( StringUtil.isNullOrEmpty( loginRequest.getMobile() ) ||
//                StringUtil.isNullOrEmpty( loginRequest.getImei() ) ) {
//            throw new ApiActionWrongDataException(
//                    "لطفا اطلاعات را کامل وارد کنید!"
//            );
//        }
//
//
//        /*
//            2 minutes
//         */
//        if ( !DebugValues.DEBUG_MODE && recentlyRequested( loginRequest.getMobile() ) ) {
//            throw new ApiActionException(
//                    HttpCode.REQUEST_REJECT,
//                    "تعداد درخواست های ارسال شده بیشتر از حد مجاز است."
//            );
//        }
//
//
//        /*
//            5 sms per day
//         */
//        if ( !DebugValues.DEBUG_MODE && tooMuchVerifyMobileRequest( loginRequest.getMobile() ) ) {
//            throw new ApiActionException(
//                    HttpCode.REQUEST_REJECT,
//                    "تعداد درخواست های ارسال شده بیشتر از حد مجاز است. لطفا با پشتیبانی تماس بگیرید."
//            );
//        }
//
//
//        /*
//            you can't send something that doesn't exist !!
//         */
//        // TODO SMS Resend
//        if ( redisSms.getCode( loginRequest.getMobile() ) == null ) {
//            throw new ApiActionException(
//                    HttpCode.REQUEST_REJECT,
//                    "امکان ارسال مجدد کد وجود ندارد. لطفا مجددا شماره خود را وارد کنید!"
//            );
//        }
//        // TODO Hapi SEND SMS
//        generateAndSendSms(loginRequest);
//
////        String verifyCode = sendVerifyMobileCode( loginRequest.getMobile(), smsCode );
//


        if(!ValidationUtil.isMobileValid(request.getMobile())){
            throw new ApiActionWrongDataException(
                    "شماره تلفن همراه خود را به درستی وارد کنید."
            );
        }

        if(StringUtil.isNullOrEmpty(request.getImei())){
            throw new ApiActionWrongDataException(
                    "درخواست غیر مجاز، لطفا مجددا تلاش کنید."
            );
        }


        if(!redisSmsProtected.permitSendSms()){
            throw new ApiActionWrongDataException(
                    "مرکز در حال محاسبات می‌باشد. لطفا کمی صبر کنید و مجددا تلاش کنید."
            );
        }
//
//        if(!redisSms.canSend(request.getImei()) || !redisSmsMobile.permitted(request.getMobile())){
//            throw new ApiActionWrongDataException(
//                    "تعداد دفعات درخواست پیامک برای شما بیش از حد مجاز است."
//            );
//        }
        generateAndSendSms(request);
        return new ApiResponseBody().ok();
    }


    @ApiAction( value = "app.customer.auth.register", forceRegisteredToken = false)
    public ApiResponseBody register(
            @ApiActionParam(nullable = false) RegisterRequest request,
            ApiActionRequest apiActionRequest ) throws ApiActionException {

        if(StringUtil.isNullOrEmpty(request.getFirstName())){
            throw new ApiActionWrongDataException(
                    "نام را وارد کنید."
            );
        }
        if(StringUtil.isNullOrEmpty(request.getLastName())){
            throw new ApiActionWrongDataException(
                    "نام خانوادگی را وارد کنید."
            );
        }
        if(StringUtil.isNullOrEmpty(request.getNationalCode())){
            throw new ApiActionWrongDataException(
                    "کد‌ملی خود را وارد نمایید."
            );
        }

        String mobile =
                redisToken.getMobile(apiActionRequest);

        if (StringUtil.isNullOrEmpty(mobile) ) {
            throw new ApiActionWrongDataException(
                    "امکان ثبت نام شما وجود ندارد، لطفا مجددا وارد شوید."
            );
        }

        AccountEntity userMobile =
                accountRepository.findFirstByMobileAndBannedIsFalseAndDeletedIsFalse(mobile);
        if (userMobile != null) {
            throw new ApiActionWrongDataException(
                    "کاربر قبلا ثبت نام شده. (شماره همراه تکراری)"
            );
        }
        CustomerEntity inviter = StringUtil.isNullOrEmpty(request.getInviteCode()) ? null :
                customerRepository.findFirstByInvitingCodeAndBannedIsFalseAndDeletedIsFalse(
                        request.getInviteCode()
                );
        if (!StringUtil.isNullOrEmpty(request.getInviteCode()) && inviter == null){
            throw new ApiActionWrongDataException(
                    "کد معرف نامعتبر است."
            );
        }

        CustomerEntity user =
                new CustomerEntity(
                        request,
                        inviter
                );
        customerRepository.save(user);


        String notificationId = redisToken.getNotificationId(apiActionRequest);
        String identity = redisToken.getUUID(apiActionRequest);
        String ip = IpUtil.getClientIpAddress();


        LocalDateTime expire = redisToken.login(
                apiActionRequest.getToken(),
                user.getId(),
                mobile,
                notificationId ,
                identity,
                false
        );
        saveLoginLog(
                user,
                apiActionRequest.getToken(),
                notificationId,
                ip,
                identity
        );


        return new ApiResponseBody().ok();
    }


    @ApiAction( value = "app.customer.auth.logout")
    public ApiResponseBody logout(ApiActionRequest apiActionRequest, int customerId ) throws ApiActionException {

        CustomerEntity customer = customerRepository.findFirstByIdAndBannedIsFalseAndDeletedIsFalse( customerId );

        if ( customer == null ) {
            throw new ApiActionWrongDataException(
                    "اطلاعات کاربر مورد نظر یافت نشد"
            );
        }


        CustomerLoginEntity customerLogin = customerLoginRepository.findFirstByAccount_IdAndLogoutIsFalseAndDeletedIsFalseOrderByIdDesc( customerId );

        if ( customerLogin == null ) {
            throw new ApiActionWrongDataException(
                    "اطلاعات کاربر مورد نظر یافت نشد"
            );
        }


        changeCustomerLoginToLogout( customerLogin );

        redisToken.logout( apiActionRequest );

        return new ApiResponseBody().ok();
    }





    private void saveLoginLog(AccountEntity user, String token, String notificationId, String ip, String spec) {
        CustomerLoginEntity login = new CustomerLoginEntity(
                notificationId,
                token,
                spec,
                ip ,
                user
        );
        customerLoginRepository.save(login);
    }





    private void changeCustomerLoginToLogout( CustomerLoginEntity customerLogin ) {

        customerLogin.setLogout( true );
        customerLogin.setLogoutDate( new Date(  ) );

        customerLoginRepository.save( customerLogin );

    }

}