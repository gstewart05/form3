package com.gmail.gstewart05.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrgRepository extends CrudRepository< Org, String >
{
	Org findByNameIgnoreCase( String pName );
}
