package com.xiborra.meep_test.tasks;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xiborra.meep_test.exceptions.ReadDataException;
import com.xiborra.meep_test.services.CheckVehiclesChangesService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CheckVehiclesChangesTask {

	@Autowired
	private CheckVehiclesChangesService checkVehiclesChangesService;

	@Scheduled(fixedDelay = 30000)
	public void executeTask() {
		log.info("XAVI - executeTask, date: " + new Date());
		try {
			checkVehiclesChangesService.checkUpdates();
		} catch (ReadDataException e) {
			log.error(e.getMessage());
		}
	}

}
