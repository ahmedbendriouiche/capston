package com.techelevator.tenmo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
@NoArgsConstructor // generates a no-args constructor
@AllArgsConstructor // generates a constructor with arguments for all fields
@Getter
@Setter
@Entity
@Table(name = "account")
public class Account {
    @NotNull(message = "account id should not be null")
    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long accountId;
    @Column(name="user_ID")
    @NotNull(message = "user ID should not be null")
    private long userId;
    @Column(name="balance")
    private BigDecimal balance;

}
