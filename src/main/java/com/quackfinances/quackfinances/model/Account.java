package com.quackfinances.quackfinances.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.quackfinances.quackfinances.enums.AccountType;
import com.quackfinances.quackfinances.dto.AccountUserLoginDTO;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

@Data
@Entity(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Column(name = "amount")
    private BigDecimal value;
    @Column(name = "create_date")
    private Timestamp createDate;
    @Column(name = "update_date")
    private Date updateData;
    private AccountType type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UserModel user;

    public Account(Integer id, String name, BigDecimal value, Timestamp createDate, Date updateDate, AccountType type) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.createDate = createDate;
        this.updateData = updateDate;
        this.type = type;
    }

    public Account() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateData() {
        return updateData;
    }

    public void setUpdateData(Date updateData) {
        this.updateData = updateData;
    }

    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    @PrePersist
    protected void onCreate() {
        createDate = new Timestamp(System.currentTimeMillis());
    }
    public AccountUserLoginDTO toDTO() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = formatter.format((TemporalAccessor) this.createDate);

        return new AccountUserLoginDTO(this.id, this.name, this.value, formattedDate.toString(), this.type);
    }
}

