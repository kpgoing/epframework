package club.xubowei.epframework.bean;

import club.xubowei.epframework.util.CastUtil;

import java.util.Map;

/**
 * @author zhisheng
 * @date 2018/8/9.
 */
public class Param {

    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    public Map<String, Object> getParamMap() {
        return paramMap;
    }
}
