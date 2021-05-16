package com.xiborra.meep_test.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.xiborra.meep_test.model.Resource;

public interface ResourcesRepository extends CrudRepository<Resource, Long> {

	public List<Resource> findByIsLeaving(Boolean isLeaving);

	public Resource findFirstByVehicleIdOrderByIdDesc(String vehicleId);

	public Resource findFirstByLicencePlateOrderByIdDesc(String licencePlate);

	@Transactional
	@Modifying
	@Query("UPDATE Resource r SET r.isLeaving = :isLeaving WHERE r.vehicleId in :vehicleIds")
	public void updateIsLeavingByVehicleIds(@Param(value = "vehicleIds") List<String> vehicleIds,
			@Param(value = "isLeaving") Boolean isLeaving);

	@Transactional
	@Modifying
	@Query("DELETE FROM Resource r WHERE r.vehicleId in :vehicleIds")
	public void deleteByVehicleIds(@Param(value = "vehicleIds") List<String> vehicleIds);

}
