package com.xiborra.meep_test.services;

import java.util.List;

import com.xiborra.meep_test.model.Resource;

public interface IResourcesService {

	public void saveAll(List<Resource> resources);

	public List<Resource> findActiveResources();

	public Resource findLastByVehicleId(String vehicleId);

	public Resource findLastByLicencePlate(String licencePlate);

	public void updateIsLeavingByVehicleIds(List<String> vehicleIds, Boolean isLeaving);

}
