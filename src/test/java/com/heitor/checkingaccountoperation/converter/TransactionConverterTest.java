package com.heitor.checkingaccountoperation.converter;

import com.heitor.checkingaccountoperation.controller.dto.NoteOutputDto;
import com.heitor.checkingaccountoperation.controller.dto.TransactionOutputDTO;
import com.heitor.checkingaccountoperation.entity.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import utils.MockUtils;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class TransactionConverterTest {

    @InjectMocks
    private TransactionConverter converter;

    @Test
    void shouldConvertToEntitySuccessfully() {
        Transaction convertedTransaction = converter.toEntity(MockUtils.createTransactionInputDto(), MockUtils.ACCOUNT_NUMBER);
        Assertions.assertEquals(MockUtils.ACCOUNT_NUMBER, convertedTransaction.getAccount());
    }

    @Test
    void shouldConvertToNoteOutputDtoListSuccessfully() {
        List<NoteOutputDto> convertedNotes = converter.toListNoteOutputDTO(MockUtils.createNotes());
        Assertions.assertEquals(MockUtils.TEN_NOTE_VALUE, convertedNotes.get(0).getNote());
    }

    @Test
    void shouldConvertToTransactionOutputDtoSuccessfully() {
        TransactionOutputDTO convertedDto = converter.toTransactionOutputDTO(MockUtils.createTransaction());
        Assertions.assertEquals(MockUtils.ACCOUNT_NUMBER, convertedDto.getAccount());
    }

    @Test
    void shouldConvertToListTransactionOutputDtoSuccessfully() {
        List<TransactionOutputDTO> convertedDtoList = MockUtils.createListTransactionOutputDTO();
        Assertions.assertEquals(MockUtils.ACCOUNT_NUMBER, convertedDtoList.get(0).getAccount());
    }
}