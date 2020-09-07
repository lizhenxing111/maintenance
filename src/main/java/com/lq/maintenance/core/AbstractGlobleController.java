package com.lq.maintenance.core;

import java.util.HashMap;

public abstract class AbstractGlobleController {
    public AbstractGlobleController() {
    }

    public HashMap<String, Object> success(HashMap<String, Object> resultMap, String msg) {
        resultMap.put("result", "yes");
        msg = msg != null && !"".equals(msg) ? msg : "请求成功";
        resultMap.put("msg", msg);
        return resultMap;
    }

    public HashMap<String, Object> error(HashMap<String, Object> resultMap, String msg) {
        resultMap.put("result", "no");
        msg = msg != null && !"".equals(msg) ? msg : "请求失败";
        resultMap.put("msg", msg);
        return resultMap;
    }

    public HashMap<String, Object> success(HashMap<String, Object> resultMap) {
        resultMap.put("result", "yes");
        if (resultMap.get("msg") == null) {
            resultMap.put("msg", "请求成功");
        }

        return resultMap;
    }

    public HashMap<String, Object> error(HashMap<String, Object> resultMap) {
        resultMap.put("result", "no");
        resultMap.put("msg", "请求失败");
        return resultMap;
    }
}