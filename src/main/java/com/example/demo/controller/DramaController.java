package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.component.DramaComponent;
import com.example.demo.model.Drama;
import com.example.demo.service.ChartService;
import com.example.demo.service.DailyCountService;
import com.example.demo.service.DramaService;
import com.example.demo.service.SeasonService;
import com.example.demo.service.SessionService;
import com.example.demo.service.UserDetailsImpl;
import com.example.demo.validator.startAndEndDayValidator;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DramaController {
	
	
	
	private final startAndEndDayValidator startAndEndDayValidator;
	private final DramaService dramaService;
	private final SessionService sessionService;
	private final SeasonService seasonService;
	private final ChartService chartService;
	private final DailyCountService dailyCountService;
	
	@Autowired
	DramaComponent targetDramaComponent;
	
	
	@GetMapping("/topPage")
	public String topPage(Model model,@AuthenticationPrincipal UserDetailsImpl userDetail) {
		
		sessionService.setNullTargetDramaComponent();
		dailyCountService.dailyCount(userDetail.getSiteUser());
		model.addAttribute("seasonss", seasonService.findAllSeasons());
		
		return "topPage";
	}
	
	@GetMapping("/dramaNew")
	public String dramaNew(@ModelAttribute("drama") Drama drama) {
		
		return "dramaNew";
	}
	
	@PostMapping("/drama_create")
	public String dramaCreate(@Validated @ModelAttribute("drama") Drama drama,BindingResult result,@RequestParam("start") String start,@RequestParam("end") String end,Model model,@AuthenticationPrincipal UserDetailsImpl userDetail) {
		
		if (result.hasErrors() || !startAndEndDayValidator.StartAndEndDayCheck(start,end)) {
			
			if (!startAndEndDayValidator.StartAndEndDayCheck(start,end)) {
				model.addAttribute("dateError", "放送開始日より放送終了日が後になるように入力してください。");
			}
			
			model.addAttribute("start", start);
			model.addAttribute("end", end);
			
			return "dramaNew";
		} 
		
		dramaService.insert(drama, start, end,userDetail.getSiteUser());
		
		return "redirect:/topPage?createSuccess";
		
		
	}
	
	@GetMapping("/dramaEdit/{id}/{fromFlag}")
	public String dramaEdit(@PathVariable Long id,@PathVariable String fromFlag,Model model) {
		
		model.addAttribute("drama", dramaService.getDramaById(id));
		
		sessionService.setTargetDramaComponent(id);
		
		model.addAttribute("sessionService", sessionService);
		model.addAttribute("start",dramaService.getDramaById(id).translateCalendarToString1(dramaService.getDramaById(id).getStartDay()));
		model.addAttribute("end", dramaService.getDramaById(id).translateCalendarToString1(dramaService.getDramaById(id).getEndDay()));
		model.addAttribute("fromFlag", fromFlag);
		model.addAttribute("fromFlag2","/"+fromFlag);
		return "dramaEdit";
	}
	
	@PostMapping("/drama_update{redirectTo}") 
	public String dramaUpdate(@PathVariable String redirectTo,@Validated @ModelAttribute Drama drama,BindingResult result,@RequestParam("start") String start,@RequestParam("end") String end,Model model,@AuthenticationPrincipal UserDetailsImpl userDetail) {
		
		
		if (result.hasErrors() || !startAndEndDayValidator.StartAndEndDayCheck(start,end)) {
					
					if (!startAndEndDayValidator.StartAndEndDayCheck(start,end)) {
						model.addAttribute("dateError", "放送開始日より放送終了日が後になるように入力してください。");
					}
					
					model.addAttribute("start", start);
					model.addAttribute("end", end);
					
					return "dramaEdit";
		} 
		
		
		dramaService.update(drama, start, end,userDetail.getSiteUser());
		
		return "redirect:/"+redirectTo+"?dramaUpdateSuccess";
		
	}
	
	@GetMapping("/dramaIndex{id}")
	public String dramaIndex(@PathVariable Long id,Model model) {
		
		
		sessionService.setSeasonComponent(id);
		
		model.addAttribute("couontDatas", chartService.createChart(sessionService.getSeasonComponent().getSeason()));
		
		model.addAttribute("dramas",dramaService.collectDramaBySeason(sessionService.getSeasonComponent().getSeason().getId()));
		model.addAttribute("sessionService", sessionService);
		model.addAttribute("from","dramaIndex"+sessionService.getSeasonComponent().getSeason().getId());
	
		return "dramaIndex";
	}
	
	@GetMapping("/favorite_dramas")
	public String favoriteDramas(@AuthenticationPrincipal UserDetailsImpl userDetail,Model model) {
		
		model.addAttribute("favoriteDramas",dramaService.collectDramaFavoriteByUser(userDetail.getSiteUser().getId()));
		return "favoriteDramas";
	}
	
	@PostMapping("/drama_delete/{id}/{redirectTo}")
	public String dramaDelete(@PathVariable Long id,@PathVariable String redirectTo) {
		
		dramaService.delete(id);
	
		return "redirect:/"+redirectTo+"?delete";
	}
	
	@GetMapping("/searchDrama")
	public String searhDrama(@RequestParam("keyword") String keyword,Model model) {
		
		model.addAttribute("dramas",dramaService.collectDramaByKeyword(keyword));
		model.addAttribute("keyword", keyword);
		return "resultDramas";
	}
	
	@GetMapping("/my_dramas")
	public String myDramas(@AuthenticationPrincipal UserDetailsImpl userDetail,Model model) {
		
		model.addAttribute("dramas",dramaService.collectDramaByCreater(userDetail.getSiteUser().getId()));
		return "myDramas";
	}

}
