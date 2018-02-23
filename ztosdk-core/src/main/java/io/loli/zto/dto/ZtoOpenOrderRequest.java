package io.loli.zto.dto;

import lombok.Data;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@ToString
public class ZtoOpenOrderRequest {
    private ZtoOpenOrderGroup orderGroup;

    @Data
    @ToString
    public static class ZtoOpenOrderGroup {
        private Integer type;
        private Integer status;
        private String partnerCode;
        private String tradeId;
        private String mailNo;
        private String siteName;
        private ZtoOpenOrderSender sender;
        private ZtoOpenOrderReceiver receiver;
        private List<ZtoOpenOrderItem> items;
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
        public static class ZtoOpenOrderSender {

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
        public static class ZtoOpenOrderReceiver {
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
        public static class ZtoOpenOrderItem {
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
