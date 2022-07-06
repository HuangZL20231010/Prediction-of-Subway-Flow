package mybatis.plus.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;


import java.sql.SQLException;

// 代码生成
public class CodeGenerator {
    public static final String URL = "jdbc:mysql://localhost:3306/springboot-vue?useUnicode=true&characterEncoding=UTF-8";

    public static void main(String[] args) throws SQLException {
        FastAutoGenerator.create(URL, "root", "myq888*oten!")
                // 全局配置
                .globalConfig(builder -> builder.outputDir("D:\\XiaoXueQi\\code"))
                // 策略配置
                .strategyConfig(builder -> builder.addInclude("sample"))
                // 执行
                .execute();



    }



}
