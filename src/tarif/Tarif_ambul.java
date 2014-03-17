package tarif;

import common.MediatorDB;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ShuryginAN
 * Date: 20.12.13
 * Time: 11:03
 */


public class Tarif_ambul extends JFrame {

    private JTable jTable;
    private JTable jTableItog;
    private List<String> columnNamesTmp;
    private List<Integer> moId;

    private MediatorDB mediatorDB;
    private String[] columnNames;
    private String[] columnNamesItog;
    private Object[][] cells;
    private Object[][] cellsItog;

    private int[] idPC = {6,7,8,9,10,11,18,21,27,28,29,30,45,46,49,50,51,52,53,54,59,60,73,74,75,76,77,78,79,80,83,84,85,86,87,88,94,95,96};

    public Tarif_ambul() {

        mediatorDB = new MediatorDB();


        moId = mediatorDB.getIdMos();


        columnNamesItog = new String[]{"Название МО","Итог по профил.","Итог по забол.","Итог по неотл.","Общий итог"};
        cellsItog = new Object[moId.size()+1][columnNamesItog.length];
        cellsItog[moId.size()][0] = "Итого";

        JTabbedPane jTabbedPane = new JTabbedPane();

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
        jTabbedPane.addTab("Неотлож.", new JScrollPane(jTable));







        jTableItog = new JTable(cellsItog,columnNamesItog);
        jTableItog.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        setCellItogSum();
        jTabbedPane.addTab("Итого", new JScrollPane(jTableItog));



        add(jTabbedPane);


        setLocationByPlatform(true);
        setSize(500,400);
        setTitle("Тарифы - поликлиника");
        setVisible(true);
    }

    private void namesColumn() {
        columnNamesTmp.add("Название МО");

        for (int i=0; i < idPC.length; i++) {
            columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]));
        }

        columnNamesTmp.add(mediatorDB.getProfileName(81));
        columnNamesTmp.add(mediatorDB.getProfileName(82));

        columnNamesTmp.add("Итого");
        columnNamesTmp.add("Название МО");

        // список в строковый массив
        columnNames = new String[columnNamesTmp.size()];

        for (int i=0; i<columnNamesTmp.size(); i++) {
            columnNames[i] = columnNamesTmp.get(i);
        }
    }


    private void setCellItogSum() {
        long sum = 0;

        for (int i=0; i<moId.size(); i++) {
            cellsItog[i][0] = mediatorDB.getMoName(moId.get(i));

            for (int j=1; j<columnNamesItog.length-1; j++) {
                sum += Integer.parseInt(cellsItog[i][j].toString());
            }

            cellsItog[i][columnNamesItog.length-1] = sum;

            sum = 0;
        }



        for (int j=1; j<columnNamesItog.length; j++) {

            sum = 0;
            for (int i=0; i < moId.size(); i++)
                sum += Integer.parseInt(cellsItog[i][j].toString());

            cellsItog[moId.size()][j] = sum;
        }
    }



    private void cellsValues(int var) {

        moId = mediatorDB.getIdMos();

        cells = new Object[moId.size()+1][columnNames.length];


        cells[moId.size()][0] = "Итого";
        cells[moId.size()][columnNames.length-1] = "Итого";


        for (int i=0; i < moId.size(); i++) {
            cells[i][0] = mediatorDB.getMoName(moId.get(i));


            List<Integer> list1 = new ArrayList<Integer>();
            List<Integer> list2 = new ArrayList<Integer>();

            if (var == 1) {
                list1 = mediatorDB.getListV_ambul(moId.get(i), "a_offers_prof");
                list1.add(mediatorDB.getListV_ambulUET(moId.get(i), "a_offers_profuet").get(0));
                list1.add(mediatorDB.getListV_ambulUET(moId.get(i), "a_offers_profuet").get(1));
                list2 = mediatorDB.getListTarif_ambul(moId.get(i), "tarifprof");
                list2.add(mediatorDB.getListTarif_ambulUET(moId.get(i), "tarifprof").get(0));
                list2.add(mediatorDB.getListTarif_ambulUET(moId.get(i), "tarifprof").get(1));
            }
            else if (var == 2) {
                list1 = mediatorDB.getListV_ambul(moId.get(i), "a_offers_disnum");
                list1.add(mediatorDB.getListV_ambulUET(moId.get(i), "a_offers_disuet").get(0));
                list1.add(mediatorDB.getListV_ambulUET(moId.get(i), "a_offers_disuet").get(1));
                list2 = mediatorDB.getListTarif_ambul(moId.get(i), "tarifdis");
                list2.add(mediatorDB.getListTarif_ambulUET(moId.get(i), "tarifdis").get(0));
                list2.add(mediatorDB.getListTarif_ambulUET(moId.get(i), "tarifdis").get(1));
            }
            else {
                list1 = mediatorDB.getListV_ambul(moId.get(i), "a_offers_neot");
                list1.add(mediatorDB.getListV_ambulUET(moId.get(i), "a_offers_neotuet").get(0));
                list1.add(mediatorDB.getListV_ambulUET(moId.get(i), "a_offers_neotuet").get(1));
                list2 = mediatorDB.getListTarif_ambul(moId.get(i), "tarifneot");
                list2.add(mediatorDB.getListTarif_ambulUET(moId.get(i), "tarifneot").get(0));
                list2.add(mediatorDB.getListTarif_ambulUET(moId.get(i), "tarifneot").get(1));
            }


            for (int j=1; j<columnNames.length-2; j++) {
                cells[i][j] = list2.get(j-1) * list1.get(j - 1);
            }


            cells[i][columnNames.length-1] = mediatorDB.getMoName(moId.get(i));
        }



        long sum = 0;

        for (int i=0; i<moId.size(); i++) {
            for (int j=1; j<columnNames.length-2; j++) {
                sum += Integer.parseInt(cells[i][j].toString());
            }

            cells[i][columnNames.length-2] = sum;
            cellsItog[i][var] = sum;

            sum = 0;
        }



        for (int j=1; j<columnNames.length-1; j++) {

            sum = 0;
            for (int i=0; i < moId.size(); i++)
                sum += Integer.parseInt(cells[i][j].toString());

            cells[moId.size()][j] = sum;
        }

    }
}























