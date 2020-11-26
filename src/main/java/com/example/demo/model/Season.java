package com.example.demo.model;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Season {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Calendar seasonStartDay;
	
	private Calendar seasonEndDay;
	
	private Calendar dramaStartDay;
	
	
	
	@OneToMany(mappedBy="season")
	private List<Drama> dramas;
	
	public String translateCalendarToSimpleYearAndMonth(Calendar c) {
		
		return c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1);
	}
	public String translateCalendarToSimpleYearAndMonth2(Calendar c) {
		
		return c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+2);
	}

}
