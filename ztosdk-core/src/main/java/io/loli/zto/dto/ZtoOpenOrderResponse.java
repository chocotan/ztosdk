package io.loli.zto.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ZtoOpenOrderResponse {

    private ZtoOpenOrderResponseResult result;
    private String statusCode;
    private Boolean status;
    private String message;

    @Data
    @ToString
    public static class ZtoOpenOrderResponseResult {
        private String orderCode;
    }
}
