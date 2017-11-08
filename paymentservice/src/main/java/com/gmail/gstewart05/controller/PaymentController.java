package com.gmail.gstewart05.controller;

import com.gmail.gstewart05.model.Payment;
import com.gmail.gstewart05.model.PaymentRepository;
import com.gmail.gstewart05.remote.model.Org;
import com.gmail.gstewart05.remote.service.OrgService;
import com.gmail.gstewart05.service.PaymentService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping( "/payment/v1" )
@RestController
public class PaymentController
{
	@Autowired
	PaymentService thePaymentService;

	@Autowired
	OrgService theOrgService;

	@PostMapping( "" )
	public ResponseEntity create( @RequestBody Payment pEntity )
	{
		try
		{
			//Check the requested org is a recognised one
			Org lOrg = theOrgService.getOrgById( pEntity.getOrganisation_id() );
			if( lOrg != null )
			{
				thePaymentService.save( pEntity );
				return new ResponseEntity( pEntity, HttpStatus.OK );
			}
		}
		catch( Exception e )
		{
		}
		return new ResponseEntity( "{\"status\":404,\"message\": \"No Org found for id: " + pEntity.getOrganisation_id() + "\"}", HttpStatus.NOT_FOUND );
	}

	@PutMapping( "/{id}" )
	public ResponseEntity update( @PathVariable( "id" ) String pID, @RequestBody Payment pEntity )
	{
		try
		{
			Payment lEntity = thePaymentService.getById( pID );
			pEntity.setId( pID );
			if( lEntity != null )
			{
				Org lOrg = theOrgService.getOrgById( pEntity.getOrganisation_id() );
				if( lOrg != null )
				{
					thePaymentService.save( pEntity );
					return new ResponseEntity( pEntity, HttpStatus.OK );
				}
				else
				{
					return new ResponseEntity( "{\"status\":404,\"message\": \"No Org found for id: " + pEntity.getOrganisation_id() + "\"}", HttpStatus.NOT_FOUND );
				}
			}
		}
		catch( Exception e )
		{
			return new ResponseEntity( "{\"status\":404,\"message\": \"No Org found for id: " + pEntity.getOrganisation_id() + "\"}", HttpStatus.NOT_FOUND );
		}
		return new ResponseEntity( "{\"status\":404,\"message\": \"No Payment found for id: " + pID + "\"}", HttpStatus.NOT_FOUND );
	}

	@DeleteMapping( "/{id}" )
	public ResponseEntity delete( @PathVariable( "id" ) String pID )
	{
		Payment lEntity = thePaymentService.getById( pID );
		if( lEntity != null )
		{
			thePaymentService.delete( lEntity );
			return new ResponseEntity( "{\"status\":200,\"message\": \"Ok\"}", HttpStatus.OK );
		}
		return new ResponseEntity( "{\"status\":404,\"message\": \"No Payment found for id: " + pID + "\"}", HttpStatus.NOT_FOUND );
	}

	@GetMapping( "" )
	public ResponseEntity list()
	{
		return new ResponseEntity( Lists.newArrayList( thePaymentService.getAll() ), HttpStatus.OK );
	}

	@GetMapping( "/{id}" )
	public ResponseEntity get( @PathVariable( "id" ) String pID )
	{
		Payment lEntity = thePaymentService.getById( pID );
		if( lEntity != null )
		{
			return new ResponseEntity( lEntity, HttpStatus.OK );
		}
		return new ResponseEntity( "{\"status\":404,\"message\": \"No Payment found for id: " + pID + "\"}", HttpStatus.NOT_FOUND );
	}
}
