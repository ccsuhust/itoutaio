package com.iexample.itoutaio.configuration;

import com.iexample.itoutaio.interceptor.LoginRequiredInterceptor;
import com.iexample.itoutaio.interceptor.PassportInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
@Component
public class ToutiaoWebConfiguration extends WebMvcConfigurerAdapter {
    @Autowired
    PassportInterceptor passportInterceptor;
    @Autowired
    LoginRequiredInterceptor LoginRequiredInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //System.out.println("ToutiaoWebConfiguration");
        registry.addInterceptor(passportInterceptor);
        registry.addInterceptor(LoginRequiredInterceptor).addPathPatterns("/setting");
        super.addInterceptors(registry);
    }
}
