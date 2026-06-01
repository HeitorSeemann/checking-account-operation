package com.heitor.checkingaccountoperation.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(nullable = false)
    private Integer account;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private Integer value;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(nullable = false, unique = true, length = 36)
    private String suid;
}
