package org.springboot.apollo.demo.service.impl;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.update.Update;
import org.springboot.apollo.demo.component.*;
import org.springboot.apollo.demo.configuration.mysql.DynamicDataSource;
import org.springboot.apollo.demo.entity.ContrarySqlRecord;
import org.springboot.apollo.demo.entity.Goods;
import org.springboot.apollo.demo.mapper.GoodsMapper;
import org.springboot.apollo.demo.service.ContrarySqlRecordService;
import org.springboot.apollo.demo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.StringReader;
import java.util.List;
import java.util.Map;

/**
 * (Goods)表服务实现类
 *
 * @author makejava
 * @since 2020-07-29 09:10:23
 */
@Service("goodsService")
public class GoodsServiceImpl implements GoodsService {
    @Resource
    private GoodsMapper goodsMapper;

    @Autowired
    GenerateInsertSql generateInsertSql;

    @Autowired
    GenerateUpdateSql generateUpdateSql;

    @Autowired
    GenerateDeleteSql generateDeleteSql;

    @Autowired
    GenerateAlterDropSql generateAlterDropSql;

    @Autowired
    ContrarySqlRecordService contrarySqlRecordService;

    @Autowired
    private MysqlComponent mysqlComponent;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Goods queryById(String id) {
        return this.goodsMapper.queryById(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public List<Goods> queryAllByLimit(int offset, int limit) {
        return this.goodsMapper.queryAllByLimit(offset, limit);
    }

    /**
     * 新增数据
     *
     * @param goods 实例对象
     * @return 实例对象
     */
    @Override
    public Goods insert(Goods goods) {
        this.goodsMapper.insert(goods);
        return goods;
    }

    /**
     * 修改数据
     *
     * @param goods 实例对象
     * @return 实例对象
     */
    @Override
    public Goods update(Goods goods) {
        this.goodsMapper.update(goods);
        return this.queryById(goods.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.goodsMapper.deleteById(id) > 0;
    }

    @Override
    @Transactional
    public ContrarySqlRecord exeUpdateSql(String applyId, String dataBase, String sql) throws Exception {
        String contrarySqls = generateContrarySqls(dataBase, sql);
        goodsMapper.exeUpdateSql(sql);

//        goodsMapper.changeDataBase("use " + mysqlComponent.getDefaultDataBase());
        ContrarySqlRecord contrarySqlRecord = new ContrarySqlRecord();
        contrarySqlRecord.setApplyId(applyId);
        contrarySqlRecord.setDataBase(dataBase);
        contrarySqlRecord.setContrarySql(contrarySqls);
        return contrarySqlRecord;
    }

    @Override
    public List<Map> selectAll(String sql) {
        return goodsMapper.exeSelectSql(sql);
    }

    public String generateContrarySqls(String dataBase, String sql) throws JSQLParserException {
        CCJSqlParserManager pm = new CCJSqlParserManager();
        Statement statement = pm.parse(new StringReader(sql));
        String contrarySqls = null;
        if (statement instanceof Update) {
            contrarySqls = generateUpdateSql.generateUpdateSql(statement);
        } else if (statement instanceof Insert) {
            contrarySqls = generateDeleteSql.generateDeleteSql(statement);
        } else if (statement instanceof Delete) {
            contrarySqls = generateInsertSql.generateInsertSql(statement);
        } else if (statement instanceof Alter) {
            contrarySqls = generateAlterDropSql.generateAlterDropSql(dataBase, statement);
        }
        System.out.println(JSON.toJSONString(mysqlComponent) + "    " + dataBase);
        return contrarySqls;
    }
}