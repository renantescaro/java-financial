package com.tescaro.financial.model;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.tescaro.financial.enums.KindEnum;

import jakarta.persistence.*;

@Entity
public class Bills {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private KindEnum kind;

    @ManyToOne
	@JoinColumn(name = "financial_kind_id", nullable = false)
	private FinancialKind financialKind;

    @Column(nullable = false)
    private Long value;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime time;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public KindEnum getKind() {
        return this.kind;
    }

    public void setKind(KindEnum kind) {
        this.kind = kind;
    }

    public FinancialKind getFinancialKind() {
        return this.financialKind;
    }

    public void setFinancialKind(FinancialKind financialKind) {
        this.financialKind = financialKind;
    }

    public Long getValue() {
        return this.value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public LocalDateTime getTime() {
        return this.time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
