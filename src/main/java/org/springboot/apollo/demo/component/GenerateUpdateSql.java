package org.springboot.apollo.demo.component;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.update.Update;
import org.springboot.apollo.demo.mapper.GoodsMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author： WangQian
 * @date： 2020/7/31 下午 5:26
 */
@Component
public class GenerateUpdateSql {
    @Resource
    GoodsMapper goodsMapper;

    public String generateUpdateSql(Statement statement) {
        //获得Update对象
        Update updateStatement = (Update) statement;
        System.out.println("传入的更新语句: " + updateStatement);
        //获得表名
        Table table = updateStatement.getTable();
        System.out.println("更新要操作的表:" + table.getName());
        //获得列名
        List<Column> columnList = updateStatement.getColumns();
        System.out.println("更新要操作的列: " + columnList);
        //获得where条件表达式
        Expression where = updateStatement.getWhere();
        System.out.println("更新条件: " + where.toString());
        StringBuffer tempBuffer = new StringBuffer();
        tempBuffer.append("select ");
        int columns = columnList.size();
        for (int i = 0; i < columns; i++) {
            if (i != columns - 1) {
                tempBuffer.append(columnList.get(i).getColumnName());
                tempBuffer.append(", ");
            } else {
                tempBuffer.append(columnList.get(i).getColumnName());
            }
        }
        tempBuffer.append(" from ");
        tempBuffer.append(table.getName());
        tempBuffer.append(" where ");
        tempBuffer.append(where.toString());
        String tempSql = tempBuffer.toString();
        System.out.println("根据更新语句生成的中间查询语句: " + tempSql);
        goodsMapper.exeSelectSql(tempSql);
        List<Map> selectRes = goodsMapper.exeSelectSql(tempSql);
        System.out.println("根据中间查询语句查询出来的结果集合: " + selectRes);
        StringBuffer contrarySqlBuffer = new StringBuffer();
        System.out.println("开始根据查询结果反向生成更新语句");
        for (Map map : selectRes) {
            contrarySqlBuffer.append("update ");
            contrarySqlBuffer.append(table.getName());
            contrarySqlBuffer.append(" set ");
            Set<Map.Entry> entrySet = map.entrySet();
            int size = entrySet.size();
            Iterator it = entrySet.iterator();
            int count = 0;
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String column = entry.getKey().toString();
                Object value = entry.getValue();
                contrarySqlBuffer.append(column);
                contrarySqlBuffer.append(" = ");
                if (value == null || value instanceof Number) {
                    contrarySqlBuffer.append(value);
                } else {
                    contrarySqlBuffer.append("'");
                    contrarySqlBuffer.append(value);
                    contrarySqlBuffer.append("'");
                }
                if (count != size - 1) {
                    contrarySqlBuffer.append(",");
                }
                count++;
            }
            contrarySqlBuffer.append(" where ");
            contrarySqlBuffer.append(where.toString());
            contrarySqlBuffer.append("#!%");
        }
        System.out.println("结果: ");
        String contrarySqls = contrarySqlBuffer.toString();
        String[] updateSqls = contrarySqls.split("#!%");
        int size = updateSqls.length;
        for (int i = 0; i < size; i++) {
            System.out.println(updateSqls[i]);
        }
        return contrarySqls;
    }
}
