package com.enigma.warungmakanbahari.service;

import com.enigma.warungmakanbahari.entity.Menu;
import com.enigma.warungmakanbahari.model.request.MenuRequest;
import com.enigma.warungmakanbahari.model.response.MenuResponse;

import java.util.List;

public interface MenuService {
    MenuResponse create(Menu menu);
    List<MenuResponse> getAll(MenuRequest request);
    MenuResponse getById(String id);
    Menu get(String id);
    MenuResponse update(Menu menu);
    void delete(String id);
}
