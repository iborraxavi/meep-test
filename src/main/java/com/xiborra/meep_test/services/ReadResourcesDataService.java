package com.xiborra.meep_test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.xiborra.meep_test.exceptions.ReadDataException;
import com.xiborra.meep_test.model.ReadResourcesDataDTO;
import com.xiborra.meep_test.properties.EndpointsProperties;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ReadResourcesDataService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private EndpointsProperties endpointsProperties;

	public ReadResourcesDataDTO[] read() throws ReadDataException {
		ResponseEntity<ReadResourcesDataDTO[]> responseEntity = restTemplate
				.getForEntity(endpointsProperties.getEndpointReadResourcesData(), ReadResourcesDataDTO[].class);
		if (responseEntity.getStatusCode().equals(HttpStatus.OK) && responseEntity.getBody() != null
				&& responseEntity.getBody().length != 0) {
//			showReadedData(responseEntity.getBody());
			return responseEntity.getBody();
		}
		throw new ReadDataException("Exception obtaining resources data");
	}

	private void showReadedData(ReadResourcesDataDTO[] readedResources) {
		for (ReadResourcesDataDTO readedResource : readedResources) {
			log.info("XAVI - ---------------------------------------------------------------------------");
			log.info("XAVI - showReadedData, id: " + readedResource.getId());
			log.info("XAVI - showReadedData, name: " + readedResource.getName());
			log.info("XAVI - showReadedData, x: " + readedResource.getX());
			log.info("XAVI - showReadedData, y: " + readedResource.getY());
			log.info("XAVI - showReadedData, licensePlate: " + readedResource.getLicencePlate());
			log.info("XAVI - showReadedData, range: " + readedResource.getRange());
			log.info("XAVI - showReadedData, batteryLevel: " + readedResource.getBatteryLevel());
			log.info("XAVI - showReadedData, helmets: " + readedResource.getHelmets());
			log.info("XAVI - showReadedData, model: " + readedResource.getModel());
			log.info("XAVI - showReadedData, resourceImageId: " + readedResource.getResourceImageId());
			log.info("XAVI - showReadedData, realTimeData: " + readedResource.getRealTimeData());
			log.info("XAVI - showReadedData, resourceType: " + readedResource.getResourceType());
			log.info("XAVI - showReadedData, companyZoneId: " + readedResource.getCompanyZoneId());
			log.info("XAVI - ---------------------------------------------------------------------------");
		}
	}

}
