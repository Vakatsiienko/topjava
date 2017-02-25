package ru.javawebinar.topjava.web;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

/**
 * Created by Iaroslav on 2/25/2017.
 */
public class ServletExtractorUtil {

    public static <T> T extractOrDefault(HttpServletRequest req, String paramName, T defaultValue, Function<String, T> parser) {
        String strValue = req.getParameter(paramName);
        return strValue == null || strValue.isEmpty() ? defaultValue : parser.apply(strValue);
    }

    public static String extractOrDefault(HttpServletRequest req, String paramName, String defaultValue) {
        String strValue = req.getParameter(paramName);
        return strValue == null || strValue.isEmpty() ? defaultValue : strValue;
    }
}
