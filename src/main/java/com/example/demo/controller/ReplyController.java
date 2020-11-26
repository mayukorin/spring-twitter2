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

import com.example.demo.component.ArticleComponent;
import com.example.demo.component.ChannelComponent;
import com.example.demo.model.Article;
import com.example.demo.service.ArticleService;
import com.example.demo.service.ReplyService;
import com.example.demo.service.SessionService;
import com.example.demo.service.UserDetailsImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ReplyController {
	
	@Autowired
	ChannelComponent channelComponent;
	
	@Autowired
	ArticleComponent articleComponent;
	
	
	final ArticleService articleService;
	final ReplyService replyService;
	final SessionService sessionService;
	
	@GetMapping("/reply_index{id}")
	public String replyIndex(@PathVariable Long id,Model model,@AuthenticationPrincipal UserDetailsImpl userDetail,@ModelAttribute Article article) {
		
		
		
		sessionService.setArticleComponent(id);
		model.addAttribute("sessionService", sessionService);
		model.addAttribute("fromMyChannel", "reply_index"+sessionService.getArticleComponent().getArticle().getId());
		model.addAttribute("fromMyChannel2", "article_index"+channelComponent.getChannel().getId());
		
		return "replyIndex";
		
	}
	
	@PostMapping("/reply_create")
	public String replyCreate(@Validated @ModelAttribute("article") Article article,BindingResult result,@AuthenticationPrincipal UserDetailsImpl userDetail,Model model) {
		
		if (result.hasErrors()) {
			
			model.addAttribute("sessionService", sessionService);
			return "/replyIndex";
			
		} 
		
		articleService.insert(article, userDetail,sessionService.getChannelComponent().getChannel());
		replyService.insert(article, sessionService.getArticleComponent().getArticle().getIdInChannel());
		return "redirect:/reply_index"+sessionService.getArticleComponent().getArticle().getId()+"?createSuccess";
	}

}
