package com.example.auth.service;

import com.example.auth.client.AuthClient;
import com.example.auth.exception.AuthRequestException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
public class MicrosoftAuthService {

    private final AuthClient authClient;
    private final String loginUrl;
    private final String clientId;
    private final String clientSecret;
    private final String scope;
    private final String grantType;

    public MicrosoftAuthService(AuthClient authClient) {
        this.authClient = authClient;
        this.loginUrl = System.getenv("MICROSOFT_LOGIN_URL");
        this.clientId = System.getenv("CLIENT_ID");
        this.clientSecret = System.getenv("CLIENT_SECRET");
        this.scope = System.getenv("SCOPE");
        this.grantType = "client_credentials";

        validateConfig();
    }

    private void validateConfig() {
        if (loginUrl == null || clientId == null || clientSecret == null || scope == null) {
            throw new IllegalArgumentException("Required environment variables are not set.");
        }
    }

    public CompletableFuture<String> retrieveToken() {
        return CompletableFuture.supplyAsync(() -> {
            Map<String, String> formParams = Map.of(
                    "client_id", clientId,
                    "client_secret", clientSecret,
                    "scope", scope,
                    "grant_type", grantType
            );

            String formData = formParams.entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));

            log.info("Sending async request to Microsoft login URL with body: {}", formData);

            try {
                String tokenResponse = authClient.sendPost(loginUrl, formData);
                log.info("Received token response successfully.");
                return tokenResponse;
            } catch (IOException e) {
                log.error("Exception during request to Microsoft login: {}", e.getMessage(), e);
                throw new AuthRequestException("Request to Microsoft login failed", e);
            }
        });
    }
}


package com.example.auth.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.classic.methods.ClassicHttpRequest;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.hc.core5.http.io.support.ClassicResponseHandlers;

import java.io.IOException;
import java.time.Duration;

@Slf4j
public class AuthClient {

    private final CloseableHttpClient client;
    private final int connectionTimeout;
    private final int socketTimeout;
    private final int connectionRequestTimeout;

    public AuthClient(Duration connectionTimeout, Duration socketTimeout, Duration connectionRequestTimeout) {
        // Convert Duration to milliseconds for HttpClient
        this.connectionTimeout = (int) connectionTimeout.toMillis();
        this.socketTimeout = (int) socketTimeout.toMillis();
        this.connectionRequestTimeout = (int) connectionRequestTimeout.toMillis();

        // Configure timeouts
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(this.connectionTimeout)
                .setResponseTimeout(this.socketTimeout)
                .setConnectionRequestTimeout(this.connectionRequestTimeout)
                .build();

        this.client = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    public String sendPost(String url, String formData) throws IOException {
        HttpPost request = new HttpPost(url);
        request.setEntity(new StringEntity(formData, ContentType.APPLICATION_FORM_URLENCODED));

        // ResponseHandler to handle the response
        var responseHandler = ClassicResponseHandlers.<String>handleResponse(response -> {
            int statusCode = response.getCode();
            HttpEntity entity = response.getEntity();
            String responseBody = entity != null ? EntityUtils.toString(entity) : null;

            log.info("Received response: Status Code = {}, Body = {}", statusCode, responseBody);

            if (statusCode >= 200 && statusCode < 300) {
                return responseBody;
            } else {
                throw new IOException("Received non-success response: " + statusCode + " - " + responseBody);
            }
        });

        return client.execute(request, responseHandler);
    }
}




package com.example.auth;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
public class MicrosoftAuthService {

    private final String loginUrl;
    private final String clientId;
    private final String clientSecret;
    private final String scope;
    private final String grantType;

    public MicrosoftAuthService() {
        this.loginUrl = System.getenv("MICROSOFT_LOGIN_URL");
        this.clientId = System.getenv("CLIENT_ID");
        this.clientSecret = System.getenv("CLIENT_SECRET");
        this.scope = System.getenv("SCOPE");
        this.grantType = "client_credentials";

        validateConfig();
    }

    private void validateConfig() {
        if (loginUrl == null || clientId == null || clientSecret == null || scope == null) {
            throw new IllegalArgumentException("Required environment variables are not set.");
        }
    }

    public CompletableFuture<String> retrieveToken() {
        return CompletableFuture.supplyAsync(() -> {
            Map<String, String> formParams = Map.of(
                    "client_id", clientId,
                    "client_secret", clientSecret,
                    "scope", scope,
                    "grant_type", grantType
            );

            String formData = formParams.entrySet()
                    .stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .collect(Collectors.joining("&"));

            HttpPost request = new HttpPost(loginUrl);
            request.setEntity(new StringEntity(formData, ContentType.APPLICATION_FORM_URLENCODED));

            try (CloseableHttpClient client = HttpClients.createDefault();
                 CloseableHttpResponse response = client.execute(request)) {

                int statusCode = response.getCode();
                HttpEntity entity = response.getEntity();
                String responseBody = entity != null ? new String(entity.getContent().readAllBytes()) : null;

                log.info("Request to Microsoft login sent. Form data: {}", formData);
                log.info("Received response: Status Code = {}, Body = {}", statusCode, responseBody);

                if (statusCode >= 200 && statusCode < 300) {
                    return responseBody;
                } else {
                    throw new AuthRequestException("Non-success response: " + statusCode, statusCode);
                }
            } catch (IOException e) {
                log.error("Exception during request to Microsoft login: {}", e.getMessage(), e);
                throw new AuthRequestException("Request to Microsoft login failed", e);
            }
        });
    }
}

package com.example.auth.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.time.Duration;

@Slf4j
public class AuthClient {

    private final CloseableHttpClient client;

    public AuthClient(Duration connectionTimeout, Duration socketTimeout, Duration connectionRequestTimeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout((int) connectionTimeout.toMillis())
                .setResponseTimeout((int) socketTimeout.toMillis())
                .setConnectionRequestTimeout((int) connectionRequestTimeout.toMillis())
                .build();

        this.client = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    public String sendPost(String url, String formData) throws IOException {
        HttpPost request = new HttpPost(url);
        request.setEntity(new StringEntity(formData, ContentType.APPLICATION_FORM_URLENCODED));

        try (CloseableHttpResponse response = client.execute(request)) {
            int statusCode = response.getCode();
            String responseBody = response.getEntity() != null ? EntityUtils.toString(response.getEntity()) : null;

            log.info("Received response: Status Code = {}, Body = {}", statusCode, responseBody);

            if (statusCode >= 200 && statusCode < 300) {
                return responseBody;
            } else {
                throw new IOException("Received non-success response: " + statusCode + " - " + responseBody);
            }
        }
    }
}


package com.example.auth.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.util.Timeout;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Slf4j
public class AuthClient {

    private final CloseableHttpClient client;

    public AuthClient(Duration connectionTimeout, Duration socketTimeout, Duration connectionRequestTimeout) {
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(connectionTimeout.toMillis()))
                .setResponseTimeout(Timeout.ofMilliseconds(socketTimeout.toMillis()))
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(connectionRequestTimeout.toMillis()))
                .build();

        this.client = HttpClients.custom()
                .setDefaultRequestConfig(requestConfig)
                .build();
    }

    public String sendPost(String url, String formData) throws IOException {
        HttpPost request = new HttpPost(url);
        request.setEntity(new StringEntity(formData, ContentType.APPLICATION_FORM_URLENCODED));

        // Using a response handler to handle the response processing
        var responseHandler = response -> {
            int statusCode = response.getCode();
            String responseBody = response.getEntity() != null ? EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8) : "";

            log.info("Received response: Status Code = {}, Body = {}", statusCode, responseBody);

            if (statusCode >= 200 && statusCode < 300) {
                return responseBody;
            } else {
                log.error("Non-successful response received: Status Code = {}, Body = {}", statusCode, responseBody);
                throw new IOException("Received non-success response: " + statusCode + " - " + responseBody);
            }
        };

        // Execute the request with the response handler
        return client.execute(request, responseHandler);
    }
}


package com.example.auth.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TokenResponse {
    
    @JsonProperty("access_token")
    private String accessToken;
    
    @JsonProperty("expires_in")
    private int expiresIn;

    @JsonProperty("token_type")
    private String tokenType;

    // Getters and Setters
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    @Override
    public String toString() {
        return "TokenResponse{" +
                "accessToken='" + accessToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", tokenType='" + tokenType + '\'' +
                '}';
    }
}




package com.example.auth.service;

import com.example.auth.client.AuthClient;
import com.example.auth.response.TokenResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.Map;

@Slf4j
public class MicrosoftAuthService {

    private final AuthClient authClient;
    private final String loginUrl;
    private final String clientId;
    private final String clientSecret;
    private final String scope;
    private final String grantType = "client_credentials";

    public MicrosoftAuthService() {
        this.authClient = new AuthClient();
        this.loginUrl = System.getenv("MICROSOFT_LOGIN_URL");
        this.clientId = System.getenv("CLIENT_ID");
        this.clientSecret = System.getenv("CLIENT_SECRET");
        this.scope = System.getenv("SCOPE");

        validateConfig();
    }

    private void validateConfig() {
        if (loginUrl == null || clientId == null || clientSecret == null || scope == null) {
            throw new IllegalArgumentException("Required environment variables are not set.");
        }
    }

    public HttpResponse<TokenResponse> retrieveToken() {
        Map<String, String> formParams = Map.of(
                "client_id", clientId,
                "client_secret", clientSecret,
                "scope", scope,
                "grant_type", grantType
        );

        try {
            log.info("Calling AuthClient to retrieve token.");
            return authClient.sendPost(loginUrl, formParams);
        } catch (IOException | InterruptedException e) {
            log.error("Exception during request to Microsoft login: {}", e.getMessage(), e);
            throw new RuntimeException("Request to Microsoft login failed", e);
        }
    }
}


package com.example.auth.client;

import com.example.auth.response.TokenResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class AuthClient {

    private static final int TIMEOUT_SECONDS = 5;
    private final HttpClient client;
    private final ObjectMapper objectMapper;

    public AuthClient() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public HttpResponse<TokenResponse> sendPost(String url, Map<String, String> formParams) throws IOException, InterruptedException {
        String formData = formParams.entrySet()
                .stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .collect(Collectors.joining("&"));

        // Building the request with detailed logging
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(TIMEOUT_SECONDS))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(formData, StandardCharsets.UTF_8))
                .build();

        logRequestDetails(request, formData);

        HttpResponse<TokenResponse> response = client.send(request, responseInfo -> {
            // Response Body Subscriber
            HttpResponse.BodySubscriber<String> subscriber = HttpResponse.BodySubscribers.ofString(StandardCharsets.UTF_8);
            return HttpResponse.BodySubscribers.mapping(subscriber, body -> {
                try {
                    log.info("Received response body: {}", body);
                    return objectMapper.readValue(body, TokenResponse.class);
                } catch (IOException e) {
                    log.error("Failed to parse JSON response: {}", e.getMessage(), e);
                    throw new RuntimeException("Failed to parse JSON response", e);
                }
            });
        });

        logResponseDetails(response);
        return response;
    }

    private void logRequestDetails(HttpRequest request, String formData) {
        log.info("Sending HTTP Request:");
        log.info("Method: {}", request.method());
        log.info("URL: {}", request.uri());
        log.info("Headers: {}", request.headers().map());
        log.info("Request Body (Form Params): {}", formData);
    }

    private void logResponseDetails(HttpResponse<?> response) {
        log.info("Received HTTP Response:");
        log.info("Status Code: {}", response.statusCode());
        log.info("Headers: {}", response.headers().map());
        log.info("Response Body: {}", response.body());
    }
}







