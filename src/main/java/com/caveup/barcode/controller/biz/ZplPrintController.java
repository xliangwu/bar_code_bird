package com.caveup.barcode.controller.biz;

import com.caveup.barcode.controller.AbstractController;
import com.caveup.barcode.helper.ZplHelper;
import com.caveup.barcode.result.ApiStatusCode;
import com.caveup.barcode.result.helper.ApiResultHelper;
import com.caveup.barcode.result.model.ApiResultModel;
import com.caveup.barcode.vo.PrintVO;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author xw80329
 */
@Api(tags = "PrintController")
@RequestMapping("/api/v1/zplPrint/")
@Slf4j
@RestController
public class ZplPrintController extends AbstractController {


    @PostMapping("/print")
    public ApiResultModel<?> print(@RequestBody @Validated PrintVO vo) {
        PrintService zebraPrinter = null;
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        if (services != null && services.length > 0) {
            for (PrintService service : services) {
                if (service.getName().equals(vo.getPrinter())) {
                    zebraPrinter = service;
                    break;
                }
            }
        }

        if (null == zebraPrinter) {
            return ApiResultHelper.error(ApiStatusCode.PRINT_COMMAND_FAIL);
        }

        Map<String, Object> params = Maps.newHashMap();
        params.put("selectedDate", vo.getSelectedDate());
        params.put("machineCode", vo.getMachineCode());
        params.put("productCode", vo.getProductCode());
        params.put("serialNumber", vo.getMachineCode());
        params.put("productName", vo.getProductName());
        params.put("companyName", "上海福助工业有限公司");
        params.put("sapCode", vo.getSapCode());
        params.put("capacity", Objects.toString(vo.getCapacity(), StringUtils.EMPTY));
        params.put("specification", vo.getSpecification());

        String templateZpl = vo.getTemplateContent();
        try {
            DocPrintJob job = zebraPrinter.createPrintJob();
            DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
            int startIndex = Math.min(vo.getStartIndex(), 1);
            int endIndex = startIndex + vo.getPrintCount();
            Optional<List<String>> zipCommandList = ZplHelper.generateZplCommand(templateZpl, params, startIndex, endIndex);
            if (zipCommandList.isPresent()) {
                int index = 0;
                for (String zplCommand : zipCommandList.get()) {
                    Doc doc = new SimpleDoc(zplCommand.getBytes(), flavor, null);
                    job.print(doc, null);
                    log.info("send command to printer,index:{}", index++);
                }
                return ApiResultHelper.success();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ApiResultHelper.error(ApiStatusCode.PRINT_COMMAND_FAIL);
    }

    @PostMapping("/preparePrint")
    public ApiResultModel<?> preview(@RequestBody @Validated PrintVO vo) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("selectedDate", vo.getSelectedDate());
        params.put("machineCode", vo.getMachineCode());
        params.put("productCode", vo.getProductCode());
        params.put("serialNumber", vo.getMachineCode());
        params.put("productName", vo.getProductName());
        params.put("sapCode", vo.getSapCode());
        params.put("capacity", Objects.toString(vo.getCapacity(), StringUtils.EMPTY));
        params.put("index", 1);
        params.put("specification", vo.getSpecification());
        params.put("companyName", "上海福助工业有限公司");

        String templateContent = vo.getTemplateContent();
        try {
            Optional<List<String>> zipCommandList = ZplHelper.generateZplCommand(templateContent, params, 1, 2);
            Map<String, Object> result = Maps.newHashMap();
            if (zipCommandList.isPresent()) {
                String zplCommand = zipCommandList.get().get(0);
                log.info("zpl command:\n{}", zplCommand);
                String imgBase64 = ZplHelper.downloadLabelary(zplCommand);
                result.put("previewImg", imgBase64);
            }

            result.put("printers", listPrinters());
            return ApiResultHelper.success(result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ApiResultHelper.error(ApiStatusCode.TEMPLATE_PREVIEW_FAIL);
    }


}
