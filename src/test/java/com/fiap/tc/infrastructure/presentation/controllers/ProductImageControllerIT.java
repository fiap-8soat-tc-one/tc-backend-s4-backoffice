package com.fiap.tc.infrastructure.presentation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.tc.infrastructure.presentation.response.CategoryResponse;
import com.fiap.tc.infrastructure.presentation.response.ProductResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.fiap.tc.util.TestUtils.readResourceFileAsString;
import static java.lang.String.format;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ProductImageControllerIT {

    private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJteWxsZXIiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwicHJvZmlsZSI6IkFETUlOSVNUUkFUT1IiLCJuYW1lIjoiTXlsbGVyIFNha2FndWNoaSIsImV4cCI6MTczODc4NzQ4MCwidXVpZCI6IjM0ODQ4ZTIwLTk2NzktMTFlYi05ZTEzLTAyNDJhYzExMDAwMiIsImF1dGhvcml0aWVzIjpbIkRFTEVURV9DVVNUT01FUlMiLCJSRUdJU1RFUl9PUkRFUlMiLCJMSVNUX1VTRVJTIiwiU0VBUkNIX09SREVSUyIsIkVESVRfT1JERVJTIiwiU0VBUkNIX1BST0RVQ1RTIiwiRURJVF9VU0VSUyIsIkRFTEVURV9QUk9EVUNUUyIsIkRFTEVURV9PUkRFUlMiLCJSRUdJU1RFUl9DVVNUT01FUlMiLCJERUxFVEVfVVNFUlMiLCJMSVNUX1BST0RVQ1RTIiwiU0VBUkNIX0NBVEVHT1JJRVMiLCJMSVNUX0NBVEVHT1JJRVMiLCJMSVNUX0NVU1RPTUVSUyIsIlVQREFURV9TVEFUVVNfT1JERVJTIiwiTElTVF9PUkRFUlMiLCJFRElUX0NVU1RPTUVSUyIsIlJFR0lTVEVSX1VTRVJTIiwiU0VBUkNIX0NVU1RPTUVSUyIsIkVESVRfQ0FURUdPUklFUyIsIlJFR0lTVEVSX0NBVEVHT1JJRVMiLCJQUk9DRVNTX1BBWU1FTlRTIiwiREVMRVRFX0NBVEVHT1JJRVMiLCJSRUdJU1RFUl9QUk9EVUNUUyIsIkVESVRfUFJPRFVDVFMiLCJTRUFSQ0hfVVNFUlMiXSwianRpIjoiYjQ5MDkxNmYtNWM0My00ZDk5LWE4MGUtNjkyZWRmY2Y4NTU2IiwiY2xpZW50X2lkIjoidGNfY2xpZW50In0.-451o3Aq_K_XGDfCdJKtbEgrsM5pj2diF5rEMclyhg8";
    private static final String CUSTOMER_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4ODQwNDA3MTAzOSIsImlkIjoiM2ZhODVmNjQtNTcxNy00NTYyLWIzZmMtMmM5NjNmNjZhZmE2IiwibmFtZSI6Ik15bGxlciBTYWthZ3VjaGkiLCJlbWFpbCI6Im15bGxlcnNha2FndWNoaUBnbWFpbC5jb20iLCJkb2N1bWVudCI6Ijg4NDA0MDcxMDM5IiwiaWF0IjoxNzM4NzAxMTQ0LCJleHAiOjE3Mzg3MDQ3NDR9.lEOHQAah5uFw1Uq0zRM1vx3a99G-ZsVQkizWIpi_Lnbz5hsDlIxyxa_64Xv2L6s5aP4RlvC-dSzw4oAQ96Yhxg";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void uploadProductImagesTest() throws Exception {
        var category = createCategory();
        var product = createProduct(category.getId());
        createProductImage(product.getId());
    }

    @Test
    @Transactional
    public void deleteProductImagesTest() throws Exception {
        var category = createCategory();
        var product = createProduct(category.getId());
        product = createProductImage(product.getId());
        mockMvc.perform(delete("/api/private/v1/products/images")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readResourceFileAsString("requests/delete_product_images.json")
                                .replace("{{productId}}", product.getId().toString())
                                .replace("{{imageId}}", product.getImages().get(0).getId().toString()))
                        .header("Authorization", getBackofficeTokenTest()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
        
    }

    public ProductResponse createProductImage(UUID productId) throws Exception {
        String responseJson = mockMvc.perform(post("/api/private/v1/products/images")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readResourceFileAsString("requests/register_product_images.json")
                                .replace("{{productId}}", productId.toString()))
                        .header("Authorization", getBackofficeTokenTest()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(responseJson, ProductResponse.class);
    }

    public CategoryResponse createCategory() throws Exception {
        String responseJson = mockMvc.perform(post("/api/private/v1/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readResourceFileAsString("requests/create_category.json"))
                        .header("Authorization", getBackofficeTokenTest()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.description").exists())
                .andReturn().getResponse().getContentAsString();


        return objectMapper.readValue(responseJson, CategoryResponse.class);
    }

    private ProductResponse createProduct(UUID categoryId) throws Exception {
        String responseJson = mockMvc.perform(post("/api/private/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readResourceFileAsString("requests/create_product.json")
                                .replace("{{categoryId}}", categoryId.toString()))
                        .header("Authorization", getBackofficeTokenTest()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.id_category").exists())
                .andExpect(jsonPath("$.images").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.price").exists())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(responseJson, ProductResponse.class);
    }

    private Object getBackofficeTokenTest() {
        return format("Bearer %s", TOKEN);
    }
}
