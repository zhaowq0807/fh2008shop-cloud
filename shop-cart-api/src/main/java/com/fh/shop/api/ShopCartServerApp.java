package com.fh.shop.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ShopCartServerApp {

    public static void main( String[] args ){
        SpringApplication.run(ShopCartServerApp.class,args);
    }

}
