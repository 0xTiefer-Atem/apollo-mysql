package org.springboot.apollo.demo.component;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.delete.Delete;
import org.springboot.apollo.demo.mapper.GoodsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author： WangQian
 * @date： 2020/7/31 下午 5:26
 */
@Component
public class GenerateInsertSql {
    @Resource
    GoodsMapper goodsMapper;


    public String generateInsertSql(Statement statement) {
        Delete deleteStatement = (Delete) statement;
        System.out.println("传入的删除语句: " + deleteStatement);
        //获得表名
        Table table = deleteStatement.getTable();
        System.out.println("删除要操作的表: " + table.getName());
        //获得删除条件
        Expression whereExpression = deleteStatement.getWhere();
        System.out.println("删除条件: " + whereExpression.toString());
        StringBuffer tempSqlBuffer = new StringBuffer();
        tempSqlBuffer.append("select * from ");
        tempSqlBuffer.append(table.getName());
        tempSqlBuffer.append(" where ");
        tempSqlBuffer.append(whereExpression.toString());
        String tempSql = tempSqlBuffer.toString();
        System.out.println("根据删除语句生成的中间查询语句: " + tempSql);
        List<Map> selectRes = goodsMapper.exeSelectSql(tempSql);
        System.out.println("根据中间查询语句查询出来的结果集合: " + selectRes);
        StringBuffer contrarySqlBuffer = new StringBuffer();
        System.out.println("开始根据查询结果反向生成插入语句");
        for (Map map : selectRes) {
            contrarySqlBuffer.append("insert into ");
            contrarySqlBuffer.append(table.getName());
            contrarySqlBuffer.append("(");
            Set keysSet = map.keySet();
            int keysSize = keysSet.size();
            int count = 0;
            for (Object key : keysSet) {
                contrarySqlBuffer.append(key.toString());
                if (count != keysSize - 1) {
                    contrarySqlBuffer.append(",");
                }
                count++;
            }
            count = 0;
            contrarySqlBuffer.append(") values (");
            Collection valuesCollection = map.values();
            int valuesSize = valuesCollection.size();
            for (Object value : valuesCollection) {
                if (value == null || value instanceof Number) {
                    contrarySqlBuffer.append(value);

                } else {
                    contrarySqlBuffer.append("'");
                    contrarySqlBuffer.append(value);
                    contrarySqlBuffer.append("'");
                }
                if (count != valuesSize - 1) {
                    contrarySqlBuffer.append(",");
                }
                count++;
            }
            count = 0;
            contrarySqlBuffer.append(")#!%");
        }
        System.out.println("结果: ");
        String contrarySqls = contrarySqlBuffer.toString();
        String[] insertSqls = contrarySqls.split("#!%");
        int size = insertSqls.length;
        for (int i = 0; i < size; i++) {
            System.out.println(insertSqls[i]);
        }
        return contrarySqls;
    }
}
