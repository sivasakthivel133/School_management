package com.school.management.service;

import com.school.management.dto.AuthRequestSignup;
import com.school.management.dto.Product;
import com.school.management.dto.ResponseDTO;
import com.school.management.entity.UserInfo;
import com.school.management.exception.BadRequestServiceAlertException;
import com.school.management.repository.UserInfoRepository;
import com.school.management.util.Constants;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ProductService {

    private final UserInfoRepository userInfoRepository;
    private final PasswordEncoder passwordEncoder;
    private List<Product> productList;

    private ProductService(UserInfoRepository userInfoRepository, PasswordEncoder passwordEncoder) {
        this.userInfoRepository = userInfoRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @PostConstruct
    public void loadProductsFromDB() {
        productList = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> Product.builder()
                        .productId(i)
                        .name("product " + i)
                        .qty(new Random().nextInt(10))
                        .price(new Random().nextInt(5000)).build()
                ).collect(Collectors.toList());
    }

    public ResponseDTO getAllProducts() {
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.userInfoRepository.findAll()).statusValue(HttpStatus.OK.getReasonPhrase()).build();
    }

    public ResponseDTO getProductById(final int id) {
        Product product = productList.stream()
                .filter(p -> p.getProductId() == id)
                .findAny()
                .orElseThrow(() -> new RuntimeException("Product " + id + Constants.NOT_FOUND));
        return ResponseDTO.builder().message(Constants.SUCCESS).data(this.userInfoRepository.findById(id)
                .orElseThrow(() -> new BadRequestServiceAlertException(Constants.NOT_FOUND))).build();
    }

    public ResponseDTO addUser(final AuthRequestSignup authRequestSignup) {
        authRequestSignup.setPassword(passwordEncoder.encode(authRequestSignup.getPassword()));
        final UserInfo user = new UserInfo();
        user.setName(authRequestSignup.getName());
        user.setPassword(authRequestSignup.getPassword());
        user.setEmail(authRequestSignup.getEmail());
        user.setRoles(authRequestSignup.getRoles());
        return ResponseDTO.builder().message(Constants.CREATED).data(this.userInfoRepository.save(user)).statusValue(HttpStatus.CREATED.getReasonPhrase()).build();
    }
}
