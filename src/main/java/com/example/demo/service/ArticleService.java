package com.example.demo.service;

import java.util.List;


import org.springframework.stereotype.Service;

import com.example.demo.model.Article;
import com.example.demo.model.Channel;
import com.example.demo.model.Reply;
import com.example.demo.repository.ArticleRepository;


import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ArticleService {


	private final ArticleRepository articleRepository;
	private final SessionService sessionService;
	private final LineNodifyService lineNodifyService;
	private final ReplyService replyService;

	public void insert(Article article,UserDetailsImpl userdetail,Channel channel) {


		article.setSiteuser(userdetail.getSiteUser());

		article.setChannel(channel);


		if (sessionService.getChannelComponent().getChannel() == null ||FindMaxIdInColumn(sessionService.getChannelComponent().getChannel().getId()) == null) {
			article.setIdInChannel(1L);
		} else {
			article.setIdInChannel(FindMaxIdInColumn(sessionService.getChannelComponent().getChannel().getId())+1);
		}

		article.setDeleteFlag(0L);
		articleRepository.save(article);

		lineNodifyService.notify(article);

	}

	public void defaultInsert(Channel c) {

		Article a = new Article();

		a.setChannel(c);
		a.setContent("このチャンネルはドラマの放送日などを主に投稿するチャンネルです。このチャンネルの投稿内容は、ドラマのお気に入り登録者のところに通知されます。");
		a.setDeleteFlag(0L);
		a.setIdInChannel(1L);
		articleRepository.save(a);		
	}

	public void delete(Long id) {

		Article deleteArticle  = articleRepository.findById(id).get();

		for(Reply r:deleteArticle.getReplysForMe()) {
			replyService.deleteById(r.getId());
		}
		deleteArticle.setReplysForMe(null);

		for(Reply r:deleteArticle.getMyReplys()) {

			replyService.deleteById(r.getId());
		}

		deleteArticle.setMyReplys(null);

		deleteArticle.setDeleteFlag(1L);
		articleRepository.save(deleteArticle);
	}

	public List<Article> collectArticlesByChannelId(Long id) {
		return articleRepository.searchArticleByChannel(id);
	}
	
	public List<Article> searchAllArticleByChannel(Long id) {
		return articleRepository.searchAllArticleByChannel(id);
	}

	public Long FindMaxIdInColumn(Long id) {
		return articleRepository.FindMaxIdInColumn(id);
	}

	public List<Article> collectMyArticle(Long id) {
		return articleRepository.serachArticleBySiteuser(id);
	}

	public void deleteById(Long id) {
		articleRepository.deleteById(id);
	}

	public Article findById(Long id) {
		return articleRepository.findById(id).get();
	}





}
