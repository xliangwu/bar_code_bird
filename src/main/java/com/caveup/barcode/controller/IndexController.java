package com.caveup.barcode.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xw80329
 */
@Controller
public class IndexController {

    private static final String DEFAULT_INDEX_PAGE = "index";

    @RequestMapping(value = {"/", "/index"})
    public String greeting() {
        return DEFAULT_INDEX_PAGE;
    }
}
