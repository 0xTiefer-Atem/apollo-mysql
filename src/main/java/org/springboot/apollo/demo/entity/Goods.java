package org.springboot.apollo.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (Goods)实体类
 *
 * @author makejava
 * @since 2020-07-29 09:06:27
 */

@Data
public class Goods {
    
    private String id;
    
    private String caccount;
    
    private String cdata;
    
    private String csaledata;
    
    private String csaleaccount;
    
    private String ctype;
    
    private String cdescribe;
    
    private String cstate;
    
    private String photourl;
    
    private String phone;
    /**
    * 商品的状态 1：已交易 0:交易中
    */
    private Integer code;
}