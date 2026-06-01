package com.heitor.checkingaccountoperation.controller;

import com.heitor.checkingaccountoperation.controller.dto.NoteOutputDto;
import com.heitor.checkingaccountoperation.controller.dto.TransactionOutputDTO;
import com.heitor.checkingaccountoperation.controller.dto.TransactionInputDto;
import com.heitor.checkingaccountoperation.controller.exception.WithdrawalException;
import com.heitor.checkingaccountoperation.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/checkingaccount/transactions")

public class TransactionRest {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("withdrawal/{account}")
    public ResponseEntity<List<NoteOutputDto>> withdraw(@PathVariable("account") Integer account,
                                                     @Valid @RequestBody TransactionInputDto dto) throws WithdrawalException {

        return ResponseEntity.status(HttpStatus.CREATED).body(transactionService.withdraw(dto, account));
    }

    @GetMapping("withdrawal/{account}")
    public ResponseEntity<List<TransactionOutputDTO>> search(@PathVariable("account") Integer account) throws WithdrawalException {

        List<TransactionOutputDTO> list =  transactionService.search(account);
        return (list.isEmpty()) ? ResponseEntity.notFound().build() : ResponseEntity.ok(list);
    }

}