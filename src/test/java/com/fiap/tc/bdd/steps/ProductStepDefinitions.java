package com.fiap.tc.bdd.steps;

import com.fiap.tc.infrastructure.presentation.requests.ProductRequest;
import com.fiap.tc.infrastructure.presentation.response.ProductResponse;
import com.fiap.tc.util.TestUtils;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductStepDefinitions extends BaseScenariosStepDefinitions {
    public static final String PRODUCT_URL = "http://localhost:8080/api/private/v1/products";
    public static final String GET_PRODUCT_URL = "http://localhost:8080/api/private/v1/products/%s";
    public static final UUID DEFAULT_CATEGORY = UUID.fromString("52ad266d-bcb5-4101-a8e1-cc45cc83afad");

    private ProductResponse newProduct;

    @When("send a request to register a new product")
    public void registerNewProduct() {
        registerProduct();
    }

    @Then("the product is registered with success")
    public void productRegisteredWithSuccess() {
        assertThat(newProduct).isNotNull();
        assertThat(newProduct).isInstanceOf(ProductResponse.class);
        var productResult = getProduct(newProduct.getId().toString());
        assertThat(productResult).isEqualTo(newProduct);
    }


    @SneakyThrows
    private void registerProduct() {
        var request = objectMapper.readValue(
                TestUtils.readResourceFileAsString("requests/create_product.json").replace("{{categoryId}}",
                        DEFAULT_CATEGORY.toString()),
                ProductRequest.class);

        var requestEntity = new HttpEntity<>(request, getBackofficeTokenHttpHeaders());

        var productResponseResult = testRestTemplate.exchange(PRODUCT_URL, HttpMethod.POST, requestEntity,
                ProductResponse.class);
        assertThat(productResponseResult.getStatusCode()).isEqualTo(HttpStatus.OK);

        newProduct = productResponseResult.getBody();
    }

    private ProductResponse getProduct(String id) {
        var url = String.format(GET_PRODUCT_URL, id);
        var requestEntity = new HttpEntity<>(null, getBackofficeTokenHttpHeaders());
        var result = testRestTemplate.exchange(url, HttpMethod.GET, requestEntity,
                ProductResponse.class);
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isInstanceOf(ProductResponse.class);
        return result.getBody();
    }

}
