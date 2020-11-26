package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.example.demo.model.DramaFavoriteCount;

public interface DramaFavoriteCountRepository extends JpaRepository<DramaFavoriteCount,Long> {
	
	@Query(value="select distinct(date_format(df.created_at,'%Y-%m-%d')) from drama_favorite_count df,drama d where date_format(df.created_at,'%Y-%m') = :#{#datee} and df.drama_id = d.id and d.season_id = :#{#id}",nativeQuery=true)
	List<String> collectFavoriteCountDay(String datee,Long id);
	
	@Query(value="select distinct(date_format(df.created_at,'%Y-%m-%d')) from drama_favorite_count df,drama d where date_format(df.created_at,'%Y-%m-%d') = :#{#datee} and df.drama_id = d.id and d.season_id = :#{#id}",nativeQuery=true)
	List<String> collectFavoriteCountDayByDay(String datee,Long id);
	
	@Query(value="select * from drama_favorite_count df where date_format(df.created_at,'%Y-%m-%d') = :#{#date} and df.drama_id = :#{#id}",nativeQuery=true)
	DramaFavoriteCount collectFavoriteCountByDramaAndCreatedAt(String date,Long id);
	
	@Query("select df from DramaFavoriteCount as df where df.drama.id = :#{#id}")
	List<DramaFavoriteCount> collectDramaFavoriteCountByDrama(Long id);

}
