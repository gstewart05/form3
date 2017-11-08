package com.gmail.gstewart05.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Charge
{
	@Id
	@Column( nullable = false )
	@GeneratedValue( generator = "uuid" )
	@GenericGenerator( name = "uuid", strategy = "uuid2" )
	@JsonIgnore
	String id;

	@Column( nullable = false )
	@NonNull
	BigDecimal	amount;

	@Column( nullable = false )
	@NonNull
	String	currency;
}
