package com.enigma.warungmakanbahari.service.impl;

import com.enigma.warungmakanbahari.entity.Menu;
import com.enigma.warungmakanbahari.model.request.MenuRequest;
import com.enigma.warungmakanbahari.model.response.MenuResponse;
import com.enigma.warungmakanbahari.repository.MenuRepository;
import com.enigma.warungmakanbahari.service.MenuService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MenuResponse create(Menu menu) {
        Menu newMenu = menuRepository.save(menu);
        return getMenuResponse(newMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<MenuResponse> getAll(MenuRequest request) {
        Specification<Menu> specification = getMenuSpecification(request);
        List<Menu> menus = menuRepository.findAll(specification);
        List<MenuResponse> menuResponses = new ArrayList<>();
        for (Menu menu : menus) {
            menuResponses.add(getMenuResponse(menu));
        }
        return menuResponses;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MenuResponse getById(String id) {
        Optional<Menu> optionalMenu = menuRepository.findById(id);

        if (optionalMenu.isPresent()) {
            return getMenuResponse(optionalMenu.get());
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "menu not found");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Menu get(String id) {
        Optional<Menu> optionalMenu = menuRepository.findById(id);

        if (optionalMenu.isPresent()) optionalMenu.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "menu not found");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MenuResponse update(Menu menu) {
        Optional<Menu> optionalMenu = menuRepository.findById(menu.getId());
        if (optionalMenu.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "menu not found");
        Menu updatedMenu = menuRepository.save(menu);
        return getMenuResponse(updatedMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String id) {
        Optional<Menu> optionalMenu = menuRepository.findById(id);
        if (optionalMenu.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "menu not found");
        menuRepository.delete(optionalMenu.get());
    }

    private Specification<Menu> getMenuSpecification(MenuRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getName() != null) {
                Predicate namePredicate = criteriaBuilder.like(
                        root.get("name"),
                        "%" + request.getName() + "%"
                );
                predicates.add(namePredicate);
            }
            if (request.getMinPrice() != null) {
                Predicate minPricePredicate = criteriaBuilder.greaterThanOrEqualTo(
                        root.get("price"),
                        request.getMinPrice()
                );
                predicates.add(minPricePredicate);
            }
            if (request.getMaxPrice() != null) {
                Predicate minPricePredicate = criteriaBuilder.lessThanOrEqualTo(
                        root.get("price"),
                        request.getMaxPrice()
                );
                predicates.add(minPricePredicate);
            }
            return query.where(predicates.toArray(new Predicate[] {})).getRestriction();
        };
    }

    private MenuResponse getMenuResponse(Menu newMenu) {
        MenuResponse menuResponse = MenuResponse.builder()
                .name(newMenu.getName())
                .price(newMenu.getPrice())
                .build();
        return menuResponse;
    }
}
