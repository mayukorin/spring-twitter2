package com.example.demo.validator;

import org.springframework.stereotype.Component;

import com.example.demo.repository.ArticleRepository;
import com.example.demo.service.SessionService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ArticleTargetIdValidator {

	private final ArticleRepository articleRepository;
	private final SessionService sessionService;

	public boolean articleTargetIdCheck(Long id) {
		return id == null || articleRepository.countArticleByChannelId(id,sessionService.getChannelComponent().getChannel().getId()) != 0;
	}

}
