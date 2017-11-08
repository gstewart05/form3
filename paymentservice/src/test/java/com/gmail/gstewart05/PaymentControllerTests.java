package com.gmail.gstewart05;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.gstewart05.controller.PaymentController;
import com.gmail.gstewart05.model.*;
import com.gmail.gstewart05.remote.model.Org;
import com.gmail.gstewart05.remote.service.OrgService;
import com.gmail.gstewart05.service.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith( SpringRunner.class )
@SpringBootTest
public class PaymentControllerTests
{
	private MockMvc theMvc;

	@Mock
	private PaymentService thePaymentService;

	@Mock
	private OrgService theOrgService;

	@InjectMocks
	private PaymentController thePaymentController;

	@Autowired
	private WebApplicationContext theWebApplicationContext;

	@Before
	public void setup()
	{
		theMvc = MockMvcBuilders.standaloneSetup( thePaymentController ).build();
	}

	public Payment getPaymentExample( String pID, int pVersion, String pOrgId )
	{
		return Payment.builder()
				.id( pID )
				.type( "Payment" )
				.version( pVersion )
				.organisation_id( pOrgId )
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
				).build();
	}

	@Test
	public void testGetAll() throws Exception
	{
		List< Payment > lList = new ArrayList<>();
		lList.add( getPaymentExample( "1", 0, "a" ) );
		lList.add( getPaymentExample( "2", 0, "b" ) );
		lList.add( getPaymentExample( "3", 0, "c" ) );
		when( thePaymentService.getAll() ).thenReturn( lList );

		theMvc.perform( MockMvcRequestBuilders.get( "/payment/v1" ).accept( MediaType.APPLICATION_JSON ) )
				.andExpect( jsonPath( "$", hasSize( 3 ) ) );

		verify( thePaymentService, times( 1 ) ).getAll();
		verifyNoMoreInteractions( thePaymentService );
	}

	@Test
	public void testGetById() throws Exception
	{
		when( thePaymentService.getById( "1" ) ).thenReturn( getPaymentExample( "1", 0, "a" ) );

		theMvc.perform( MockMvcRequestBuilders.get( "/payment/v1/1" ).accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isOk() )
				.andExpect( jsonPath( "$.id", is( "1" ) ) )
				.andExpect( jsonPath( "$.organisation_id", is( "a" ) ) )
				.andExpect( jsonPath( "$.version", is( 0 ) ) );

		verify( thePaymentService, times( 1 ) ).getById( "1" );
		verifyNoMoreInteractions( thePaymentService );
	}

	@Test
	public void testGetByIdFail() throws Exception
	{
		when( thePaymentService.getById( "2" ) ).thenReturn( null );

		theMvc.perform( MockMvcRequestBuilders.get( "/payment/v1/2" ).accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isNotFound() );

		verify( thePaymentService, times( 1 ) ).getById( "2" );
		verifyNoMoreInteractions( thePaymentService );
	}

	@Test
	public void testCreate() throws Exception
	{
		Payment lPayment = getPaymentExample( "1", 0, "a" );
		when( thePaymentService.save( lPayment ) ).thenReturn( lPayment );
		when( theOrgService.getOrgById( "a" ) ).thenReturn( new Org( "a", "Org A" ) );

		theMvc.perform( MockMvcRequestBuilders.post( "/payment/v1" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( lPayment ) )
				.accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isOk() )
				.andExpect( jsonPath( "$.id", is( "1" ) ) )
				.andExpect( jsonPath( "$.organisation_id", is( "a" ) ) )
				.andExpect( jsonPath( "$.version", is( 0 ) ) );

		verify( thePaymentService, times( 1 ) ).save( any( Payment.class ) );
		verifyNoMoreInteractions( thePaymentService );
	}

	@Test
	public void testCreateFail() throws Exception
	{
		Payment lPayment = getPaymentExample( "1", 0, "a" );
		when( theOrgService.getOrgById( "a" ) ).thenReturn( null );

		theMvc.perform( MockMvcRequestBuilders.post( "/payment/v1" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( lPayment ) )
				.accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isNotFound() );

		verify( theOrgService, times( 1 ) ).getOrgById( "a" );
		verifyNoMoreInteractions( thePaymentService );
		verifyNoMoreInteractions( theOrgService );
	}

	@Test
	public void testUpdate() throws Exception
	{
		Payment lPayment = getPaymentExample( "1", 0, "a" );
		when( thePaymentService.getById( "1" ) ).thenReturn( lPayment );
		when( thePaymentService.save( lPayment ) ).thenReturn( lPayment );
		when( theOrgService.getOrgById( "a" ) ).thenReturn( new Org( "a", "Org A" ) );

		theMvc.perform( MockMvcRequestBuilders.put( "/payment/v1/1" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( lPayment ) )
				.accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isOk() )
				.andExpect( jsonPath( "$.id", is( "1" ) ) )
				.andExpect( jsonPath( "$.organisation_id", is( "a" ) ) )
				.andExpect( jsonPath( "$.version", is( 0 ) ) );

		verify( thePaymentService, times( 1 ) ).getById( "1" );
		verify( thePaymentService, times( 1 ) ).save( any( Payment.class ) );
		verify( theOrgService, times( 1 ) ).getOrgById( "a" );
		verifyNoMoreInteractions( thePaymentService );
		verifyNoMoreInteractions( theOrgService );
	}

	@Test
	public void testUpdateFail() throws Exception
	{
		Payment lPayment = getPaymentExample( "1", 0, "a" );

		theMvc.perform( MockMvcRequestBuilders.put( "/payment/v1/2" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( lPayment ) )
				.accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isNotFound() );

		verify( thePaymentService, times( 1 ) ).getById( "2" );
		verifyNoMoreInteractions( thePaymentService );
	}

	@Test
	public void testUpdateFail2() throws Exception
	{
		Payment lPayment = getPaymentExample( "1", 0, "a" );
		when( thePaymentService.getById( "1" ) ).thenReturn( lPayment );
		when( theOrgService.getOrgById( "a" ) ).thenReturn( null );

		theMvc.perform( MockMvcRequestBuilders.put( "/payment/v1/1" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( lPayment ) )
				.accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isNotFound() );

		verify( thePaymentService, times( 1 ) ).getById( "1" );
		verify( theOrgService, times( 1 ) ).getOrgById( "a" );
		verifyNoMoreInteractions( thePaymentService );
		verifyNoMoreInteractions( theOrgService );
	}

	@Test
	public void testDelete() throws Exception
	{
		Payment lPayment = getPaymentExample( "1", 0, "a" );
		when( thePaymentService.getById( "1" ) ).thenReturn( lPayment );
		doNothing().when( thePaymentService ).delete( lPayment );

		theMvc.perform( MockMvcRequestBuilders.delete( "/payment/v1/1" )
				.accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isOk() );

		verify( thePaymentService, times( 1 ) ).getById( "1" );
		verify( thePaymentService, times( 1 ) ).delete( any( Payment.class ) );
		verifyNoMoreInteractions( thePaymentService );
	}

	@Test
	public void testDeleteFail() throws Exception
	{
		when( thePaymentService.getById( "2" ) ).thenReturn( null );
		doNothing().when( thePaymentService ).delete( any( Payment.class ) );

		theMvc.perform( MockMvcRequestBuilders.delete( "/payment/v1/2" )
				.accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isNotFound() );

		verify( thePaymentService, times( 1 ) ).getById( "2" );
		verifyNoMoreInteractions( thePaymentService );
	}

	public static String asJsonString( final Object obj )
	{
		try
		{
			return new ObjectMapper().writeValueAsString( obj );
		}
		catch( Exception e )
		{
			throw new RuntimeException( e );
		}
	}
}
