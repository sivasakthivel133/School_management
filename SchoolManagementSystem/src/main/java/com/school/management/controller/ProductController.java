package com.school.management.controller;

import com.school.management.dto.AuthRequestLogin;
import com.school.management.dto.AuthRequestSignup;
import com.school.management.dto.ResponseDTO;
import com.school.management.service.JwtService;
import com.school.management.service.ProductService;
import com.school.management.util.Constants;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class ProductController {

    private final ProductService productService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    public ProductController(ProductService productService, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.productService = productService;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/welcome")
    public ResponseDTO welcome() {
        return new ResponseDTO("Success", "Welcome, this endpoint is not secure", "200 OK");
    }

    @PostMapping("/signup")
    public ResponseDTO addNewUser(@RequestBody final AuthRequestSignup authRequestSignup) {
        return this.productService.addUser(authRequestSignup);
    }

    @GetMapping("/all")
    public ResponseDTO getAllTheProducts() {
        return this.productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ResponseDTO getProductById(@PathVariable final int id) {
        return this.productService.getProductById(id);
    }

    @PostMapping("/login")
    public ResponseDTO login(@RequestBody final AuthRequestLogin authRequestLogin) {
        System.out.println("its working also");

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestLogin.getUsername(), authRequestLogin.getPassword()));
        System.out.println("its working also");
        if (authentication.isAuthenticated()) {
            String token = this.jwtService.generateToken(authRequestLogin.getUsername());
            return new ResponseDTO(Constants.VALID_TOKEN, token, "200 OK");
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }


}
