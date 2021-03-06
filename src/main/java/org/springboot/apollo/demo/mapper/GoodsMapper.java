package org.springboot.apollo.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springboot.apollo.demo.entity.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Goods)表数据库访问层
 *
 * @author makejava
 * @since 2020-07-29 09:06:28
 */
@Mapper
public interface GoodsMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Goods queryById(String id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Goods> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param goods 实例对象
     * @return 对象列表
     */
    List<Goods> queryAll(Goods goods);

    /**
     * 新增数据
     *
     * @param goods 实例对象
     * @return 影响行数
     */
    int insert(Goods goods);

    /**
     * 修改数据
     *
     * @param goods 实例对象
     * @return 影响行数
     */
    int update(Goods goods);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(String id);

    int exeUpdateSql(@Param("sql") String sql);

    List<Map> exeSelectSql(@Param("sql") String sql);

    HashMap<String, String> selectColumnInfo(@Param("dataBase") String dataBase, @Param("table") String table ,@Param("column") String column);

    void changeDataBase(@Param("sql") String sql);
}