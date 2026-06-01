package com.heitor.checkingaccountoperation.service;

import com.heitor.checkingaccountoperation.controller.dto.NoteOutputDto;
import com.heitor.checkingaccountoperation.controller.dto.TransactionOutputDTO;
import com.heitor.checkingaccountoperation.controller.dto.TransactionInputDto;
import com.heitor.checkingaccountoperation.controller.exception.WithdrawalException;
import com.heitor.checkingaccountoperation.converter.TransactionConverter;
import com.heitor.checkingaccountoperation.entity.Transaction;
import com.heitor.checkingaccountoperation.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository repository;

    @Autowired()
    private TransactionConverter converter;

    private static final int[] NOTES = {100, 50, 20, 10};
    private static final String MSG_VALUE_NOT_ALLOWED = "Valor não permite saque.";
    private static final String ERROR_MSG_TRANSACTION_ERROR = "Erro ao efetivar lançamento.";
    private static final String ERROR_MSG_SEARCH_ERROR = "Erro ao buscar lançamentos.";

    public List<NoteOutputDto> withdraw(TransactionInputDto dto, Integer account ) throws WithdrawalException {
        try {
            HashMap<Integer, Integer> quantityNotes = searchNoteValues(dto.getValue());
            List<NoteOutputDto> notesToWithdraw = converter.toListNoteOutputDTO(quantityNotes);
            save(converter.toEntity(dto, account));
            return notesToWithdraw;
        } catch (Exception e) {
            throw new WithdrawalException(e.getMessage());
        }
    }

    public HashMap<Integer, Integer> searchNoteValues(Integer value) throws WithdrawalException {
        HashMap<Integer, Integer> quantityNotes = new HashMap<>();
        Integer notesToWithdraw;
        Integer valueToWithdraw = value;

        while (valueToWithdraw != 0) {
            for (int i = 0; i < NOTES.length; i++){
                if (valueToWithdraw >= NOTES[i]) {
                    notesToWithdraw = valueToWithdraw / NOTES[i];
                    valueToWithdraw = valueToWithdraw % NOTES[i];
                    quantityNotes.put(NOTES[i], notesToWithdraw);
                }
            }
            if (valueToWithdraw > 0 && valueToWithdraw < searchSmallestNote()){
                throw new WithdrawalException(MSG_VALUE_NOT_ALLOWED);
            }
        }
        return quantityNotes;
    }

    public static int searchSmallestNote(){
        int temp;
        for (int i = 0; i < NOTES.length; i++) {
            for (int j = i + 1; j < NOTES.length; j++) {
                if (NOTES[i] > NOTES[j]) {
                    temp = NOTES[i];
                    NOTES[i] = NOTES[j];
                    NOTES[j] = temp;
                }
            }
        }
        return NOTES[0];
    }

    public void save(Transaction entity) throws WithdrawalException {
        try {
            repository.save(entity);
        } catch (Exception e) {
            throw new WithdrawalException(ERROR_MSG_TRANSACTION_ERROR);
        }
    }

    public List<TransactionOutputDTO> search(Integer account) throws WithdrawalException {
        List<TransactionOutputDTO> transactions;
        try {
            transactions =  converter.toListTransactionOutputDTO(repository.findByAccount(account));
        } catch (Exception e) {
            throw new WithdrawalException(ERROR_MSG_SEARCH_ERROR);
        }
        return transactions;
    }

}