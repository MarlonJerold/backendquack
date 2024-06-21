package com.quackfinances.quackfinances.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.quackfinances.quackfinances.enums.CardEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity(name = "card")
public class Card {

    public Card(CardEnum cardEnum, LocalDateTime createDate, Integer id, LocalDateTime updateData, BigDecimal value, BigDecimal valueUsed, String cardName, User user, String bankName) {
        this.cardEnum = cardEnum;
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

    @Column(name = "amount")
    private BigDecimal value;

    @CreationTimestamp
    private LocalDateTime createDate;
    @Column(name = "card_name")
    private String cardName;

    @Column(name = "amount_used")
    private BigDecimal valueUsed;

    @LastModifiedDate
    @Column(name = "update_date")
    private LocalDateTime updateData;
    private CardEnum cardEnum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    private String bankName;

    public String getBankName() {
        return bankName;
    }

    public Card setBankaName(String bankName) {
        this.bankName = bankName;
        return this;
    }


    public User getUser() {
        return user;
    }

    public Card setUser(User user) {
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

    public CardEnum getCardEnum() {
        return cardEnum;
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

    public Card setCardEnum(CardEnum cardEnum) {
        this.cardEnum = cardEnum;
        return this;
    }


}
