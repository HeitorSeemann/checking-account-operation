package com.heitor.checkingaccountoperation.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.heitor.checkingaccountoperation.controller.dto.TransactionInputDTO;
import com.heitor.checkingaccountoperation.controller.dto.WithdrawalMessageDTO;
import com.heitor.checkingaccountoperation.service.TransactionService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WithdrawalConsumer {

    private final TransactionService service;

    private static final ObjectMapper OBJECT_MAPPER = JsonMapper.builder()
            .addModule(new JavaTimeModule())
            .build();

    @PostConstruct
    public void init() {
        log.info("[KAFKA-INIT] Classe WithdrawalConsumer loaded!");
    }

    @KafkaListener(topics = "account-withdrawals")
    public void consumeWithdrawal(String messageJson) {
        try {
            log.info("[KAFKA-LOG] Msg received topic: {}", messageJson);

            WithdrawalMessageDTO dto = OBJECT_MAPPER.readValue(messageJson, WithdrawalMessageDTO.class);

            TransactionInputDTO inputDto = new TransactionInputDTO();
            inputDto.setValue(dto.getAmount());

            service.withdraw(inputDto, dto.getAccountId(), dto.getUuid());

            log.info("[KAFKA-SUCCESS] account: {}", dto.getAccountId());

        } catch (Exception e) {
            log.error("[KAFKA-ERROR] Error: {}", e.getMessage(), e);
        }
    }
}
