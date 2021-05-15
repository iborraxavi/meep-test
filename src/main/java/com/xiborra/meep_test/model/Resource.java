package com.xiborra.meep_test.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "resources")
@Getter
@Setter
public class Resource {

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

	// Baja del vehiculo.
	private Boolean isLeaving;
	
}
