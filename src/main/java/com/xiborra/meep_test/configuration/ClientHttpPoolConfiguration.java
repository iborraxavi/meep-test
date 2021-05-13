package com.xiborra.meep_test.configuration;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientHttpPoolConfiguration {

	// Número máximo total de peticiones
	public static final Integer MAX_TOTAL = 2000;
	// DOC APACHE: Determines the timeout in milliseconds until a connection is
	// established. A timeout value of zero is interpreted as an infinite timeout.
	public static final Integer CONNECTION_TIMEOUT = 30000;
	// DOC APACHE: Defines the socket timeout (SO_TIMEOUT) in milliseconds,
	// which is the timeout for waiting for data or, put differently,
	// a maximum period inactivity between two consecutive data packets).
	// * A timeout value of zero is interpreted as an infinite timeout.
	// * A negative value is interpreted as undefined (system default).
	public static final Integer SOCKET_TIMEOUT = 0;

	@Bean
	public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
		PoolingHttpClientConnectionManager result = new PoolingHttpClientConnectionManager();
		result.setMaxTotal(MAX_TOTAL);
		return result;
	}

	@Bean
	public RequestConfig requestConfig() {
		return RequestConfig.custom().setConnectTimeout(CONNECTION_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
	}

	@Bean
	public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager,
			RequestConfig requestConfig) {
		return HttpClientBuilder.create().setConnectionManager(poolingHttpClientConnectionManager)
				.setDefaultRequestConfig(requestConfig).build();
	}

}
