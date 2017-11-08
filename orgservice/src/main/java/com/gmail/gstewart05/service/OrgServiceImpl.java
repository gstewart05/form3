package com.gmail.gstewart05.service;

import com.gmail.gstewart05.model.Org;
import com.gmail.gstewart05.model.OrgRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrgServiceImpl implements OrgService
{
	@Autowired
	OrgRepository theRepository;

	@Override
	public List< Org > getAll()
	{
		return Lists.newArrayList( theRepository.findAll() );
	}

	@Override
	public Org getById( String pID )
	{
		return theRepository.findOne( pID );
	}

	@Override
	public Org getByName( String pName )
	{
		return theRepository.findByNameIgnoreCase( pName );
	}

	@Override
	public void delete( Org pOrg )
	{
		theRepository.delete( pOrg );
	}

	@Override
	public void delete( String pID )
	{
		theRepository.delete( pID );
	}

	@Override
	public Org save( Org pOrg )
	{
		return theRepository.save( pOrg );
	}

}
