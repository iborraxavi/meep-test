package com.xiborra.meep_test.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiborra.meep_test.exceptions.ReadDataException;
import com.xiborra.meep_test.model.Resource;
import com.xiborra.meep_test.model.ReadResourcesDataDTO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CheckVehiclesChangesService {

	@Autowired
	private ReadResourcesDataService readResourcesData;

	@Autowired
	private ResourcesService resourcesService;

	public void checkUpdates() throws ReadDataException {
		// Obtenemos los valores de la API
		ReadResourcesDataDTO[] resources = readResourcesData.read();
		log.info("XAVI - resources: " + resources.length);
		// Obtenemos los valores de la anterior petición para poder comparar si se he
		// eliminado alguno
		List<Resource> currentActiveResources = resourcesService.findActiveResources();
		log.info("XAVI - currentActiveResources: " + currentActiveResources.size());
		// Buscamos y guardamos los nuevos recursos
		findNewVehicles(resources, currentActiveResources);
	}

	/**
	 * Cogemos la lista de los recursos activos y la copiamos para no modificar su
	 * contenido, es la lsita a partir de la cual sacaremos mediante el id del
	 * vehiculo los vehiculos que no tenemmos en BBDD
	 * 
	 * @param readedResources        Recursos leído del Endpoint
	 * @param currentActiveResources List de recursos activos leídos de la BBDD
	 */
	private void findNewVehicles(ReadResourcesDataDTO[] readedResources, List<Resource> currentActiveResources) {
		List<String> readedResourcesIds = Arrays.asList(readedResources).stream().map(ReadResourcesDataDTO::getId)
				.collect(Collectors.toList());
		List<String> currentActiveResourcesIds = currentActiveResources.stream().map(Resource::getVehicleId)
				.collect(Collectors.toList());
		// Eliminamos todos los Ids de los recursos activos de la lista de leídos, así
		// sabremos que IDs de Vehículos son los que tenemos que guardar
		readedResourcesIds.removeAll(currentActiveResourcesIds);
		// Validamos los registros que vamos a guardar, tenemos que verificar la
		// integridad de los datos
		List<Resource> validatedResources = validateNewResources(Arrays.asList(readedResources).stream()
				.filter(readResource -> readedResourcesIds.contains(readResource.getId()))
				.collect(Collectors.toList()));
		// Guardamos los registros validados
		if (!validatedResources.isEmpty()) {
			log.info("XAVI - findNewVehicles, saved: " + validatedResources.size());
			resourcesService.saveAll(validatedResources);
		}
	}

	/**
	 * Antes de guardar un nuevo recurso tenemos que validar que sea un recurso
	 * válido, buscaremos un recurso con el mismo id o la misma matricula para
	 * verificar que no haya venido otro vehículo que no corresponde con el id del
	 * mismo
	 * 
	 * @param newReadedResources Lista de nuevos recursos encontrados
	 * @return Lista de nuevos recursos validados
	 */
	private List<Resource> validateNewResources(List<ReadResourcesDataDTO> newReadedResources) {
		log.info("XAVI - validateNewResources, newReadedResources: " + newReadedResources.size());
		List<Resource> validatedResources = new ArrayList<>();
		for (ReadResourcesDataDTO newReadedResource : newReadedResources) {
			// Validamos el recurso leído respecto al último registro que tengamos con el
			// mismo id, en caso de que tengamos alguno
			Boolean isValidById = validateWithLastResourceById(newReadedResource);
			if (Boolean.TRUE.equals(isValidById)
					&& Boolean.TRUE.equals(validateWithLastResourceByLicencePlate(newReadedResource))) {
				validatedResources.add(parseReadDataIntoResourceEntity(newReadedResource));
			}
		}
		return validatedResources;
	}

	/**
	 * Validación del recurso leído a partir del Id
	 * 
	 * @param newReadedResource recurso leído
	 * @return Resultado de la evaluación de la validación
	 */
	private Boolean validateWithLastResourceById(ReadResourcesDataDTO newReadedResource) {
		Resource lastResourceById = resourcesService.findLastByVehicleId(newReadedResource.getId());
		if (lastResourceById != null) {
			log.info("XAVI - validateNewResources, lastResourceById: " + lastResourceById.getVehicleId());
			return validateIsSameVehicles(newReadedResource, lastResourceById);
		}
		return true;
	}

	/**
	 * Validación del recurso leído a partir del licencePlate
	 * 
	 * @param newReadedResource recurso leído
	 * @return Resultado de la evaluación de la validación
	 */
	private Boolean validateWithLastResourceByLicencePlate(ReadResourcesDataDTO newReadedResource) {
		Resource lastResourceByLicencePlate = resourcesService
				.findLastByLicencePlate(newReadedResource.getLicencePlate());
		if (lastResourceByLicencePlate != null) {
			log.info("XAVI - validateNewResources, lastResourceByLicencePlate: "
					+ lastResourceByLicencePlate.getLicencePlate());
			return validateIsSameVehicles(newReadedResource, lastResourceByLicencePlate);
		}
		return true;
	}

	/**
	 * Validamos que sea el mismo vehiculo por si ha habido un problema de
	 * integridad de datos
	 * 
	 * @param newReadedResource Recurso validado extraido del endpoint
	 * @param lastResourceById  Recurso con el mismo Id o licencePlate que el
	 *                          obtenido del endpoint
	 * @return Resultado de la validación de la integridad de datos
	 */
	private Boolean validateIsSameVehicles(ReadResourcesDataDTO newReadedResource, Resource lastResourceById) {
		// ID
		if (!newReadedResource.getId().equals(lastResourceById.getVehicleId())) {
			return false;
		}
		// LICENCE PLATE
		if (!newReadedResource.getLicencePlate().equals(lastResourceById.getLicencePlate())) {
			return false;
		}
		// RESOURCE TYPE
		if (!newReadedResource.getResourceType().equals(lastResourceById.getResourceType())) {
			return false;
		}
		// MODEL
		if (!newReadedResource.getModel().equals(lastResourceById.getModel())) {
			return false;
		}
		return true;
	}

	public Resource parseReadDataIntoResourceEntity(ReadResourcesDataDTO readedResource) {
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

		parsedResource.setIsLeaving(Boolean.FALSE);
		return parsedResource;
	}

}
