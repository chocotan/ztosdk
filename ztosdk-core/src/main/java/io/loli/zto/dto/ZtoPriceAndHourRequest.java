package io.loli.zto.dto;

import lombok.Data;

@Data
public class ZtoPriceAndHourRequest {
    private String sendProv;
    private String sendCity;
    private String dispProv;
    private String dispCity;
}
