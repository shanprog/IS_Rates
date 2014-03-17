package tarif;

import common.MediatorDB;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ShuryginAN
 * Date: 20.12.13
 * Time: 9:28
 */


public class BaseTarif_ambul extends JFrame {

    private JTable jTable;
    private List<String> columnNamesTmp;
    private List<Integer> moId;

    private MediatorDB mediatorDB;
    private String[] columnNames;
    private Object[][] cells;

    private int[] idPC = {6,7,8,9,10,11,18,21,27,28,29,30,45,46,49,50,51,52,53,54,59,60,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,94,95,96};

    public BaseTarif_ambul() {

        mediatorDB = new MediatorDB();


        JTabbedPane jTabbedPane = new JTabbedPane();

//        JPanel jPanelProf = new JPanel();
//        JPanel jPanelDis = new JPanel();
//        JPanel jPanelNeotl = new JPanel();





        columnNamesTmp = new ArrayList<String>();

        // формирование заголовков таблицы
        namesColumn();

        // формирование данных в таблице
        cellsValues(1);

        jTable = new JTable(cells, columnNames);
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTabbedPane.addTab("Проф.", new JScrollPane(jTable));

        // формирование данных в таблице
        cellsValues(2);

        jTable = new JTable(cells, columnNames);
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTabbedPane.addTab("Заболевания.", new JScrollPane(jTable));

        // формирование данных в таблице
        cellsValues(3);

        jTable = new JTable(cells, columnNames);
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        jTabbedPane.addTab("Неотлож..", new JScrollPane(jTable));

        add(jTabbedPane);

        setLocationByPlatform(true);
        setSize(500,400);
        setTitle("Базовые тарифы - поликлин.-амбул.");
        setVisible(true);
    }

    private void namesColumn() {
        columnNamesTmp.add("Название МО");

        for (int i=0; i < idPC.length; i++) {
            columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]));
        }

        columnNamesTmp.add("Название МО");

        // список в строковый массив
        columnNames = new String[columnNamesTmp.size()];

        for (int i=0; i<columnNamesTmp.size(); i++) {
            columnNames[i] = columnNamesTmp.get(i);
        }
    }

    private void cellsValues(int var) {

        moId = mediatorDB.getIdMos();

        cells = new Object[moId.size()][columnNames.length];


        for (int i=0; i < moId.size(); i++) {
            cells[i][0] = mediatorDB.getMoName(moId.get(i));

            List<Integer> list = new ArrayList<Integer>();

            if (var == 1)
                list = mediatorDB.getBaseTarifAmbul(moId.get(i), "tarifprof");
            else if (var == 2)
                list = mediatorDB.getBaseTarifAmbul(moId.get(i), "tarifdis");
            else if (var == 3)
                list = mediatorDB.getBaseTarifAmbul(moId.get(i), "tarifneot");

            for (int j=1; j<columnNames.length-1; j++) {
                cells[i][j] = list.get(j - 1);
            }

            cells[i][columnNames.length-1] = mediatorDB.getMoName(moId.get(i));
        }

    }



}
