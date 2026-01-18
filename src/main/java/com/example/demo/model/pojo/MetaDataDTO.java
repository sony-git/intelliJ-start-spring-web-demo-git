package com.example.demo.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

@Data
@AllArgsConstructor
public class MetaDataDTO {
    private String path;
    private String method;
    private int status;
    @org.springframework.beans.factory.annotation.Value("${app.version}")
    private String appVersion;
    private Map<String, String[]> requestParams;
}