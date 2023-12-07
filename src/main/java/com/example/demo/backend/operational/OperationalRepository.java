package com.example.demo.backend.operational;

import com.example.demo.backend.vehicle.VehicleTier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OperationalRepository extends JpaRepository<OperationalEntity, VehicleTier>
{}
