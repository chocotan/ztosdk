package io.loli.zto.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
public class ZtoCommonOrderRequest {
    private ZtoCommonOrderRequest.ZtoCommonOrderGroup orderGroup;

    @Data
    @ToString
    public static class ZtoCommonOrderGroup {
        private Integer type;
        private Integer status;
        private String partnerCode;
        private String tradeId;
        private String mailNo;
        private String siteName;
        private ZtoCommonOrderRequest.ZtoCommonOrderGroup.ZtoCommonOrderSender sender;
        private ZtoCommonOrderRequest.ZtoCommonOrderGroup.ZtoCommonOrderReceiver receiver;
        private List<ZtoCommonOrderRequest.ZtoCommonOrderGroup.ZtoCommonOrderItem> items;
        private Date starttime;
        private Date endtime;
        private Long weight;
        private String size;
        private Integer quantity;
        private Long price;
        private Long freight;
        private Long premium;
        private Long packCharges;
        private Long otherCharges;
        private Long orderSum;
        private String collectMoneytype;
        private Long collectSum;
        private String remark;

        @Data
        @ToString
        public static class ZtoCommonOrderSender {

            private String id;
            private String name;
            private String company;
            private String mobile;
            private String phone;
            private String prov;
            private String city;
            private String county;
            private String address;
            private String zipcode;
        }

        @Data
        @ToString
        public static class ZtoCommonOrderReceiver {
            private String id;
            private String name;
            private String company;
            private String mobile;
            private String phone;
            private String prov;
            private String city;
            private String county;
            private String address;
            private String zipcode;
        }

        @Data
        @ToString
        public static class ZtoCommonOrderItem {
            private String id;
            private String name;
            private String category;
            private String material;
            private String size;
            private Long weight;
            private Long unitprice;
            private Integer quantity;
            private String remark;
        }
    }
}
