package com.gmail.gstewart05.model;

import lombok.*;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Org
{
	@Id
	@Column( nullable = false )
	@GeneratedValue( generator = "uuid" )
	@GenericGenerator( name = "uuid", strategy = "uuid2" )
	String id;

	@Column( nullable = false)
	@NonNull
	String name;
}
