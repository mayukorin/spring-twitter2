package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Drama;
import com.example.demo.model.Season;
import com.example.demo.model.SiteUser;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DailyCountService {

	private final SeasonService seasonService;
	private final DramaFavoriteCountService dramaFavoriteCountService;


	public void dailyCount(SiteUser user) {

		if (user.getAdmin() == true) {

			Calendar today = Calendar.getInstance();
			List<Season> seasons = seasonService.findAllSeasons();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			for (Season s:seasons) {
				
				if (dramaFavoriteCountService.collectFavoriteCountDayByDay(sdf.format(today.getTime()),s.getId()).size() > 0  || !(today.compareTo(s.getSeasonStartDay()) >= 0 && today.compareTo(s.getSeasonEndDay()) <= 0)) {

					continue;
				}


				for (Drama d:s.getDramas()) {
					dramaFavoriteCountService.insert(d);

				}


			}
		}
	}



}
