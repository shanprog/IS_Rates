 /**
 * User: ShuryginAN
 * Date: 27.11.13
 * Time: 16:03
 */
package volumes;

import common.MediatorDB;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;



 public class LoadFileWindow extends JFrame {

    private JTextField folderName;
    private JTextArea textArea;
    String folder;

    public LoadFileWindow() {

        setTitle("Загрузка файлов");
        setSize(300, 200);

        setLayout(new BorderLayout());

        folderName = new JTextField();
        textArea = new JTextArea();

        add(folderName, BorderLayout.NORTH);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        folderName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                folder = folderName.getText();


                File myFolder = new File(folder);
                File[] files = myFolder.listFiles();

//                File tmp = files[0];

//                loadXLS(tmp.getAbsolutePath());

                for (File f: files) {
                    System.out.println(f.getName());
                    loadXLS(f.getAbsolutePath());
                }

                textArea.append("Файлы обработаны");

            }
        });


        //setDefaultCloseOperation(this.EXIT_ON_CLOSE);
        setLocationByPlatform(true);
        setVisible(true);
    }

    private void prin(String folder) {

        AdapterXLS adapterXLS = new AdapterXLS(folder);

        java.util.List<String> list = adapterXLS.getQueries();

        for (String str: list) {
            textArea.append(str + "\n");
        }


//        File myFolder = new File(folder);
//        File[] files = myFolder.listFiles();
//
//        for (File f : files) {
//            textArea.append(f.getName() + "\n");
//        }
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

            sheet = wb.getSheetAt(0);
            row = sheet.getRow(8);
            cell = row.getCell(0);


            int idMo = mediatorDB.getIdMo(cell.getStringCellValue());

            String a1, a2, a3, a4;

            sheet = wb.getSheetAt(0);
            row = sheet.getRow(16);
            cell = row.getCell(2);

            a1 = (int) cell.getNumericCellValue() + "";

            sheet = wb.getSheetAt(1);
            row = sheet.getRow(5);
            cell = row.getCell(2);

            a2 = (int) cell.getNumericCellValue() + "";

            sheet = wb.getSheetAt(2);
            row = sheet.getRow(6);
            cell = row.getCell(2);

            a3 = (int) cell.getNumericCellValue() + "";

            sheet = wb.getSheetAt(3);
            row = sheet.getRow(5);
            cell = row.getCell(2);

            a4 = (int) cell.getNumericCellValue() + "";

            queries.add("INSERT INTO numberAttached VALUES (NULL, "+ idMo +", " + a1 + ", "+ a2 +", "+ a3 +", "+ a4 +", DATE(NOW()));");

//            SELECT profilescare.id_pc FROM profilescare, age, listprofiles
//            WHERE
//            age.id_age = profilescare.age AND
//            listprofiles.id_profile = profilescare.name AND
//            listprofiles.name = 'Эндокринология' AND
//            age.name = 'Дети';

            String ageProfile;
            String nameProfile;




            // обработка круглосуточного стационара
            sheet = wb.getSheetAt(0);

            for (int i=18; i<91; i++) {

                if (i == 88)
                    continue;

                row = sheet.getRow(i);

                int numNorm = (int) row.getCell(4).getNumericCellValue();
                int numRegcentr = (int) row.getCell(5).getNumericCellValue();
                int numIntcentr = (int) row.getCell(6).getNumericCellValue();
                int kDays = (int) row.getCell(8).getNumericCellValue();
                int offerNums = (int) row.getCell(9).getNumericCellValue();
                int offerKDays = (int) row.getCell(10).getNumericCellValue();

                cell = row.getCell(1);
                ageProfile = cell.getStringCellValue();
                cell = row.getCell(0);
                if (cell.getStringCellValue().equals("")) {
                    row = sheet.getRow(i-1);
                    cell = row.getCell(0);
                }
                nameProfile = cell.getStringCellValue();

                if ((i == 89) || (i == 90) ) {
                    nameProfile = "Региональный сосудистый центр, первичные сосудистые отделения (" + nameProfile + ")";
                }

                int profileCare = 0;
                profileCare = mediatorDB.getIdPC(nameProfile, ageProfile);

                queries.add("INSERT INTO v_24hours VALUES (NULL," + idMo + ", " + profileCare + ", "
                        + numNorm + ", " + numRegcentr + ", " + numIntcentr + ", " +kDays + ", " + offerNums + ", " + offerKDays  + ", DATE(NOW()), " + offerNums + ", " + offerKDays + ");");

//                System.out.println(profileCare + "\t" + nameProfile + "\t" + ageProfile + "\t" + numNorm);
//                System.out.println("v_24h");
            }

            // обработка дневного стационара
            sheet = wb.getSheetAt(1);

            for (int i=7; i<29; i++) {

                row = sheet.getRow(i);

                int numNorm = (int) row.getCell(4).getNumericCellValue();
                int numOut = (int) row.getCell(5).getNumericCellValue();
                int offersNum = (int) row.getCell(6).getNumericCellValue();
                int offersOut = (int) row.getCell(7).getNumericCellValue();

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

                queries.add("INSERT INTO v_8hours VALUES (NULL, " + idMo + ", " + profileCare + ", " + numNorm + ", " + numOut + ", " + offersNum + ", " + offersOut + ", DATE(NOW()), " + offersNum + ", " + offersOut + ");");

//                System.out.println(profileCare + "\t" + nameProfile + "\t" + ageProfile + "\t" + offersNum);
//                INSERT INTO `is_rates`.`v_8hours` (`id_v8h`, `mo_name`, `profile_care`, `num_norm`, `num_out`, `offers_nums`, `offers_out`, `ins_date`, `actuality`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
//                System.out.println("v_8h");
            }

            ///////////////////////////////////////////////////

            // обработка амб-полик
            sheet = wb.getSheetAt(2);

            for (int i=8; i<50; i++) {

                row = sheet.getRow(i);

                int numProf = (int) row.getCell(4).getNumericCellValue();
                int numNeot = (int) row.getCell(7).getNumericCellValue();
                int numDis = (int) row.getCell(10).getNumericCellValue();
                int numDisNum = (int) row.getCell(11).getNumericCellValue();

                String numProfUet = "NULL";
                String numNeotUet = "NULL";
                String numDisUet = "NULL";
                String offersDisUet = "NULL";
                String offersProfUet = "NULL";
                String offersNeotUet = "NULL";


                int offersProf = (int) row.getCell(13).getNumericCellValue();
                int offersNeot = (int) row.getCell(15).getNumericCellValue();
                int offersDis = (int) row.getCell(17).getNumericCellValue();
                int offersDisNum = (int) row.getCell(18).getNumericCellValue();

                if ((i == 41) || (i == 42))  {
                    numProfUet = String.valueOf((int) row.getCell(5).getNumericCellValue());
                    numNeotUet = String.valueOf((int) row.getCell(8).getNumericCellValue());
                    numDisUet = String.valueOf((int) row.getCell(12).getNumericCellValue());

                    offersProfUet = String.valueOf((int) row.getCell(14).getNumericCellValue());
                    offersNeotUet =  String.valueOf((int) row.getCell(16).getNumericCellValue());
                    offersDisUet = String.valueOf((int) row.getCell(19).getNumericCellValue());
                }
//                System.out.println("---------\n" + offersNeotUet + "\n--------------------");


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

                queries.add("INSERT INTO v_ambul VALUES (NULL, " + idMo + ", " + profileCare + ", " + numProf + ", " + numProfUet + ", " + numNeot + ", " + numNeotUet + ", " + numDis + ", " + numDisNum + ", " + numDisUet + ", " + offersProf + ", " + offersProfUet + ", " + offersNeot + ", " + offersNeotUet + ", " + offersDis + ", " + offersDisNum + ", " + offersDisUet + ", DATE(NOW()), " + offersProf + ", " + offersProfUet + ", " + offersNeot + ", " + offersNeotUet + ", " + offersDis + ", " + offersDisNum + ", " + offersDisUet + ");");
//                System.out.println(profileCare + "\t" + nameProfile + "\t" + ageProfile + "\t" + offersDis);
//                System.out.println("v_ambl");
            }


            //////////////////////////////////////////////////////////


            // обработка скорой медпомощи
            sheet = wb.getSheetAt(3);
            row = sheet.getRow(5);
            int offersSMP = (int) row.getCell(5).getNumericCellValue();

            queries.add("INSERT INTO v_smc VALUES (NULL, " + idMo + ", " + offersSMP + ", DATE(NOW()), " + offersSMP + ");");

//            System.out.println("v_smp");


            // обработка прочих

            sheet = wb.getSheetAt(4);

            for (int i=4; i<9; i++) {

                if (i == 7)
                    continue;

                row = sheet.getRow(i);

                String num24h = "NULL";
                String numAmbul = "NULL";
                String num8h = "NULL";

                if (i != 8) {
                    num24h = String.valueOf((int) row.getCell(2).getNumericCellValue());
                    numAmbul = String.valueOf((int) row.getCell(3).getNumericCellValue());
                }
                else {
                    num8h = String.valueOf((int) row.getCell(4).getNumericCellValue());
                }

                cell = row.getCell(1);
                ageProfile = cell.getStringCellValue();

                if (ageProfile.equals("")){
                    ageProfile = "Другое";
                }

                cell = row.getCell(0);
                if (cell.getStringCellValue().equals("")) {
                    row = sheet.getRow(i-1);
                    cell = row.getCell(0);
                }
                nameProfile = cell.getStringCellValue();

                int profileCare = 0;
                profileCare = mediatorDB.getIdPC(nameProfile, ageProfile);

                queries.add("INSERT INTO v_other VALUES (NULL, " + idMo + ", " + profileCare + ", " + num24h + ", " + numAmbul + ", " + num8h + ", DATE(NOW()), " + num24h + ", " + numAmbul + ", " + num8h + ");");

//                System.out.println(profileCare + "\t" + nameProfile + "\t" + ageProfile + "\t" + offersNum);
//                INSERT INTO `is_rates`.`v_other` (`id_vother`, `mo_name`, `profile_care`, `num_24h`, `num_ambul`, `num_8h`, `ins_date`, `actuality`) VALUES (NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
//                System.out.println("v_other");
            }


//            INSERT INTO `is_rates`.`v_smc` (`id_vsmc`, `mo_name`, `offers`, `ins_date`, `actuality`) VALUES (NULL, NULL, NULL, NULL, NULL);

            mediatorDB.setQuries(queries);

//            System.out.println("End");

            textArea.append("Обработанный файл: " + fileName + "\n");
//            for (String str: queries) {
//                textArea.append(str + "\n");
//            }


        }
        catch (FileNotFoundException fnfe) {

        }
        catch (IOException ioe) {

        }


    }


     // C:\tmp\predl\2014_280001.xls

}
