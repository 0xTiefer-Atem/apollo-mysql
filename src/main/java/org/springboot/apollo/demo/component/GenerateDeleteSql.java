package org.springboot.apollo.demo.component;

import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.relational.ExpressionList;
import net.sf.jsqlparser.expression.operators.relational.ItemsList;
import net.sf.jsqlparser.expression.operators.relational.MultiExpressionList;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.schema.Table;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.insert.Insert;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author： WangQian
 * @date： 2020/7/31 下午 5:24
 */
@Component
public class GenerateDeleteSql {
    public String generateDeleteSql(Statement statement) {
        Insert insertStatement = (Insert) statement;
        System.out.println("传入的插入语句: " + insertStatement);
        //获得表名
        Table table = insertStatement.getTable();
        System.out.println("插入要操作的表: " + table.getName());
        //获得列名
        List<Column> columnList = insertStatement.getColumns();
        int size = columnList.size();
        System.out.println("插入要操作的列: " + columnList);
        //获得插入的值
        ItemsList itemsList = insertStatement.getItemsList();
        StringBuffer contrarySqlBuffer = new StringBuffer();
        if (itemsList instanceof MultiExpressionList) {
            MultiExpressionList multiExpressionList = (MultiExpressionList) itemsList;
            System.out.println("插入多组值: " + multiExpressionList);
            List<ExpressionList> expressionLists = multiExpressionList.getExprList();
            int eSize = expressionLists.size();
            for (int i = 0; i < eSize; i++) {
                List<Expression> expressionList = expressionLists.get(i).getExpressions();
                contrarySqlBuffer.append("delete from ");
                contrarySqlBuffer.append(table.getName());
                contrarySqlBuffer.append(" where ");
                for (int j = 0; j < size; j++) {
                    contrarySqlBuffer.append(columnList.get(j).getColumnName());
                    contrarySqlBuffer.append(" = ");
                    contrarySqlBuffer.append(expressionList.get(j).toString());
                    if (j != size - 1) {
                        contrarySqlBuffer.append(" and ");
                    }
                }
                contrarySqlBuffer.append("#!%");
            }
        } else {
            ExpressionList expressionList = (ExpressionList) itemsList;
            System.out.println("插入一组值: " + expressionList);
            List<Expression> expressionList1 = expressionList.getExpressions();
            contrarySqlBuffer.append("delete from ");
            contrarySqlBuffer.append(table.getName());
            contrarySqlBuffer.append(" where ");
            for (int i = 0; i < size; i++) {
                contrarySqlBuffer.append(columnList.get(i).getColumnName());
                contrarySqlBuffer.append(" = ");
                contrarySqlBuffer.append(expressionList1.get(i).toString());
                if (i != size - 1) {
                    contrarySqlBuffer.append(" and ");
                }
            }
        }
        System.out.println("结果: ");
        String contrarySqls = contrarySqlBuffer.toString();
        String[] deleteSqls = contrarySqls.split("#!%");
        int length = deleteSqls.length;
        System.out.println(length);
        for (int i = 0; i < length; i++) {
            System.out.println(deleteSqls[i]);
        }
        return contrarySqls;
    }
}
