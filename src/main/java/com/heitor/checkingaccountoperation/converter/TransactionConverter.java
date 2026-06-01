package com.heitor.checkingaccountoperation.converter;

import com.heitor.checkingaccountoperation.controller.dto.NoteOutputDto;
import com.heitor.checkingaccountoperation.controller.dto.TransactionOutputDTO;
import com.heitor.checkingaccountoperation.controller.dto.TransactionInputDto;
import com.heitor.checkingaccountoperation.entity.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;

@Component
public class TransactionConverter {

    public Transaction toEntity (TransactionInputDto dto, Integer conta) {
        Transaction entity = new Transaction();
        entity.setAccount(conta);
        entity.setType("Withdrawal");
        entity.setValue(dto.getValue());
        entity.setDate(LocalDateTime.now());
        return entity;
    }

    public List<NoteOutputDto> toListNoteOutputDTO(HashMap<Integer, Integer> notes) {
        List<NoteOutputDto> noteList = new ArrayList<>();

        for(Map.Entry<Integer, Integer> note : notes.entrySet()) {
            NoteOutputDto dto = new NoteOutputDto();
            dto.setNote(note.getKey());
            dto.setQuantity(note.getValue());
            noteList.add(dto);
        }
        noteList.sort(Comparator.comparing(NoteOutputDto:: getNote));
        return noteList;
    }

    public TransactionOutputDTO toTransactionOutputDTO(Transaction entity) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, TransactionOutputDTO.class);
    }

    public List<TransactionOutputDTO> toListTransactionOutputDTO(List<Transaction> entities) {
        List<TransactionOutputDTO> listDTO = new ArrayList<>();
        entities.stream().forEach(ent -> {
            listDTO.add(toTransactionOutputDTO(ent));
        });
        return listDTO;
    }

}