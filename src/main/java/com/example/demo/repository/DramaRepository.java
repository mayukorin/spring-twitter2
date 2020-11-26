package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.example.demo.model.Drama;

public interface DramaRepository extends JpaRepository<Drama,Long> {
	
	@Query("select d from Drama d where d.id = :#{#id}")
	Drama serachDramaById(Long id);
	
	@Query("select d from Drama d where d.name = :#{#name}")
	Drama searchDramaByName(String name);
	
	@Query(value="select distinct(date_format(d.start_day,'%Y-%m')) from drama as d order by d.start_day desc",nativeQuery=true)
	List<String> collectStartMonth();
	
	@Query(value="select date_format(d.start_day,'%Y-%m') from drama as d where d.id = :#{#id}",nativeQuery=true)
	String GetStartMonth(Long id);
	
	
	@Query(value="select * from Drama as d where date_format(d.start_day,'%Y-%m') = :#{#datee} order by d.start_day desc",nativeQuery=true)
	List<Drama> collectDramaByStart(String datee);
	
	@Query(value="select d.name from Drama as d where date_format(d.start_day,'%Y-%m') = :#{#datee}",nativeQuery=true)
	List<String> collectDramaNameByStart(String datee);
	
	@Query("select d from Drama d,Favorite f where f.user.id = :#{#id} and d.id = f.drama.id")
	List<Drama> collectDramaFavoriteByUser(Long id);
	
	@Query("select d from Drama d where d.season.id = :#{#id} order by d.startDay desc")
	List<Drama> collectDramaBySeason(Long id);
	
	@Query(value="select * from Drama as d where d.name like %:#{#keyword}%",nativeQuery=true)
	List<Drama> collectDramaByKeyword(String keyword);
	
	@Query("select d from Drama d where d.creater.id = :#{#id}")
	List<Drama> collectDramaByCreater(Long id);
	
	
}
