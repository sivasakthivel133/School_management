package com.school.management.dto;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;
@Data
public class DynamicKeyValueDTO {
    private Map<String, Object> data;

    public DynamicKeyValueDTO() {
        this.data = new HashMap<>();
    }

    public void put(String key, Object value) {
        data.put(key, value);
    }

    public Object get(String key) {
        return data.get(key);
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "DynamicKeyValueDTO{" +
                "data=" + data +
                '}';
    }
}
