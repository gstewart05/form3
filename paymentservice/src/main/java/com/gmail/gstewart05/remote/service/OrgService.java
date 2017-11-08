package com.gmail.gstewart05.remote.service;

import com.gmail.gstewart05.remote.model.Org;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient( "org-service" )
@RequestMapping( "/org/v1" )
public interface OrgService
{
	@RequestMapping( "/search/{name}" )
	Org getOrgByName( @PathVariable( "name" ) String pName );

	@RequestMapping( "{id}" )
	Org getOrgById( @PathVariable( "id" ) String pID );
}
