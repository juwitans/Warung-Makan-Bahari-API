package com.enigma.warungmakanbahari.service.impl;

import com.enigma.warungmakanbahari.entity.*;
import com.enigma.warungmakanbahari.model.request.OrderDetailRequest;
import com.enigma.warungmakanbahari.model.request.OrderRequest;
import com.enigma.warungmakanbahari.model.request.SearchOrderRequest;
import com.enigma.warungmakanbahari.repository.OrderRepository;
import com.enigma.warungmakanbahari.service.*;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final MenuService menuService;
    private final OrderDetailService orderDetailService;
    private final TransactionTypeService transactionTypeService;
    private final TableService tableService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order create(OrderRequest request) {
        Customer customer = customerService.get(request.getCustomerId());
        Order order = Order.builder()
                .customer(customer)
                .transDate(new Date())
                .table(tableService.getById(request.getTableId()))
                .build();
        orderRepository.saveAndFlush(order);

        TransactionType transactionType = TransactionType.builder()
                .type(request.getTransType())
                .build();
        transactionTypeService.createOrUpdate(transactionType);

        List<OrderDetail> orderDetails = new ArrayList<>();
        for (OrderDetailRequest orderDetailRequest : request.getOrderDetails()) {
            Menu menu = menuService.get(orderDetailRequest.getMenuId());

            OrderDetail orderDetail = OrderDetail.builder()
                    .order(order)
                    .menu(menu)
                    .price(menu.getPrice())
                    .build();

            orderDetails.add(orderDetailService.createOrUpdate(orderDetail));
        }

        order.setOrderDetails(orderDetails);
        order.setTransactionType(transactionType);
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order getById(String id) {
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) return optionalOrder.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "order not found");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Order> getAll(SearchOrderRequest request) {
        Specification<Order> specification = getOrderSpecification(request);
        List<Order> orders = orderRepository.findAll(specification);
        return orders;
    }

    private Specification<Order> getOrderSpecification(SearchOrderRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getTransType() != null) {
                Predicate namePredicate = criteriaBuilder.equal(
                        root.join("m_transaction_type").get("type"),
                        request.getTransType()
                );
                predicates.add(namePredicate);
            }
            if (request.getProductName() != null) {
                Subquery<Long> subquery = query.subquery(Long.class);
                Root<OrderDetail> orderDetailRoot = subquery.from(OrderDetail.class);

                subquery.select(criteriaBuilder.sum(orderDetailRoot.get("price")));
                subquery.where(criteriaBuilder.equal(orderDetailRoot.get("order"), root));

                Predicate minPredicate = criteriaBuilder.greaterThanOrEqualTo(subquery, request.getProductName());
                predicates.add(minPredicate);
            }
            if (request.getDay() != null) {
                Predicate dayPredicate = criteriaBuilder.equal(
                        criteriaBuilder.function("date", Date.class, root.get("transDate")),
                        request.getDay()
                );
                predicates.add(dayPredicate);
            }
            if (request.getMonth() > 0 && request.getMonth() <= 12) {
                Predicate monthPredicate = criteriaBuilder.equal(
                        criteriaBuilder.function("month", Integer.class, root.get("transDate")),
                        request.getMonth()
                );
                predicates.add(monthPredicate);
            }
            if (request.getYear() > 0) {
                Predicate yearPredicate = criteriaBuilder.equal(
                        criteriaBuilder.function("year", Integer.class, root.get("transDate")),
                        request.getYear()
                );
                predicates.add(yearPredicate);
            }
            if (request.getStartDate() != null) {
                Predicate startDatePredicate = criteriaBuilder.greaterThanOrEqualTo(
                        root.get("transDate"),
                        request.getStartDate()
                );
                predicates.add(startDatePredicate);
            }
            if (request.getEndDate() != null) {
                Predicate endDatePredicate = criteriaBuilder.lessThanOrEqualTo(
                        root.get("transDate"),
                        request.getEndDate()
                );
                predicates.add(endDatePredicate);
            }
            return query.where(predicates.toArray(new Predicate[] {})).getRestriction();
        };
    }
}


