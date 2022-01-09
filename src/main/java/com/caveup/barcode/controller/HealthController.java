package com.caveup.barcode.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xw80329
 */
@RestController
public class HealthController extends AbstractController {

    @GetMapping("/health")
    public String health() {
        return "ok";
    }
}
