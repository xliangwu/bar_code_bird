package com.caveup.barcode.functions;

import fr.w3blog.zpl.constant.ZebraFont;
import fr.w3blog.zpl.model.ZebraLabel;
import fr.w3blog.zpl.model.element.ZebraBarCode39;
import fr.w3blog.zpl.model.element.ZebraText;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;

@Slf4j
public class ZebraLabelTest {

    @Test
    public void zplTest() {
        ZebraLabel zebraLabel = new ZebraLabel(912, 912);
        zebraLabel.setDefaultZebraFont(ZebraFont.ZEBRA_ZERO);

        zebraLabel.addElement(new ZebraText(10, 84, "Product:", 14));
        zebraLabel.addElement(new ZebraText(395, 85, "Camera", 14));
        zebraLabel.addElement(new ZebraText(10, 161, "CA201212AA", 14));
        zebraLabel.addElement(new ZebraBarCode39(10, 297, "CA201212AA", 118, 2, 2));
        zebraLabel.addElement(new ZebraText(10, 365, "Qté:", 11));
        zebraLabel.addElement(new ZebraText(180, 365, "3", 11));
        zebraLabel.addElement(new ZebraText(317, 365, "QA", 11));
        zebraLabel.addElement(new ZebraText(10, 520, "Ref log:", 11));
        zebraLabel.addElement(new ZebraText(180, 520, "哈哈", 11));
        zebraLabel.addElement(new ZebraText(10, 596, "Ref client:", 11));
        String zipCommand = zebraLabel.getZplCode();
        Assert.assertNotNull(zipCommand);
        log.info(zipCommand);
    }

    @Test
    public void printLookupTest() {
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        if (services != null && services.length > 0) {
            for (PrintService service : services) {
                System.out.print(service);
            }
        }
    }
}
