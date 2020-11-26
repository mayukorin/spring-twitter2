package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.repository.ReplyRepository;
import com.example.demo.model.Article;
import com.example.demo.model.Reply;
import com.example.demo.repository.ArticleRepository;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReplyService {

	private final ReplyRepository replyRepository;
	private final ArticleRepository articleRepository;
	private final SessionService sessionService;

	public void insert(Article article,Long targetArticleId) {

		if (targetArticleId != null) {

			Reply reply = new Reply();

			reply.setReplyArticle(article);

			reply.setTargetArticle(articleRepository.findArticleByChannelId(targetArticleId,sessionService.getChannelComponent().getChannel().getId()));

			replyRepository.save(reply);
		}
	}

	public void deleteById(Long id) {
		replyRepository.deleteById(id);
	}

}
