package com.xiborra.meep_test.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadResourcesDataDTO {

	private String id;
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
