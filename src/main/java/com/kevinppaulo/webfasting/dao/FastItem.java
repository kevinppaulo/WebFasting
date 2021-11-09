package com.kevinppaulo.webfasting.dao;

import java.time.Duration;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.UUID;

import com.kevinppaulo.webfasting.model.Fast;

public class FastItem {
	public String started;
	public String ended;
	public Integer intendedDuration;
	public String duration;
	public UUID uuid;

	public FastItem(Fast fast) {
		this.started = fast.getStarted().toString();
		this.ended = fast.getEnded().toString();
		this.intendedDuration = fast.getIntendedDuration();
		long durationMinutes = Duration.between(fast.getStarted(), fast.getEnded()).toMinutes();
		this.duration = String.format("%d hours and %d minutes", (int) durationMinutes / 60, durationMinutes % 60);
		this.uuid = fast.getUuid();
	}
}
