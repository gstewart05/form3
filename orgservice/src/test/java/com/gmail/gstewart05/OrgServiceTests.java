package com.gmail.gstewart05;

import com.gmail.gstewart05.model.Org;
import com.gmail.gstewart05.model.OrgRepository;
import com.gmail.gstewart05.service.OrgServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

@RunWith( SpringRunner.class )
public class OrgServiceTests
{
	@Mock
	private OrgRepository theOrgRepository;

	@InjectMocks
	private OrgServiceImpl theOrgService;

	@Before
	public void setup()
	{
		MockitoAnnotations.initMocks( this );
	}

	@Test
	public void testGetAll()
	{
		List< Org > lList = new ArrayList<>();
		lList.add( Org.builder().name( "Form 3" ).build() );
		lList.add( Org.builder().name( "Apple" ).build() );
		lList.add( Org.builder().name( "Samsung" ).build() );

		when( theOrgRepository.findAll() ).thenReturn( lList );

		List lResult = theOrgService.getAll();
		assertEquals( 3, lList.size() );

		verify( theOrgRepository, times( 1 ) ).findAll();
		verifyNoMoreInteractions( theOrgRepository );
	}

	@Test
	public void testGetById()
	{
		when( theOrgRepository.findOne( "1" ) ).thenReturn( Org.builder().id( "1" ).name( "Form 3" ).build() );

		Org lOrg = theOrgService.getById( "1" );
		assertEquals( "1", lOrg.getId() );
		assertEquals( "Form 3", lOrg.getName() );

		verify( theOrgRepository, times( 1 ) ).findOne( "1" );
		verifyNoMoreInteractions( theOrgRepository );
	}

	@Test
	public void testGetByIdFail()
	{
		when( theOrgRepository.findOne( "1" ) ).thenReturn( Org.builder().id( "1" ).name( "Form 3" ).build() );

		Org lOrg = theOrgService.getById( "2" );
		assertNull( lOrg );

		verify( theOrgRepository, times( 1 ) ).findOne( "2" );
		verifyNoMoreInteractions( theOrgRepository );
	}

	@Test
	public void testGetByName()
	{
		when( theOrgRepository.findByNameIgnoreCase( "Form 3" ) ).thenReturn( Org.builder().id( "1" ).name( "Form 3" ).build() );

		Org lOrg = theOrgService.getByName( "Form 3" );
		assertEquals( "1", lOrg.getId() );
		assertEquals( "Form 3", lOrg.getName() );

		verify( theOrgRepository, times( 1 ) ).findByNameIgnoreCase( "Form 3" );
		verifyNoMoreInteractions( theOrgRepository );
	}

	@Test
	public void testGetByNameFail()
	{
		when( theOrgRepository.findByNameIgnoreCase( "Form 3" ) ).thenReturn( Org.builder().id( "1" ).name( "Form 3" ).build() );

		Org lOrg = theOrgService.getByName( "Form 4" );
		assertNull( lOrg );

		verify( theOrgRepository, times( 1 ) ).findByNameIgnoreCase( "Form 4" );
		verifyNoMoreInteractions( theOrgRepository );
	}

	@Test
	public void testSave()
	{
		Org lSave = Org.builder().id( "1" ).name( "Form 3" ).build();
		when( theOrgRepository.save( lSave ) ).thenReturn( lSave );

		Org lOrg = theOrgService.save( lSave );
		assertEquals( "1", lOrg.getId() );
		assertEquals( "Form 3", lOrg.getName() );

		verify( theOrgRepository, times( 1 ) ).save( lSave );
		verifyNoMoreInteractions( theOrgRepository );
	}

	@Test
	public void testDeleteByObject()
	{
		Org lDelete = Org.builder().id( "1" ).name( "Form 3" ).build();
		theOrgRepository.delete( lDelete );

		verify( theOrgRepository, times( 1 ) ).delete( lDelete );
		verifyNoMoreInteractions( theOrgRepository );
	}

	@Test
	public void testDeleteById()
	{
		theOrgRepository.delete( "1" );

		verify( theOrgRepository, times( 1 ) ).delete( "1" );
		verifyNoMoreInteractions( theOrgRepository );
	}
}
