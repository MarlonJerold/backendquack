package com.quackfinances.quackfinances.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.LastModifiedDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Entity(name = "category")
public class CategoryModel {

    public CategoryModel(String categoriaName, LocalDateTime createDate, Integer id, LocalDateTime updateData, TransactionType transactionType, UserModel user) {
        this.categoriaName = categoriaName;
        this.createDate = createDate;
        this.id = id;
        this.updateData = updateData;
        this.transactionType = transactionType;
        this.user = user;
    }

    public CategoryModel() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    String categoriaName;
    @CreationTimestamp
    private LocalDateTime createDate;
    @LastModifiedDate
    private LocalDateTime updateData;
    private TransactionType transactionType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserModel user;

    public TransactionType transactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
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

    public UserModel getUser() {
        return user;
    }

    public CategoryModel setUser(UserModel user) {
        this.user = user;
        return this;
    }
}
