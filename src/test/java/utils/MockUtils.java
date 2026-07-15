package utils;

import com.heitor.checkingaccountoperation.controller.dto.TransactionInputDTO;
import com.heitor.checkingaccountoperation.controller.dto.TransactionOutputDTO;
import com.heitor.checkingaccountoperation.entity.Transaction;

import java.util.*;

public class MockUtils {
    public static final int PERMITTED_WITHDRAWAL_AMOUNT = 30;
    public static final int ACCOUNT_NUMBER = 110;
    public static final int TRANSACTION_VALUE = 160;

    public static TransactionInputDTO createTransactionInputDto() {
        TransactionInputDTO dto = new TransactionInputDTO();
        dto.setValue(TRANSACTION_VALUE);
        return dto;
    }

    public static List<TransactionOutputDTO> createListTransactionOutputDTO() {
        TransactionOutputDTO dto = new TransactionOutputDTO();
        dto.setValue(TRANSACTION_VALUE);
        dto.setAccount(ACCOUNT_NUMBER);
        List<TransactionOutputDTO> dtoList = new ArrayList<>();
        dtoList.add(dto);
        return dtoList;
    }

    public static Transaction createTransaction() {
        Transaction entity = new Transaction();
        entity.setValue(TRANSACTION_VALUE);
        entity.setAccount(ACCOUNT_NUMBER);
        return entity;
    }
}
