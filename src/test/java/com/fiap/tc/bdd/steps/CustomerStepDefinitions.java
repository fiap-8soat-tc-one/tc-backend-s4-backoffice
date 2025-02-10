package com.fiap.tc.bdd.steps;

import com.fiap.tc.infrastructure.presentation.requests.CustomerRequest;
import com.fiap.tc.infrastructure.presentation.response.CustomerResponse;
import com.fiap.tc.util.CpfGenerator;
import com.fiap.tc.util.TestUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerStepDefinitions extends BaseScenariosStepDefinitions {
    public static final String CUSTOMER_URL = "http://localhost:8080/api/public/v1/customers";
    public static final String GET_CUSTOMER_URL = "http://localhost:8080/api/private/v1/customers/%s";

    private CustomerResponse newCustomer;
    private CustomerResponse customerResult;


    @When("send a request to register a new customer")
    public void registerNewCustomer() {
        registerCustomer();
    }

    @Then("the customer is registered with success")
    public void customerRegisteredWithSuccess() {
        assertThat(newCustomer).isNotNull();
        assertThat(newCustomer).isInstanceOf(CustomerResponse.class);
    }


    @SneakyThrows
    private void registerCustomer() {
        var request = objectMapper.readValue(TestUtils.readResourceFileAsString("requests/create_customer.json"),
                CustomerRequest.class);

        request.setDocument(CpfGenerator.execute(false));

        var requestEntity = new HttpEntity<>(request, null);

        var customerResponseResult = testRestTemplate.exchange(CUSTOMER_URL, HttpMethod.PUT, requestEntity,
                CustomerResponse.class);
        assertThat(customerResponseResult.getStatusCode()).isEqualTo(HttpStatus.OK);

        newCustomer = customerResponseResult.getBody();
    }

    @Given("a backoffice user is logged")
    public void loginBackofficeUser() {
        var backofficeLoggedUserToken = getBackofficeToken();
        assertThat(backofficeLoggedUserToken).isNotNull();
    }

    @When("send a request to search a customer")
    public void sendRequestFindCustomer() {
        customerResult = getCustomer();
    }

    @Then("the search return the registered customer")
    public void findCustomerReturnRegisteredCustomer() {
        assertThat(customerResult).isNotNull();
        assertThat(customerResult).isInstanceOf(CustomerResponse.class);

    }


    private CustomerResponse getCustomer() {
        var url = String.format(GET_CUSTOMER_URL, BaseScenariosStepDefinitions.CUSTOMER_DOCUMENT);
        var requestEntity = new HttpEntity<>(null, getBackofficeTokenHttpHeaders());
        var result = testRestTemplate.exchange(url, HttpMethod.GET, requestEntity,
                CustomerResponse.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isInstanceOf(CustomerResponse.class);
        return result.getBody();
    }

}
