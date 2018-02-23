package io.loli.zto.dto;

import lombok.Data;

@Data
public class ZtoPriceAndHourResponse {
    private String msg;
    private ZtoPriceAndHourResponseData data;
    private Boolean status;

    @Data
    public static class ZtoPriceAndHourResponseData{
        private String addMoney;
        private String firstMoney;
        private String hour;
    }
}
