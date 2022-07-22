package com.example.onlineShopApp;

import org.springframework.data.repository.CrudRepository;

public interface OrderLinesDao extends CrudRepository<OrderLines, Integer> {
}
