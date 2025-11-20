package com.example.SummerProject.Repository;

import com.example.SummerProject.Entity.ShopOwnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShopOwnerRepository extends JpaRepository<ShopOwnerEntity,Long> {
    Optional<ShopOwnerEntity> findByEmailId(String emailId);



}
