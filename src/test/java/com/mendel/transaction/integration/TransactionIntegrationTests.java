package com.mendel.transaction.integration;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.mendel.transaction.dto.TransactionRequestDTO;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ComponentScan(basePackages = "com.mendel")
@SpringBootTest
@AutoConfigureMockMvc
class TransactionIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createTransactionWithoutParent() throws Exception {
        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(null, BigDecimal.valueOf(150), "cards");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(transactionRequestDTO);

        this.mockMvc.perform(put("/transactions/1").content(requestJson).contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json("{\"id\":1,\"parentId\":null,\"amount\":150,\"type\":\"cards\"}"));
    }

    @Test
    public void createTransactionAndTransactionChild() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(null, BigDecimal.valueOf(150), "cards");
        String requestJson = ow.writeValueAsString(transactionRequestDTO);

        this.mockMvc.perform(put("/transactions/1").content(requestJson).contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json("{\"id\":1,\"parentId\":null,\"amount\":150,\"type\":\"cards\"}"));

        transactionRequestDTO = new TransactionRequestDTO(1L, BigDecimal.valueOf(120), "cards");
        requestJson = ow.writeValueAsString(transactionRequestDTO);

        this.mockMvc.perform(put("/transactions/2").content(requestJson).contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json("{\"id\":2,\"parentId\":1,\"amount\":120,\"type\":\"cards\"}"));
    }

    @Test
    public void getSum() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(null, BigDecimal.valueOf(150), "cards");
        String requestJson = ow.writeValueAsString(transactionRequestDTO);

        this.mockMvc.perform(put("/transactions/1").content(requestJson).contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json("{\"id\":1,\"parentId\":null,\"amount\":150,\"type\":\"cards\"}"));

        transactionRequestDTO = new TransactionRequestDTO(1L, BigDecimal.valueOf(120), "cards");
        requestJson = ow.writeValueAsString(transactionRequestDTO);

        this.mockMvc.perform(put("/transactions/2").content(requestJson).contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json("{\"id\":2,\"parentId\":1,\"amount\":120,\"type\":\"cards\"}"));

        this.mockMvc.perform(get("/transactions/sum/1")).
                andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json("{\"sum\":270}"));

        this.mockMvc.perform(get("/transactions/sum/2")).
                andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json("{\"sum\":120}"));
    }

    @Test
    public void getSumTxNotExist() throws Exception {

        this.mockMvc.perform(get("/transactions/sum/1")).
                andExpect(status().isNotFound());

    }

    @Test
    public void getTypes() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();

        TransactionRequestDTO transactionRequestDTO = new TransactionRequestDTO(null, BigDecimal.valueOf(150), "cards");
        String requestJson = ow.writeValueAsString(transactionRequestDTO);

        this.mockMvc.perform(put("/transactions/1").content(requestJson).contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json("{\"id\":1,\"parentId\":null,\"amount\":150,\"type\":\"cards\"}"));

        TransactionRequestDTO transactionRequestDTO1 = new TransactionRequestDTO(1L, BigDecimal.valueOf(120), "ecommerce");
        String requestJson1 = ow.writeValueAsString(transactionRequestDTO1);

        this.mockMvc.perform(put("/transactions/2").content(requestJson1).contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json("{\"id\":2,\"parentId\":1,\"amount\":120,\"type\":\"ecommerce\"}"));

        TransactionRequestDTO transactionRequestDTO2 = new TransactionRequestDTO(1L, BigDecimal.valueOf(120), "presential");
        String requestJson2 = ow.writeValueAsString(transactionRequestDTO2);
        this.mockMvc.perform(put("/transactions/3").content(requestJson2).contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json("{\"id\":3,\"parentId\":1,\"amount\":120,\"type\":\"presential\"}"));

        this.mockMvc.perform(put("/transactions/4").content(requestJson2).contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json("{\"id\":4,\"parentId\":1,\"amount\":120,\"type\":\"presential\"}"));


        this.mockMvc.perform(get("/transactions/types/cards")).
                andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json("[1]"));

        this.mockMvc.perform(get("/transactions/types/ecommerce")).
                andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json("[1]"));

        this.mockMvc.perform(get("/transactions/types/presential")).
                andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json("[3,4]"));
    }


}
