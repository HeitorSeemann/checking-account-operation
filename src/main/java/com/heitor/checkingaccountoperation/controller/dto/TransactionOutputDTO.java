package com.heitor.checkingaccountoperation.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TransactionOutputDTO {

    private Long id;

    private Integer account;

    private Integer value;

    private String type;

    private LocalDateTime date;

}