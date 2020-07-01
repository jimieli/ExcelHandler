import com.jimie.handler.excel.config.ExcelHandlerConfiguration;
import com.jimie.handler.excel.constant.ExcelType;
import com.jimie.handler.excel.core.ExcelReader;
import com.jimie.handler.sql.bo.ColumnModel;
import com.jimie.handler.sql.bo.TableModel;
import com.jimie.handler.sql.constant.BuilderConstant;
import com.jimie.handler.sql.core.Builder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author litingjie
 * 2020-7-1
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ExcelHandlerConfiguration.class)
public class DataTest {

    @Autowired
    ExcelReader<Map<String,List<List<Object>>>> excelReader;

    @Autowired
    Builder builder;

    @Test
    public void analysisExcel(){
        try {

            File file = new File("C:\\Users\\JiMie\\Desktop\\kingwant\\ameco\\AMECO数据表_v1_2020_07_1.xlsx");
            Map<String,List<List<Object>>> map = excelReader.readFromExcel(file.toPath(), ExcelType.xlsx);


            List<List<Object>> sheet = map.get("Sheet1");

            List<TableModel> tableModels = buildTableModel(sheet);

            System.out.println(tableModels);


            List<String> sqlList = new ArrayList<>();

            List<Map<String,String>> fileAndBeanList = new ArrayList<>();


            StringBuilder sqls = new StringBuilder();

            tableModels.forEach(model->{
                String sql = builder.createTable(model);
                Map<String,String> bean = builder.createBean(model);

                fileAndBeanList.add(bean);

                sqls.append(sql).append(";").append(BuilderConstant.LINE_SPLIT);


                sqlList.add(sql);
            });
            buildJavaFile(fileAndBeanList);

            System.out.println(sqls.toString());

            System.out.println(sqlList);
            System.out.println(fileAndBeanList);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void buildJavaFile(List<Map<String,String>> fileAndBeanList){

        fileAndBeanList.forEach(map->{

            String fileName = map.get("fileName");
            String bean = map.get("bean");

            File file = new File("C:\\Users\\JiMie\\Desktop\\java",fileName);
            BufferedWriter writer = null;
            try {
                writer = Files.newBufferedWriter(file.toPath(), StandardOpenOption.CREATE_NEW);

                writer.write(bean);
                writer.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(writer != null ){
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

    }

    private List<TableModel> buildTableModel(List<List<Object>> sheet){

        List<TableModel> result = new ArrayList<>();

        List<Integer> startIndexList = new ArrayList<>();

        for (int i = 0; i < sheet.size(); i++) {
            List<Object> objects = sheet.get(i);
            if(objects.size()<5){
                continue;
            }
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
                end = sheet.size()-1;
            }

            int start = startIndexList.get(i);

            TableModel tableModel = new TableModel();
            buildModelInfo(tableModel,sheet.get(start));


            int columnStart = start + 2;

            buildModelColumn(tableModel,sheet,columnStart,end);

            System.out.println(tableModel);
            buildDefaultColumn(tableModel);

            result.add(tableModel);
        }

        return result;
    }

    private void buildDefaultColumn(TableModel model) {
        List<ColumnModel> column = model.getColumn();

        column.add(new ColumnModel("cruser","","字符",50,false,false,""));
        column.add(new ColumnModel("mduser","","字符",50,false,false,""));
        column.add(new ColumnModel("crtime","","日期",null,false,false,""));
        column.add(new ColumnModel("mdtime","","日期",null,false,false,""));
        column.add(new ColumnModel("remark","","字符",50,false,false,""));
        column.add(new ColumnModel("version","","整形",null,false,false,""));


    }

    private void buildModelColumn(TableModel tableModel,List<List<Object>> sheet,int startIndex,int endIndex){

        List<ColumnModel> column = tableModel.getColumn();

        for (int i = startIndex; i <= endIndex ; i++) {


            List<Object> row = sheet.get(i);

            if(row.size()<6){
                System.out.println(row);
                continue;
            }

            String name = getString(row,0);
            String comment = getString(row,1);
            String dataType = getString(row,2);

            Integer length = null;
            Object o = row.get(3);
            if(o instanceof Integer){
                length = (Integer) o;
            }

            boolean required = getBoolean(row,4);
            boolean primary = getBoolean(row,5);

            Object defaultValue = row.get(6);
            column.add(new ColumnModel(name, comment, dataType, length, required, primary, defaultValue));

//            System.out.println(row);


        }
    }

    private boolean getBoolean(List<Object> row,int index){
        return "是".equals(getString(row,index));
    }

    private String getString(List<Object> row,int index){
        return row.get(index).toString().trim();
    }

    private void buildModelInfo(TableModel tableModel,List<Object> firstRow){
        String tableName = firstRow.get(5).toString().trim();
        String beanName = firstRow.get(8).toString().trim();

        List<ColumnModel> column = new ArrayList<ColumnModel>();

        tableModel.setBeanName(beanName);
        tableModel.setTableName(tableName);
        tableModel.setColumn(column);


    }
}
