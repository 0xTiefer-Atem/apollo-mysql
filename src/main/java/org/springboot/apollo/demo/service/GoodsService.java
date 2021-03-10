package org.springboot.apollo.demo.service;

import net.sf.jsqlparser.JSQLParserException;
import org.apache.ibatis.annotations.Param;
import org.springboot.apollo.demo.entity.ContrarySqlRecord;
import org.springboot.apollo.demo.entity.Goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Goods)表服务接口
 *
 * @author makejava
 * @since 2020-07-29 09:10:23
 */
public interface GoodsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Goods queryById(String id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Goods> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param goods 实例对象
     * @return 实例对象
     */
    Goods insert(Goods goods);

    /**
     * 修改数据
     *
     * @param goods 实例对象
     * @return 实例对象
     */
    Goods update(Goods goods);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    ContrarySqlRecord exeUpdateSql(String applyId, String dataBase, String sql) throws Exception;

    List<Map> selectAll(String sql);

}