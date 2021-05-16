package com.xiborra.meep_test.tasks;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.xiborra.meep_test.exceptions.ReadDataException;
import com.xiborra.meep_test.model.CheckUpdatesResponse;
import com.xiborra.meep_test.model.ReadResourcesDataDTO;
import com.xiborra.meep_test.properties.EndpointsProperties;
import com.xiborra.meep_test.services.CheckVehiclesChangesService;
import com.xiborra.meep_test.services.ReadResourcesDataService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CheckVehiclesChangesTask {

	@Autowired
	private CheckVehiclesChangesService checkVehiclesChangesService;

	@Autowired
	private ReadResourcesDataService readResourcesDataService;

	@Autowired
	private EndpointsProperties endpointsProperties;

	@Scheduled(fixedDelay = 30000)
	public void executeTask() {
		try {
			Date initDate = new Date();
			// Obtenemos los valores de la API
			ReadResourcesDataDTO[] resources = readResourcesDataService
					.read(endpointsProperties.getEndpointReadResourcesData());
			// Comprobamos los cambios que ha habido en los recursos recibidos de la API
			CheckUpdatesResponse checkUpdatesResponse = checkVehiclesChangesService.checkUpdates(resources);
			log.info("CheckVehiclesChangesTask ({}), saved: {}, updated: {}", initDate,
					checkUpdatesResponse.getSavedResources(), checkUpdatesResponse.getUpdatedResources());
		} catch (ReadDataException e) {
			log.error(e.getMessage());
		}
	}

}
