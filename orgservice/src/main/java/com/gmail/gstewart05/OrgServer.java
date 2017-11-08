package com.gmail.gstewart05;

import com.gmail.gstewart05.model.Org;
import com.gmail.gstewart05.model.OrgRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@Slf4j
public class OrgServer
{
	public static void main( String[] args )
	{
		SpringApplication.run( OrgServer.class, args );
	}

	@Bean
	public CommandLineRunner demo( OrgRepository pRepository )
	{
		log.info( "Adding fake data" );
		return ( args ) ->
		{
			pRepository.save( Org.builder().name( "Form 3" ).build() );
			pRepository.save( Org.builder().name( "JAMDev" ).build() );
			pRepository.save( Org.builder().name( "Apple" ).build() );
			pRepository.save( Org.builder().name( "Samsung" ).build() );
		};
	}
}