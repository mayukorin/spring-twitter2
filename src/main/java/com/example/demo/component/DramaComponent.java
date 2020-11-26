package com.example.demo.component;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import org.springframework.context.annotation.ScopedProxyMode;

import com.example.demo.model.Drama;

import lombok.Getter;
import lombok.Setter;

@Component
@Scope(value= "session",proxyMode = ScopedProxyMode.TARGET_CLASS)
@SuppressWarnings("serial")
@Getter
@Setter
public class DramaComponent implements Serializable {
	
	private Drama drama;
	


}
