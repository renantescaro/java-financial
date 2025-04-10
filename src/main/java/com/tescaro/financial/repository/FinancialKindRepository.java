package com.tescaro.financial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tescaro.financial.model.FinancialKind;

public interface FinancialKindRepository  extends JpaRepository<FinancialKind, Long> {}
