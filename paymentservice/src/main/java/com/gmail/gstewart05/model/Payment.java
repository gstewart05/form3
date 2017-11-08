package com.gmail.gstewart05.model;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment
{
	@Id
	@Column( nullable = false )
	@GeneratedValue( generator = "uuid" )
	@GenericGenerator( name = "uuid", strategy = "uuid2" )
	String id;

	@Column( nullable = false )
	@NonNull
	String type;

	@Column( nullable = false )
	@NonNull
	int version;

	@Column( nullable = false )
	@NonNull
	String organisation_id;

	@OneToOne( cascade = {CascadeType.ALL} )
	@NonNull
	Attributes attributes;
}
