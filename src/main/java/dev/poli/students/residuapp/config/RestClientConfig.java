package dev.poli.students.residuapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
class RestClientConfig {

    @Bean
    RestTemplate restTemplate() {
        var segTimeOut = 10;
        var restTemplate = new RestTemplate();
        var requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectionRequestTimeout(segTimeOut * 1000);
        requestFactory.setConnectTimeout(segTimeOut * 1000);
        restTemplate.setRequestFactory(requestFactory);
        return restTemplate;
    }
}
