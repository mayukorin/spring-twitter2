package com.example.demo.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Channel;
import com.example.demo.model.Drama;
import com.example.demo.model.DramaFavoriteCount;
import com.example.demo.model.Favorite;
import com.example.demo.model.Season;
import com.example.demo.model.SiteUser;
import com.example.demo.repository.*;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DramaService {

	private final DramaRepository dramaRepository;
	private final ChannelRepository channelRepository;
	private final DramaFavoriteCountService dramaFavoriteCountService;
	private final SeasonService seasonService;
	private final ChannelService channelService;
	private final FavoriteService favoriteService;
	private final ArticleService articleService;


	public Drama getDramaById(Long id) {
		return dramaRepository.findById(id).get();
	}

	public void insert(Drama drama,String start,String end,SiteUser user) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		try {
			Date dateStart = dateFormat.parse(start);
			Date dateEnd = dateFormat.parse(end);

			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(dateEnd);
			drama.setEndDay(endCalendar);

			Calendar startCalendar = Calendar.getInstance();
			startCalendar.setTime(dateStart);
			drama.setStartDay(startCalendar);


			drama.setCreater(user);



			if (seasonService.collectStartMonth(start.substring(0,7)) == 0) {
				Season newSeason = seasonService.insert(startCalendar,endCalendar);
				drama.setSeason(newSeason);

			} else {
				Season s = seasonService.CollectSeasonByStartMonth(start.substring(0,7));

				if (endCalendar.compareTo(s.getSeasonEndDay()) > 0) {
					s = seasonService.update(s,endCalendar);

				}

				drama.setSeason(s);


			}

			dramaRepository.save(drama);

			Channel c = new Channel();
			c.setDrama(drama);
			c.setTitle("放送日について");

			channelRepository.save(c);

			articleService.defaultInsert(c);

		} catch (ParseException e) {

			System.out.println("日付に変換できませんでした");
		}

	}



	public void update(Drama drama,String start,String end,SiteUser user) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
		try {
			Date dateStart = dateFormat.parse(start);
			Date dateEnd = dateFormat.parse(end);

			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(dateEnd);
			drama.setEndDay(endCalendar);

			Calendar startCalendar = Calendar.getInstance();
			startCalendar.setTime(dateStart);
			drama.setStartDay(startCalendar);


			if (seasonService.collectStartMonth(start.substring(0,7)) == 0) {
				Season newSeason = seasonService.insert(startCalendar,endCalendar);
				drama.setSeason(newSeason);

			} else {
				Season s = seasonService.CollectSeasonByStartMonth(start.substring(0,7));

				if (endCalendar.compareTo(s.getSeasonEndDay()) > 0) {
					s = seasonService.update(s,endCalendar);

				}

				drama.setSeason(s);


			}




			dramaRepository.save(drama);


		} catch (ParseException e) {

			System.out.println("日付に変換できませんでした");
		}

	}

	public void delete(Long id) {

		List<Channel> channels = channelService.getChannelsByDramaId(id);

		for (Channel channel:channels) {

			channelService.delete(channel.getId());

		}

		List<Favorite> favorites = favoriteService.collectFavoritesByDrama(id);

		for (Favorite f:favorites) {
			favoriteService.deleteById(f.getId());
		}

		List<DramaFavoriteCount> dfs = dramaFavoriteCountService.collectDramaFavoriteCountByDrama(id);

		for (DramaFavoriteCount df:dfs) {

			dramaFavoriteCountService.deleteById(df.getId());
		}
		dramaRepository.deleteById(id);
	}

	public List<String> collectStartMonth() {


		return dramaRepository.collectStartMonth();
	}

	
	public List<Drama> collectDramaFavoriteByUser(Long id) {

		return dramaRepository.collectDramaFavoriteByUser(id);
	}

	public List<Drama> collectDramaBySeason(Long id) {
		return dramaRepository.collectDramaBySeason(id);
	}

	public List<Drama> collectDramaByKeyword(String keyword) {
		return dramaRepository.collectDramaByKeyword(keyword);
	}

	public List<Drama> collectDramaByCreater(Long id) {
		return dramaRepository.collectDramaByCreater(id);
	}

	public Drama findById(Long id) {
		return dramaRepository.findById(id).get();
	}









}
