package com.xiborra.meep_test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiborra.meep_test.exceptions.ReadDataException;
import com.xiborra.meep_test.model.dto.ReadResourcesDataDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CheckVehiclesChangesService {

	@Autowired
	private ReadResourcesDataService readResourcesData;

	public void checkUpdates() throws ReadDataException {
		ReadResourcesDataDTO[] resources = readResourcesData.read();
		log.info("XAVI - resources: " + resources.length);
	}

}
