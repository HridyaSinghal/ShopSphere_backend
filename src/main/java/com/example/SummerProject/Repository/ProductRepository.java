package com.example.SummerProject.Repository;

import com.example.SummerProject.Entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity,Long> {

    List<ProductEntity> findByShopIdAndProductNameContainingIgnoreCase(Long shopId, String productName);

    // Get products by shop ID and category
    List<ProductEntity> findByShopIdAndCategory(Long shopId, String category);

    //get all product of specific shop
    @Query("SELECT p FROM ProductEntity p WHERE p.shop.shop_id = :shopId")
    List<ProductEntity> findByShopId(@Param("shopId") Long shopId);

    //delete product from specific shop
    @Modifying
    @Transactional
    @Query("DELETE FROM ProductEntity p WHERE p.pro_id = :proId AND p.shop.shop_id = :shopId")
    void deleteByShopIdAndProductId(@Param("shopId") Long shopId, @Param("proId") Long proId);



}
