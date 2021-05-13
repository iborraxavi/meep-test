package com.xiborra.meep_test.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "last_readed_vehicles")
@Getter
public class LastReadedVehicle {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String vehicleId;
	private String name;

	// Geographic coordinate x
	private Double x;
	// Geographic coordinate y
	private Double y;
	
	private String licencePlate;
	private Integer range;
	private Integer batteryLevel;
	private Integer helmets;
	private String model;
	
	private String resourceImageId;
	private Boolean realTimeData;
	private String resourceType;
	
	private Integer companyZoneId;
	
}
