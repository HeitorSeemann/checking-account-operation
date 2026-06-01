package com.heitor.checkingaccountoperation.controller;

import com.heitor.checkingaccountoperation.controller.dto.TransactionOutputDTO;
import com.heitor.checkingaccountoperation.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TransactionRestTest {

    MockMvc mockMvc;

    @InjectMocks
    private TransactionRest transactionRest;

    @Mock
    private TransactionService service;

    @Test
    void shouldGetSuccessfully() throws Exception {
        TransactionOutputDTO dto = getTransactionOutputDTO();
        mockMvc = MockMvcBuilders.standaloneSetup(transactionRest).build();
        Mockito.when(service.search(Mockito.any())).thenReturn(List.of(dto));

        mockMvc.perform(get("/checkingaccount/transactions/withdrawal/56"))
                .andExpect(status().isOk());
    }

    private TransactionOutputDTO getTransactionOutputDTO() {
        TransactionOutputDTO dto = new TransactionOutputDTO();
        dto.setAccount(56);
        return dto;
    }

}