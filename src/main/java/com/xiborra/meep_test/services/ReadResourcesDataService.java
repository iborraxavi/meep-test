package com.xiborra.meep_test.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.xiborra.meep_test.dto.ReadResourcesDataDTO;
import com.xiborra.meep_test.exceptions.ReadDataException;

@Service
public class ReadResourcesDataService {

	@Autowired
	private RestTemplate restTemplate;

	public ReadResourcesDataDTO[] read(String endpointUrl) throws ReadDataException {
		ResponseEntity<ReadResourcesDataDTO[]> responseEntity = null;
		try {
			responseEntity = restTemplate.getForEntity(endpointUrl, ReadResourcesDataDTO[].class);
		} catch (RestClientException e) {
			throw new ReadDataException("Exception parsing response: " + e.getMessage());
		}
		if (!responseEntity.getHeaders().getContentType().includes(MediaType.APPLICATION_JSON)) {
			throw new ReadDataException("Incorrect Content Type: " + responseEntity.getHeaders().getContentType());
		}
		if (responseEntity.getStatusCode().equals(HttpStatus.OK) && responseEntity.getBody() != null
				&& responseEntity.getBody().length != 0) {
			return responseEntity.getBody();
		}
		throw new ReadDataException("Exception obtaining resources data");

	}

}
