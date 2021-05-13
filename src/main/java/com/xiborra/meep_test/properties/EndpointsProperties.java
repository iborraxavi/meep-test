package com.xiborra.meep_test.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Getter;

@Configuration
@PropertySource("classpath:properties/endpoints.properties")
@Getter
public class EndpointsProperties {

	@Value("${endpoint.read.resources.data}")
	private String endpointReadResourcesData;
	
}
