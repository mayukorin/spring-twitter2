package com.example.demo.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;


import com.example.demo.validator.UniqueDrama;


import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
@Entity
public class Drama {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@UniqueDrama
	private String name;
	
	
	private Calendar startDay;
	
	private Calendar endDay;
	
	
	@ManyToOne
	private Season season;
	
	@ManyToOne
	private SiteUser creater;
	
	@OneToMany(mappedBy="drama")
	private List<Favorite> favorites;
	
	@OneToMany(mappedBy="drama")
	private List<DramaFavoriteCount> favoriteCounts;
	
	@OneToMany(mappedBy="drama")
	private List<Channel> channels;
	
	
	public String translateCalendarToString1(Calendar c) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		
		String stringDay = sdf.format(c.getTime());
		
		return stringDay;
	}
	
	public String translateCalendarToString2(Calendar c) {
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			
			String stringDay = sdf.format(c.getTime());
			
			return stringDay;
		}
	
	public String translateCalendarToSimpleYearAndMonth(Calendar c) {
		
		return c.get(Calendar.YEAR)+"-"+(c.get(Calendar.MONTH)+1);
	}
	
	
	
	
	

}
