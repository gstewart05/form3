package com.gmail.gstewart05;

import com.gmail.gstewart05.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@Slf4j
public class PaymentServer
{
	public static void main( String[] args )
	{
		SpringApplication.run( PaymentServer.class, args );
	}

	@Bean
	public CommandLineRunner demo( PaymentRepository pRepository )
	{
		log.info( "Adding fake data" );
		return ( args ) ->
		{
			pRepository.save( Payment.builder()
					.type( "Payment" )
					.version( 0 )
					.organisation_id( "743d5b63-8e6f-432e-a8fa-c5d8d2ee5fcb" )
					.attributes( Attributes.builder()
							.amount( new BigDecimal( 100.21 ) )
							.beneficiary_party( Party.builder()
									.account_name( "W Owens" )
									.account_number( "31926819" )
									.account_number_code( "BBAN" )
									.account_type( 0 )
									.address( "1 The Beneficiary Localtown SE2" )
									.bank_id( 403000 )
									.bank_id_code( "GBDSC" )
									.name( "Wilfred Jeremiah Owens" )
									.build() )
							.charges_information( Charges.builder()
									.bearer_code( "SHAR" )
									.sender_charges( new ArrayList< Charge >( Arrays.asList(
											Charge.builder()
													.amount( new BigDecimal( 5 ) )
													.currency( "GBP" )
													.build(),
											Charge.builder()
													.amount( new BigDecimal( 10 ) )
													.currency( "USD" )
													.build() ) ) )
									.receiver_charges( new ArrayList< Charge >( Arrays.asList(
											Charge.builder()
													.amount( new BigDecimal( 5 ) )
													.currency( "GBP" )
													.build() ) ) )
									.build() )

							.currency( "GBP" )
							.debtor_party( Party.builder()
									.account_name( "EJ Brown Black" )
									.account_number( "GB29XABC10161234567801" )
									.account_number_code( "IBAN" )
									.address( "10 Debtor Crescent Sourcetown NE1" )
									.bank_id( 203301 )
									.bank_id_code( "GBDSC" )
									.name( "Emelia Jane Brown" ).build() )
							.end_to_end_reference( "Wil piano Jan" )
							.fx( FX.builder()
									.contract_reference( "FX123" )
									.exchange_rate( new BigDecimal( 2 ) )
									.original_amount( new BigDecimal( 200.42 ) )
									.original_currency( "USD" )
									.build() )
							.numeric_reference( 1002001 )
							.payment_id( "123456789012345678" )
							.payment_purpose( "Paying for goods/services" )
							.payment_scheme( "FPS" )
							.payment_type( "Credit" )
							.processing_date( "2017-01-18" )
							.reference( "Payment for Em's piano lessons" )
							.scheme_payment_sub_type( "InternetBanking" )
							.scheme_payment_type( "ImmediatePayment" )
							.sponsor_party( Party.builder()
									.account_number( "56781234" )
									.bank_id( 123123 )
									.bank_id_code( "GBDSC" )
									.build() )
							.build()
					).build() );
		};
	}
}