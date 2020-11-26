package com.example.demo.service;


import com.example.demo.model.SiteUser;
import com.example.demo.model.Drama;
import com.example.demo.model.Favorite;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.repository.FavoriteRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FavoriteService {

	private final FavoriteRepository favoriteRepository;

	public void save(SiteUser user,Drama drama) {

		Favorite f = new Favorite();

		f.setDrama(drama);
		f.setUser(user);

		favoriteRepository.save(f);

	}
	public void delete(SiteUser user,Drama drama) {

		Favorite deleteFavorite = favoriteRepository.FindFavoriteByUserAndDrama(user.getId(), drama.getId());
		favoriteRepository.deleteById(deleteFavorite.getId());
	}

	public Long CountFavoriteByUserAndDrama(Long siteuserId,Long dramaId) {

		return favoriteRepository.CountFavoriteByUserAndDrama(siteuserId,dramaId);
	}

	public List<Favorite> collectFavoritesByDrama(Long id) {
		return favoriteRepository.collectFavoritesByDrama(id);
	}

	public Long CountFavoriteByDrama(Long id) {
		return favoriteRepository.CountFavoriteByDrama(id);
	}

	public List<SiteUser> collectFavoriteUsers(Long id) {
		return favoriteRepository.collectFavoriteUsers(id);
	}

	public void deleteById(Long id) {
		favoriteRepository.deleteById(id);
	}


}
