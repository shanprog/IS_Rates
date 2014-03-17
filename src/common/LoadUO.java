package common;

import net.miginfocom.swing.MigLayout;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class LoadUO {

    private JFileChooser fileChooser;
    private JTextField filePath;

    private java.util.List<JCheckBox> checkboxes;
    MediatorDB mediatorDB;

    private JFrame frame;

    public LoadUO() {

        frame = new JFrame();

        frame.setTitle("Загрузка УО");
        mediatorDB = new MediatorDB();
        createGUI();

        frame.pack();

        Dimension sizeWindow = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(sizeWindow.width / 2 - frame.getSize().width / 2, sizeWindow.height / 2 - frame.getSize().height / 2);

        frame.setResizable(false);
        frame.setVisible(true);
    }


    private void createGUI() {

        JCheckBox stac_24h = new JCheckBox("Стационар", true);
        JCheckBox stac_8h = new JCheckBox("Дневной стационар", true);
        JCheckBox ambul_zab = new JCheckBox("Поликл. заб.", true);
        JCheckBox ambul_neot = new JCheckBox("Поликл. неот.", true);
        JCheckBox ambul_prof = new JCheckBox("Поликл. проф.", true);
        JCheckBox stomat_zab = new JCheckBox("Стомат. заб.", true);
        JCheckBox stomat_neot = new JCheckBox("Стомат. неот.", true);
        JCheckBox stomat_prof = new JCheckBox("Стомат. проф.", true);
        JCheckBox smp = new JCheckBox("СМП", true);

        checkboxes = new ArrayList<JCheckBox>(9);

        checkboxes.add(stac_24h);
        checkboxes.add(stac_8h);
        checkboxes.add(ambul_prof);
        checkboxes.add(ambul_neot);
        checkboxes.add(ambul_zab);
        checkboxes.add(stomat_prof);
        checkboxes.add(stomat_neot);
        checkboxes.add(stomat_zab);
        checkboxes.add(smp);

        filePath = new JTextField(30);

        JButton fileChooseButton = new JButton("Папка");
        fileChooser = new JFileChooser();

        JButton buttonSelAll = new JButton("Выбрать все");
        JButton buttonSelNone = new JButton("Снять все");


        JButton loadButton = new JButton("Загрузить");


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new MigLayout());

        JPanel topPanel = new JPanel(new MigLayout());
        JPanel bottomPanel = new JPanel(new MigLayout());


        for (int i = 0; i < 5; i++)
            topPanel.add(checkboxes.get(i), "cell 0 " + i);

        for (int i = 5; i < checkboxes.size(); i++)
            topPanel.add(checkboxes.get(i), "cell 1 " + (i - 5));
        topPanel.add(new JPanel(), "cell 1 4");

        JPanel selButtonPanel = new JPanel();
        selButtonPanel.setLayout(new MigLayout());

        selButtonPanel.add(buttonSelAll, "sg buttons, gapleft 20, wrap");
        selButtonPanel.add(buttonSelNone, "sg buttons, gapleft 20");

        topPanel.add(selButtonPanel, "cell 2 0, span 1 5");

        topPanel.setBorder(BorderFactory.createTitledBorder("Тип"));

        bottomPanel.add(filePath);
        bottomPanel.add(fileChooseButton, "wrap");
        bottomPanel.add(loadButton, "span 2, align center");

        mainPanel.add(topPanel, "sgx panels, wrap");
        mainPanel.add(bottomPanel, "sgx panels");

        frame.add(mainPanel);

        fileChooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                fileChooser.setDialogTitle("Выбор папки загрузки");
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                int res = fileChooser.showOpenDialog(frame);

                if (res == JFileChooser.APPROVE_OPTION) {
                    filePath.setText(fileChooser.getSelectedFile().getPath());
                }
            }
        });

        buttonSelAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JCheckBox chek : checkboxes) {
                    chek.setSelected(true);
                }
            }
        });

        buttonSelNone.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JCheckBox chek : checkboxes) {
                    chek.setSelected(false);
                }
            }
        });

        class LoadUOinBase extends Thread {
            @Override
            public void run() {

                if (filePath.getText().trim().equals(""))
                    JOptionPane.showMessageDialog(frame, "Путь к папке не может быть пустым");
                else {
                    File myFolder = new File(filePath.getText());
                    File[] files = myFolder.listFiles();

                    for (File file : files) {

                        java.util.List<String> queries;

                        int idMo;

                        try {

                            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(file.getAbsolutePath()));
                            queries = new ArrayList<String>();

                            HSSFWorkbook wb = new HSSFWorkbook(fs);

                            HSSFSheet sheet;
                            HSSFRow row;
                            HSSFCell cell;

                            sheet = wb.getSheetAt(0);
                            row = sheet.getRow(8);
                            cell = row.getCell(0);

                            idMo = mediatorDB.getIdMo(cell.getStringCellValue());

                            for (int i = 0; i < checkboxes.size(); i++) {

                                if (checkboxes.get(i).isSelected()) {

                                    switch (i) {
                                        case 0:
                                            process_24h(wb, sheet, row, cell, idMo, queries);
                                            break;
                                        case 1:
                                            process_8h(wb, sheet, row, cell, idMo, queries);
                                            break;
                                        case 2:
                                            process_ambulProf(wb, sheet, row, cell, idMo, queries);
                                            break;
                                        case 3:
                                            process_ambulNeot(wb, sheet, row, cell, idMo, queries);
                                            break;
                                        case 4:
                                            process_ambulZab(wb, sheet, row, cell, idMo, queries);
                                            break;
                                        case 5:
                                            process_stomatProf(wb, sheet, row, cell, idMo, queries);
                                            break;
                                        case 6:
                                            process_stomatNeot(wb, sheet, row, cell, idMo, queries);
                                            break;
                                        case 7:
                                            process_stomatZab(wb, sheet, row, cell, idMo, queries);
                                            break;
                                        case 8:
                                            process_smp(wb, sheet, row, cell, idMo, queries);
                                            break;

                                    }

                                }

                            }



//                            LOAD DATA LOCAL INFILE "C:/sites/home/aofoms/www/documents/polises/C_sendmailFilespolis.csv"
//                            INTO TABLE polises2
//                            FIELDS TERMINATED BY ';' LINES TERMINATED BY '\n' IGNORE 1 LINES (enp,spol,npol,q);

//                            mediatorDB.setQuries(queries);


                            for (String str : queries) {
                                System.out.println(str);
                            }

                        } catch (IOException ioException) {
                            JOptionPane.showMessageDialog(frame, "Файл не может быть прочитан");
                        }

                        System.out.println();
                    }
                }
            }
        }

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoadUOinBase().start();
            }
        });
    }

    private void process_24h(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, HSSFCell cell, int idMo, java.util.List<String> queries) {

        String ageProfile;
        String nameProfile;

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
    }

    private void process_8h(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, HSSFCell cell, int idMo, java.util.List<String> queries) {

        String ageProfile;
        String nameProfile;

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

    }

    private void process_ambulProf(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, HSSFCell cell, int idMo, java.util.List<String> queries) {

        String ageProfile;
        String nameProfile;

        sheet = wb.getSheetAt(2);

        queries.add("DELETE FROM rasprv_ambulProf WHERE mo = " + idMo);
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
    }

    private void process_ambulNeot(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, HSSFCell cell, int idMo, java.util.List<String> queries) {

        String ageProfile;
        String nameProfile;

        sheet = wb.getSheetAt(3);

        queries.add("DELETE FROM rasprv_ambulNeot WHERE mo = " + idMo);
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
    }

    private void process_ambulZab(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, HSSFCell cell, int idMo, java.util.List<String> queries) {

        String ageProfile;
        String nameProfile;

        sheet = wb.getSheetAt(4);

        queries.add("DELETE FROM rasprv_ambulZab WHERE mo = " + idMo);
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
    }

    private void process_stomatProf(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, HSSFCell cell, int idMo, java.util.List<String> queries) {

        String ageProfile;
        String nameProfile;

        sheet = wb.getSheetAt(5);

        queries.add("DELETE FROM rasprv_stomatProf WHERE mo = " + idMo);
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
    }

    private void process_stomatNeot(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, HSSFCell cell, int idMo, java.util.List<String> queries) {
        String ageProfile;
        String nameProfile;

        sheet = wb.getSheetAt(6);

        queries.add("DELETE FROM rasprv_stomatNeot WHERE mo = " + idMo);
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
    }

    private void process_stomatZab(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, HSSFCell cell, int idMo, java.util.List<String> queries) {
        String ageProfile;
        String nameProfile;

        sheet = wb.getSheetAt(7);
        queries.add("DELETE FROM rasprv_stomatZab WHERE mo = " + idMo);
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
    }

    private void process_smp(HSSFWorkbook wb, HSSFSheet sheet, HSSFRow row, HSSFCell cell, int idMo, java.util.List<String> queries) {

        sheet = wb.getSheetAt(8);
        queries.add("DELETE FROM rasprv_smp WHERE mo = " + idMo);
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

            queries.add("INSERT INTO rasprv_smp VALUES (NULL," + idMo + ", 97, " + dms_I + ", " + sv_I + ", " + dms_II + ", " + sv_II + ", " + dms_III + ", " + sv_III + ", " + dms_IV + ", " + sv_IV + ", "+
                    (dms_I + dms_II + dms_III + dms_IV + sv_I + sv_II + sv_III + sv_IV)+");");
        }
    }


}
