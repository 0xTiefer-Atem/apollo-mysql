package org.springboot.apollo.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.io.Serializable;

/**
 * (ContrarySqlRecord)实体类
 *
 * @author makejava
 * @since 2020-08-02 11:55:00
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContrarySqlRecord {
    /**
    * id值
    */
    private Integer id;
    /**
    * sql申请id号
    */
    private String applyId;
    /**
     * 数据库
     */
    private String dataBase;
    /**
    * 生成的相反sql语句
    */
    private String contrarySql;
    /**
    * 相反sql的创建时间
    */
    private Date createTime;

}