package com.song.demo.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @ClassName readAliParamUtil
 * @Description //TODO(解析支付宝异步通知的参数)
 * @Author 宋正健
 * @Date 2019/6/15 14:14
 * @Version 1.0
 **/
@Slf4j
public class readAliParamUtil {
    public static Map<String, String> read(HttpServletRequest request) {
        Map<String, String> maps = new HashMap<String, String>();
        Map<String, String[]> result = request.getParameterMap();
        for (Iterator<String> iter = result.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) result.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            maps.put(name, valueStr);
        }
        log.info(JSON.toJSONString(maps));
        return maps;
    }
}
