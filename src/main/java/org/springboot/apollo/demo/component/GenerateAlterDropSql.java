package org.springboot.apollo.demo.component;

import com.alibaba.fastjson.JSON;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.alter.AlterExpression;
import net.sf.jsqlparser.statement.alter.AlterOperation;
import org.apache.commons.lang3.StringUtils;
import org.springboot.apollo.demo.mapper.GoodsMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;

/**
 * @author： WangQian
 * @date： 2020/8/31 上午 9:59
 */
@Component
public class GenerateAlterDropSql {
    @Resource
    GoodsMapper goodsMapper;

    public String generateAlterDropSql(String dataBase, Statement statement) {
        //Alter对象
        Alter alter = (Alter) statement;
        System.out.println(alter.toString());
        String tableName = alter.getTable().getName();
        StringBuffer contrarySqlBuffer = new StringBuffer();
        contrarySqlBuffer.append("ALTER TABLE ")
                .append(tableName);
        String tempStr = contrarySqlBuffer.toString();
        int size = alter.getAlterExpressions().size();
        for (int i = 0; i < size; i++) {
            AlterExpression alterExpression = alter.getAlterExpressions().get(i);
            //添加字段，生成删除字段的语句
            if (alterExpression.getOperation() == AlterOperation.ADD) {
                for (AlterExpression.ColumnDataType columnDataType : alterExpression.getColDataTypeList()) {
                    contrarySqlBuffer.append(" DROP COLUMN ")
                            .append(columnDataType.getColumnName());
                }
            }

            //删除字段，生成添加字段语句
            if (alterExpression.getOperation() == AlterOperation.DROP) {
                HashMap<String, String> columnMap = goodsMapper.selectColumnInfo(dataBase, tableName, alterExpression.getColumnName());
                String jsonStr = JSON.toJSONString(columnMap);
                //查出为空
                if(StringUtils.isEmpty(jsonStr)) {
                    continue;
                }
                if(StringUtils.isNotEmpty(columnMap.get("COLUMN_KEY"))) {
                    //主键标识
                    continue;
                }
                String columnType = columnMap.get("COLUMN_TYPE");
                contrarySqlBuffer.append(" ADD COLUMN ")
                        //类名
                        .append(alterExpression.getColumnName())
                        .append(" ")
                        //类型
                        .append(columnType)
                        .append(" ");
                //为空标识
                if ("NO".equals(columnMap.get("IS_NULLABLE"))) {
                    contrarySqlBuffer.append("NOT NULL ");
                }
                //默认值
                if (StringUtils.isNotEmpty(columnMap.get("COLUMN_DEFAULT"))) {
                    if (columnType.contains("date") || columnType.contains("time")) {
                        contrarySqlBuffer.append("default ")
                                .append(columnMap.get("COLUMN_DEFAULT"))
                                .append(" ");
                    } else {
                        contrarySqlBuffer.append("default '")
                                .append(columnMap.get("COLUMN_DEFAULT"))
                                .append("' ");
                    }
                }
                //注释
                if (StringUtils.isNotEmpty(columnMap.get("COLUMN_COMMENT"))) {
                    contrarySqlBuffer.append("comment '")
                            .append(columnMap.get("COLUMN_COMMENT"))
                            .append("'");
                }
            }

            //修改字段名称和类型
            if (alterExpression.getOperation() == AlterOperation.CHANGE) {
                String oldColName = alterExpression.getColOldName();
                String newColName = "";
                for (AlterExpression.ColumnDataType columnDataType : alterExpression.getColDataTypeList()) {
                    newColName = columnDataType.getColumnName();
                }
                HashMap<String, String> columnMap = goodsMapper.selectColumnInfo(dataBase, tableName, oldColName);
                String jsonStr = JSON.toJSONString(columnMap);
                //查出为空
                if(StringUtils.isEmpty(jsonStr)) {
                    continue;
                }
                if(StringUtils.isNotEmpty(columnMap.get("COLUMN_KEY"))) {
                    //主键标识
                    continue;
                }
                String columnType = columnMap.get("COLUMN_TYPE");
                contrarySqlBuffer.append(" CHANGE COLUMN ")
                        //旧字段
                        .append(newColName)
                        .append(" ")
                        //新字段
                        .append(oldColName)
                        .append(" ")
                        //类型
                        .append(columnType)
                        .append(" ");
                //为空标识
                if ("NO".equals(columnMap.get("IS_NULLABLE"))) {
                    contrarySqlBuffer.append("NOT NULL ");
                }
                //默认值
                if (StringUtils.isNotEmpty(columnMap.get("COLUMN_DEFAULT"))) {
                    if (columnType.contains("date") || columnType.contains("time")) {
                        contrarySqlBuffer.append("default ")
                                .append(columnMap.get("COLUMN_DEFAULT"))
                                .append(" ");
                    } else {
                        contrarySqlBuffer.append("default '")
                                .append(columnMap.get("COLUMN_DEFAULT"))
                                .append("' ");
                    }
                }
                //注释
                if (StringUtils.isNotEmpty(columnMap.get("COLUMN_COMMENT"))) {
                    contrarySqlBuffer.append("comment '")
                            .append(columnMap.get("COLUMN_COMMENT"))
                            .append("'");
                }

            }

            //修改字段类型
            if(alterExpression.getOperation() == AlterOperation.MODIFY) {
                String colName = "";
                for (AlterExpression.ColumnDataType columnDataType : alterExpression.getColDataTypeList()) {

                            colName = columnDataType.getColumnName();
                }
                HashMap<String, String> columnMap = goodsMapper.selectColumnInfo(dataBase, tableName, colName);
                String jsonStr = JSON.toJSONString(columnMap);
                //查出为空
                if(StringUtils.isEmpty(jsonStr)) {
                    continue;
                }
                if(StringUtils.isNotEmpty(columnMap.get("COLUMN_KEY"))) {
                    //主键标识
                    continue;
                }
                String columnType = columnMap.get("COLUMN_TYPE");
                contrarySqlBuffer.append(" MODIFY COLUMN ")
                        //类名
                        .append(colName)
                        .append(" ")
                        //类型
                        .append(columnType)
                        .append(" ");
                //为空标识
                if ("NO".equals(columnMap.get("IS_NULLABLE"))) {
                    contrarySqlBuffer.append("NOT NULL ");
                }
                //默认值
                if (StringUtils.isNotEmpty(columnMap.get("COLUMN_DEFAULT"))) {
                    if (columnType.contains("date") || columnType.contains("time")) {
                        contrarySqlBuffer.append("default ")
                                .append(columnMap.get("COLUMN_DEFAULT"))
                                .append(" ");
                    } else {
                        contrarySqlBuffer.append("default '")
                                .append(columnMap.get("COLUMN_DEFAULT"))
                                .append("' ");
                    }
                }
                //注释
                if (StringUtils.isNotEmpty(columnMap.get("COLUMN_COMMENT"))) {
                    contrarySqlBuffer.append("comment '")
                            .append(columnMap.get("COLUMN_COMMENT"))
                            .append("'");
                }
            }
            if (i != size - 1) {
                contrarySqlBuffer.append(" , ");
            }

        }
        String contrarySqlStr = contrarySqlBuffer.toString();
        System.out.println(contrarySqlBuffer.toString());
        if(tempStr.equals(contrarySqlStr)){
            return null;
        }
        return contrarySqlStr;
    }
}
