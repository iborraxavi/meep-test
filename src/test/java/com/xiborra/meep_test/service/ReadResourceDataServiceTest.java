package com.xiborra.meep_test.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.xiborra.meep_test.exceptions.ReadDataException;
import com.xiborra.meep_test.properties.EndpointsProperties;
import com.xiborra.meep_test.services.ReadResourcesDataService;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("classpath:application.properties")
public class ReadResourceDataServiceTest {

	@Autowired
	private EndpointsProperties endpointsProperties;

	@Autowired
	private ReadResourcesDataService readResourcesDataService;

	/**
	 * Test OK de la llamada a la API, solo validamos que hacemos la petición y no
	 * devuelve ninguna Exception, por lo que entendemos que la petición ha ido bien
	 * 
	 * @throws ReadDataException
	 */
	@Test
	public void testCorrectApiCall() throws ReadDataException {
		readResourcesDataService.read(endpointsProperties.getEndpointReadResourcesData());
		org.assertj.core.api.Assertions.assertThatNoException();
	}

	/**
	 * Test con petición errónea para comprobar que estén bien controladas las
	 * exceptions en la validación de la petición
	 * 
	 * @throws ReadDataException
	 */
	@Test()
	public void testIncorrectResponseApi() throws ReadDataException {
		Exception exception = Assertions.assertThrows(ReadDataException.class, () -> {
			readResourcesDataService.read("https://no-existe.meep.me");
		});
		Assertions.assertTrue(exception.getMessage().startsWith("Exception parsing response: "));
	}
}
