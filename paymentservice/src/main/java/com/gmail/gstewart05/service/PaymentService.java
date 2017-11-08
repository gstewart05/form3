package com.gmail.gstewart05.service;

import com.gmail.gstewart05.model.Payment;

import java.util.List;

public interface PaymentService
{
	List< Payment > getAll();
	Payment getById( String pID );
	void delete( Payment pOrg );
	void delete( String pID );
	Payment save( Payment pOrg );
}
