package com.xiborra.meep_test.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.xiborra.meep_test.model.Resource;

public interface ResourcesRepository extends CrudRepository<Resource, Long> {

	public List<Resource> findByIsLeaving(Boolean isLeaving);
	
	public Resource findFirstByVehicleIdOrderByIdDesc(String vehicleId);
	
	public Resource findFirstByLicencePlateOrderByIdDesc(String licencePlate);
	
}
