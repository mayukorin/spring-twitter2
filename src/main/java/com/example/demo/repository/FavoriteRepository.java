package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Favorite;
import com.example.demo.model.SiteUser;
public interface FavoriteRepository extends JpaRepository<Favorite,Long> {
	
	@Query("select count(f) from Favorite as f where f.user.id=:#{#id1} and f.drama.id=:#{#id2}")
	Long CountFavoriteByUserAndDrama(Long id1,Long id2);
	
	@Query("select f from Favorite as f where f.user.id=:#{#id1} and f.drama.id=:#{#id2}")
	Favorite FindFavoriteByUserAndDrama(Long id1,Long id2);
	
	@Query("select f from Favorite as f where f.user.id=:#{#id1}")
	List<Favorite> FindFavoriteByUser(Long id1);
	
	@Query("select count(f) from Favorite as f where f.drama.id = :#{#id}")
	Long CountFavoriteByDrama(Long id);
	
	@Query("select f from Favorite as f where f.drama.id = :#{#id}")
	List<Favorite> collectFavoritesByDrama(Long id);
	
	@Query("select f.user from Favorite as f where f.drama.id = :#{#id}")
	List<SiteUser> collectFavoriteUsers(Long id);

}
