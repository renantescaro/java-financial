package com.tescaro.financial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tescaro.financial.model.Bills;

public interface BillsRepository  extends JpaRepository<Bills, Long> {}
