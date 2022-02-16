package com.caveup.barcode.controller.biz;

import com.caveup.barcode.constants.PrintType;
import com.caveup.barcode.controller.AbstractController;
import com.caveup.barcode.entity.HtmlTable;
import com.caveup.barcode.helper.JsoupHelper;
import com.caveup.barcode.helper.PdfHelper;
import com.caveup.barcode.model.CustomerStorageEntity;
import com.caveup.barcode.model.SysMachineEntity;
import com.caveup.barcode.model.TemplateEntity;
import com.caveup.barcode.result.ApiStatusCode;
import com.caveup.barcode.result.helper.ApiResultHelper;
import com.caveup.barcode.result.model.ApiResultModel;
import com.caveup.barcode.service.CustomerStorageRepository;
import com.caveup.barcode.service.SysMachineRepository;
import com.caveup.barcode.service.TemplateRepository;
import com.caveup.barcode.vo.MetaData;
import com.caveup.barcode.vo.PrintVO;
import com.google.common.collect.Maps;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Base64Utils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * @author xw80329
 */
@Api(tags = "PrintController")
@RequestMapping("/api/v1/print/")
@Slf4j
@RestController
public class PrintController extends AbstractController {

    @Resource
    private SysMachineRepository sysMachineRepository;

    @Resource
    private TemplateRepository templateRepository;

    @Resource
    private CustomerStorageRepository customerStorageRepository;

    @GetMapping("/loadMetaData")
    public ApiResultModel<?> loadMetaData() {
        List<SysMachineEntity> machines = sysMachineRepository.list();
        List<CustomerStorageEntity> commodities = customerStorageRepository.list();
        List<TemplateEntity> templates = templateRepository.list();

        MetaData metaData = new MetaData();
        metaData.setMachines(machines);
        metaData.setCommodities(commodities);
        metaData.setTemplates(templates);
        return ApiResultHelper.success(metaData);
    }

    @PostMapping("/print")
    public ApiResultModel<?> print(@RequestBody @Validated PrintVO vo, HttpServletResponse response) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("selectedDate", vo.getSelectedDate());
        params.put("machineCode", vo.getMachineCode());
        params.put("productCode", vo.getProductCode());
        params.put("serialNumber", vo.getMachineCode());
        params.put("productName", vo.getProductName());
        params.put("sapCode", vo.getSapCode());
        params.put("capacity", Objects.toString(vo.getCapacity(), StringUtils.EMPTY));
        params.put("specification", vo.getSpecification());

        String templateContent = vo.getTemplateContent();
        try {
            Optional<HtmlTable> optional = JsoupHelper.parseTable(templateContent);
            if (optional.isPresent()) {
                int startIndex = Math.min(vo.getStartIndex(), 1);
                int endIndex = startIndex + vo.getPrintCount();
                Optional<String> outputOption = PdfHelper.generatePrintPdf(optional.get(), params, startIndex, endIndex, PrintType.P2_2);
                if (outputOption.isPresent()) {
                    log.info("output =>{}", outputOption.get());
                    File output = new File(outputOption.get());
                    byte[] content = FileUtils.readFileToByteArray(output);
                    String pdfBase64 = Base64Utils.encodeToString(content);
                    FileUtils.deleteQuietly(output);
                    return ApiResultHelper.success(pdfBase64);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ApiResultHelper.error(ApiStatusCode.PRINT_FAIL);
    }

    @PostMapping("/preview")
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

        String templateContent = vo.getTemplateContent();
        try {
            Optional<String> optional = JsoupHelper.interpolate(templateContent, params);
            if (optional.isPresent()) {
                return ApiResultHelper.success(optional.get());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ApiResultHelper.error(ApiStatusCode.TEMPLATE_PREVIEW_FAIL);
    }
}
