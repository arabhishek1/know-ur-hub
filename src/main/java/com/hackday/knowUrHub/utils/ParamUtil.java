package com.hackday.knowUrHub.utils;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by abhishek.ar on 6/5/15.
 */
public class ParamUtil {

    private static final Logger logger = LoggerFactory.getLogger(ParamUtil.class);

    public static String refineParam(String param) {
        if (param == null)
            return "";

        String refined = null;

        try {
            refined = URLDecoder.decode(param, "UTF-8");
            refined = refined.replaceAll("\\s", "").replaceAll(",+", ",");
            refined = refined.replaceAll("^,|,$", "");
        } catch (UnsupportedEncodingException e) {
            logger.error("Unable to decode params " + e.getMessage(), e);
        }

        return refined;
    }

    public static <T> List<T> convertStringToList(String param, Class<T> type) {
        return convertStringToList(param, ',', type);
    }

    public static <T> List<T> convertStringToList(String param, char delimiter, Class<T> type) {
        String[] values = StringUtils.split(param, delimiter);
        if (values == null) {
            return new ArrayList<T>();
        }
        return convertList(Arrays.asList(values), type);
    }

    public static <T> List<T> convertList(List<String> params, Class<T> type) {
        List<T> list = new ArrayList<T>();
        for (String param : params) {
            list.add((T)ConvertUtils.convert(param, type));
        }
        return list;
    }

    public static String get(Map params, String key, String defaultValue) {
        Object value = params.get(key);
        if (null == value) {
            return defaultValue;
        }
        return (String) value;
    }

}

