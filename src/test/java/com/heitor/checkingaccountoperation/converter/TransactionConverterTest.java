package com.heitor.checkingaccountoperation.converter;

import com.heitor.checkingaccountoperation.controller.dto.TransactionOutputDTO;
import com.heitor.checkingaccountoperation.entity.Transaction;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import utils.MockUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TransactionConverterTest {

    private final TransactionConverter converter = Mappers.getMapper(TransactionConverter.class);

    @Test
    public void shouldConvertInputDtoToEntity() {
        Transaction convertedTransaction = converter.toEntity(MockUtils.createTransactionInputDto(), MockUtils.ACCOUNT_NUMBER);

        assertNotNull(convertedTransaction);
        assertEquals(MockUtils.ACCOUNT_NUMBER, convertedTransaction.getAccount());
        assertEquals(MockUtils.TRANSACTION_VALUE, convertedTransaction.getValue());
    }

    @Test
    public void shouldConvertEntityToOutputDto() {
        TransactionOutputDTO convertedDto = converter.toTransactionOutputDTO(MockUtils.createTransaction());

        assertNotNull(convertedDto);
        assertEquals(MockUtils.ACCOUNT_NUMBER, convertedDto.getAccount());
        assertEquals(MockUtils.TRANSACTION_VALUE, convertedDto.getValue());
    }
}
