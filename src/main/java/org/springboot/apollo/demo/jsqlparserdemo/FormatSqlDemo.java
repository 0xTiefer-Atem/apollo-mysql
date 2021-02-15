package org.springboot.apollo.demo.jsqlparserdemo;

import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.parser.ParserException;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.statement.Statement;

import java.io.StringReader;
import java.util.List;

import static com.alibaba.druid.sql.SQLUtils.toSQLString;

/**
 * @author： WangQian
 * @date： 2020/8/5 上午 10:31
 */
public class FormatSqlDemo {
    public static void main(String[] args) {
//        String str = "create table `tb_user` (\n" +
//                "  `id` varchar(22) not null COMMENT '主键id',\n" +
//                "  `usercode` varchar(11) DEFAULT null COMMENT '手机号',\n" +
//                "  `name` varchar(10) DEFAULT nu)";
//       druidFormatSql(str);
//       jSqlParserFormatSql(str);

//        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
//        String date = sf.format(new Date());
//        System.out.println(date);
    }

    public static void jSqlParserFormatSql(String sql) {
        CCJSqlParserManager pm = new CCJSqlParserManager();
        Statement statement = null;
        try {
            statement = pm.parse(new StringReader(sql));
        } catch (Exception e) {
            System.out.println("SQL转换中发生了错误：" + e.getMessage());
        }
        System.out.println(statement);

    }

    public static void druidFormatSql(String sql) {
        System.out.println("格式化之前：");
        System.out.println(sql);
        System.out.println("格式化之后：");
        try {
            SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, "mysql");
            List<SQLStatement> statementList = parser.parseStatementList();
            sql = toSQLString(statementList, "mysql");
            System.out.println(sql);
        } catch (ParserException e) {
            System.out.println("SQL转换中发生了错误：" + e.getMessage());
        }
    }
}
