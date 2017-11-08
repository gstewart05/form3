package com.gmail.gstewart05.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@ToString
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Charges
{
	@Id
	@Column( nullable = false )
	@GeneratedValue( generator = "uuid" )
	@GenericGenerator( name = "uuid", strategy = "uuid2" )
	@JsonIgnore
	String id;

	String bearer_code;

	@OneToMany( cascade = {CascadeType.ALL} )
	@JoinTable(name = "Sender_Charges", joinColumns = @JoinColumn(name = "Sender_ID"), inverseJoinColumns = @JoinColumn(name = "Charge_ID"))
	List< Charge > sender_charges;

	@OneToMany( cascade = {CascadeType.ALL} )
	@JoinTable(name = "Reciever_Charges", joinColumns = @JoinColumn(name = "Reciever_ID"), inverseJoinColumns = @JoinColumn(name = "Charge_ID"))
	List< Charge > receiver_charges;
}
