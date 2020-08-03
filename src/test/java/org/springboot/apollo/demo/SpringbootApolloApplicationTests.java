package org.springboot.apollo.demo;

import com.alibaba.druid.sql.SQLUtils;
  import com.alibaba.druid.sql.ast.SQLStatement;
 import com.alibaba.druid.sql.dialect.postgresql.visitor.PGSchemaStatVisitor;
import com.alibaba.druid.sql.visitor.SchemaStatVisitor;
import com.alibaba.druid.stat.TableStat.*;
 import com.alibaba.druid.stat.*;
 import com.alibaba.druid.util.JdbcConstants;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class SpringbootApolloApplicationTests {

    @Test
    void contextLoads() {
        String mysqlUrl = "jdbc:mysql://47.107.64.157:3306/#{mysqlUrl}?useUnicode=true&characterEncoding=utf8";
        System.out.println(mysqlUrl.replace("#{mysqlUrl}", "xxx"));
        System.out.println(UUID.randomUUID().toString());
    }


    @Test
    void parseSql() {
        String sql = "insert into a (name,value) values('a','x);";

        String dbType = JdbcConstants.MYSQL;
        List<SQLStatement> stmtList = SQLUtils.parseStatements(sql, dbType);
        SQLStatement stmt = stmtList.get(0);

        SchemaStatVisitor statVisitor = SQLUtils.createSchemaStatVisitor(dbType);
        stmt.accept(statVisitor);

        System.out.println(statVisitor.getColumns()); // [t_user.name, t_user.age, t_user.id]
        System.out.println(statVisitor.getTables()); // {t_user=Select}
        System.out.println(statVisitor.getConditions()); // [t_user.id = 1]

    }
}
