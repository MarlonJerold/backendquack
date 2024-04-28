package com.quackfinances.quackfinances.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.userdetails.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity(name = "card")
public class Card {

    public Card(CardType cardType, LocalDateTime createDate, Integer id, LocalDateTime updateData, BigDecimal value, BigDecimal valueUsed, String cardName, UserModel user, String bankName) {
        this.cardType = cardType;
        this.createDate = createDate;
        this.id = id;
        this.updateData = updateData;
        this.value = value;
        this.valueUsed = valueUsed;
        this.cardName = cardName;
        this.user = user;
        this.bankName = bankName;
    }

    public Card(){}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private BigDecimal value;

    @CreationTimestamp
    private LocalDateTime createDate;

    private String cardName;
    private BigDecimal valueUsed;

    @LastModifiedDate
    private LocalDateTime updateData;
    private CardType cardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserModel user;
    private String bankName;

    public String getBankName() {
        return bankName;
    }

    public Card setBankaName(String bankName) {
        this.bankName = bankName;
        return this;
    }


    public UserModel getUser() {
        return user;
    }

    public Card setUser(UserModel user) {
        this.user = user;
        return this;
    }

    public BigDecimal getValue() {
        return value;
    }

    public Card setValue(BigDecimal value) {
        this.value = value;
        return this;
    }

    public LocalDateTime getUpdateData() {
        return updateData;
    }

    public Card setUpdateData(LocalDateTime updateData) {
        this.updateData = updateData;
        return this;
    }

    public Integer getId() {
        return id;
    }

    public Card setId(Integer id) {
        this.id = id;
        return this;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public Card setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
        return this;
    }

    public CardType getCardType() {
        return cardType;
    }

    public String getCardName() {
        return cardName;
    }

    public Card setCardName(String cardName) {
        this.cardName = cardName;
        return this;
    }

    public BigDecimal getValueUsed() {
        return valueUsed;
    }

    public Card setValueUsed(BigDecimal valueUsed) {
        this.valueUsed = valueUsed;
        return this;
    }

    public Card setCardType(CardType cardType) {
        this.cardType = cardType;
        return this;
    }


}
