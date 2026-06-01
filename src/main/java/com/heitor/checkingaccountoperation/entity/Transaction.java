package com.heitor.checkingaccountoperation.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account")
    @NotNull
    private Integer account;

    @Column(name = "value")
    @NotNull
    private Integer value;

    @Column(name = "type")
    @NotNull
    private String type;

    @Column(name = "date")
    private LocalDateTime date;

}