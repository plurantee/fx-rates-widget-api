package com.fxrates.fxrateswidgetapi.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fxrates.fxrateswidgetapi.model.CurrencyTransaction;

public interface CurrencyTransactionRepository extends JpaRepository<CurrencyTransaction, Long> {

}
