package io.loli.zto.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ZtoEBillOrderRequest {
    private String partner;
    private String id;
    private String typeid;
    private String type;
    private String tradeid;
    private String branch_id;
    private String seller;
    private String buyer;
    private ZtoEBillOrderSender sender;
    private ZtoEBillOrderReceiver receiver;
    private Double weight;
    private String size;
    private String quantity;
    private Double price;
    private Double freight;
    private Double premium;
    private Double pack_charges;
    private Double other_charges;
    private Double order_sum;
    private String collect_moneytype;
    private Double collect_sum;
    private String remark;
    private String order_type;
    @Data
    public static class ZtoEBillOrderSender{
        private String id;
        private String name;
        private String company;
        private String mobile;
        private String phone;
        private String area;
        private String city;
        private String address;
        private String zipcode;
        private String email;
        private String im;
        private Date starttime;
        private Date endtime;
    }
    @Data
    public static class ZtoEBillOrderReceiver{
        private String id;
        private String name;
        private String company;
        private String mobile;
        private String phone;
        private String area;
        private String city;
        private String address;
        private String zipcode;
        private String email;
        private String im;
    }
}
