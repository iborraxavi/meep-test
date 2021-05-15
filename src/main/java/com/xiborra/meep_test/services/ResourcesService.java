package com.xiborra.meep_test.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiborra.meep_test.model.Resource;
import com.xiborra.meep_test.repository.ResourcesRepository;

@Service
public class ResourcesService implements IResourcesService {

	@Autowired
	private ResourcesRepository resourcesRepository;

	@Override
	public void saveAll(List<Resource> resources) {
		resourcesRepository.saveAll(resources);
	}

	@Override
	public void updateIsLeavingByVehicleIds(List<String> vehicleIds, Boolean isLeaving) {
		resourcesRepository.updateIsLeavingByVehicleIds(vehicleIds, isLeaving);
	}

	@Override
	public List<Resource> findActiveResources() {
		return resourcesRepository.findByIsLeaving(Boolean.FALSE);
	}

	@Override
	public Resource findLastByVehicleId(String vehicleId) {
		return resourcesRepository.findFirstByVehicleIdOrderByIdDesc(vehicleId);
	}

	@Override
	public Resource findLastByLicencePlate(String licencePlate) {
		return resourcesRepository.findFirstByLicencePlateOrderByIdDesc(licencePlate);
	}

}
