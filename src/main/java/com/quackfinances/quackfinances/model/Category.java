package com.quackfinances.quackfinances.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.quackfinances.quackfinances.enums.TransactionEnum;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;

@Data
@Entity(name = "category")
public class Category {

    public Category(String categoriaName, LocalDateTime createDate, Integer id, LocalDateTime updateData, TransactionEnum transactionEnum, User user) {
        this.categoriaName = categoriaName;
        this.createDate = createDate;
        this.id = id;
        this.updateData = updateData;
        this.transactionEnum = transactionEnum;
        this.user = user;
    }

    public Category() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    String categoriaName;
    @CreationTimestamp
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime updateData;
    private TransactionEnum transactionEnum;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private User user;

    public TransactionEnum transactionType() {
        return transactionEnum;
    }

    public void setTransactionEnum(TransactionEnum transactionEnum) {
        this.transactionEnum = transactionEnum;
    }

    public Integer id() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String categoriaName() {
        return categoriaName;
    }

    public void setCategoriaName(String categoriaName) {
        this.categoriaName = categoriaName;
    }

    public LocalDateTime createDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime updateData() {
        return updateData;
    }

    public void setUpdateData(LocalDateTime updateData) {
        this.updateData = updateData;
    }

    public User getUser() {
        return user;
    }

    public Category setUser(User user) {
        this.user = user;
        return this;
    }
}
