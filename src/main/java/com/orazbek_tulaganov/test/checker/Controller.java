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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.*;

@RestController
public class Controller {

    static ArrayList<Test> TestList;
    static Set<Integer> got;
    public static final Random random = new Random();

    @GetMapping("/{name}")
    public static Tests main(@PathVariable String name) throws IOException {
        Tests tests1 = new Tests();
        TestList = new ArrayList<>();
        got = new HashSet<>();
        InputStream inputStream;
        ClassPathResource resource;
        if(name.equals("ok"))resource = new ClassPathResource("/okresult.xlsx");
        else if(name.equals("web"))resource = new ClassPathResource("/web.xlsx");
        else if(name.equals("algo"))resource = new ClassPathResource("/algo.xlsx");
        else if(name.equals("proginjinir"))resource = new ClassPathResource("/proginjinir.xlsx");
        else resource = new ClassPathResource("/test.xlsx");
        try {
            inputStream = resource.getInputStream();
        } catch (FileNotFoundException e) {
            return null;
        }
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        XSSFSheet sheet = workbook.getSheetAt(0);

        int count = sheet.getLastRowNum();
        count++;

        if(count > 0)while (got.size()<25){
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
            CellType cellType = cell.getCellType();
            switch (cellType){
                case NUMERIC:
                    test.setQuestion(String.valueOf(cell.getNumericCellValue()));
                    break;
                case STRING:
                    test.setQuestion(cell.getStringCellValue());
                    break;
                case BOOLEAN:
                    test.setQuestion(String.valueOf(cell.getBooleanCellValue()));
                    break;
                case _NONE:
                    test.setQuestion("");
                    break;
                default:
                    test.setQuestion(String.valueOf(i));
            }



            cell = cellIterator.next();

            test.setRight(String.valueOf((char) ('A' + rightVariant)));

            cellType = cell.getCellType();
            switch (cellType){
                case NUMERIC:
                    javoblar.set(rightVariant, String.valueOf(cell.getNumericCellValue()));
                    break;
                case STRING:
                    javoblar.set(rightVariant, cell.getStringCellValue());
                    break;
                case BOOLEAN:
                    javoblar.set(rightVariant,String.valueOf(cell.getBooleanCellValue()));
                    break;
                case _NONE:
                    javoblar.set(rightVariant,"");
                    break;
            }

            for (int j = 0; j < javoblar.size(); j++) {
                if (j != rightVariant) {
                    cell = cellIterator.next();cellType = cell.getCellType();
                    switch (cellType){
                        case NUMERIC:
                            javoblar.set(j, String.valueOf(cell.getNumericCellValue()));
                            break;
                        case STRING:
                            javoblar.set(j, cell.getStringCellValue());
                            break;
                        case BOOLEAN:
                            javoblar.set(j,String.valueOf(cell.getBooleanCellValue()));
                            break;
                        case _NONE:
                            javoblar.set(j,"");
                            break;
                    }
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
