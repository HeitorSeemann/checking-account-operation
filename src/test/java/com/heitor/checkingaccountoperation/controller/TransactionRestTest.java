package com.heitor.checkingaccountoperation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heitor.checkingaccountoperation.controller.dto.NoteOutputDto;
import com.heitor.checkingaccountoperation.controller.dto.TransactionInputDto;
import com.heitor.checkingaccountoperation.controller.dto.TransactionOutputDTO;
import com.heitor.checkingaccountoperation.controller.exception.handler.WithdrawalExceptionHandler;
import com.heitor.checkingaccountoperation.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import utils.MockUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TransactionRestTest {

    private MockMvc mockMvc;

    @InjectMocks
    private TransactionRest transactionController;

    @Mock
    private TransactionService transactionService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final String TEST_SUID = UUID.randomUUID().toString();

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(transactionController)
                .setControllerAdvice(new WithdrawalExceptionHandler())
                .build();
    }

    @Test
    void shouldExecuteWithdrawalSuccessfully() throws Exception {
        TransactionInputDto inputDto = MockUtils.createTransactionInputDto();
        List<NoteOutputDto> listNoteOutputDto = new ArrayList<>();

        TransactionOutputDTO outputDto = new TransactionOutputDTO();
        outputDto.setAccount(MockUtils.ACCOUNT_NUMBER);
        outputDto.setValue(MockUtils.PERMITTED_WITHDRAWAL_AMOUNT);

        Mockito.when(transactionService.withdraw(Mockito.any(TransactionInputDto.class), Mockito.anyInt(), Mockito.eq(TEST_SUID)))
                .thenReturn(listNoteOutputDto);

        mockMvc.perform(post("/accounts/withdrawals/{accountNumber}", MockUtils.ACCOUNT_NUMBER)
                        .header("Idempotency-Key", TEST_SUID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturnBadRequestWhenIdempotencyKeyIsMissing() throws Exception {
        TransactionInputDto inputDto = MockUtils.createTransactionInputDto();
        mockMvc.perform(post("/accounts/withdrawals/{accountNumber}", MockUtils.ACCOUNT_NUMBER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldGetTransactionsSuccessfully() throws Exception {
        List<TransactionOutputDTO> mockList = MockUtils.createListTransactionOutputDTO();

        Mockito.when(transactionService.search(MockUtils.ACCOUNT_NUMBER))
                .thenReturn(mockList);

        mockMvc.perform(get("/accounts/withdrawals/{accountNumber}", MockUtils.ACCOUNT_NUMBER)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].account").value(MockUtils.ACCOUNT_NUMBER))
                .andExpect(jsonPath("$[0].value").value(MockUtils.TRANSACTION_VALUE));
    }
}
