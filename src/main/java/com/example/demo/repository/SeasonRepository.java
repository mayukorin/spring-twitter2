package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.Season;

public interface SeasonRepository extends JpaRepository<Season,Long> {
	
	@Query(value="select count(*) from season as s where date_format(s.drama_start_day,'%Y-%m') = :#{#datee} ",nativeQuery=true)
	Long collectStartMonth(String datee);
	
	@Query(value="select * from season as s where date_format(s.drama_start_day,'%Y-%m') = :#{#datee} ",nativeQuery=true)
	Season CollectSeasonByStartMonth(String datee);
	
	@Query(value="select date_format(s.drama_start_day,'%Y-%m') from season as s where s.id = :#{#id}",nativeQuery=true)
	String searchSeasonStartSimple(Long id);
	
	@Query(value="select date_format(s.drama_start_day,'%Y-%m') from season as s order by s.drama_start_day desc",nativeQuery=true)
	List<String> collectSeasonStartSimple();
	
	@Query(value="select * from season as s where date_format(s.drama_start_day,'%Y-%m') = :#{#datee} ",nativeQuery=true)
	Season CollectSeasonByDramaStartMonth(String datee);

	

}
