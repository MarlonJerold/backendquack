package com.quackfinances.quackfinances.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.quackfinances.quackfinances.enums.CardEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity(name = "card")
public class Card {

    public Card(CardEnum cardEnum, LocalDateTime createDate, Integer id, LocalDateTime updateData, BigDecimal currentValue, BigDecimal valueUsed, String cardName, User user, String bankName) {
        this.cardEnum = cardEnum;
        this.createDate = createDate;
        this.id = id;
        this.updateData = updateData;
        this.currentValue = currentValue;
        this.valueUsed = valueUsed;
        this.cardName = cardName;
        this.user = user;
        this.bankName = bankName;
    }

    public Card() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "amount")
    private BigDecimal currentValue;

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

    private LocalDate invoiceDate;

    public Card(Builder builder) {
    }

    public static class Builder {
        private Integer id;
        private BigDecimal currentValue;
        private LocalDateTime createDate;
        private String cardName;
        private BigDecimal valueUsed;
        private LocalDateTime updateData;
        private CardEnum cardEnum;
        private User user;
        private String bankName;
        private LocalDate invoiceDate;

        public Builder() {
        }

        public Builder withId(Integer id) {
            this.id = id;
            return this;
        }

        public Builder withCurrentValue(BigDecimal currentValue) {
            this.currentValue = currentValue;
            return this;
        }

        public Builder withCreateDate(LocalDateTime createDate) {
            this.createDate = createDate;
            return this;
        }

        public Builder withCardName(String cardName) {
            this.cardName = cardName;
            return this;
        }

        public Builder withValueUsed(BigDecimal valueUsed) {
            this.valueUsed = valueUsed;
            return this;
        }

        public Builder withUpdateData(LocalDateTime updateData) {
            this.updateData = updateData;
            return this;
        }

        public Builder withCardEnum(CardEnum cardEnum) {
            this.cardEnum = cardEnum;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public Builder withBankName(String bankName) {
            this.bankName = bankName;
            return this;
        }

        public Builder withInvoiceDate(LocalDate invoiceDate) {
            this.invoiceDate = invoiceDate;
            return this;
        }

        public Card build() {
            return new Card(this);
        }
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

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

    public BigDecimal getCurrentValue() {
        return currentValue;
    }

    public Card setCurrentValue(BigDecimal currentValue) {
        this.currentValue = currentValue;
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
