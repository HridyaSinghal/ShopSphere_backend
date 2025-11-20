package com.example.SummerProject.Repository;

import com.example.SummerProject.Entity.ShopEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopsRepository extends JpaRepository<ShopEntity,Long> {

    // No custom methods currently defined

}
