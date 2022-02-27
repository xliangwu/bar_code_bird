package com.caveup.barcode.helper;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author xw80329
 */
@Slf4j
public class ZplHelper {

    private static final Map<String, String> COL_NAMES = new HashMap<>();

    static {
        COL_NAMES.put("_NAME_", "供应商");
        COL_NAMES.put("_PRODUCTNAME_", "品 名");
        COL_NAMES.put("_SELECTEDDATE_", "生产日期");
        COL_NAMES.put("_CAPACITY_", "数 量");
        COL_NAMES.put("_SPECIFICATION_", "规 格");
        COL_NAMES.put("_SERIALNUMBER_", "批 号");
        COL_NAMES.put("_SAPCODE_", "SAP代码");
    }

    public static Optional<List<String>> generateZplCommand(String zipCommandTemplate,
                                                            Map<String, Object> params, int startIndex, int endIndex) {
        try {
            List<String> actualZplCommands = Lists.newArrayList();
            String[] commands = zipCommandTemplate.split("\n");
            List<String> zplCommands = Arrays.asList(commands);
            for (int i = startIndex; i < endIndex; i++) {
                actualZplCommands.add(zplCommandInterpolate(zplCommands, params, i));
            }
            return Optional.of(actualZplCommands);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
    }

    public static String zplCommandInterpolate(List<String> zplCommands, Map<String, Object> params, int index) {
        StringBuilder zplCommand = new StringBuilder();
        SimpleDateFormat df = new SimpleDateFormat(("yyyy年MM月dd日"));
        SimpleDateFormat df2 = new SimpleDateFormat(("yyyyMMdd"));
        for (String command : zplCommands) {
            command = command.replace("^CI27", "^CI28");
            String actualCommand = command;
            for (Map.Entry<String, String> item : COL_NAMES.entrySet()) {
                if (command.contains(item.getKey())) {
                    actualCommand = command.replace(item.getKey(), item.getValue());
                }
            }

            for (Map.Entry<String, Object> item : params.entrySet()) {
                if (command.contains(item.getKey()) && "selectedDate".equals(item.getKey())) {
                    actualCommand = command.replace(item.getKey(), df.format(item.getValue()));
                } else if (command.contains(item.getKey()) && "serialNumber".equals(item.getKey())) {
                    String val = String.format("%s%03d", params.get("machineCode"), index);
                    actualCommand = command.replace(item.getKey(), val);
                } else if (command.contains("QR_CODE")) {
                    Date selectedDate = (Date) params.get("selectedDate");
                    String val = String.format("%s,%s", df2.format(selectedDate), params.get("machineCode"));
                    actualCommand = command.replace("QR_CODE", val);
                } else if (command.contains(item.getKey())) {
                    actualCommand = command.replace(item.getKey(), String.valueOf(item.getValue()));
                }
            }
            zplCommand.append(actualCommand).append("\r\n");
        }
        return zplCommand.toString();
    }

    public static String downloadLabelary(String zplCommand) throws IOException {
        String url = "http://api.labelary.com/v1/printers/8dpmm/labels/4x3/0/";
        HttpResponse response = HttpRequest.post(url).timeout(5000).header("Accept", "image/png").body(zplCommand).execute();
        if (response.getStatus() == HttpStatus.HTTP_OK) {
            byte[] content = response.bodyBytes();
            return Base64Utils.encodeToString(content);
        }
        return null;
    }

}
