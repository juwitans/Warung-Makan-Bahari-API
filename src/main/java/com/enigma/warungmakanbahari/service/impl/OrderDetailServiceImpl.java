package com.enigma.warungmakanbahari.service.impl;

import com.enigma.warungmakanbahari.entity.OrderDetail;
import com.enigma.warungmakanbahari.repository.OrderDetailRepository;
import com.enigma.warungmakanbahari.service.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderDetail createOrUpdate(OrderDetail orderDetail) {
        return orderDetailRepository.save(orderDetail);
    }
}
