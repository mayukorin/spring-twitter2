package com.example.demo.model;


import java.util.Date;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DramaFavoriteCount {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Date created_at;
	
	@ManyToOne
	private Drama drama;
	
	private Long favoriteCount;
	
	@PrePersist
	public void onPrePersist() {
		setCreated_at(new Date());
	}

}
