package com.heitor.checkingaccountoperation.controller;

import com.heitor.checkingaccountoperation.controller.dto.NoteOutputDto;
import com.heitor.checkingaccountoperation.controller.dto.TransactionInputDto;
import com.heitor.checkingaccountoperation.controller.dto.TransactionOutputDTO;
import com.heitor.checkingaccountoperation.controller.exception.WithdrawalException;
import com.heitor.checkingaccountoperation.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts/withdrawals")
public class TransactionRest {

    private final TransactionService service;

    @Autowired
    public TransactionRest(TransactionService service) {
        this.service = service;
    }

    @PostMapping("/{accountNumber}")
    public ResponseEntity<List<NoteOutputDto>> executeWithdrawal(
            @PathVariable Integer accountNumber,
            @RequestBody TransactionInputDto inputDto,
            @RequestHeader("Idempotency-Key") String suid) throws WithdrawalException {

        List<NoteOutputDto> list = service.withdraw(inputDto, accountNumber, suid);

        return new ResponseEntity<>(list, HttpStatus.CREATED);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<List<TransactionOutputDTO>> getTransactionsByAccount(
            @PathVariable Integer accountNumber) throws WithdrawalException {

        List<TransactionOutputDTO> transactions = service.search(accountNumber);

        if (transactions.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }
}
