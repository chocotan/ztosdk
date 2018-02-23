package io.loli.zto.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class ZtoTraceResponse {
    private List<ZtoTraceWithBillCode> data;

    private String msg;
    private Boolean status;

    @Data
    @ToString
    public static class ZtoTraceWithBillCode {
        private String billCode;
        private List<ZtoTrace> traces;
    }

    @Data
    @ToString
    public static class ZtoTrace {
        private String desc;
        private String dispOrRecMan;
        private String dispOrRecManCode;
        private String dispOrRecManPhone;
        private String isCenter;
        private String preOrNextCity;
        private String preOrNextProv;
        private String preOrNextSite;
        private String preOrNextSiteCode;
        private String preOrNextSitePhone;
        private String remark;
        private String scanCity;
        @JSONField(format = "yyyy-MM-dd HH:mm:ss")
        private Date scanDate;
        private String scanProv;
        private String scanSite;
        private String scanSiteCode;
        private String scanSitePhone;
        private String scanType;
        private String signMan;
    }
}
