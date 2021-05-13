package com.xiborra.meep_test.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;

import com.xiborra.meep_test.exceptions.MyRestTemplateException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomErrorResponseHandler extends DefaultResponseErrorHandler {

	@Override
	public void handleError(ClientHttpResponse response) throws IOException {
		if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
			try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.getBody()))) {
				String httpBodyResponse = reader.lines().collect(Collectors.joining(""));
				throw new MyRestTemplateException(response.getStatusCode(), httpBodyResponse);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
	}

}
