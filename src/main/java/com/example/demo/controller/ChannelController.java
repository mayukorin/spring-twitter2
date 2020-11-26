package com.example.demo.controller;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.component.DramaComponent;

import com.example.demo.model.Article;
import com.example.demo.model.Channel;


import com.example.demo.service.ArticleService;
import com.example.demo.service.ChannelService;
import com.example.demo.service.FavoriteService;
import com.example.demo.service.SessionService;
import com.example.demo.service.UserDetailsImpl;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class ChannelController {
	
	@Autowired
	DramaComponent targetDramaComponent;
	
	
	
	
	private final ArticleService articleService;
	private final ChannelService channelService;
	private final SessionService sessionService;
	private final FavoriteService favoriteService;
	
	@GetMapping("/channelIndex{id}")
	public String channelIndex(@PathVariable Long id,Model model,@ModelAttribute("deleteError") String deleteError,@AuthenticationPrincipal UserDetailsImpl userDetail) {
		
		sessionService.setTargetDramaComponent(id);
		sessionService.setNullChannelComponent();
		
		
		model.addAttribute("channels",channelService.getChannelsByDramaId(id));
		model.addAttribute("sessionService", sessionService);
		
		
		model.addAttribute("favoriteFlag", favoriteService.CountFavoriteByUserAndDrama(userDetail.getSiteUser().getId(),id));
		model.addAttribute("fromMyChannel","channelIndex"+sessionService.getTargetDramaComponent().getDrama().getId());
		
		
		
		return "channelIndex";
		
	}
	
	@GetMapping("/channel_new")
	public String channelNew(Model model, @ModelAttribute Article article,@ModelAttribute Channel channel) {
		
		
		
		model.addAttribute("sessionService", sessionService);
		
		return "channelNew";
	}
	
	@PostMapping("/channel_create")
	public String channelCreate(Model model,RedirectAttributes redirectAttributes,@Validated @ModelAttribute("article") Article article,BindingResult resultArticle,@Validated @ModelAttribute("channel") Channel channel,BindingResult resultChannel,@AuthenticationPrincipal UserDetailsImpl userDetail) {
		
		
		if (resultArticle.hasErrors() || resultChannel.hasErrors()) {
			model.addAttribute("sessionService", sessionService);
			return "channelNew";
		}
		channelService.insert(channel, userDetail,sessionService.getTargetDramaComponent().getDrama());
		articleService.insert(article, userDetail,channel);
		
		return "redirect:/channelIndex"+sessionService.getTargetDramaComponent().getDrama().getId()+"?createSuccess";
	}
	
	@GetMapping("/channel_delete/{id}/{fromMyChannel}")
	public String channelDelete(@PathVariable Long id,@PathVariable String fromMyChannel,@AuthenticationPrincipal UserDetailsImpl userDetail,Model model,RedirectAttributes redirectAttributes) {
		
		
		channelService.delete(id);
		
		return "redirect:/"+fromMyChannel+"?channelDelete";
		
	}
	
	@GetMapping("/myChannel")
	public String MyChannel(@AuthenticationPrincipal UserDetailsImpl userDetail,Model model) {
		
		model.addAttribute("myChannels", channelService.findMyCreateChannel(userDetail.getSiteUser().getId()));
		model.addAttribute("fromMyChannel","myChannel");
		return "MyChannel";
	}
	
	

}
