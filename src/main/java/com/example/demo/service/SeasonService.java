package com.example.demo.service;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Season;
import com.example.demo.repository.SeasonRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SeasonService {

	private final SeasonRepository seasonRepository;

	public Season insert(Calendar dramaStartDay,Calendar dramaEndDay) {

		Season s = new Season();

		Calendar seasonStartDay = (Calendar)dramaStartDay.clone();
		int day = seasonStartDay.getActualMinimum(Calendar.DAY_OF_MONTH);
		seasonStartDay.set(Calendar.DAY_OF_MONTH, day);
		seasonStartDay.add(Calendar.MONTH, -1);


		Calendar seasonEndDay = (Calendar)dramaEndDay.clone();
		int dayEnd = dramaEndDay.getActualMaximum(Calendar.DAY_OF_MONTH);
		seasonEndDay.set(Calendar.DAY_OF_MONTH, dayEnd);



		s.setSeasonEndDay(seasonEndDay);
		s.setSeasonStartDay(seasonStartDay);
		s.setDramaStartDay((Calendar)dramaStartDay.clone());

		seasonRepository.save(s);	

		return s;

	}

	public Season update(Season s,Calendar dramaEndDay) {


		Calendar seasonEndDay = (Calendar)dramaEndDay.clone();
		int dayEnd = dramaEndDay.getActualMaximum(Calendar.DAY_OF_MONTH);
		seasonEndDay.set(Calendar.DAY_OF_MONTH, dayEnd);


		s.setSeasonEndDay(seasonEndDay);
		seasonRepository.save(s);

		return s;


	}

	public List<String> findAllSeasonsStarts() {

		return seasonRepository.collectSeasonStartSimple();
	}

	public Long collectStartMonth(String start) {
		return seasonRepository.collectStartMonth(start);
	}

	public Season  CollectSeasonByStartMonth(String start) {
		return seasonRepository. CollectSeasonByStartMonth(start);
	}

	public List<Season> findAllSeasons() {
		return seasonRepository.findAll();
	}

	public Season findById(Long id) {
		return seasonRepository.findById(id).get();
	}



}
