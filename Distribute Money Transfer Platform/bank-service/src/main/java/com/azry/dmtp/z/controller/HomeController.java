package com.azry.dmtp.z.controller;

import com.azry.dmtp.z.annotation.LogControllerUsage;
import com.azry.dmtp.z.model.BankServiceConstants;
import com.azry.dmtp.z.model.api.HomeResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "home", description = "The home API")
@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @LogControllerUsage
    @GetMapping
    public ResponseEntity<HomeResponse> getGreeting() {
        HomeResponse response = new HomeResponse(BankServiceConstants.GREETING);
        return ResponseEntity.ok().body(response);
    }
}

