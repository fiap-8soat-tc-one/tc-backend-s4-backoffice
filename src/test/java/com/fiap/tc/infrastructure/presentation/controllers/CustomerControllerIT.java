package com.fiap.tc.infrastructure.presentation.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.tc.infrastructure.presentation.response.CustomerResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.fiap.tc.util.TestUtils.readResourceFileAsString;
import static java.lang.String.format;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class CustomerControllerIT {
    private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiJteWxsZXIiLCJzY29wZSI6WyJyZWFkIiwid3JpdGUiXSwicHJvZmlsZSI6IkFETUlOSVNUUkFUT1IiLCJuYW1lIjoiTXlsbGVyIFNha2FndWNoaSIsImV4cCI6MTczODc4NzQ4MCwidXVpZCI6IjM0ODQ4ZTIwLTk2NzktMTFlYi05ZTEzLTAyNDJhYzExMDAwMiIsImF1dGhvcml0aWVzIjpbIkRFTEVURV9DVVNUT01FUlMiLCJSRUdJU1RFUl9PUkRFUlMiLCJMSVNUX1VTRVJTIiwiU0VBUkNIX09SREVSUyIsIkVESVRfT1JERVJTIiwiU0VBUkNIX1BST0RVQ1RTIiwiRURJVF9VU0VSUyIsIkRFTEVURV9QUk9EVUNUUyIsIkRFTEVURV9PUkRFUlMiLCJSRUdJU1RFUl9DVVNUT01FUlMiLCJERUxFVEVfVVNFUlMiLCJMSVNUX1BST0RVQ1RTIiwiU0VBUkNIX0NBVEVHT1JJRVMiLCJMSVNUX0NBVEVHT1JJRVMiLCJMSVNUX0NVU1RPTUVSUyIsIlVQREFURV9TVEFUVVNfT1JERVJTIiwiTElTVF9PUkRFUlMiLCJFRElUX0NVU1RPTUVSUyIsIlJFR0lTVEVSX1VTRVJTIiwiU0VBUkNIX0NVU1RPTUVSUyIsIkVESVRfQ0FURUdPUklFUyIsIlJFR0lTVEVSX0NBVEVHT1JJRVMiLCJQUk9DRVNTX1BBWU1FTlRTIiwiREVMRVRFX0NBVEVHT1JJRVMiLCJSRUdJU1RFUl9QUk9EVUNUUyIsIkVESVRfUFJPRFVDVFMiLCJTRUFSQ0hfVVNFUlMiXSwianRpIjoiYjQ5MDkxNmYtNWM0My00ZDk5LWE4MGUtNjkyZWRmY2Y4NTU2IiwiY2xpZW50X2lkIjoidGNfY2xpZW50In0.-451o3Aq_K_XGDfCdJKtbEgrsM5pj2diF5rEMclyhg8";
    private static final String CUSTOMER_TOKEN = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI4ODQwNDA3MTAzOSIsImlkIjoiM2ZhODVmNjQtNTcxNy00NTYyLWIzZmMtMmM5NjNmNjZhZmE2IiwibmFtZSI6Ik15bGxlciBTYWthZ3VjaGkiLCJlbWFpbCI6Im15bGxlcnNha2FndWNoaUBnbWFpbC5jb20iLCJkb2N1bWVudCI6Ijg4NDA0MDcxMDM5IiwiaWF0IjoxNzM4NzAxMTQ0LCJleHAiOjE3Mzg3MDQ3NDR9.lEOHQAah5uFw1Uq0zRM1vx3a99G-ZsVQkizWIpi_Lnbz5hsDlIxyxa_64Xv2L6s5aP4RlvC-dSzw4oAQ96Yhxg";

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    public void createCustomerTest() throws Exception {
        createCustomer();
    }

    private CustomerResponse createCustomer() throws Exception {
        String responseJson = mockMvc.perform(put("/api/public/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(readResourceFileAsString("requests/create_customer.json"))
                        .header("X-Authorization-Token", CUSTOMER_TOKEN))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.document").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.email").exists())
                .andReturn().getResponse().getContentAsString();

        return objectMapper.readValue(responseJson, CustomerResponse.class);
    }

    @Test
    @Transactional
    public void listCustomersTest() throws Exception {
        createCustomer();

        mockMvc.perform(get("/api/private/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", getCustomerTokenTest()))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").exists())
                .andExpect(jsonPath("$.content[0].id").exists())
                .andExpect(jsonPath("$.content[0].document").exists())
                .andExpect(jsonPath("$.content[0].name").exists())
                .andExpect(jsonPath("$.content[0].email").exists());
    }

    @Test
    @Transactional
    public void deleteCustomerTest() throws Exception {
        var customer = createCustomer();

        mockMvc.perform(delete("/api/private/v1/customers/{document}", customer.getDocument())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", getCustomerTokenTest()))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/private/v1/customers/{document}", customer.getDocument())
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", getCustomerTokenTest()))
                .andExpect(status().isNotFound());
    }

    private Object getCustomerTokenTest() {
        return format("Bearer %s", TOKEN);
    }
}
