package io.loli.zto.dto;

import lombok.Data;

@Data
public class ZtoBagAddrMarkResponse {
    private Boolean status;
    private String message;
    private String statusCode;
    private String result;

    @Data
    public static class ZtoAddrMark{
        private String bagAddr;
        private String mark;
    }
}
