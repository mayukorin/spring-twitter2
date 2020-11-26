package com.example.demo.service;

import java.util.Collection;


import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.core.userdetails.User;


import com.example.demo.model.SiteUser;

public class UserDetailsImpl extends User {

	private final SiteUser siteuser;

	public UserDetailsImpl(SiteUser siteuser,Collection<GrantedAuthority> authorities) {

		super(siteuser.getName(),siteuser.getPassword(),authorities);

		this.siteuser = siteuser;
	}


	public SiteUser getSiteUser() {
		return siteuser;
	}



	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return super.getAuthorities();
	}
	@Override
	public String getPassword() {
		return siteuser.getPassword();
	}

	public void setPassword(String password) {

		this.siteuser.setPassword(password);
	}
	@Override
	public String getUsername() {
		return siteuser.getName();
	}
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	@Override
	public boolean isEnabled() {
		return true;
	}

}
