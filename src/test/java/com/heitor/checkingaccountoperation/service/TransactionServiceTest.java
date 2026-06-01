package com.heitor.checkingaccountoperation.service;

import com.heitor.checkingaccountoperation.controller.dto.TransactionOutputDTO;
import com.heitor.checkingaccountoperation.controller.exception.WithdrawalException;
import com.heitor.checkingaccountoperation.converter.TransactionConverter;
import com.heitor.checkingaccountoperation.repository.TransactionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import utils.MockUtils;

import java.util.HashMap;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService service;

    @Mock
    private TransactionRepository repository;

    @Mock
    private TransactionConverter converter;

    @Test
    void shouldSearchNoteValuesSuccessfully() throws WithdrawalException {
        HashMap<Integer, Integer> fetchedNoteValues = service.searchNoteValues(MockUtils.PERMITTED_WITHDRAWAL_AMOUNT);
        HashMap<Integer, Integer> expectedNoteValues = MockUtils.createNotes();
        Assertions.assertEquals(expectedNoteValues, fetchedNoteValues);
    }

    @Test
    void shouldWithdrawSuccessfully() throws WithdrawalException {
        service.withdraw(MockUtils.createTransactionInputDto(), MockUtils.ACCOUNT_NUMBER);
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void shouldNotWithdrawSuccessfully() {
        Mockito.when(repository.save(Mockito.any())).thenThrow(Mockito.mock(DataAccessException.class));

        Exception exception = Assertions.assertThrows(Exception.class, () -> {
            service.withdraw(MockUtils.createTransactionInputDto(), MockUtils.ACCOUNT_NUMBER);
        });

        Assertions.assertEquals("Erro ao efetivar lançamento.", exception.getMessage());
    }

    @Test
    void shouldSearchTransactionsSuccessfully() throws WithdrawalException {
        List<TransactionOutputDTO> expectedTransactionList = MockUtils.createListTransactionOutputDTO();
        Mockito.when(converter.toListTransactionOutputDTO(Mockito.anyList())).thenReturn(expectedTransactionList);

        List<TransactionOutputDTO> fetchedTransactionList = service.search(MockUtils.ACCOUNT_NUMBER);
        Assertions.assertEquals(expectedTransactionList, fetchedTransactionList);
    }
}