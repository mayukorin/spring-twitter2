package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.repository.DramaFavoriteCountRepository;
import com.example.demo.model.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DramaFavoriteCountService {

	private final DramaFavoriteCountRepository dramaFavoriteCountRepository;
	private final FavoriteService favoriteService;

	public List<String> collectFavoriteCountDay(String date,Long seasonId) {
		return dramaFavoriteCountRepository.collectFavoriteCountDay(date, seasonId);
	}

	public void insert(Drama drama) {

		DramaFavoriteCount df = new DramaFavoriteCount();
		Long count = favoriteService.CountFavoriteByDrama(drama.getId());
		df.setDrama(drama);
		df.setFavoriteCount(count);

		dramaFavoriteCountRepository.save(df);

	}

	public List<String> collectFavoriteCountDayByDay(String date,Long seasonId) {
		return dramaFavoriteCountRepository.collectFavoriteCountDayByDay(date, seasonId);
	}

	public DramaFavoriteCount collectFavoriteCountByDramaAndCreatedAt(String day,Long dramaId) {
		return dramaFavoriteCountRepository.collectFavoriteCountByDramaAndCreatedAt(day, dramaId);

	}

	public List<DramaFavoriteCount> collectDramaFavoriteCountByDrama(Long id) {
		return dramaFavoriteCountRepository.collectDramaFavoriteCountByDrama(id);
	}

	public void deleteById(Long id) {
		dramaFavoriteCountRepository.deleteById(id);
	}

}
