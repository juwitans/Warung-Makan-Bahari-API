package com.enigma.warungmakanbahari.service.impl;

import com.enigma.warungmakanbahari.entity.TransactionType;
import com.enigma.warungmakanbahari.repository.TransactionTypeRepository;
import com.enigma.warungmakanbahari.service.TransactionTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionTypeServiceImpl implements TransactionTypeService {
    private final TransactionTypeRepository transactionTypeRepository;

    @Override
    public TransactionType createOrUpdate(TransactionType transactionType) {
        return transactionTypeRepository.save(transactionType);
    }
}
