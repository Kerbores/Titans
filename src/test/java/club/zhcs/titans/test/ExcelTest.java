package club.zhcs.titans.test;

import club.zhcs.titans.nutz.excel.ExcelParser;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */
public class ExcelTest {

    public static void main(String[] args) {
        try {
            System.out.println("SKLODA");
            List<List<String>> records = ExcelParser.parseExcel(new FileInputStream(new File("D:/test1.xlsx")));
            System.out.println(records);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
