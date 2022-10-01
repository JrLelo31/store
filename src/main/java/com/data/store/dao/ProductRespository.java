package com.data.store.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.data.store.entities.Product;

public interface ProductRespository extends JpaRepository<Product, Long> {

}
