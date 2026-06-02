package com.heitor.checkingaccountoperation.controller.dto;

import lombok.Data;

@Data
public class WithdrawalMessageDTO {
    private Integer accountId;
    private Integer amount;
    private String uuid;
}

