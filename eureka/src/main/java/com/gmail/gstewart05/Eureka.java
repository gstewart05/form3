package com.gmail.gstewart05;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationPidFileWriter;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class Eureka
{
	public static void main( String[] args )
	{
		SpringApplication lEurekaServer = new SpringApplication( Eureka.class );
		lEurekaServer.run( args );
	}
}