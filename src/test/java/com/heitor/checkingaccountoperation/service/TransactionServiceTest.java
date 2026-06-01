package com.heitor.checkingaccountoperation.service;

import com.heitor.checkingaccountoperation.controller.dto.NoteOutputDto;
import com.heitor.checkingaccountoperation.controller.dto.TransactionInputDto;
import com.heitor.checkingaccountoperation.controller.dto.TransactionOutputDTO;
import com.heitor.checkingaccountoperation.controller.exception.WithdrawalException;
import com.heitor.checkingaccountoperation.converter.TransactionConverter;
import com.heitor.checkingaccountoperation.entity.Transaction;
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

import java.util.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionService service;

    @Mock
    private TransactionRepository repository;

    @Mock
    private TransactionConverter converter;

    private static final String TEST_SUID = UUID.randomUUID().toString();

    @Test
    void shouldWithdrawWithSuccess() throws WithdrawalException {
        TransactionInputDto inputDto = new TransactionInputDto();
        inputDto.setValue(180);

        Transaction mockEntity = new Transaction();
        List<NoteOutputDto> mockNotesList = new ArrayList<>();

        Mockito.when(repository.findBySuid(TEST_SUID)).thenReturn(Optional.empty());
        Mockito.when(converter.toEntity(inputDto, MockUtils.ACCOUNT_NUMBER)).thenReturn(mockEntity);
        Mockito.when(converter.toListNoteOutputDTO(Mockito.any(HashMap.class))).thenReturn(mockNotesList);

        List<NoteOutputDto> result = service.withdraw(inputDto, MockUtils.ACCOUNT_NUMBER, TEST_SUID);

        Assertions.assertNotNull(result);
        Mockito.verify(repository, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    void shouldThrowExceptionWhenSuidAlreadyUsed() {
        TransactionInputDto inputDto = MockUtils.createTransactionInputDto();
        Mockito.when(repository.findBySuid(TEST_SUID)).thenReturn(Optional.of(new Transaction()));

        WithdrawalException exception = Assertions.assertThrows(WithdrawalException.class, () -> {
            service.withdraw(inputDto, MockUtils.ACCOUNT_NUMBER, TEST_SUID);
        });

        Assertions.assertEquals("Error - SUID already used", exception.getMessage());
        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
    }

    @Test
    void shouldThrowExceptionWhenDatabaseSaveFails() {
        TransactionInputDto inputDto = new TransactionInputDto();
        inputDto.setValue(50);

        Mockito.when(repository.findBySuid(TEST_SUID)).thenReturn(Optional.empty());
        Mockito.when(converter.toEntity(inputDto, MockUtils.ACCOUNT_NUMBER)).thenReturn(new Transaction());
        Mockito.when(repository.save(Mockito.any())).thenThrow(Mockito.mock(DataAccessException.class));

        WithdrawalException exception = Assertions.assertThrows(WithdrawalException.class, () -> {
            service.withdraw(inputDto, MockUtils.ACCOUNT_NUMBER, TEST_SUID);
        });

        Assertions.assertEquals("Error to withdraw", exception.getMessage());
    }

    @Test
    void shouldCalculateNoteValuesCorrectly() throws WithdrawalException {
        HashMap<Integer, Integer> result = service.searchNoteValues(180);
        Assertions.assertEquals(1, result.get(100));
        Assertions.assertEquals(1, result.get(50));
        Assertions.assertEquals(1, result.get(20));
        Assertions.assertEquals(1, result.get(10));
    }

    @Test
    void shouldThrowExceptionWhenValueIsNotAllowed() {
        WithdrawalException exception = Assertions.assertThrows(WithdrawalException.class, () -> {
            service.searchNoteValues(7);
        });

        Assertions.assertEquals("Value not allowed to withdraw", exception.getMessage());
    }

    @Test
    void shouldSearchTransactionsWithSuccess() throws WithdrawalException {
        List<Transaction> mockEntities = new ArrayList<>();
        List<TransactionOutputDTO> mockDtos = MockUtils.createListTransactionOutputDTO();

        Mockito.when(repository.findByAccount(MockUtils.ACCOUNT_NUMBER)).thenReturn(mockEntities);
        Mockito.when(converter.toListTransactionOutputDTO(mockEntities)).thenReturn(mockDtos);

        List<TransactionOutputDTO> result = service.search(MockUtils.ACCOUNT_NUMBER);

        Assertions.assertEquals(mockDtos, result);
    }

    @Test
    void shouldThrowExceptionWhenSearchFails() {
        Mockito.when(repository.findByAccount(MockUtils.ACCOUNT_NUMBER)).thenThrow(RuntimeException.class);

        WithdrawalException exception = Assertions.assertThrows(WithdrawalException.class, () -> {
            service.search(MockUtils.ACCOUNT_NUMBER);
        });

        Assertions.assertEquals("Error to find withdraw", exception.getMessage());
    }

}
