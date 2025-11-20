package com.example.SummerProject.Service;

import com.example.SummerProject.DTO.ShopDTO;
import com.example.SummerProject.Entity.ProductEntity;
import com.example.SummerProject.Entity.ShopEntity;
import com.example.SummerProject.Repository.ProductRepository;
import com.example.SummerProject.Repository.ShopsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopService {
    @Autowired
    private ShopsRepository shopsRepository;

    @Autowired
    private ProductRepository productRepository;
    //get all shops
        public List<ShopDTO> getAllShops() {
            List<ShopEntity> shops = shopsRepository.findAll();

            List<ShopDTO> shopDTOs = new ArrayList<>();
            for (ShopEntity shop : shops) {
                ShopDTO dto = new ShopDTO();
                dto.setShopId(shop.getShop_id());
                dto.setShopName(shop.getShopName());
                dto.setShopOwnerName(shop.getShopOwnerName());
                dto.setTotalEarning(shop.getTotalEarning());
                dto.setLink(shop.getLink());
                dto.setShopOwnerAddress(shop.getShopOwner() != null ? shop.getShopOwner().getAddress() : null);
                shopDTOs.add(dto);
            }

            return shopDTOs;
        }



}
