package com.gmail.gstewart05;

import com.gmail.gstewart05.model.*;
import com.gmail.gstewart05.service.PaymentServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith( SpringRunner.class )
public class PaymentServiceTests
{
	@Mock
	private PaymentRepository thePaymentRepository;

	@InjectMocks
	private PaymentServiceImpl thePaymentService;

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks( this );
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
	public void testGetAll()
	{
		List< Payment > lList = new ArrayList<>();
		lList.add( getPaymentExample( "1", 0, "a" ) );
		lList.add( getPaymentExample( "2", 0, "b" ) );
		lList.add( getPaymentExample( "3", 0, "c" ) );

		when( thePaymentRepository.findAll() ).thenReturn( lList );

		List lResult = thePaymentService.getAll();
		assertEquals( 3, lList.size() );

		verify( thePaymentRepository, times( 1 ) ).findAll();
		verifyNoMoreInteractions( thePaymentRepository );
	}

	@Test
	public void testGetById()
	{
		when( thePaymentRepository.findOne( "1" ) ).thenReturn( getPaymentExample( "1", 0, "a" ) );

		Payment lPayment = thePaymentService.getById( "1" );
		assertEquals( "1", lPayment.getId() );
		assertEquals( 0, lPayment.getVersion() );
		assertEquals( "a", lPayment.getOrganisation_id() );

		verify( thePaymentRepository, times( 1 ) ).findOne( "1" );
		verifyNoMoreInteractions( thePaymentRepository );
	}

	@Test
	public void testGetByIdFail()
	{
		when( thePaymentRepository.findOne( "1" ) ).thenReturn( getPaymentExample( "1", 0, "a" ) );

		Payment lPayment = thePaymentService.getById( "2" );
		assertNull( lPayment );

		verify( thePaymentRepository, times( 1 ) ).findOne( "2" );
		verifyNoMoreInteractions( thePaymentRepository );
	}

	@Test
	public void testSave()
	{
		Payment lSave = getPaymentExample( "1", 0, "a" );
		when( thePaymentRepository.save( lSave ) ).thenReturn( lSave );

		Payment lPayment = thePaymentService.save( lSave );
		assertEquals( "1", lPayment.getId() );
		assertEquals( 0, lPayment.getVersion() );
		assertEquals( "a", lPayment.getOrganisation_id() );

		verify( thePaymentRepository, times( 1 ) ).save( lSave );
		verifyNoMoreInteractions( thePaymentRepository );
	}

	@Test
	public void testDeleteByObject()
	{
		Payment lDelete = getPaymentExample( "1", 0, "a" );
		thePaymentRepository.delete( lDelete );

		verify( thePaymentRepository, times( 1 ) ).delete( lDelete );
		verifyNoMoreInteractions( thePaymentRepository );
	}

	@Test
	public void testDeleteById()
	{
		thePaymentRepository.delete( "1" );

		verify( thePaymentRepository, times( 1 ) ).delete( "1" );
		verifyNoMoreInteractions( thePaymentRepository );
	}
}
