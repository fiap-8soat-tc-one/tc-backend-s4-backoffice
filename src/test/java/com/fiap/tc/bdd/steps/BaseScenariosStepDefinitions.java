package com.fiap.tc.bdd.steps;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.tc.infrastructure.core.config.RestClientOAuthConfig;
import com.fiap.tc.infrastructure.dto.AuthDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import static java.lang.String.format;
import static java.util.Objects.isNull;
import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("ALL")
public class BaseScenariosStepDefinitions {

    @LocalServerPort
    int port;
    public static final String URL_BACKOFFICE_LOGIN = "http://localhost:8081/%s";
    public static final String BACKOFFICE_LOGIN_RESOURCE = "oauth/token";
    public static final String BACKOFFICE_LOGIN_TEMPLATE = "username=%s&password=%s&grant_type=password";

    public static final String URL_UPDATE_ORDER_TEMPLATE = "%s:%s/%s";
    public static final String ORDER_STATUS_RESOURCE = "api/private/v1/orders/status";
    public static final String GET_ORDER_RESOURCE = "api/private/v1/orders/%s";

    public static final String CUSTOMER_LOGIN_URL = "http://localhost:8081/api/public/v1/oauth/token";
    public static final String CUSTOMER_DOCUMENT = "88404071039";

    public static final String CUSTOMER_TOKEN_VALIDATE_URL = "http://localhost:8081/api/public/v1/oauth/token/validate";

    @Autowired
    private RestClientOAuthConfig restClientConfig;
    @Autowired
    protected TestRestTemplate testRestTemplate;
    @Autowired
    protected ObjectMapper objectMapper;

    private String backofficeToken;

    protected String getBackofficeToken() {
        if (isNull(backofficeToken)) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.set("Authorization", format("Basic %s", restClientConfig.getClientId()));

            HttpEntity<String> request = new HttpEntity<>(
                    format(BACKOFFICE_LOGIN_TEMPLATE, restClientConfig.getUserName(),
                            restClientConfig.getPassword()), headers);
            var url = String.format(URL_BACKOFFICE_LOGIN, BACKOFFICE_LOGIN_RESOURCE);
            ResponseEntity<AuthDetail> response = testRestTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    request,
                    AuthDetail.class);

            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody().getAccessToken()).isNotNull();
            return response.getBody().getAccessToken();
        }
        return backofficeToken;
    }

    protected HttpHeaders getBackofficeTokenHttpHeaders() {
        var headers = new HttpHeaders();
        headers.set("Authorization", format("Bearer %s", getBackofficeToken()));
        return headers;
    }


}
