package com.gmail.gstewart05.service;

import com.gmail.gstewart05.model.Org;

import java.util.List;

public interface OrgService
{
	List< Org > getAll();
	Org getById( String pID );
	Org getByName( String pName );
	void delete( Org pOrg );
	void delete( String pID );
	Org save( Org pOrg );
}
