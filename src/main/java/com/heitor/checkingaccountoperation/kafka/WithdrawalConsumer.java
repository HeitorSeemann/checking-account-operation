package com.heitor.checkingaccountoperation.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.heitor.checkingaccountoperation.controller.dto.TransactionInputDto;
import com.heitor.checkingaccountoperation.controller.dto.WithdrawalMessageDTO;
import com.heitor.checkingaccountoperation.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WithdrawalConsumer {

    private final TransactionService service;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "account-withdrawals", groupId = "checking-account-group")
    public void consumeWithdrawal(String messageJson) {
        try {
            log.info("Message received do Kafka: {}", messageJson);

            WithdrawalMessageDTO dto = objectMapper.readValue(messageJson, WithdrawalMessageDTO.class);

            TransactionInputDto inputDto = new TransactionInputDto();
            inputDto.setValue(dto.getAmount());

            service.withdraw(inputDto, dto.getAccountId(), dto.getUuid());

            log.info("WithDrawal processed to account: {}", dto.getAccountId());

        } catch (Exception e) {
            log.error("Error to process the message: {}", e.getMessage(), e);
        }
    }
}

