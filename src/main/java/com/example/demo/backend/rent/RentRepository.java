package com.example.demo.backend.rent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentRepository  extends JpaRepository<RentEntity, Integer>
{}