package com.xiborra.meep_test.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheckUpdatesResponse {

	private Integer savedResources;
	private Integer updatedResources;

	public CheckUpdatesResponse() {

	}

	public CheckUpdatesResponse(Integer savedResources, Integer updatedResources) {
		this.savedResources = savedResources;
		this.updatedResources = updatedResources;
	}

}
