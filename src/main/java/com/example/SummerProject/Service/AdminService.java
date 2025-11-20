package com.example.SummerProject.Service;

import com.example.SummerProject.DTO.AdminCustomerResponse;
import com.example.SummerProject.DTO.AdminShopResponse;
import com.example.SummerProject.Entity.AdminEntity;
import com.example.SummerProject.Entity.ShopEntity;
import com.example.SummerProject.Entity.ShopOwnerEntity;
import com.example.SummerProject.Entity.UserEntity;
import com.example.SummerProject.Repository.AdminRespository;
import com.example.SummerProject.Repository.ShopOwnerRepository;
import com.example.SummerProject.Repository.ShopsRepository;
import com.example.SummerProject.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

@Service
public class AdminService {

@Autowired
private AdminRespository adminRespository;

@Autowired
private UserRepository userRepository;

@Autowired
private ShopOwnerRepository shopOwnerRepository;

    public void saveDetail(AdminEntity adminEntity) {
        adminRespository.save(adminEntity);
    }


    public List<AdminCustomerResponse> getDetail() {
        List<UserEntity> detail = userRepository.findAll();

        return convertToAdminCustomerResponse(detail);

    }


    public List<AdminShopResponse> getShopDetail() {
        List<ShopOwnerEntity> detail = shopOwnerRepository.findAll();

        return convertToAdminShopResponse(detail);


    }

    private List<AdminShopResponse> convertToAdminShopResponse(List<ShopOwnerEntity> detail) {

        List<AdminShopResponse> dtoList = detail.stream().map(user ->
                new AdminShopResponse(
                        user.getId(),
                        user.getName(),
                        user.getShopName(),
                        user.getAddress()
                )
        ).collect(Collectors.toList());

        return dtoList;

    }


    private List<AdminCustomerResponse> convertToAdminCustomerResponse(List<UserEntity> detail) {

        List<AdminCustomerResponse> dtoList = detail.stream().map(user ->
                new AdminCustomerResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmailId(),
                        user.getAddress()
                )
        ).collect(Collectors.toList());

        return dtoList;

    }

}
