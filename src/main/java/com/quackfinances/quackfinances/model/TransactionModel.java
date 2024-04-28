package com.quackfinances.quackfinances.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.LastModifiedDate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@Data
@Entity(name = "transaction")
public class TransactionModel implements Serializable {

    public TransactionModel(Integer id, String description, BigDecimal value, LocalDateTime createDate, LocalDateTime updateData, Account account, TransactionType transactionType, String identifier,Integer sourceAccountId, Integer destinationAccountId, String category ) {
        this.id = id;
        this.description = description;
        this.value = value;
        this.createDate = createDate;
        this.updateData = updateData;
        this.account = account;
        this.transactionType = transactionType;
        this.identifier = identifier;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.category = category;
    }

    public TransactionModel() {

    }

    public TransactionModel(String category, BigDecimal value) {
        this.category = category;
        this.value = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String description;
    private BigDecimal value;

    @CreationTimestamp
    private LocalDateTime createDate;

    @LastModifiedDate
    private LocalDateTime updateData;

    @JdbcTypeCode(SqlTypes.JSON)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_by")
    private Account account;

    @JdbcTypeCode(SqlTypes.JSON)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_by")
    private UserModel user;
    private TransactionType transactionType;
    private String identifier;
    private Integer accountId;

    Integer sourceAccountId;
    Integer destinationAccountId;

    private String category;

    public String getCategory() {
        return category;
    }

    public TransactionModel setCategory(String category) {
        this.category = category;
        return this;
    }

    public Integer getDestinationAccountId() {
        return destinationAccountId;
    }

    public TransactionModel setDestinationAccountId(Integer destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
        return this;
    }

    public Integer getSourceAccountId() {
        return sourceAccountId;
    }

    public TransactionModel setSourceAccountId(Integer sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
        return this;
    }

    public Integer getDestinationAccount() {
        return destinationAccountId;
    }

    public TransactionModel setDestinationAccount(Integer destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
        return this;
    }

    public Integer getSourceAccount() {
        return sourceAccountId;
    }

    public TransactionModel setSourceAccount(Integer sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
        return this;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    @PrePersist
    public void generateIdentifier() {
        this.identifier = UUID.randomUUID().toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getUpdateData() {
        return updateData;
    }

    public void setUpdateData(LocalDateTime updateData) {
        this.updateData = updateData;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }


    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
