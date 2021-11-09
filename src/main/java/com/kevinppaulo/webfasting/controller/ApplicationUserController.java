package com.kevinppaulo.webfasting.controller;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.kevinppaulo.webfasting.dao.FastItem;
import com.kevinppaulo.webfasting.dao.StartFastingDao;
import com.kevinppaulo.webfasting.model.Fast;
import com.kevinppaulo.webfasting.service.FastingService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("")
@RequiredArgsConstructor
public class ApplicationUserController {

	private final FastingService fastingService;
	
	@GetMapping("")
	public String home(Model model, @AuthenticationPrincipal OidcUser principal){
		boolean isFasting = fastingService.isFasting(principal);

		List<FastItem> previousFasts = fastingService.getUserFasts(principal)
			.stream()
			.filter(fast -> fast.getEnded() != null)
			.map(fast -> new FastItem(fast))
			.collect(Collectors.toList());
		
		model.addAttribute("previousFasts", previousFasts);

		model.addAttribute("isFasting", isFasting);

		if(isFasting){
			Fast fast = fastingService.getCurrentFast(principal);
			model.addAttribute("startTime", fast.getStarted());
			model.addAttribute("intendedDuration", fast.getIntendedDuration());
		}

		if (principal != null) {
            model.addAttribute("profile", principal.getClaims());
        }
        return "index";
	}

	@PostMapping("/start")
	public String startFasting(@ModelAttribute StartFastingDao startFastingDao, Model model, @AuthenticationPrincipal OidcUser principal){
		if(startFastingDao.getLength() != null){
			fastingService.startFasting(startFastingDao, principal);
		}
		return "redirect:";
	}

	@GetMapping("/finish")
	public String endFast(@RequestParam boolean save, @AuthenticationPrincipal OidcUser principal){
		if(save){
			fastingService.finishCurrentFast(principal);
		}else{
			fastingService.cancelCurrentFast(principal);
		}

		return "redirect:";
	}

	@GetMapping("/delete")
	public String deleteFast(@RequestParam UUID uuid, @AuthenticationPrincipal OidcUser principal){
		try {
			fastingService.deleteFast(uuid, principal);
		} catch (NotFoundException e) {
			// TODO: add a 404 page
			return "redirect:";
		}
		return "redirect:";
	}
}
