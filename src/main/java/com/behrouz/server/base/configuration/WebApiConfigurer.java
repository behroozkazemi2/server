package com.behrouz.server.base.configuration;


import com.behrouz.server.base.components.ApiHandlerInterceptor;
import com.behrouz.server.base.components.RequestDistributor;
import com.behrouz.server.niazPardazandeh.SmsComponent;
import com.behrouz.server.security.SecurityInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * created by: Hapi
 **/

@EnableWebMvc
@Configuration
@ComponentScan
public class WebApiConfigurer implements WebMvcConfigurer {


    @Autowired
    private RequestDistributor requestDistributor;


    @Autowired
    private SecurityInterceptor securityInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(securityInterceptor);
        registry.addInterceptor(new ApiHandlerInterceptor( requestDistributor )).addPathPatterns( "/api" , "/api/simple" );

    }

    @Bean
    public SmsComponent smsComponent() {
        return new SmsComponent();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**", "/files/**")
                .addResourceLocations(
                        "classpath:/META-INF/resources/",
                        "classpath:/resources/",
//                        "file:/home/reza/Documents/Xima_new_product/",
                        "file:/opt/java/xima_shop/images/original/",
                        "classpath:/static/",
                        "classpath:/public/"
                );
    }


}
