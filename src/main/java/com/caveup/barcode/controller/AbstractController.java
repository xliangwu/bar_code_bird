package com.caveup.barcode.controller;

import org.apache.commons.compress.utils.Lists;
import org.springframework.web.bind.annotation.RestController;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import java.util.List;

/**
 * @author xw80329
 * @Date 2021/3/7
 */
@RestController
public abstract class AbstractController {

    protected List<String> listPrinters() {
        List<String> printers = Lists.newArrayList();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        if (services != null && services.length > 0) {
            for (PrintService service : services) {
                printers.add(service.getName());
            }
        }
        return printers;
    }
}
