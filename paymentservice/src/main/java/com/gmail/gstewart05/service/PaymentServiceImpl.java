package com.gmail.gstewart05.service;

import com.gmail.gstewart05.model.Payment;
import com.gmail.gstewart05.model.PaymentRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService
{
	@Autowired
	private PaymentRepository theRepository;

	@Override
	public List< Payment > getAll()
	{
		return Lists.newArrayList( theRepository.findAll() );
	}

	@Override
	public Payment getById( String pID )
	{
		return theRepository.findOne( pID );
	}

	@Override
	public void delete( Payment pOrg )
	{
		theRepository.delete( pOrg );
	}

	@Override
	public void delete( String pID )
	{
		theRepository.delete( pID );
	}

	@Override
	public Payment save( Payment pOrg )
	{
		return theRepository.save( pOrg );
	}
}
