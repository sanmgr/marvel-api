package com.yapily.project.marvel.service;

import com.yapily.project.marvel.exception.DataNotFoundApiException;
import com.yapily.project.marvel.model.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class MarvelWebService {
    private static final Logger LOG = LoggerFactory.getLogger(MarvelWebService.class);

    @Autowired
    private RestTemplate restTemplate;

    public MarvelWebService() {
        this.restTemplate = new RestTemplate();
    }

    public Response exchange(final String url, final Integer offSet, final Integer limit) {
        LOG.info(url);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url);
        builder.queryParam("ts", "thisislongtimestamp");
        builder.queryParam("apikey", "48b1be69d9711f872cfa5eac0e0d4c8d");
        builder.queryParam("hash", "7ab09d9a672394670257fed63a468749");

        if (offSet != null && limit != null) {
            builder.queryParam("offset", offSet);
            builder.queryParam("limit", limit);
        }

        URI uri = builder.build().encode().toUri();
        ResponseEntity<String> actualResponse = null;

        int i = 0;
        int maxTries = 3;
        while (i < maxTries) {
            try {
                actualResponse = restTemplate.getForEntity(uri, String.class);
                return new Response(actualResponse.getStatusCode(), actualResponse.getBody());
            } catch (HttpStatusCodeException ex) {
                return new Response(ex.getStatusCode(), ex.getStatusText());
            } catch (ResourceAccessException ex) {
                return new Response(HttpStatus.REQUEST_TIMEOUT, "REQUEST_TIMEOUT");
            } catch (Exception ex) {
                LOG.error("something failed::: " + ex.getClass(), ex);
            }
            i++;
        }
        return null;
    }
}
