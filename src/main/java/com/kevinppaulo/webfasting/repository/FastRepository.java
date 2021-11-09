package com.kevinppaulo.webfasting.repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.kevinppaulo.webfasting.model.Fast;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FastRepository extends JpaRepository<Fast, Long>{
	public List<Fast> findAllByUserEmail(String userEmail);

	public Optional<Fast> findByUserEmailAndUuid(String userEmail, UUID uuid);

	public Boolean existsByUserEmailAndEnded(String userEmail, Instant ended);

	public Fast findFirstByUserEmailOrderByCreatedDesc(String userEmail);
}
