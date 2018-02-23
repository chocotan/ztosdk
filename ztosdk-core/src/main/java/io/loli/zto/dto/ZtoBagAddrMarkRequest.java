package io.loli.zto.dto;

import lombok.Data;

@Data
public class ZtoBagAddrMarkRequest {
    private String unionCode;
    private String send_province;
    private String send_city;
    private String send_district;
    private String send_address;
    private String receive_province;
    private String receive_city;
    private String receive_district;
    private String receive_address;
}
