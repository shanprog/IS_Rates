/**
 * User: ShuryginAN
 * Date: 27.11.13
 * Time: 16:12
 */

package volumes;



import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.extractor.ExcelExtractor;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


public class AdapterXLS {

    private POIFSFileSystem fs;

    public AdapterXLS(String filename) {
        try {
            fs = new POIFSFileSystem(new FileInputStream(filename));
        }
        catch (FileNotFoundException fnfe) {

        }
        catch (IOException ioe) {

        }

    }

    public List<String> getQueries() {

        List<String> queries = new ArrayList<String>();

        try{
            HSSFWorkbook wb = new HSSFWorkbook(fs);

            HSSFSheet sheet = wb.getSheetAt(0);
            HSSFRow row = null;
            HSSFCell cell = null;

            row = sheet.getRow(16);
            cell = row.getCell(2);


            queries.add("INSERT INTO numberAttached VALUES (NULL, x, " + (int)cell.getNumericCellValue() + ", 0,0,0,0,0");
        }
        catch (IOException ioe) {

        }

        return queries;

    }

    public String getTextFromTable() throws Exception{
        InputStream in = new FileInputStream("C:/tmp/1.xls");
        // Внимание InputStream будет закрыт
        // Если нужно не закрывающий см. JavaDoc по POIFSFileSystem :  http://goo.gl/1Auu7
        HSSFWorkbook wb = new HSSFWorkbook(in);

        ExcelExtractor extractor = new ExcelExtractor(wb);
        extractor.setFormulasNotResults(false); // Считать формулы
        extractor.setIncludeSheetNames(true);
        String text = extractor.getText();

        return text;
    }



    public void textov() throws Exception{

//        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("input.xls"));
        HSSFWorkbook wb = new HSSFWorkbook(fs);
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow row = null;
        HSSFCell cell = null;
        int rows = sheet.getPhysicalNumberOfRows(); //получаем актуальное число строк
        for(int r = 0; r < rows; r++) {
            row = sheet.getRow(r); //берем строку
            if(row != null) {
                cell = row.getCell(0); //берем первую ячейку
                if(cell != null) {
                    cell.setCellValue("Modified " + r); //задаем значение ячейки
                }
            }
        }
        FileOutputStream fileOut = new FileOutputStream("output.xls");
        wb.write(fileOut);
        fileOut.close();
    }

}
