package io.loli.zto.client;

import lombok.Data;

@Data
public class ZtoResponseDto {
    private String statusCode;
    private String message;
    private Boolean status;
}
