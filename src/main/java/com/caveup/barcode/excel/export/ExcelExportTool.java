package com.caveup.barcode.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.StringUtils;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.caveup.barcode.excel.convert.TimestampStringConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 导出数据导Excel
 *
 * @author xw80329
 * @Date 2019/11/30
 */
@Slf4j
public class ExcelExportTool {

    /**
     * 导出数据导Excel中
     *
     * @param data
     * @param headers
     * @param outputFile
     * @throws Exception
     */
    public static void exportObjects(List<List<Object>> data, List<String> headers, String outputFile) throws Exception {
        if (StringUtils.isEmpty(outputFile)) {
            throw new Exception("output file should non-empty");
        }

        if (CollectionUtils.isEmpty(data) || CollectionUtils.isEmpty(headers)) {
            throw new Exception("data or headers should non-empty");
        }

        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        List<List<String>> col = new ArrayList<>();
        headers.stream().forEach(e -> col.add(Arrays.asList(e)));
        EasyExcel.write(outputFile).registerConverter(new TimestampStringConverter()).head(col).sheet("数据").doWrite(data);
    }

    /**
     * 导出数据导Excel中
     *
     * @param data
     * @param headers
     * @param outputFile
     * @throws Exception
     */
    public static void exportObjects(List<List<Object>> data, List<String> headers, List<Integer> columnWidths, String outputFile) throws Exception {
        if (StringUtils.isEmpty(outputFile)) {
            throw new Exception("output file should non-empty");
        }

        if (CollectionUtils.isEmpty(headers)) {
            throw new Exception("data or headers should non-empty");
        }

        // 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
        List<List<String>> col = new ArrayList<>();
        headers.stream().forEach(e -> col.add(Arrays.asList(e)));
        ExcelWriterSheetBuilder sheetBuilder = EasyExcel.write(outputFile)
                .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                .registerConverter(new TimestampStringConverter()).head(col).sheet("数据");
        Map<Integer, Integer> columnWidthMap = new HashMap<>();
        for (int i = 0; i < columnWidths.size(); i++) {
            columnWidthMap.put(i, columnWidths.get(i) * 256);
        }
        sheetBuilder.build();
        sheetBuilder.doWrite(data);
    }
}
