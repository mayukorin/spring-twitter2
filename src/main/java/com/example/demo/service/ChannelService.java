package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Article;
import com.example.demo.model.Channel;
import com.example.demo.model.Drama;
import com.example.demo.model.Reply;

import com.example.demo.repository.ChannelRepository;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ChannelService {

	private final ChannelRepository channelRepository;
	public final ArticleService articleService;
	public final ReplyService replyService;

	public void insert(Channel channel,UserDetailsImpl userdetail,Drama drama) {

		channel.setCreater(userdetail.getSiteUser());
		channel.setDrama(drama);

		channelRepository.save(channel);



	}

	public void delete(Long id) {

		List<Article> articleByChannel = articleService.searchAllArticleByChannel(id);

		for (Article article:articleByChannel) {
			for (Reply r:article.getReplysForMe()) {
				replyService.deleteById(r.getId());
			}
			
			for (Reply r:article.getMyReplys()) {
				replyService.deleteById(r.getId());
			}

			articleService.deleteById(article.getId());

		}

		channelRepository.deleteById(id);

	}

	public boolean deleteCheck(Long id,UserDetailsImpl userdetail) {

		return userdetail.getSiteUser().getId() == channelRepository.findById(id).get().getCreater().getId();
	}

	public List<Channel> getChannelsByDramaId(Long id) {

		return channelRepository.findChannelByDramaId(id);
	}

	public List<Channel> findMyCreateChannel(Long id) {

		return channelRepository.findChannelByCreater(id);
	}

	public Channel findById(Long id) {
		return channelRepository.findById(id).get();
	}

}
