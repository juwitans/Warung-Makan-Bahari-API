package com.enigma.warungmakanbahari.service.impl;

import com.enigma.warungmakanbahari.entity.TableInfo;
import com.enigma.warungmakanbahari.repository.TableInfoRepository;
import com.enigma.warungmakanbahari.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TableServiceImpl implements TableService {
    private final TableInfoRepository tableInfoRepository;

    @Override
    public TableInfo create(TableInfo table) {
        return tableInfoRepository.save(table);
    }

    @Override
    public List<TableInfo> getAll() {
        return tableInfoRepository.findAll();
    }

    @Override
    public TableInfo getById(String id) {
        Optional<TableInfo> optional = tableInfoRepository.findById(id);
        if (optional.isPresent()) return optional.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "table not found");
    }

    @Override
    public TableInfo update(TableInfo table) {
        Optional<TableInfo> optional = tableInfoRepository.findById(table.getId());
        if (optional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "table not found");
        return tableInfoRepository.save(table);
    }

    @Override
    public void delete(String id) {
        Optional<TableInfo> optional = tableInfoRepository.findById(id);
        if (optional.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "table not found");
        tableInfoRepository.delete(optional.get());
    }
}
