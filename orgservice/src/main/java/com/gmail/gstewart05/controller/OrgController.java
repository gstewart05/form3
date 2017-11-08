package com.gmail.gstewart05.controller;

import com.gmail.gstewart05.model.Org;
import com.gmail.gstewart05.model.OrgRepository;
import com.gmail.gstewart05.service.OrgService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping( "/org/v1" )
@RestController
public class OrgController
{
	@Autowired
	OrgService theOrgService;

	@PostMapping( "" )
	public ResponseEntity create( @RequestBody Org pEntity )
	{
		theOrgService.save( pEntity );
		return new ResponseEntity( pEntity, HttpStatus.OK );
	}

	@PutMapping( "/{id}" )
	public ResponseEntity update( @PathVariable( "id" ) String pID, @RequestBody Org pEntity )
	{
		Org lEntity = theOrgService.getById( pID );
		if( lEntity != null )
		{
			pEntity.setId( pID );
			theOrgService.save( pEntity );
			return new ResponseEntity( pEntity, HttpStatus.OK );
		}
		return new ResponseEntity( "{\"status\":404,\"message\": \"No Org found for id: " + pID + "\"}", HttpStatus.NOT_FOUND );
	}

	@DeleteMapping( "/{id}" )
	public ResponseEntity delete( @PathVariable( "id" ) String pID )
	{
		Org lEntity = theOrgService.getById( pID );
		if( lEntity != null )
		{
			theOrgService.delete( lEntity );
			return new ResponseEntity( "{\"status\":200,\"message\": \"Ok\"}", HttpStatus.OK );
		}
		return new ResponseEntity( "{\"status\":404,\"message\": \"No Org found for id: " + pID + "\"}", HttpStatus.NOT_FOUND );
	}

	@GetMapping( "" )
	public ResponseEntity list()
	{
		return new ResponseEntity( theOrgService.getAll(), HttpStatus.OK );
	}

	@GetMapping( "/search/{name}" )
	public ResponseEntity search( @PathVariable( "name" ) String pName )
	{
		Org lEntity = theOrgService.getByName( pName );
		if( lEntity != null )
		{
			return new ResponseEntity( lEntity, HttpStatus.OK );
		}
		return new ResponseEntity( "{\"status\":404,\"message\": \"No Org found for name: " + pName + "\"}", HttpStatus.NOT_FOUND );
	}

	@GetMapping( "/{id}" )
	public ResponseEntity get( @PathVariable( "id" ) String pID )
	{
		Org lEntity = theOrgService.getById( pID );
		if( lEntity != null )
		{
			return new ResponseEntity( lEntity, HttpStatus.OK );
		}
		return new ResponseEntity( "{\"status\":404,\"message\": \"No Org found for id: " + pID + "\"}", HttpStatus.NOT_FOUND );
	}
}
