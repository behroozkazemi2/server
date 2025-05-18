package com.behrouz.server.api.provider;

import com.behrouz.server.api.provider.request.ProviderLoginRequest;
import com.behrouz.server.api.provider.response.ProviderLoginResponse;
import com.behrouz.server.base.ApiResponseBody;
import com.behrouz.server.base.annotation.ApiAction;
import com.behrouz.server.base.annotation.ApiActionParam;
import com.behrouz.server.base.exception.ApiActionFailureException;
import com.behrouz.server.model.account.OperatorEntity;
import com.behrouz.server.model.account.PasswordEntity;
import com.behrouz.server.repository.OperatorRepository;
import com.behrouz.server.repository.PasswordRepository;
import com.behrouz.server.security.components.PasswordComponent;
import com.behrouz.server.strategy.StringGenerator;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthProviderApi extends ProviderBaseApi {

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private PasswordComponent passwordComponent;


    @ApiAction(value = "app.provider.auth.login", tokenRequired = false)
    public ApiResponseBody<ProviderLoginResponse> login(@ApiActionParam(nullable = false) ProviderLoginRequest loginRequest) throws ApiActionFailureException {

        OperatorEntity operator =
                operatorRepository.findFirstByUsernameAndDeletedIsFalse(loginRequest.getUsername());

        if (operator == null) {
            throw new ApiActionFailureException("نام کاربری اشتباه می‌باشد.");
        }

        if(operator.isBanned()){
            throw new ApiActionFailureException("حساب کاربری شما مسدود شده است. لطفا با پشتیبانی تماس بگیرید.");
        }

        PasswordEntity passwordEntity =
                passwordRepository.findFirstByOperator_IdAndDeletedIsFalseOrderByIdDesc( operator.getId() );


        if(passwordEntity == null){
            throw new ApiActionFailureException("رمز عبور تعریف نشده است.");
        }

        System.out.println("Operator: " + operator.getId() + " ,Password: " + passwordEntity.getId());
        if(!passwordComponent.passwordMatched(loginRequest.getPassword(), passwordEntity.getHashPassword())){
            throw new ApiActionFailureException("نام کاربری یا رمز عبور اشتباه است.");
        }

        String token = "j8-" + StringGenerator.generateToken(36);

        redisToken.login(operator, token);


        ProviderLoginResponse response =
                new ProviderLoginResponse(operator, token);

        return new ApiResponseBody().ok(response);


    }
}
