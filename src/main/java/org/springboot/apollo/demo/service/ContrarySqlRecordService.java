package org.springboot.apollo.demo.service;

import org.apache.ibatis.annotations.Param;
import org.springboot.apollo.demo.entity.ContrarySqlRecord;

import java.util.List;

/**
 * @author： WangQian
 * @date： 2020/8/2 上午 11:57
 */
public interface ContrarySqlRecordService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ContrarySqlRecord queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<ContrarySqlRecord> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param contrarySqlRecord 实例对象
     * @return 对象列表
     */
    List<ContrarySqlRecord> queryAll(ContrarySqlRecord contrarySqlRecord);

    /**
     * 新增数据
     *
     * @param contrarySqlRecord 实例对象
     * @return 影响行数
     */
    int insert(ContrarySqlRecord contrarySqlRecord);

    /**
     * 修改数据
     *
     * @param contrarySqlRecord 实例对象
     * @return 影响行数
     */
    int update(ContrarySqlRecord contrarySqlRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    ContrarySqlRecord queryLatestRecord(@Param("applyId") String applyId);

    int rollBackSql(@Param("contrarySql") String contrarySql);

}
