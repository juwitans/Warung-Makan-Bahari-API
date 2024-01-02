package com.enigma.warungmakanbahari.controller;

import com.enigma.warungmakanbahari.entity.TableInfo;
import com.enigma.warungmakanbahari.model.response.WebResponse;
import com.enigma.warungmakanbahari.service.TableService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/tables")
@RequiredArgsConstructor
public class TableController {
    private final TableService tableService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PostMapping
    public ResponseEntity<?> createNewTable(@RequestBody TableInfo table) {
        TableInfo newTable = tableService.create(table);
        WebResponse<TableInfo> response = WebResponse.<TableInfo>builder()
                .message("successfully create new table")
                .status(HttpStatus.CREATED.getReasonPhrase())
                .data(newTable)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllTables() {
        List<TableInfo> tables = tableService.getAll();
        WebResponse<List<TableInfo>> response = WebResponse.<List<TableInfo>>builder()
                .message("successfully get all tables")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(tables)
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping(path = "/:{id}")
    public ResponseEntity<?> getTableById(@PathVariable String id) {
        TableInfo table = tableService.getById(id);
        WebResponse<TableInfo> response = WebResponse.<TableInfo>builder()
                .message("successfully get table by id")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(table)
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateTable(@RequestBody TableInfo table) {
        TableInfo updatedTable = tableService.update(table);
        WebResponse<TableInfo> response = WebResponse.<TableInfo>builder()
                .message("successfully update table")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(updatedTable)
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @DeleteMapping(path = "/:{id}")
    public ResponseEntity<?> deleteTable(@PathVariable String id) {
        tableService.delete(id);
        WebResponse<String> response = WebResponse.<String>builder()
                .message("successfully delete table")
                .status(HttpStatus.OK.getReasonPhrase())
                .data("table deleted")
                .build();

        return ResponseEntity.ok(response);
    }
}
