package com.xiborra.meep_test.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiborra.meep_test.dto.CheckUpdatesResponse;
import com.xiborra.meep_test.dto.ReadResourcesDataDTO;
import com.xiborra.meep_test.model.Resource;
import com.xiborra.meep_test.services.CheckVehiclesChangesService;
import com.xiborra.meep_test.services.ResourcesService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class CheckVehiclesChangeServiceTest {

	private static final String RESOURCES_MINIFIED_JSON = "[{\"id\":\"Test-Resource-01\",\"name\":\"TEST-01\",\"x\":-9.145825,\"y\":38.716877,\"licencePlate\":\"TEST-01\",\"range\":40,\"batteryLevel\":53,\"helmets\":2,\"model\":\"Askoll\",\"resourceImageId\":\"vehicle_gen_ecooltra\",\"realTimeData\":true,\"resourceType\":\"MOPED\",\"companyZoneId\":473},{\"id\":\"Test-Resource-02\",\"name\":\"TEST-02\",\"x\":-9.14558,\"y\":38.723324,\"licencePlate\":\"TEST-02\",\"range\":64,\"batteryLevel\":85,\"helmets\":2,\"model\":\"Askoll\",\"resourceImageId\":\"vehicle_gen_ecooltra\",\"realTimeData\":true,\"resourceType\":\"MOPED\",\"companyZoneId\":473}]";

	@Mock
	private ResourcesService resourcesService;

	@InjectMocks
	private CheckVehiclesChangesService checkVehiclesChangesService;

	private ReadResourcesDataDTO[] resources;

	@BeforeEach
	public void init() throws JsonMappingException, JsonProcessingException {
		resources = new ObjectMapper().readValue(RESOURCES_MINIFIED_JSON, ReadResourcesDataDTO[].class);
	}

	@AfterEach
	public void deleteTestResources() {
		resourcesService.deleteByVehicleIds(List.of(resources[0].getId(), resources[1].getId()));
	}

	@Test
	public void testOkSavedTwo() {
		// Devolvemos el resultado de la BBDD vacío para que guarde los dos registros
		Mockito.when(resourcesService.findActiveResources()).thenReturn(new ArrayList<>());

		CheckUpdatesResponse checkUpdatesResponse = checkVehiclesChangesService.checkUpdates(resources);

		Assertions.assertEquals(2, checkUpdatesResponse.getSavedResources());
		Assertions.assertEquals(0, checkUpdatesResponse.getUpdatedResources());
	}

	@Test
	public void testOkSavedZero() {
		// Devolvemos el resultado de la bbdd los dos anteriores para comprobar que ni
		// guarda uno nuevo ni actualiza ninguno
		Mockito.when(resourcesService.findActiveResources())
				.thenReturn(List.of(parseReadDataIntoResourceEntity(resources[0], Boolean.FALSE),
						parseReadDataIntoResourceEntity(resources[1], Boolean.FALSE)));

		CheckUpdatesResponse checkUpdatesResponse = checkVehiclesChangesService.checkUpdates(resources);

		Assertions.assertEquals(0, checkUpdatesResponse.getSavedResources());
		Assertions.assertEquals(0, checkUpdatesResponse.getUpdatedResources());
	}

	@Test
	public void testUpdateTwo() {
		// Devolvemos el resultado de la BBDD con los dos recursos activados para que
		// los desactive
		Mockito.when(resourcesService.findActiveResources())
				.thenReturn(List.of(parseReadDataIntoResourceEntity(resources[0], Boolean.FALSE),
						parseReadDataIntoResourceEntity(resources[1], Boolean.FALSE)));

		// Enviamos la respuesta de la API vacía para que actualize los dos recursos
		CheckUpdatesResponse checkUpdatesResponse = checkVehiclesChangesService
				.checkUpdates(new ReadResourcesDataDTO[0]);

		Assertions.assertEquals(0, checkUpdatesResponse.getSavedResources());
		Assertions.assertEquals(2, checkUpdatesResponse.getUpdatedResources());
	}

	public Resource parseReadDataIntoResourceEntity(ReadResourcesDataDTO readedResource, Boolean isLeaving) {
		Resource parsedResource = new Resource();
		parsedResource.setVehicleId(readedResource.getId());
		parsedResource.setName(readedResource.getName());

		parsedResource.setX(readedResource.getX());
		parsedResource.setY(readedResource.getY());

		parsedResource.setLicencePlate(readedResource.getLicencePlate());
		parsedResource.setRange(readedResource.getRange());
		parsedResource.setBatteryLevel(readedResource.getBatteryLevel());
		parsedResource.setHelmets(readedResource.getHelmets());
		parsedResource.setModel(readedResource.getModel());

		parsedResource.setResourceImageId(readedResource.getResourceImageId());
		parsedResource.setRealTimeData(readedResource.getRealTimeData());
		parsedResource.setResourceType(readedResource.getResourceType());

		parsedResource.setCompanyZoneId(readedResource.getCompanyZoneId());

		parsedResource.setIsLeaving(isLeaving);
		return parsedResource;
	}

}
