package com.heitor.checkingaccountoperation.converter;

import com.heitor.checkingaccountoperation.entity.Transaction;
import com.heitor.checkingaccountoperation.controller.dto.TransactionInputDTO;
import com.heitor.checkingaccountoperation.controller.dto.TransactionOutputDTO;
import com.heitor.checkingaccountoperation.controller.dto.NoteOutputDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TransactionConverter {

    TransactionConverter INSTANCE = Mappers.getMapper(TransactionConverter.class);

    Transaction dtoToEntity(TransactionInputDTO dto);

    @Mapping(source = "accountNumber", target = "account")
    Transaction toEntity(TransactionInputDTO inputDto, Integer accountNumber);

    TransactionOutputDTO toTransactionOutputDTO(Transaction entity);

    List<TransactionOutputDTO> toListTransactionOutputDTO(List<Transaction> list);

    default List<NoteOutputDto> toListNoteOutputDTO(HashMap<Integer, Integer> quantityNotes) {
        if (quantityNotes == null) {
            return List.of();
        }

        return quantityNotes.entrySet().stream()
                .map(entry -> {
                    NoteOutputDto dto = new NoteOutputDto();
                    dto.setNote(entry.getKey());
                    dto.setQuantity(entry.getValue());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}