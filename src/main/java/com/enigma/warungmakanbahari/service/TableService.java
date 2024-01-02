package com.enigma.warungmakanbahari.service;

import com.enigma.warungmakanbahari.entity.TableInfo;

import java.util.List;

public interface TableService {
    TableInfo create(TableInfo table);
    List<TableInfo> getAll();
    TableInfo getById(String id);
    TableInfo update(TableInfo table);
    void delete(String id);
}
