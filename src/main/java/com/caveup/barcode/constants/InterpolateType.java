package com.caveup.barcode.constants;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author xw80329
 */
@Slf4j
public enum InterpolateType {

    /**
     * 不需要进行插值计算
     */
    TEXT_CODE("TEXT"),

    /**
     *
     */
    QR_CODE("QR_CODE") {
        @Override
        public String interpolate(String originText, String format, List<String> keys, Map<String, Object> params) {
            try {
                if (StringUtils.isBlank(format)) {
                    String key = keys.get(0);
                    return String.valueOf(params.get(key));
                } else {
                    Object[] objs = new Object[keys.size()];
                    for (int i = 0; i < keys.size(); i++) {
                        objs[i] = params.getOrDefault(keys.get(i), StringUtils.EMPTY);
                    }
                    return String.format(format, objs);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return originText;
            }
        }
    },

    /**
     * 参数解析
     */
    PARAM_CODE("PARAM") {
        @Override
        public String interpolate(String originText, String format, List<String> keys, Map<String, Object> params) {
            try {
                if (StringUtils.isBlank(format)) {
                    String key = keys.get(0);
                    return String.valueOf(params.get(key));
                } else {
                    Object[] objs = new Object[keys.size()];
                    for (int i = 0; i < keys.size(); i++) {
                        objs[i] = params.getOrDefault(keys.get(i), StringUtils.EMPTY);
                    }
                    return String.format(format, objs);
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return originText;
            }
        }
    },

    /**
     * date
     */
    DATE_CODE("DATE") {
        @Override
        public String interpolate(String originText, String format, List<String> keys, Map<String, Object> params) {
            if (StringUtils.isBlank(format) || CollectionUtils.isEmpty(keys)) {
                return originText;
            }

            try {
                String key = keys.get(0);
                Date currentDate = (Date) params.get(key);
                SimpleDateFormat fmt = new SimpleDateFormat(format);
                return fmt.format(currentDate);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return originText;
            }
        }
    };

    @Getter
    private String field;

    InterpolateType(String field) {
        this.field = field;
    }

    public String interpolate(String originText, String format, List<String> keys, Map<String, Object> params) {
        return originText;
    }

    public static InterpolateType valueOfType(String text) {
        if (StringUtils.isBlank(text)) {
            return TEXT_CODE;
        }

        for (InterpolateType type : InterpolateType.values()) {
            if (type.getField().equals(text.toUpperCase())) {
                return type;
            }
        }

        return TEXT_CODE;
    }
}
