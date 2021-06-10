package com.fh.shop.config;

import com.fh.shop.filter.Filter1;
import com.fh.shop.filter.Filter2;
import com.fh.shop.filter.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class FilterConfig {

//    @Bean
//    public Filter1 filter1(){
//        return new Filter1();
//    }
//    @Bean
//    public Filter2 filter2(){
//        return new Filter2();
//    }

    @Bean
    public JwtFilter jwtFilter(){
        return new JwtFilter();
    }
}
