package com.enigma.warungmakanbahari.repository;

import com.enigma.warungmakanbahari.entity.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypeRepository extends JpaRepository<TransactionType, String> {
}
