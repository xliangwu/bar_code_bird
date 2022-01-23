package com.caveup.barcode.controller.biz;

import com.caveup.barcode.controller.AbstractController;
import com.caveup.barcode.entity.DataSourceType;
import com.caveup.barcode.excel.loader.impl.CustomerStorageExcelLoader;
import com.caveup.barcode.excel.validator.ExcelValidator;
import com.caveup.barcode.excel.validator.impl.CustomerStorageDataValidator;
import com.caveup.barcode.helper.ContentTypeUtil;
import com.caveup.barcode.result.ApiStatusCode;
import com.caveup.barcode.result.helper.ApiResultHelper;
import com.caveup.barcode.result.model.ApiResultModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * @author xw80329
 * @Date 2020/12/2
 */
@Api(tags = "DataController", description = "标签-第三方数据")
@RequestMapping("/api/v1/data/")
@Slf4j
@RestController
@Validated
public class DataController extends AbstractController {

    @Resource
    private CustomerStorageExcelLoader customerStorageExcelLoader;

    @GetMapping(value = "/downloadTemplate")
    @ApiOperation(value = "下载模板")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sourceType", paramType = "query", value = "数据类型"),
    })
    public void downloadTemplate(HttpServletResponse response,
                                 @RequestParam(name = "sourceType") @NotBlank String sourceType) {
        /**
         * 开发过程中，必不可少的需要读取文件，对于打包方式的不同，还会存在一些坑，比如以jar包方式部署时，文件都存在于jar包中，
         * 某些读取方式在开发工程中都可行，但是打包后，由于文件被保存在jar中，会导致读取失败。
         * 这时就需要通过类加载器读取文件，类加载器可以读取jar包中的class类当然也可以读取jar包中的文件
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

    @PostMapping(value = "/uploadTemplate")
    public ApiResultModel uploadTemplate(@RequestParam MultipartFile file,
                                         @RequestParam(name = "sourceType") @NotBlank String sourceType,
                                         @RequestParam(name = "customerId") @NotNull Integer customerId) {
        DataSourceType dataSourceType = DataSourceType.valueOfCode(sourceType);
        if (null == dataSourceType) {
            throw new IllegalArgumentException(ApiStatusCode.TEMPLATE_UPLOAD_FAIL.getComments());
        }

        StringBuilder result = new StringBuilder();
        //验证文件格式
        if (!isEndSuffix(file.getOriginalFilename(), "xlsx") && !isEndSuffix(file.getOriginalFilename(), "xls")) {
            result.append("当前数据上传只允许.xlsx 或.xls 格式");
        } else {
            try {
                File tempFile = File.createTempFile("data_", "." + FilenameUtils.getExtension(file.getOriginalFilename()));
                FileUtils.copyInputStreamToFile(file.getInputStream(), tempFile);

                ExcelValidator<Class<?>> validator = getExcelValidator(dataSourceType);
                if (validator != null) {
                    Pair<Boolean, String> validatorRes = validator.validate(tempFile);
                    if (!validatorRes.getLeft()) {
                        return ApiResultHelper.error(ApiStatusCode.TEMPLATE_UPLOAD_FAIL.getCode(), validatorRes.getRight());
                    }

                    loader(dataSourceType, tempFile, customerId);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                result.append(e.getMessage());
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

    private void loader(DataSourceType sourceType, File excelFile, Integer customerId) {
        if (sourceType == DataSourceType.CUSTOMER_PRODUCT) {
            customerStorageExcelLoader.load(excelFile, (t) -> {
                t.setCustomerId(customerId);
                t.setCreatedTime(new Date());
                return t;
            });
        } else {
            log.error("SKIP excel data load, file:{},customer id:{}", excelFile.getAbsolutePath(), customerId);
        }
    }
}
