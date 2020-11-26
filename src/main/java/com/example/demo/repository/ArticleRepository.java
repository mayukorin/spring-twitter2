package com.example.demo.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Article;




public interface ArticleRepository extends JpaRepository<Article,Long> {
	

	@Query("select a from Article a where a.siteuser.id = :#{#id} and a.deleteFlag = 0 order by a.created_at desc")
	List<Article> serachArticleBySiteuser(Long id);
	
	@Query("select a from Article a where a.id = :#{#id}")
	Article serachArticleById(Long id);
	
	@Query("select a from Article as a where a.channel.id=:#{#id} and a.deleteFlag = 0")
	List<Article> searchArticleByChannel(Long id);
	
	@Query("select a from Article as a where a.channel.id=:#{#id}")
	List<Article> searchAllArticleByChannel(Long id);
	
	@Query("select count(a) from Article as a where a.id=:#{#id}")
	Long countArticleById(Long id);
	
	@Query("select count(a) from Article as a where a.IdInChannel=:#{#id} and a.channel.id = :#{#id2} and a.deleteFlag = 0")
	Long countArticleByChannelId(Long id,Long id2);
	
	@Query("select a from Article as a where a.IdInChannel=:#{#id} and a.channel.id = :#{#id2}")
	Article findArticleByChannelId(Long id,Long id2);
	
	
	
	@Query(value="select max(id_in_channel) from Article as a where a.channel_id =:#{#id}",nativeQuery=true)
	Long FindMaxIdInColumn(Long id);
	
	

	
	
	
}
