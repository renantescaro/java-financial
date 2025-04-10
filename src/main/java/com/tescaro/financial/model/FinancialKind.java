package com.tescaro.financial.model;

import jakarta.persistence.*;

@Entity
public class FinancialKind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean periodical;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getPeriodical() {
        return this.periodical;
    }

    public void setPeriodical(Boolean periodical) {
        this.periodical = periodical;
    }
}
