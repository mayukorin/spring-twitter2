package com.example.demo.component;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.example.demo.model.Channel;


import lombok.Getter;
import lombok.Setter;

@Component
@Scope(value= "session",proxyMode = ScopedProxyMode.TARGET_CLASS)
@SuppressWarnings("serial")
@Getter
@Setter
public class ChannelComponent  implements Serializable  {
	
	private Channel channel;

}
