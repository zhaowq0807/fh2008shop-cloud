package com.fh.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ShopRegCenterServer {

    public static void main( String[] args ){
        System.out.println("qweqwe");
        SpringApplication.run(ShopRegCenterServer.class,args);
    }
}
