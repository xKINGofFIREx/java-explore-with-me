package ru.client;

import dtos.EndpointHitDto;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseClient {
    private final RestTemplate rest;

    public BaseClient(RestTemplateBuilder builder) {
        this.rest = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory("http://stats-server:9091"))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
    }

    public ResponseEntity<Object> saveHit(EndpointHitDto endpointHitDto) {
        return post("/hit", endpointHitDto);
    }

    public ResponseEntity<Object> getStats(String start, String end, List<String> uris, Boolean unique) {
        start = URLEncoder.encode(start, StandardCharsets.UTF_8);
        end = URLEncoder.encode(end, StandardCharsets.UTF_8);
        StringBuilder path = new StringBuilder("/stats?start={start}&end={end}");

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("start", start);
        parameters.put("end", end);

        if (uris == null && unique == null)
            return get(path.toString(), parameters);

        if (unique != null && uris == null) {
            parameters.put("unique", unique);
            return get(path.append("&unique={unique}").toString(), parameters);
        }

        path.append("&");
        for (String uri : uris)
            path.append("uris=").append(uri).append("&");
        path.replace(path.length() - 1, path.length(), "");

        if (unique == null) {
            return get(path.toString(), parameters);
        }

        parameters.put("unique", unique);
        return get(path.append("&unique={unique}").toString(), parameters);
    }

    protected ResponseEntity<Object> get(String path, @Nullable Map<String, Object> parameters) {
        return makeAndSendRequest(HttpMethod.GET, path, parameters, null);
    }

    protected <T> ResponseEntity<Object> post(String path, T body) {
        return makeAndSendRequest(HttpMethod.POST, path, null, body);
    }

    private <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method, String path, @Nullable Map<String, Object> parameters, @Nullable T body) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, new HttpHeaders());

        ResponseEntity<Object> ewmServerResponse;
        try {
            if (parameters != null)
                ewmServerResponse = rest.exchange(path, method, requestEntity, Object.class, parameters);
            else
                ewmServerResponse = rest.exchange(path, method, requestEntity, Object.class);
        } catch (HttpStatusCodeException e) {
            return ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }
        return prepareGatewayResponse(ewmServerResponse);
    }

    private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
        if (response.getStatusCode().is2xxSuccessful())
            return response;

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody())
            return responseBuilder.body(response.getBody());

        return responseBuilder.build();
    }
}
