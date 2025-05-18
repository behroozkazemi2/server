package com.behrouz.server.security;

import com.behrouz.server.model.account.OperatorEntity;
import com.behrouz.server.model.account.OperatorLoginHistoryEntity;
import com.behrouz.server.model.account.PasswordEntity;
import com.behrouz.server.model.global.AddressEntity;
import com.behrouz.server.security.captcha.CaptchaAuthenticationDetails;
import com.behrouz.server.security.components.PasswordComponent;
import com.behrouz.server.security.model.OperatorSessionDetail;
import com.behrouz.server.security.model.UserSessionDetail;
import com.behrouz.server.strategy.StrategyGenerator;
import com.behrouz.server.repository.AddressRepository;
import com.behrouz.server.repository.OperatorLoginHistoryRepository;
import com.behrouz.server.repository.OperatorRepository;
import com.behrouz.server.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Hapi KZM
 * Package com.behrouz.server.security
 * Project Koala Server
 * 09 September 2018 13:32
 **/
@Component
public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {


    @Autowired
    private PasswordRepository passwordRepository;


    @Autowired
    private PasswordComponent passwordComponent;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private OperatorLoginHistoryRepository operatorLoginHistory;

    @Autowired
    private AddressRepository addressRepository;


    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        if(!(authentication.getDetails() instanceof CaptchaAuthenticationDetails)){
            throw new BadCredentialsException("کد امنیتی وارد نشده است.");
        }


        CaptchaAuthenticationDetails captcha =
                (CaptchaAuthenticationDetails) authentication.getDetails();



        if(!captcha.isCorrect())
            throw new BadCredentialsException("کد امنیتی صحیح نمی باشد");

        String username =
                authentication.getName();
        String password =
                authentication.getCredentials().toString();


        return loginAuthentication(username , password);

    }


    private Authentication loginAuthentication(String username, String password) {


        OperatorEntity operator = operatorRepository.findFirstByUsernameAndBannedIsFalse( username );

        if( operator == null ){
            throw new BadCredentialsException("نام کاربری صحیح نمی باشد");
        }


        PasswordEntity passwordEntity =
                passwordRepository.findFirstByOperator_IdAndDeletedIsFalseOrderByIdDesc( operator.getId() );


        if(passwordEntity == null || !passwordComponent.passwordMatched( password , passwordEntity.getHashPassword() ) ){
            throw new BadCredentialsException("رمز عبور صحیح نمی باشد");
        }



        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());


        OperatorSessionDetail sessionDetail =
                createSessionDetail(passwordEntity);

        token.setDetails( sessionDetail );


        saveLoginLog(passwordEntity , sessionDetail);

        return token;

    }

    private void saveLoginLog( PasswordEntity passwordEntity, OperatorSessionDetail sessionDetail ) {

        operatorLoginHistory.save(
                new OperatorLoginHistoryEntity(
                        passwordEntity,
                        sessionDetail
                )
        );

    }


    private OperatorSessionDetail createSessionDetail(PasswordEntity passwordEntity) {

        if ( passwordEntity.getOperator() == null ){
            throw new BadCredentialsException("اطلاعات اپراتور کامل نیست!!");
        }


        OperatorSessionDetail sessionDetail =
                new OperatorSessionDetail();

        AddressEntity addresses =
                addressRepository.findFirstByAccount_IdAndDeletedIsFalse(passwordEntity.getOperator().getId());

        sessionDetail.setUser( new UserSessionDetail(
                passwordEntity.getOperator().getId(),
                passwordEntity.getOperator().getFirstName(),
                passwordEntity.getOperator().getLastName(),
                passwordEntity.getOperator().getAvatar(),
                StrategyGenerator.generateOperatorToken(),
                new Date(),
                addresses.getId()
        ) );

        sessionDetail.setFirstLogin( false );


        return sessionDetail;
    }





    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(
                UsernamePasswordAuthenticationToken.class);
    }

}
