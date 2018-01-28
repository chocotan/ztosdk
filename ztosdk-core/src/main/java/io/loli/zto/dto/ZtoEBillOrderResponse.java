package io.loli.zto.dto;

import lombok.Data;

@Data
public class ZtoEBillOrderResponse {
    private Boolean result;
    private String message;
    private ZtoEBillOrderResponseData data;

    @Data
    public static class ZtoEBillOrderResponseData {
        private String billCode;
        private String message;
        private String orderId;
        private String siteCode;
        private String siteName;
        private Boolean update;
    }
}
