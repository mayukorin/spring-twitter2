package com.example.demo.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Channel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	private String title;
	
	@ManyToOne
	private Drama drama;
	
	@ManyToOne
	private SiteUser creater;
	
	private Date created_at;
	
	@OneToMany(mappedBy="channel")
	private List<Article> articles;
	
	@PrePersist
	public void onPrePersist() {
		setCreated_at(new Date());
	}
	

}
