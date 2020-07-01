package com.jimie.handler.sql.core.impl;

import com.jimie.handler.sql.bo.TableModel;
import com.jimie.handler.sql.core.HeadBuilder;
import org.springframework.stereotype.Component;

/**
 * @author litingjie
 * 2020-7-1
 */
@Component
public class HeadBuilderImpl implements HeadBuilder {

    @Override
    public String createSql(TableModel model) {
/*        StringBuilder builder = new StringBuilder();
        builder.append("create table ")
                .append(model.getTableName())
                .append("(%s)");*/
        return "create table "+model.getTableName()+"(%s)";

    }

    @Override
    public String createBean(TableModel model) {

        String result = "package com.businessameco;\n" +
                "\n" +
                "import com.triweb.annotation.DbType;\n" +
                "import com.triweb.annotation.TWTableName;\n" +
                "import com.triweb.entity.BaseEntityImpl;\n" +
                "import lombok.Data;\n" +
                "\n" +
                "/**\n" +
                " * @author litingjie\n" +
                " * 2020-7-1\n" +
                " */\n";

        return result + "@Data\r\n@DbType\r\n@TWTableName(name = \""+model.getTableName()+"\")\r\npublic class "+model.getBeanName()+" extends BaseEntityImpl {\r\n%s}";
    }

}
