package com.kevinppaulo.webfasting.service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.kevinppaulo.webfasting.dao.StartFastingDao;
import com.kevinppaulo.webfasting.model.Fast;
import com.kevinppaulo.webfasting.repository.FastRepository;

import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FastingService {

	private final FastRepository fastRepository;

	public Fast getCurrentFast(OidcUser principal){
		return fastRepository.findFirstByUserEmailOrderByCreatedDesc(principal.getEmail());
	}

	public List<Fast> getUserFasts(OidcUser principal){
		return fastRepository.findAllByUserEmail(principal.getEmail());
	}

	public boolean isFasting(OidcUser principal){
		return fastRepository.existsByUserEmailAndEnded(principal.getEmail(), null);
	}

	public void cancelCurrentFast(OidcUser principal){
		Fast fast = getCurrentFast(principal);
		fastRepository.delete(fast);
	}

	public void finishCurrentFast(OidcUser principal){
		Fast fast = getCurrentFast(principal);
		fast.setEnded(Instant.now());
		fastRepository.save(fast);
	}

	public void deleteFast(UUID fastUuid, OidcUser principal) throws NotFoundException{
		Fast fast = fastRepository.findByUserEmailAndUuid(principal.getEmail(), fastUuid)
			.orElseThrow(() -> new NotFoundException(""));
		fastRepository.delete(fast);
	}

	public void startFasting(StartFastingDao startFastingDao, OidcUser principal){
		fastRepository.save(Fast.builder()
			.created(Instant.now())
			.started(Instant.now())
			.ended(null)
			.intendedDuration(startFastingDao.getLength())
			.userEmail(principal.getEmail())
			.uuid(UUID.randomUUID())
			.build()
		);
	}
}
