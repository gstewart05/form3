package com.gmail.gstewart05;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gmail.gstewart05.controller.OrgController;
import com.gmail.gstewart05.model.Org;
import com.gmail.gstewart05.service.OrgService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

@RunWith( SpringRunner.class )
@SpringBootTest
public class OrgControllerTests
{
	private MockMvc theMvc;

	@Mock
	private OrgService theOrgService;

	@InjectMocks
	private OrgController theOrgController;

	@Autowired
	private WebApplicationContext theWebApplicationContext;

	@Before
	public void setup()
	{
		theMvc = MockMvcBuilders.standaloneSetup( theOrgController ).build();
	}

	@Test
	public void testGetAll() throws Exception
	{
		List< Org > lList = new ArrayList<>();
		lList.add( Org.builder().name( "Form 3" ).build() );
		lList.add( Org.builder().name( "Apple" ).build() );
		lList.add( Org.builder().name( "Samsung" ).build() );
		when( theOrgService.getAll() ).thenReturn( lList );

		theMvc.perform( MockMvcRequestBuilders.get( "/org/v1" ).accept( MediaType.APPLICATION_JSON ) )
				.andExpect( jsonPath( "$", hasSize( 3 ) ) );

		verify( theOrgService, times( 1 ) ).getAll();
		verifyNoMoreInteractions( theOrgService );
	}

	@Test
	public void testGetById() throws Exception
	{
		when( theOrgService.getById( "1" ) ).thenReturn( Org.builder().id( "1" ).name( "Form 3" ).build() );

		theMvc.perform( MockMvcRequestBuilders.get( "/org/v1/1" ).accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isOk() )
				.andExpect( jsonPath( "$.id", is( "1" ) ) )
				.andExpect( jsonPath( "$.name", is( "Form 3" ) ) );

		verify( theOrgService, times( 1 ) ).getById( "1" );
		verifyNoMoreInteractions( theOrgService );
	}

	@Test
	public void testGetByIdFail() throws Exception
	{
		when( theOrgService.getById( "2" ) ).thenReturn( null );

		theMvc.perform( MockMvcRequestBuilders.get( "/org/v1/2" ).accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isNotFound() );

		verify( theOrgService, times( 1 ) ).getById( "2" );
		verifyNoMoreInteractions( theOrgService );
	}

	@Test
	public void testGetByName() throws Exception
	{
		when( theOrgService.getByName( "Form 3" ) ).thenReturn( Org.builder().id( "1" ).name( "Form 3" ).build() );

		theMvc.perform( MockMvcRequestBuilders.get( "/org/v1/search/Form 3" ).accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isOk() )
				.andExpect( jsonPath( "$.id", is( "1" ) ) )
				.andExpect( jsonPath( "$.name", is( "Form 3" ) ) );

		verify( theOrgService, times( 1 ) ).getByName( "Form 3" );
		verifyNoMoreInteractions( theOrgService );
	}

	@Test
	public void testGetByNameFail() throws Exception
	{
		when( theOrgService.getByName( "Form 4" ) ).thenReturn( null );

		theMvc.perform( MockMvcRequestBuilders.get( "/org/v1/search/Form 4" ).accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isNotFound() );

		verify( theOrgService, times( 1 ) ).getByName( "Form 4" );
		verifyNoMoreInteractions( theOrgService );
	}

	@Test
	public void testCreate() throws Exception
	{
		Org lOrg = Org.builder().id( "1" ).name( "Form 3" ).build();
		when( theOrgService.save( lOrg ) ).thenReturn( lOrg );

		theMvc.perform( MockMvcRequestBuilders.post( "/org/v1" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( lOrg ) )
				.accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isOk() )
				.andExpect( jsonPath( "$.name", is( "Form 3" ) ) );

		verify( theOrgService, times( 1 ) ).save( any( Org.class ) );
		verifyNoMoreInteractions( theOrgService );
	}

	@Test
	public void testUpdate() throws Exception
	{
		Org lOrg = Org.builder().id( "1" ).name( "Form 4" ).build();
		when( theOrgService.getById( "1" ) ).thenReturn( Org.builder().id( "1" ).name( "Form 3" ).build() );
		when( theOrgService.save( lOrg ) ).thenReturn( lOrg );

		theMvc.perform( MockMvcRequestBuilders.put( "/org/v1/1" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( lOrg ) )
				.accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isOk() )
				.andExpect( jsonPath( "$.name", is( "Form 4" ) ) );

		verify( theOrgService, times( 1 ) ).getById( "1" );
		verify( theOrgService, times( 1 ) ).save( any( Org.class ) );
		verifyNoMoreInteractions( theOrgService );
	}

	@Test
	public void testUpdateFail() throws Exception
	{
		Org lOrg = Org.builder().id( "1" ).name( "Form 4" ).build();
		when( theOrgService.save( lOrg ) ).thenReturn( lOrg );

		theMvc.perform( MockMvcRequestBuilders.put( "/org/v1/2" ).contentType( MediaType.APPLICATION_JSON ).content( asJsonString( lOrg ) )
				.accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isNotFound() );

		verify( theOrgService, times( 1 ) ).getById( "2" );
		verifyNoMoreInteractions( theOrgService );
	}


	@Test
	public void testDelete() throws Exception
	{
		Org lOrg = Org.builder().id( "1" ).name( "Form 3" ).build();
		when( theOrgService.getById( "1" ) ).thenReturn( Org.builder().id( "1" ).name( "Form 3" ).build() );
		doNothing().when( theOrgService ).delete( lOrg );

		theMvc.perform( MockMvcRequestBuilders.delete( "/org/v1/1" )
				.accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isOk() );

		verify( theOrgService, times( 1 ) ).getById( "1" );
		verify( theOrgService, times( 1 ) ).delete( any( Org.class ) );
		verifyNoMoreInteractions( theOrgService );
	}

	@Test
	public void testDeleteFail() throws Exception
	{
		when( theOrgService.getById( "2" ) ).thenReturn( null );
		doNothing().when( theOrgService ).delete( any( Org.class ) );

		theMvc.perform( MockMvcRequestBuilders.delete( "/org/v1/2" )
				.accept( MediaType.APPLICATION_JSON ) )
				.andExpect( status().isNotFound() );

		verify( theOrgService, times( 1 ) ).getById( "2" );
		verifyNoMoreInteractions( theOrgService );
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
