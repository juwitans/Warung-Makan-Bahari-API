package com.enigma.warungmakanbahari.controller;

import com.enigma.warungmakanbahari.entity.Menu;
import com.enigma.warungmakanbahari.model.request.MenuRequest;
import com.enigma.warungmakanbahari.model.response.MenuResponse;
import com.enigma.warungmakanbahari.model.response.WebResponse;
import com.enigma.warungmakanbahari.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PostMapping
    public ResponseEntity<?> createNewMenu(@RequestBody Menu menu) {
        MenuResponse menuResponse = menuService.create(menu);
        WebResponse<MenuResponse> response = WebResponse.<MenuResponse>builder()
                .message("successfully create new menu")
                .status(HttpStatus.CREATED.getReasonPhrase())
                .data(menuResponse)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<?> getAllMenus(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) Long minPrice,
                                         @RequestParam(required = false) Long maxPrice) {
        MenuRequest menuRequest = MenuRequest.builder()
                .name(name)
                .minPrice(minPrice)
                .maxPrice(maxPrice)
                .build();

        List<MenuResponse> menus = menuService.getAll(menuRequest);
        WebResponse<List<MenuResponse>> response = WebResponse.<List<MenuResponse>>builder()
                .message("successfully get all menus")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(menus)
                .build();
        
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getMenuById(@PathVariable String id) {
        MenuResponse menu = menuService.getById(id);
        WebResponse<MenuResponse> response = WebResponse.<MenuResponse>builder()
                .message("successfully get menu by id")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(menu)
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateMenu(@RequestBody Menu menu) {
        MenuResponse updatedMenu = menuService.update(menu);
        WebResponse<MenuResponse> response = WebResponse.<MenuResponse>builder()
                .message("successfully update menu")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(updatedMenu)
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable String id) {
        menuService.delete(id);
        WebResponse<String> response = WebResponse.<String>builder()
                .message("successfully delete menu")
                .status(HttpStatus.OK.getReasonPhrase())
                .data("menu deleted")
                .build();

        return ResponseEntity.ok(response);
    }
}
