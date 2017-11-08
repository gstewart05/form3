package com.gmail.gstewart05.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Attributes
{
	@Id
	@Column( nullable = false )
	@GeneratedValue( generator = "uuid" )
	@GenericGenerator( name = "uuid", strategy = "uuid2" )
	@JsonIgnore
	String id;

	@Column( nullable = false )
	@NonNull
	BigDecimal amount;

	@OneToOne( cascade = {CascadeType.ALL} )
	@NonNull
	Party beneficiary_party;

	@OneToOne( cascade = {CascadeType.ALL} )
	@NonNull
	Charges charges_information;

	@Column( nullable = false )
	@NonNull
	String currency;

	@OneToOne( cascade = {CascadeType.ALL} )
	@NonNull
	Party debtor_party;

	@Column( nullable = false )
	@NonNull
	String end_to_end_reference;

	@OneToOne( cascade = {CascadeType.ALL} )
	@NonNull
	FX fx;

	@Column( nullable = false )
	@NonNull
	long numeric_reference;

	@Column( nullable = false )
	@NonNull
	String payment_id;

	@Column( nullable = false )
	@NonNull
	String payment_purpose;

	@Column( nullable = false )
	@NonNull
	String payment_scheme;

	@Column( nullable = false )
	@NonNull
	String payment_type;

	@Column( nullable = false )
	@NonNull
	String processing_date;

	@Column( nullable = false )
	@NonNull
	String reference;

	@Column( nullable = false )
	@NonNull
	String scheme_payment_sub_type;

	@Column( nullable = false )
	@NonNull
	String scheme_payment_type;

	@OneToOne( cascade = {CascadeType.ALL} )
	@NonNull
	Party sponsor_party;
}
