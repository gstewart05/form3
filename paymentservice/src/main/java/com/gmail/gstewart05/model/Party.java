package com.gmail.gstewart05.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Party
{
	@Id
	@Column( nullable = false )
	@GeneratedValue( generator = "uuid" )
	@GenericGenerator( name = "uuid", strategy = "uuid2" )
	@JsonIgnore
	String id;

	String name;

	String account_name;

	@Column( nullable = false )
	@NonNull
	String account_number;

	String account_number_code;

	long account_type;

	String address;

	@Column( nullable = false )
	@NonNull
	long bank_id;

	@Column( nullable = false )
	@NonNull
	String bank_id_code;
}
