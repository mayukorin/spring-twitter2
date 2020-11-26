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
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.component.ChannelComponent;
import com.example.demo.component.DramaComponent;
import com.example.demo.model.Article;
import com.example.demo.service.ArticleService;
import com.example.demo.service.ReplyService;
import com.example.demo.service.SessionService;
import com.example.demo.service.UserDetailsImpl;
import com.example.demo.validator.ArticleTargetIdValidator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ArticleController {
	
	
	private final ArticleService articleService;
	
	
	
	private final ReplyService replyService;
	private final SessionService sessionService;
	private final ArticleTargetIdValidator articleTargetIdValidator;
	
	@Autowired
	ChannelComponent channelComponent;
	
	@Autowired
	DramaComponent targetDramaComponent;
	

	
	@GetMapping("/article_new")
	public String articleNew(@ModelAttribute("article") Article article,Model model) {
		
		
		
		
		model.addAttribute("channel", sessionService.getChannelComponent().getChannel());
		model.addAttribute("sessionService", sessionService);
		return "articleNew";
	}
	
	
	@PostMapping("/article_create")
	public String articleCreate(@Validated @ModelAttribute("article") Article article,BindingResult result,@AuthenticationPrincipal UserDetailsImpl userDetail,@RequestParam("targetArticleId") Long id,Model model) {
		
		
		
		if (result.hasErrors() || !articleTargetIdValidator.articleTargetIdCheck(id)) {
			
			if (!articleTargetIdValidator.articleTargetIdCheck(id)) {
				
				model.addAttribute("target_error", "その番号のメッセージは存在しません");
				
			}
			model.addAttribute("sessionService", sessionService);
			model.addAttribute("targetAt", id);
			return "articleNew";
		}
		
		articleService.insert(article, userDetail, channelComponent.getChannel());
		replyService.insert(article, id);
		return "redirect:/article_index"+channelComponent.getChannel().getId()+"?createSuccess";
	}

	
	@GetMapping("/article_delete/{id}/{fromMyChannel}")
	public String articleDelter(@PathVariable Long id,@PathVariable String fromMyChannel) {
		
		articleService.delete(id);
		
		return "redirect:/"+fromMyChannel+"?delete";
		
	}
	
	@GetMapping("/article_index{id}")
	public String articleIndex(@PathVariable Long id,Model model) {
		
		sessionService.setTragetChannelComponent(id);
		model.addAttribute("sessionService", sessionService);
		model.addAttribute("articles", articleService.collectArticlesByChannelId(id));
		model.addAttribute("fromMyChannel", "article_index"+channelComponent.getChannel().getId());
		
		return "articleIndex";
		
	
		
	}
	
	@GetMapping("/myArticle")
	public String myArticle(@AuthenticationPrincipal UserDetailsImpl userDetail,Model model) {
		
		model.addAttribute("myArticles",articleService.collectMyArticle(userDetail.getSiteUser().getId()));
		model.addAttribute("fromMyChannel", "myArticle");
		
		
		return "myArticle";
	}

}
