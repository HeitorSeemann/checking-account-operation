package utils;

import com.heitor.checkingaccountoperation.controller.dto.TransactionOutputDTO;
import com.heitor.checkingaccountoperation.controller.dto.TransactionInputDto;
import com.heitor.checkingaccountoperation.entity.Transaction;

import java.util.*;

public class MockUtils {

    public static final int TEN_NOTE_VALUE = 10;
    public static final int PERMITTED_WITHDRAWAL_AMOUNT = 30;
    public static final int ACCOUNT_NUMBER = 110;
    public static final int TRANSACTION_VALUE = 160;
    public static final int TWENTY_NOTE_VALUE = 20;
    public static final int DEFAULT_QUANTITY = 1;
    public static final String ERROR_MSG_POSTING_TRANSACTION = "Error post-processing transaction.";

    public static HashMap<Integer, Integer> createNotes() {
        HashMap<Integer, Integer> cashNotes = new HashMap<>();
        cashNotes.put(TEN_NOTE_VALUE, DEFAULT_QUANTITY);
        cashNotes.put(TWENTY_NOTE_VALUE, DEFAULT_QUANTITY);
        return cashNotes;
    }

    public static TransactionInputDto createTransactionInputDto() {
        TransactionInputDto dto = new TransactionInputDto();
        dto.setValue(PERMITTED_WITHDRAWAL_AMOUNT);
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