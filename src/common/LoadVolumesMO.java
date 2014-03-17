package common;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.*;
import java.util.ArrayList;

/**
 * User: ShuryginAN
 * Date: 10.01.14
 * Time: 9:24
 */


public class LoadVolumesMO {

    public LoadVolumesMO() {
        String folder = "C:/tmp/";

        File myFolder = new File(folder);
        File[] files = myFolder.listFiles();

        for (File f: files) {
            System.out.println(f.getName());
            loadXLS(f.getAbsolutePath());
        }
    }

    private void loadXLS(String fileName) {

        MediatorDB mediatorDB;
        java.util.List<String> queries;

        try {
            mediatorDB  = new MediatorDB();
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(fileName));
            queries = new ArrayList<String>();

            HSSFWorkbook wb = new HSSFWorkbook(fs);

            HSSFSheet sheet = null;
            HSSFRow row = null;
            HSSFCell cell = null;

            String ageProfile;
            String nameProfile;

            sheet = wb.getSheetAt(0);
            row = sheet.getRow(8);
            cell = row.getCell(0);

            int idMo = mediatorDB.getIdMo(cell.getStringCellValue());



            // обработка круглосуточного стационара

            sheet = wb.getSheetAt(0);

            queries.add("DELETE FROM rasprv_24h WHERE mo = " + idMo);
            for (int i=17; i<90; i++) {
                if (i == 87)
                    continue;

                row = sheet.getRow(i);

                int dms_I = (int) row.getCell(2).getNumericCellValue();
                int dms_II = (int) row.getCell(5).getNumericCellValue();
                int dms_III = (int) row.getCell(8).getNumericCellValue();
                int dms_IV = (int) row.getCell(11).getNumericCellValue();

                int sv_I = (int) row.getCell(3).getNumericCellValue();
                int sv_II = (int) row.getCell(6).getNumericCellValue();
                int sv_III = (int) row.getCell(9).getNumericCellValue();
                int sv_IV = (int) row.getCell(12).getNumericCellValue();

                cell = row.getCell(1);
                ageProfile = cell.getStringCellValue();
                cell = row.getCell(0);
                if (cell.getStringCellValue().equals("")) {
                    row = sheet.getRow(i-1);
                    cell = row.getCell(0);
                }
                nameProfile = cell.getStringCellValue();

                if ((i == 88) || (i == 89) ) {
                    nameProfile = "Региональный сосудистый центр, первичные сосудистые отделения (" + nameProfile + ")";
                }

                int profileCare = 0;
                profileCare = mediatorDB.getIdPC(nameProfile, ageProfile);

                queries.add("INSERT INTO rasprv_24h VALUES (NULL," + idMo + ", " + profileCare + ", "
                        + dms_I + ", " + sv_I + ", " + dms_II + "," + sv_II + ", " + dms_III + ", " + sv_III + ", " + dms_IV + ", " + sv_IV +
                        ", "
                        + (dms_I + dms_II + dms_III + dms_IV + sv_I + sv_II + sv_III + sv_IV) + ");");


            }

            // обработка дневного стационара
            sheet = wb.getSheetAt(1);

            queries.add("DELETE FROM rasprv_8h WHERE mo = " + idMo);
            for (int i=5; i<27; i++) {

                row = sheet.getRow(i);

                int dms_I = (int) row.getCell(2).getNumericCellValue();
                int dms_II = (int) row.getCell(5).getNumericCellValue();
                int dms_III = (int) row.getCell(8).getNumericCellValue();
                int dms_IV = (int) row.getCell(11).getNumericCellValue();

                int sv_I = (int) row.getCell(3).getNumericCellValue();
                int sv_II = (int) row.getCell(6).getNumericCellValue();
                int sv_III = (int) row.getCell(9).getNumericCellValue();
                int sv_IV = (int) row.getCell(12).getNumericCellValue();

                cell = row.getCell(1);
                ageProfile = cell.getStringCellValue();

                cell = row.getCell(0);
                if (cell.getStringCellValue().equals("")) {
                    row = sheet.getRow(i-1);
                    cell = row.getCell(0);
                }
                nameProfile = cell.getStringCellValue();

                int profileCare = 0;
                profileCare = mediatorDB.getIdPC(nameProfile, ageProfile);


                queries.add("INSERT INTO rasprv_8h VALUES (NULL," + idMo + ", " + profileCare + ", "
                        + dms_I + ", " + sv_I + ", " + dms_II + "," + sv_II + ", " + dms_III + ", " + sv_III + ", " + dms_IV + ", " + sv_IV + ", "
                        + (dms_I + dms_II + dms_III + dms_IV + sv_I + sv_II + sv_III + sv_IV) + ");");

            }

            // обработка амб-полик проф
            sheet = wb.getSheetAt(2);

            for (int i=6; i<46; i++) {

                if (i == 39)
                    continue;

                row = sheet.getRow(i);

                int dms_I = (int) row.getCell(2).getNumericCellValue();
                int dms_II = (int) row.getCell(5).getNumericCellValue();
                int dms_III = (int) row.getCell(8).getNumericCellValue();
                int dms_IV = (int) row.getCell(11).getNumericCellValue();

                int sv_I = (int) row.getCell(3).getNumericCellValue();
                int sv_II = (int) row.getCell(6).getNumericCellValue();
                int sv_III = (int) row.getCell(9).getNumericCellValue();
                int sv_IV = (int) row.getCell(12).getNumericCellValue();


                cell = row.getCell(1);
                ageProfile = cell.getStringCellValue();

                cell = row.getCell(0);
                if (cell.getStringCellValue().equals("")) {
                    row = sheet.getRow(i-1);
                    cell = row.getCell(0);
                }
                nameProfile = cell.getStringCellValue();


                int profileCare = 0;
                profileCare = mediatorDB.getIdPC(nameProfile, ageProfile);

                queries.add("INSERT INTO rasprv_ambulProf VALUES (NULL," + idMo + ", " + profileCare + ", "
                        + dms_I + ", " + sv_I + ", " + dms_II + "," + sv_II + ", " + dms_III + ", " + sv_III + ", " + dms_IV + ", " + sv_IV + ", "+
                        (dms_I + dms_II + dms_III + dms_IV + sv_I + sv_II + sv_III + sv_IV)+");");
            }

            // обработка амб-полик неот
            sheet = wb.getSheetAt(3);

            for (int i=6; i<46; i++) {

                if (i == 39)
                    continue;

                row = sheet.getRow(i);

                int dms_I = (int) row.getCell(2).getNumericCellValue();
                int dms_II = (int) row.getCell(5).getNumericCellValue();
                int dms_III = (int) row.getCell(8).getNumericCellValue();
                int dms_IV = (int) row.getCell(11).getNumericCellValue();

                int sv_I = (int) row.getCell(3).getNumericCellValue();
                int sv_II = (int) row.getCell(6).getNumericCellValue();
                int sv_III = (int) row.getCell(9).getNumericCellValue();
                int sv_IV = (int) row.getCell(12).getNumericCellValue();


                cell = row.getCell(1);
                ageProfile = cell.getStringCellValue();

                cell = row.getCell(0);
                if (cell.getStringCellValue().equals("")) {
                    row = sheet.getRow(i-1);
                    cell = row.getCell(0);
                }
                nameProfile = cell.getStringCellValue();


                int profileCare = 0;
                profileCare = mediatorDB.getIdPC(nameProfile, ageProfile);

                queries.add("INSERT INTO rasprv_ambulNeot VALUES (NULL," + idMo + ", " + profileCare + ", "
                        + dms_I + ", " + sv_I + ", " + dms_II + "," + sv_II + ", " + dms_III + ", " + sv_III + ", " + dms_IV + ", " + sv_IV + ", "+
                        (dms_I + dms_II + dms_III + dms_IV + sv_I + sv_II + sv_III + sv_IV)+");");
            }

            // обработка амб-полик забол
            sheet = wb.getSheetAt(4);

            for (int i=7; i<47; i++) {

                if (i == 40)
                    continue;

                row = sheet.getRow(i);

                int dms_obr_I = (int) row.getCell(2).getNumericCellValue();
                int dms_pos_I = (int) row.getCell(3).getNumericCellValue();
                int dms_obr_II = (int) row.getCell(8).getNumericCellValue();
                int dms_pos_II = (int) row.getCell(9).getNumericCellValue();
                int dms_obr_III = (int) row.getCell(14).getNumericCellValue();
                int dms_pos_III = (int) row.getCell(15).getNumericCellValue();
                int dms_obr_IV = (int) row.getCell(20).getNumericCellValue();
                int dms_pos_IV = (int) row.getCell(21).getNumericCellValue();

                int sv_obr_I = (int) row.getCell(4).getNumericCellValue();
                int sv_pos_I = (int) row.getCell(5).getNumericCellValue();
                int sv_obr_II = (int) row.getCell(10).getNumericCellValue();
                int sv_pos_II = (int) row.getCell(11).getNumericCellValue();
                int sv_obr_III = (int) row.getCell(16).getNumericCellValue();
                int sv_pos_III = (int) row.getCell(17).getNumericCellValue();
                int sv_obr_IV = (int) row.getCell(22).getNumericCellValue();
                int sv_pos_IV = (int) row.getCell(23).getNumericCellValue();


                cell = row.getCell(1);
                ageProfile = cell.getStringCellValue();

                cell = row.getCell(0);
                if (cell.getStringCellValue().equals("")) {
                    row = sheet.getRow(i-1);
                    cell = row.getCell(0);
                }
                nameProfile = cell.getStringCellValue();


                int profileCare = 0;
                profileCare = mediatorDB.getIdPC(nameProfile, ageProfile);

                queries.add("INSERT INTO rasprv_ambulZab VALUES (NULL," + idMo + ", " + profileCare + ", "
                        + dms_obr_I + ", " + dms_pos_I + ", "  + sv_obr_I + ", " + sv_pos_I + ", "
                        + dms_obr_II + ", " + dms_pos_II + ", "  + sv_obr_II + ", " + sv_pos_II + ", "
                        + dms_obr_III + ", " + dms_pos_III + ", "  + sv_obr_III + ", " + sv_pos_III + ", "
                        + dms_obr_IV + ", " + dms_pos_IV + ", "  + sv_obr_IV + ", " + sv_pos_IV
                        + ", " +
                        (dms_obr_I + dms_obr_II + dms_obr_III + dms_obr_IV + sv_obr_I + sv_obr_II + sv_obr_III + sv_obr_IV) + ", " +
                        (dms_pos_I + dms_pos_II + dms_pos_III + dms_pos_IV + sv_pos_I + sv_pos_II + sv_pos_III + sv_pos_IV) + ");");
            }

            // обработка стоматологии проф
            sheet = wb.getSheetAt(5);

            for (int i=7; i<9; i++) {

                row = sheet.getRow(i);

                int dms_pos_I = (int) row.getCell(2).getNumericCellValue();
                int dms_uet_I = (int) row.getCell(3).getNumericCellValue();
                int dms_pos_II = (int) row.getCell(8).getNumericCellValue();
                int dms_uet_II = (int) row.getCell(9).getNumericCellValue();
                int dms_pos_III = (int) row.getCell(14).getNumericCellValue();
                int dms_uet_III = (int) row.getCell(15).getNumericCellValue();
                int dms_pos_IV = (int) row.getCell(20).getNumericCellValue();
                int dms_uet_IV = (int) row.getCell(21).getNumericCellValue();

                int sv_pos_I = (int) row.getCell(4).getNumericCellValue();
                int sv_uet_I = (int) row.getCell(5).getNumericCellValue();
                int sv_pos_II = (int) row.getCell(10).getNumericCellValue();
                int sv_uet_II = (int) row.getCell(11).getNumericCellValue();
                int sv_pos_III = (int) row.getCell(16).getNumericCellValue();
                int sv_uet_III = (int) row.getCell(17).getNumericCellValue();
                int sv_pos_IV = (int) row.getCell(22).getNumericCellValue();
                int sv_uet_IV = (int) row.getCell(23).getNumericCellValue();


                cell = row.getCell(1);
                ageProfile = cell.getStringCellValue();

                cell = row.getCell(0);
                if (cell.getStringCellValue().equals("")) {
                    row = sheet.getRow(i-1);
                    cell = row.getCell(0);
                }
                nameProfile = cell.getStringCellValue();


                int profileCare = 0;
                profileCare = mediatorDB.getIdPC(nameProfile, ageProfile);

                queries.add("INSERT INTO rasprv_stomatProf VALUES (NULL," + idMo + ", " + profileCare + ", "
                        + dms_pos_I + ", " + dms_uet_I + ", "  + sv_pos_I + ", " + sv_uet_I + ", "
                        + dms_pos_II + ", " + dms_uet_II + ", "  + sv_pos_II + ", " + sv_uet_II + ", "
                        + dms_pos_III + ", " + dms_uet_III + ", "  + sv_pos_III + ", " + sv_uet_III + ", "
                        + dms_pos_IV + ", " + dms_uet_IV + ", "  + sv_pos_IV + ", " + sv_uet_IV
                        + ", "+
                        (dms_pos_I + dms_pos_II + dms_pos_III + dms_pos_IV + sv_pos_I + sv_pos_II + sv_pos_III + sv_pos_IV)+", "+
                        (dms_uet_I + dms_uet_II + dms_uet_III + dms_uet_IV + sv_uet_I + sv_uet_II + sv_uet_III + sv_uet_IV)+");");
            }

            // обработка стоматологии неот
            sheet = wb.getSheetAt(6);

            for (int i=7; i<9; i++) {

                row = sheet.getRow(i);

                int dms_pos_I = (int) row.getCell(2).getNumericCellValue();
                int dms_uet_I = (int) row.getCell(3).getNumericCellValue();
                int dms_pos_II = (int) row.getCell(8).getNumericCellValue();
                int dms_uet_II = (int) row.getCell(9).getNumericCellValue();
                int dms_pos_III = (int) row.getCell(14).getNumericCellValue();
                int dms_uet_III = (int) row.getCell(15).getNumericCellValue();
                int dms_pos_IV = (int) row.getCell(20).getNumericCellValue();
                int dms_uet_IV = (int) row.getCell(21).getNumericCellValue();

                int sv_pos_I = (int) row.getCell(4).getNumericCellValue();
                int sv_uet_I = (int) row.getCell(5).getNumericCellValue();
                int sv_pos_II = (int) row.getCell(10).getNumericCellValue();
                int sv_uet_II = (int) row.getCell(11).getNumericCellValue();
                int sv_pos_III = (int) row.getCell(16).getNumericCellValue();
                int sv_uet_III = (int) row.getCell(17).getNumericCellValue();
                int sv_pos_IV = (int) row.getCell(22).getNumericCellValue();
                int sv_uet_IV = (int) row.getCell(23).getNumericCellValue();


                cell = row.getCell(1);
                ageProfile = cell.getStringCellValue();

                cell = row.getCell(0);
                if (cell.getStringCellValue().equals("")) {
                    row = sheet.getRow(i-1);
                    cell = row.getCell(0);
                }
                nameProfile = cell.getStringCellValue();


                int profileCare = 0;
                profileCare = mediatorDB.getIdPC(nameProfile, ageProfile);

                queries.add("INSERT INTO rasprv_stomatNeot VALUES (NULL," + idMo + ", " + profileCare + ", "
                        + dms_pos_I + ", " + dms_uet_I + ", "  + sv_pos_I + ", " + sv_uet_I + ", "
                        + dms_pos_II + ", " + dms_uet_II + ", "  + sv_pos_II + ", " + sv_uet_II + ", "
                        + dms_pos_III + ", " + dms_uet_III + ", "  + sv_pos_III + ", " + sv_uet_III + ", "
                        + dms_pos_IV + ", " + dms_uet_IV + ", "  + sv_pos_IV + ", " + sv_uet_IV
                        + ", "+
                        (dms_pos_I + dms_pos_II + dms_pos_III + dms_pos_IV + sv_pos_I + sv_pos_II + sv_pos_III + sv_pos_IV)+", "+
                        (dms_uet_I + dms_uet_II + dms_uet_III + dms_uet_IV + sv_uet_I + sv_uet_II + sv_uet_III + sv_uet_IV)+");");
            }

            // обработка стоматологии забол
            sheet = wb.getSheetAt(7);

            for (int i=7; i<9; i++) {

                row = sheet.getRow(i);

                int dms_obr_I = (int) row.getCell(2).getNumericCellValue();
                int dms_pos_I = (int) row.getCell(3).getNumericCellValue();
                int dms_uet_I = (int) row.getCell(4).getNumericCellValue();
                int dms_obr_II = (int) row.getCell(11).getNumericCellValue();
                int dms_pos_II = (int) row.getCell(12).getNumericCellValue();
                int dms_uet_II = (int) row.getCell(13).getNumericCellValue();
                int dms_obr_III = (int) row.getCell(20).getNumericCellValue();
                int dms_pos_III = (int) row.getCell(21).getNumericCellValue();
                int dms_uet_III = (int) row.getCell(22).getNumericCellValue();
                int dms_obr_IV = (int) row.getCell(29).getNumericCellValue();
                int dms_pos_IV = (int) row.getCell(30).getNumericCellValue();
                int dms_uet_IV = (int) row.getCell(31).getNumericCellValue();

                int sv_obr_I = (int) row.getCell(5).getNumericCellValue();
                int sv_pos_I = (int) row.getCell(6).getNumericCellValue();
                int sv_uet_I = (int) row.getCell(7).getNumericCellValue();
                int sv_obr_II = (int) row.getCell(14).getNumericCellValue();
                int sv_pos_II = (int) row.getCell(15).getNumericCellValue();
                int sv_uet_II = (int) row.getCell(16).getNumericCellValue();
                int sv_obr_III = (int) row.getCell(23).getNumericCellValue();
                int sv_pos_III = (int) row.getCell(24).getNumericCellValue();
                int sv_uet_III = (int) row.getCell(25).getNumericCellValue();
                int sv_obr_IV = (int) row.getCell(32).getNumericCellValue();
                int sv_pos_IV = (int) row.getCell(33).getNumericCellValue();
                int sv_uet_IV = (int) row.getCell(34).getNumericCellValue();


                cell = row.getCell(1);
                ageProfile = cell.getStringCellValue();

                cell = row.getCell(0);
                if (cell.getStringCellValue().equals("")) {
                    row = sheet.getRow(i-1);
                    cell = row.getCell(0);
                }
                nameProfile = cell.getStringCellValue();


                int profileCare = 0;
                profileCare = mediatorDB.getIdPC(nameProfile, ageProfile);

                queries.add("INSERT INTO rasprv_stomatZab VALUES (NULL," + idMo + ", " + profileCare + ", "
                        + dms_obr_I + ", " + dms_pos_I + ", " + dms_uet_I + ", " + sv_obr_I + ", " + sv_pos_I + ", " + sv_uet_I + ", "
                        + dms_obr_II + ", " + dms_pos_II + ", " + dms_uet_II + ", " + sv_obr_II + ", " + sv_pos_II + ", " + sv_uet_II + ", "
                        + dms_obr_III + ", " + dms_pos_III + ", " + dms_uet_III + ", " + sv_obr_III + ", " + sv_pos_III + ", " + sv_uet_III + ", "
                        + dms_obr_IV + ", " + dms_pos_IV + ", " + dms_uet_IV + ", " + sv_obr_IV + ", " + sv_pos_IV + ", " + sv_uet_IV
                        + ", "+
                        (dms_obr_I + dms_obr_II + dms_obr_III + dms_obr_IV + sv_obr_I + sv_obr_II + sv_obr_III + sv_obr_IV)+", "+
                        (dms_pos_I + dms_pos_II + dms_pos_III + dms_pos_IV + sv_pos_I + sv_pos_II + sv_pos_III + sv_pos_IV)+", "+
                        (dms_uet_I + dms_uet_II + dms_uet_III + dms_uet_IV + sv_uet_I + sv_uet_II + sv_uet_III + sv_uet_IV)+");");
            }

            // обработка СМП
            sheet = wb.getSheetAt(8);

            for (int i=5; i<6; i++) {

                row = sheet.getRow(i);

                int dms_I = (int) row.getCell(2).getNumericCellValue();
                int dms_II = (int) row.getCell(5).getNumericCellValue();
                int dms_III = (int) row.getCell(8).getNumericCellValue();
                int dms_IV = (int) row.getCell(11).getNumericCellValue();

                int sv_I = (int) row.getCell(3).getNumericCellValue();
                int sv_II = (int) row.getCell(6).getNumericCellValue();
                int sv_III = (int) row.getCell(9).getNumericCellValue();
                int sv_IV = (int) row.getCell(12).getNumericCellValue();

                queries.add("INSERT INTO rasprv_smp VALUES (NULL," + idMo + ", '97' , " + dms_I + ", " + sv_I + ", " + dms_II + ", " + sv_II + ", " + dms_III + ", " + sv_III + ", " + dms_IV + ", " + sv_IV + ", "+
                        (dms_I + dms_II + dms_III + dms_IV + sv_I + sv_II + sv_III + sv_IV)+");");
            }



            mediatorDB.setQuries(queries);

            for (String str: queries)
                System.out.println(str);

        }
        catch (FileNotFoundException fnfe) {

        }
        catch (IOException ioe) {

        }
    }

}
