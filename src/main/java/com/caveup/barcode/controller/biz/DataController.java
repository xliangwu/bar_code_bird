package com.caveup.barcode.controller.biz;

import com.caveup.barcode.controller.AbstractController;
import com.caveup.barcode.entity.DataSourceType;
import com.caveup.barcode.excel.export.ExcelExportTool;
import com.caveup.barcode.excel.loader.impl.CustomerStorageExcelLoader;
import com.caveup.barcode.excel.validator.ExcelValidator;
import com.caveup.barcode.excel.validator.impl.CustomerStorageDataValidator;
import com.caveup.barcode.helper.ContentTypeUtil;
import com.caveup.barcode.model.CustomerStorageEntity;
import com.caveup.barcode.model.LogEntity;
import com.caveup.barcode.result.ApiStatusCode;
import com.caveup.barcode.result.helper.ApiResultHelper;
import com.caveup.barcode.result.model.ApiResultModel;
import com.caveup.barcode.service.CustomerStorageRepository;
import com.caveup.barcode.service.LogRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author xw80329
 * @Date 2020/12/2
 */
@Api(tags = "DataController", description = "??????-???????????????")
@RequestMapping("/api/v1/data/")
@Slf4j
@RestController
@Validated
public class DataController extends AbstractController {

    @Resource
    private CustomerStorageExcelLoader customerStorageExcelLoader;

    @Resource
    private CustomerStorageRepository customerStorageRepository;

    @Resource
    private LogRepository logRepository;

    @Value("${app.dataFolder}")
    private String dataFolder;

    @GetMapping(value = "/downloadTemplate")
    @ApiOperation(value = "????????????")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", paramType = "query", value = "????????????"),
    })
    public void downloadTemplate(HttpServletResponse response,
                                 @RequestParam(name = "sourceType") @NotBlank String sourceType) {
        /**
         * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????jar???????????????????????????????????????jar?????????
         * ??????????????????????????????????????????????????????????????????????????????????????????jar??????????????????????????????
         * ????????????????????????????????????????????????????????????????????????jar?????????class????????????????????????jar???????????????
         */

        try (InputStream in = getClass().getClassLoader().getResourceAsStream(sourceType + "_template.xlsx")) {
            String fileName = sourceType + ".xlsx";
            response.addHeader("Content-Disposition", ContentTypeUtil.getContentDisposition(fileName));
            response.addHeader("Content-Type", ContentTypeUtil.getContentTypeBySuffix(fileName, true));
            assert in != null;
            IOUtils.copy(in, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            try {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            } catch (IOException ioException) {
                log.error(ioException.getMessage());
            }
        }
    }

    @GetMapping(value = "/exportData")
    @ApiOperation(value = "??????????????????")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", paramType = "query", value = "????????????"),
    })
    public void exportData(HttpServletRequest request,
                           HttpServletResponse response,
                           @RequestParam(name = "sourceType") @NotBlank String sourceType) throws Exception {

        DataSourceType dataSourceType = DataSourceType.valueOfCode(sourceType);
        if (null == dataSourceType) {
            throw new IllegalArgumentException(ApiStatusCode.TEMPLATE_EXPORT_FAIL.getComments());
        }

        /**
         * ?????????????????????????????????????????????????????????????????????????????????????????????????????????????????????jar???????????????????????????????????????jar?????????
         * ??????????????????????????????????????????????????????????????????????????????????????????jar??????????????????????????????
         * ????????????????????????????????????????????????????????????????????????jar?????????class????????????????????????jar???????????????
         */

        List<CustomerStorageEntity> allData = customerStorageRepository.list();
        List<List<Object>> exportData = Lists.newArrayList();
        for (CustomerStorageEntity entity : allData) {
            List<Object> row = Lists.newArrayList();
            row.add(entity.getCol1());
            row.add(entity.getCol2());
            row.add(entity.getCol3());
            row.add(entity.getCol4());
            row.add(entity.getCol5());
            row.add(entity.getCol6());
            row.add(entity.getCol7());
            exportData.add(row);
        }

        File output = File.createTempFile("export_", ".xlsx");
        List<String> headers = Stream.of("????????????", "SAP??????", "?????????", "?????????", "??????", "??????", "????????????").collect(Collectors.toList());
        List<Integer> widths = headers.stream().map(e -> 25).collect(Collectors.toList());
        log.info("Data size:{}", exportData.size());
        ExcelExportTool.exportObjects(exportData, headers, widths, output.getAbsolutePath());

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String fileName = "???????????????_" + dateFormat.format(new Date()) + ".xlsx";
        try (InputStream in = new FileInputStream(output)) {

            response.addHeader("Content-Disposition", ContentTypeUtil.getContentDisposition(fileName));
            response.addHeader("Content-Type", ContentTypeUtil.getContentTypeBySuffix(fileName, true));
            IOUtils.copy(in, response.getOutputStream());
            response.flushBuffer();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            String remoteIp = getRemoteHost(request);
            File backup = new File(dataFolder + File.separator + fileName);
            FileUtils.moveFile(output, backup);
            LogEntity logEntity = new LogEntity();
            logEntity.setLoggedEvent(remoteIp + "@??????");
            logEntity.setLoggedTime(new Date());
            logEntity.setLoggedEntity(backup.getAbsolutePath());
            logRepository.insert(logEntity);
        }
    }

    @PostMapping(value = "/uploadTemplate")
    public ApiResultModel uploadTemplate(@RequestParam MultipartFile file,
                                         @RequestParam(name = "sourceType") @NotBlank String sourceType,
                                         @RequestParam(name = "customerId") @NotNull Integer customerId,
                                         HttpServletRequest request) throws Exception {
        DataSourceType dataSourceType = DataSourceType.valueOfCode(sourceType);
        if (null == dataSourceType) {
            throw new IllegalArgumentException(ApiStatusCode.TEMPLATE_UPLOAD_FAIL.getComments());
        }

        StringBuilder result = new StringBuilder();
        //??????????????????
        if (!isEndSuffix(file.getOriginalFilename(), "xlsx") && !isEndSuffix(file.getOriginalFilename(), "xls")) {
            result.append("???????????????????????????.xlsx ???.xls ??????");
        } else {
            File tempFile = File.createTempFile("data_", "." + FilenameUtils.getExtension(file.getOriginalFilename()));
            try {
                FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);
                ExcelValidator<Class<?>> validator = getExcelValidator(dataSourceType);
                if (validator != null) {
                    Pair<Boolean, String> validatorRes = validator.validate(tempFile);
                    if (!validatorRes.getLeft()) {
                        return ApiResultHelper.error(ApiStatusCode.TEMPLATE_UPLOAD_FAIL.getCode(), validatorRes.getRight());
                    }

                    String remoteIp = getRemoteHost(request);
                    loader(dataSourceType, tempFile, customerId, remoteIp);
                    return ApiResultHelper.success();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                result.append(e.getMessage());
            } finally {
                String remoteIp = getRemoteHost(request);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
                File backup = new File(dataFolder + File.separator + dateFormat.format(new Date()) + "_" + file.getOriginalFilename());
                FileUtils.moveFile(tempFile, backup);
                LogEntity logEntity = new LogEntity();
                logEntity.setLoggedEvent(remoteIp + "@??????");
                logEntity.setLoggedTime(new Date());
                logEntity.setLoggedEntity(backup.getAbsolutePath());
                logRepository.insert(logEntity);
            }
        }
        return ApiResultHelper.error(ApiStatusCode.TEMPLATE_UPLOAD_FAIL.getCode(), result.toString());
    }

    private boolean isEndSuffix(String name, String suffix) {
        return StringUtils.endsWith(StringUtils.upperCase(name), StringUtils.upperCase(suffix));
    }

    private static <T> ExcelValidator<T> getExcelValidator(DataSourceType sourceType) {
        if (sourceType == DataSourceType.CUSTOMER_PRODUCT) {
            return (ExcelValidator<T>) new CustomerStorageDataValidator();
        }
        return null;
    }

    private String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    private void loader(DataSourceType sourceType, File excelFile, Integer customerId, String remoteIp) {
        if (sourceType == DataSourceType.CUSTOMER_PRODUCT) {
            customerStorageExcelLoader.load(excelFile, (t) -> {
                t.setCustomerId(customerId);
                t.setCreatedTime(new Date());
                t.setCol8(remoteIp);
                return t;
            });
        } else {
            log.error("SKIP excel data load, file:{},customer id:{}", excelFile.getAbsolutePath(), customerId);
        }
    }
}
