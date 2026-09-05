package com.eren.ecommerce2.a;

import com.eren.ecommerce2.a.entityRequest;
import com.eren.ecommerce2.a.entityResponse;
import com.eren.ecommerce2.a.service;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/entity")
@RequiredArgsConstructor

public class entityController {
    
    private final service service;

    @PostMapping
    public entityResponse createEntity(@RequestBody entityRequest request) {
        return service.crateEntity(request);
    }
}
