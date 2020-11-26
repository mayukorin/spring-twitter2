package com.example.demo.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.model.Drama;
import com.example.demo.model.DramaFavoriteCount;
import com.example.demo.model.Season;



import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChartService {


	private final DramaFavoriteCountService dramaFavoriteCountDayService;
	private final DramaService dramaService;

	public Map<List<String>,Map<String,List<Long>>> createChart(Season s) {

		List<Drama> dramas = dramaService.collectDramaBySeason(s.getId());
		Map<List<String>,Map<String,List<Long>>> monthDatas = new LinkedHashMap<>();

		if (dramas.size() > 0) {
			Calendar startDay = (Calendar)s.getSeasonStartDay().clone();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM");




			for (;startDay.compareTo(s.getSeasonEndDay()) <= 0;startDay.add(Calendar.MONTH, 1)) {

				Map<String,List<Long>> countData = new LinkedHashMap<>();

				List<String> days = dramaFavoriteCountDayService.collectFavoriteCountDay(sdf2.format(startDay.getTime()),s.getId());

				if (days.size() == 0) {
					days.add(sdf.format(startDay.getTime()));
					days.add(sdf2.format(startDay.getTime())+"-14");

				}


				for (Drama d:dramas) {

					List<Long> counts = new ArrayList<>();
					for (String day:days) {
						DramaFavoriteCount df = dramaFavoriteCountDayService.collectFavoriteCountByDramaAndCreatedAt(day, d.getId());


						if (df == null) {
							counts.add(0L);

						} else {
							counts.add(df.getFavoriteCount());
						}

					}
					countData.put(d.getName(),counts);
				}

				monthDatas.put(days,countData);


			}
		}



		return monthDatas;


	}
}
