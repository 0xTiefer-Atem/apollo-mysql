package org.springboot.apollo.demo.jsqlparserdemo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.OrExpression;
import net.sf.jsqlparser.expression.operators.relational.EqualsTo;
import net.sf.jsqlparser.parser.CCJSqlParserManager;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.alter.Alter;
import net.sf.jsqlparser.statement.create.table.CreateTable;
import net.sf.jsqlparser.statement.delete.Delete;
import net.sf.jsqlparser.statement.drop.Drop;
import net.sf.jsqlparser.statement.insert.Insert;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.statement.select.SelectBody;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.util.deparser.ExpressionDeParser;
import org.springboot.apollo.demo.component.GenerateAlterDropSql;
import org.springboot.apollo.demo.component.GenerateDeleteSql;
import org.springboot.apollo.demo.component.GenerateInsertSql;
import org.springboot.apollo.demo.component.GenerateUpdateSql;

import java.io.StringReader;
import java.util.Date;

/**
 * @author： WangQian
 * @date： 2020/7/31 下午 2:49
 */
public class JsqlparserDemo {


    public static void main(String[] args) throws JSQLParserException {
//        System.out.println("update goods set caccount = 100.0,cdata = 1586097327292 where cstate = 0 AND phone = '17777777777'#!%update goods set caccount = 200.0,cdata = 1586108050425 where cstate = 0 AND phone = '17777777777'#!%update goods set caccount = 300.0,cdata = 1586159564590 where cstate = 0 AND phone = '17777777777'#!%".split("#!%").length);
        demo();
//        testParseWhere1();
//        testParseWhere2();
//        testParseWhere3();
//        String a = "asd4";
//        String b = a.replace("4", "999");
//        System.out.println(a);
//        System.out.println(b);

//        String s = "SELECT e.*,d.dname FROM emp e LEFT JOIN dept d ON d.did=e.did WHERE 1=1";
//        String regex = ".*(FROM.*)$";
//        String result = s.replaceAll(regex, "SELECT count(*) $1");
//        System.out.println(result);

//        JSONObject object = JSONObject.parseObject("{\"namespace\":{\"appId\":\"SampleApp\",\"clusterName\":\"default\",\"namespaceName\":\"TEST1.base\"},\"env\":\"TEST\"}");
//        JSONObject object1 = JSONObject.parseObject(JSON.toJSONString(object.get("namespace")));
//        System.out.println(object1.get("clusterName"));
    }

    public static void demo() throws JSQLParserException{
        CCJSqlParserManager pm = new CCJSqlParserManager();

        StringBuffer stringBuffer = new StringBuffer();
//        stringBuffer.append("update ac_operator op ");
//        stringBuffer.append("set op.errcount='xxx'");
//        stringBuffer.append(",lastlogin='中国' ");
//        stringBuffer.append("where PROCESS_ID=");
//        stringBuffer.append("(select distinct g.id from tempTable g where g.ID='中国')");
//        stringBuffer.append("and columnName2 = '890' and columnName3 = '678' and columnName4 = '456'");

//        stringBuffer.append("insert into ac_operator ");
//        stringBuffer.append("(aaa,bbb,ccc,ddd) ");
//        stringBuffer.append("values");
//        stringBuffer.append("('xxx','123','s34','')");
//        stringBuffer.append(", ");
//        stringBuffer.append("('abc','xxx','23ed','saz/'), ");
//        stringBuffer.append("('123','wepi','sder','ccccc')");

//        stringBuffer.append("delete from ac_operator ");
//        stringBuffer.append("where ");
//        stringBuffer.append("xxx = 'x' and a='b'");

        stringBuffer.append("ALTER TABLE person ");
//        stringBuffer.append("ADD func varchar(50) ");
//        stringBuffer.append(",");
//        stringBuffer.append(" DROP COLUMN gene ");
//        stringBuffer.append(",");
//        stringBuffer.append(" ALTER COLUMN genedetail varchar(50) ");
//        stringBuffer.append(",");
        stringBuffer.append(" CHANGE aaa bbb decimal(10,1) DEFAULT NULL COMMENT '注释'");

        Statement statement = pm.parse(new StringReader(stringBuffer.toString()));

        if (statement instanceof Update) {
            new GenerateUpdateSql().generateUpdateSql(statement);
        }else if(statement instanceof Insert) {
            new GenerateDeleteSql().generateDeleteSql(statement);
        }else if(statement instanceof Delete) {
            new GenerateInsertSql().generateInsertSql(statement);
        }else if(statement instanceof Alter) {
            new GenerateAlterDropSql().generateAlterDropSql("xxxx" ,statement);
        }
    }


    //单条件等于查询
    //由于单条件等于，返回为EqualsTo，根据EqualsTo获得表名，列名，及对应单条件值.
    public static void testParseWhere1(){
        String sql = "select *from A as a left join B on a.bid = B.id left join C on A.cid = C.id left join D on B.did = D.id where a.id = 23";
        try {
            Select select = (Select) CCJSqlParserUtil.parse(sql);
            SelectBody selectBody = select.getSelectBody();
            PlainSelect plainSelect = (PlainSelect)selectBody;

            Expression where = plainSelect.getWhere();
            ExpressionDeParser expressionDeParser = new ExpressionDeParser();
            plainSelect.getWhere().accept(expressionDeParser);

            // 此处根据where实际情况强转 最外层
            EqualsTo equalsTo = (EqualsTo)where;
            System.out.println("Table:"+((Column)equalsTo.getLeftExpression()).getTable());
            System.out.println("Field:"+((Column)equalsTo.getLeftExpression()).getColumnName());
            System.out.println("equal:"+equalsTo.getRightExpression());

        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }


    //两个条件or连接
    //代码中有两个条件or连接，可回忆转成OrExpression,里面还是两个EqualsTo。
    public static void testParseWhere2(){
        String sql = "select *from A as a left join B on a.bid = B.id left join C on A.cid = C.id left join D on B.did = D.id where a.id = 23 or b.id = 34";
        try {
            Select select = (Select)CCJSqlParserUtil.parse(sql);
            SelectBody selectBody = select.getSelectBody();
            PlainSelect plainSelect = (PlainSelect)selectBody;

            Expression where = plainSelect.getWhere();
            ExpressionDeParser expressionDeParser = new ExpressionDeParser();
            plainSelect.getWhere().accept(expressionDeParser);

            // 此处根据where实际情况强转 最外层
            OrExpression orExpression = (OrExpression)where;
            EqualsTo equalsTo = (EqualsTo)orExpression.getLeftExpression();

            System.out.println("Table:"+((Column)equalsTo.getLeftExpression()).getTable());
            System.out.println("Field:"+((Column)equalsTo.getLeftExpression()).getColumnName());
            System.out.println("equal:"+equalsTo.getRightExpression());
            System.out.println("-----------------");
            equalsTo = (EqualsTo)orExpression.getRightExpression();

            System.out.println("Table:"+((Column)equalsTo.getLeftExpression()).getTable());
            System.out.println("Field:"+((Column)equalsTo.getLeftExpression()).getColumnName());
            System.out.println("equal:"+equalsTo.getRightExpression());

        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }

    //三个条件or连接
    //得到的第一层的leftExpression还是一个orExpression,rightExpression是一个EqualsTo
    public static void testParseWhere3(){
        String sql = "select *from A as a left join B on a.bid = B.id left join C on A.cid = C.id left join D on B.did = D.id where a.id = 23 or b.id = 34 or c.id = 54";
        try {
            Select select = (Select)CCJSqlParserUtil.parse(sql);
            SelectBody selectBody = select.getSelectBody();
            PlainSelect plainSelect = (PlainSelect)selectBody;

            Expression where = plainSelect.getWhere();
            ExpressionDeParser expressionDeParser = new ExpressionDeParser();
            plainSelect.getWhere().accept(expressionDeParser);

            // 此处根据where实际情况强转 最外层
            OrExpression orExpression = (OrExpression)where;

            OrExpression leftOrExpression = (OrExpression)orExpression.getLeftExpression();

            EqualsTo equalsTo = (EqualsTo)leftOrExpression.getLeftExpression();

            System.out.println("Table:"+((Column)equalsTo.getLeftExpression()).getTable());
            System.out.println("Field:"+((Column)equalsTo.getLeftExpression()).getColumnName());
            System.out.println("equal:"+equalsTo.getRightExpression());
            System.out.println("-----------------");
            equalsTo = (EqualsTo)leftOrExpression.getRightExpression();

            System.out.println("Table:"+((Column)equalsTo.getLeftExpression()).getTable());
            System.out.println("Field:"+((Column)equalsTo.getLeftExpression()).getColumnName());
            System.out.println("equal:"+equalsTo.getRightExpression());
            System.out.println("-----------------");
            equalsTo = (EqualsTo)orExpression.getRightExpression();

            System.out.println("Table:"+((Column)equalsTo.getLeftExpression()).getTable());
            System.out.println("Field:"+((Column)equalsTo.getLeftExpression()).getColumnName());
            System.out.println("equal:"+equalsTo.getRightExpression());

        } catch (JSQLParserException e) {
            e.printStackTrace();
        }
    }
}