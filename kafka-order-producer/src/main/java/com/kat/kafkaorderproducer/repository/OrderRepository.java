package com.kat.kafkaorderproducer.repository;

import com.kat.kafkaorderproducer.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<Order, Long> {
}
