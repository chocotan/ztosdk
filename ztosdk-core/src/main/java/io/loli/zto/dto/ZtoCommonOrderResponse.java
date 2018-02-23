package io.loli.zto.dto;

import lombok.Data;

@Data
public class ZtoCommonOrderResponse {
    private String result;
    private String statusCode;
    private String status;
    private String message;
}
