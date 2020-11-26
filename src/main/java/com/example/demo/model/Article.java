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
import javax.validation.constraints.Size;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Article {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private Long IdInChannel;
	
	private Long deleteFlag;
	
	@NotBlank
	@Size(max=140)
	private String content;
	
	private Date created_at;
	
	@ManyToOne
	private SiteUser siteuser;
	
	
	@ManyToOne
	private Channel channel;
	
	@OneToMany(mappedBy="ReplyArticle")
	private List<Reply> MyReplys;
	
	@OneToMany(mappedBy="targetArticle")
	private List<Reply> replysForMe;
	
	
	
	@PrePersist
	public void onPrePersist() {
		setCreated_at(new Date());
	}
	
	
	
	

}
