package com.orazbek_tulaganov.test.checker;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@RestController
public class Controller {

    ArrayList<Test> TestList;
    Set<Integer> got;
    public static final Random random = new Random();

    @GetMapping("/")
    public Tests main() throws IOException {
        Tests tests1 = new Tests();
        TestList = new ArrayList<>();
        got = new HashSet<>();
        FileInputStream inputStream;
        ClassPathResource resource = new ClassPathResource("/test.xlsx");

        try {
            inputStream = new FileInputStream(resource.getFile());
        } catch (FileNotFoundException e) {
            return null;
        }
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = workbook.getSheetAt(0);

        int count = sheet.getLastRowNum();
        count++;

        if(count > 0)while (got.size()<30){
            int x = random.nextInt(count);
            got.add(x);
        }
        for(Integer i: got) {
            Row row = sheet.getRow(i);
            Test test = new Test();
            ArrayList<String> javoblar = new ArrayList<>();
            Iterator<Cell> cellIterator = row.cellIterator();
            Cell cell = cellIterator.next();
            int rightVariant = random.nextInt(4);


            javoblar.add("");
            javoblar.add("");
            javoblar.add("");
            javoblar.add("");

            test.setQuestion(String.valueOf(cell.getNumericCellValue()));

            cell = cellIterator.next();

            test.setRight(String.valueOf((char) ('A' + rightVariant)));

            javoblar.set(rightVariant, cell.getStringCellValue());

            for (int j = 0; j < javoblar.size(); j++) {
                if (j != rightVariant) {
                    cell = cellIterator.next();
                    javoblar.set(j, cell.getStringCellValue());
                }
            }
            test.setA(javoblar.get(0));
            test.setB(javoblar.get(1));
            test.setC(javoblar.get(2));
            test.setD(javoblar.get(3));
            TestList.add(test);
        }
        inputStream.close();
        tests1.setTests(TestList);
        return tests1;
    }


}
