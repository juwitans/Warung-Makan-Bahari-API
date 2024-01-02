package com.enigma.warungmakanbahari.repository;

import com.enigma.warungmakanbahari.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
}
