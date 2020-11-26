package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.component.ArticleComponent;
import com.example.demo.component.ChannelComponent;
import com.example.demo.component.DramaComponent;
import com.example.demo.component.SeasonComponent;
import com.example.demo.model.Article;
import com.example.demo.model.Channel;

import com.example.demo.model.Season;
import com.example.demo.repository.ArticleRepository;
import com.example.demo.repository.ChannelRepository;
import com.example.demo.repository.DramaRepository;
import com.example.demo.repository.SeasonRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SessionService {





	private final DramaRepository dramaRepository;
	private final ChannelRepository channelRepository;
	private final ArticleRepository articleRepository;
	private final SeasonRepository seasonRepository;

	@Autowired
	DramaComponent targetDramaComponent;

	@Autowired
	ChannelComponent channelComponent;

	@Autowired
	ArticleComponent articleComponent;

	@Autowired
	SeasonComponent seasonComponent;



	public void setTargetDramaComponent(Long id) {
		setSeasonComponent(dramaRepository.findById(id).get().getSeason().getId());
		targetDramaComponent.setDrama(dramaRepository.findById(id).get());
	}

	public DramaComponent getTargetDramaComponent() {
		return targetDramaComponent;
	}

	public void setNullTargetDramaComponent() {
		targetDramaComponent.setDrama(null);
	}

	public void setTragetChannelComponent(Long id) {

		Channel targetChannel = channelRepository.findById(id).get();
		channelComponent.setChannel(targetChannel);
		setTargetDramaComponent(targetChannel.getDrama().getId());

	}

	public void setNullChannelComponent() {
		channelComponent.setChannel(null);
	}

	public ChannelComponent getChannelComponent() {
		return channelComponent;
	}

	public void setArticleComponent(Long id) {

		Article targetArticle = articleRepository.findById(id).get();
		articleComponent.setArticle(targetArticle);
		setTragetChannelComponent(targetArticle.getChannel().getId());
	}

	public ArticleComponent getArticleComponent() {
		return articleComponent;
	}


	public void setSeasonComponent(Long id) {
		Season s = seasonRepository.findById(id).get();

		seasonComponent.setSeason(s);

	}
	public SeasonComponent getSeasonComponent() {
		return seasonComponent;
	}


}
