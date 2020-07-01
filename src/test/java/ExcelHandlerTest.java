import com.jimie.handler.excel.config.ExcelHandlerConfiguration;
import com.jimie.handler.excel.constant.ExcelType;
import com.jimie.handler.excel.core.ExcelReader;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author litingjie
 * 2020-6-26
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ExcelHandlerConfiguration.class)
public class ExcelHandlerTest {

    @Test
    public void test(){

        HSSFWorkbook a = null;
        XSSFWorkbook b = null;

        HSSFWorkbook C = new HSSFWorkbook();

    }

    @Autowired
    ExcelReader<Map<String,List<List<Object>>>> excelReader;


    @Test
    public void analysisExcel(){
        try {

            File file = new File("C:\\Users\\JiMie\\Desktop\\kingwant\\ameco\\AMECO数据表_v1_2020_07_1.xlsx");
            Map<String,List<List<Object>>> map = excelReader.readFromExcel(file.toPath(), ExcelType.xlsx);

            List<String> tableSql = createTableSql(map.get("Sheet1"));
            System.out.println(tableSql);

            System.out.println(map);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private List<String> createTableSql(List<List<Object>> datas){

        List<String> result = new ArrayList<>();

        List<Integer> startIndexList = new ArrayList<>();

        for (int i = 0; i < datas.size(); i++) {
            List<Object> objects = datas.get(i);

            String o = objects.get(5).toString();
            if(o.startsWith("triweb")){
                startIndexList.add(i);
            }
        }

        for (int i = 0; i < startIndexList.size(); i++) {

            int nextStart = i + 1;
            int end = 0;
            if(i+1<startIndexList.size()){
                end = startIndexList.get(nextStart) - 1;
            }else{
                end = datas.size()-1;
            }

            String sql = createSql(datas,startIndexList.get(i),end);
            result.add(sql);
        }

        return result;
    }

    private String createSql(List<List<Object>> data, int startIndex, int endIndex){

        List<Object> row = data.get(startIndex);
        String tableName = row.get(0).toString();

        StringBuilder builder = new StringBuilder("create table ").append(tableName).append("(").
                append("  id varchar(32) primary key,\n" +
                "  cruser varchar(50),\n" +
                "  mduser varchar(50),\n" +
                "  crtime bigint,\n" +
                "  mdtime bigint,\n" +
                "  remark varchar(256),\n" +
                "  version int,");

        for (int i = startIndex + 2; i <= endIndex; i++) {

            List<Object> columnRow = data.get(i);

            String columnName = columnRow.get(1).toString();
            String type = columnRow.get(2).toString();

            builder.append(columnSql(columnName,type));
        }

        builder.deleteCharAt(builder.length()-1);
        builder.append(")");

        return builder.toString();
    }

    Pattern pattern = Pattern.compile("[a-z]+");

    private String columnSql(String columnName,String type){
        StringBuilder builder = new StringBuilder();

        Matcher matcher = pattern.matcher(type);
        String columnType;

        if(matcher.find()){
            columnType = matcher.group();
        }else{
            throw new RuntimeException("写法不匹配规则");
        }

        String remain = type.replace(columnType,"");
        boolean hasNum = remain.length()>0;

        builder.append(columnName);
        builder.append(" ");
        builder.append(columnType);
        if(hasNum){
            builder.append("(").append(remain).append("),");
        }else{
            builder.append(",");
        }


        return builder.toString();
    }

}
