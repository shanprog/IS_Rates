package volumes;

import common.MediatorDB;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ShuryginAN
 * Date: 12.12.13
 * Time: 9:26
 */


public class Table_8h {

    private JTable jTable;
    private List<String> columnNamesTmp;
    private List<Integer> moId;

    private MediatorDB mediatorDB;
    private String[] columnNames;
    private Object[][] cells;

    private int[] idPC = {10, 11, 18, 21, 27, 28, 29, 30, 47, 48, 49, 50, 51, 52, 53, 54, 62, 63, 73, 74, 75, 76};

    public Table_8h() {

        mediatorDB = new MediatorDB();

        columnNamesTmp = new ArrayList<String>();

        // формирование заголовков таблицы
        namesColumn();

        // формирование данных в таблице
        cellsValues();

        jTable = new JTable(cells, columnNames);
    }




    private void namesColumn() {

        columnNamesTmp.add("Название МО");
        columnNamesTmp.add("Итого пациенто-дней по нормативу");


        for (int i=0; i < idPC.length; i++) {
            columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]));
        }

        columnNamesTmp.add("Итого выбывших больных");

        columnNamesTmp.add("Итого пациенто-дней 2014");

        for (int i=0; i < idPC.length; i++) {
            columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]));
        }

        columnNamesTmp.add("Итого выбывших больных 2014");

        columnNamesTmp.add("Внесение изменений");
        columnNamesTmp.add("Название МО");

        columnNames = new String[columnNamesTmp.size()];

        for (int i=0; i<columnNamesTmp.size(); i++) {
            columnNames[i] = columnNamesTmp.get(i);
        }
    }

    private void cellsValues() {

//        cells = new Object[1][columnNames.length];
//
//        for (int i=0; i< columnNames.length; i++) {
//            cells[0][i] = i + "";
//        }

        moId = mediatorDB.getIdMos();

        cells = new Object[moId.size()+1][columnNames.length];


        cells[moId.size()][0] = "Итого";

        for (int i=0; i < moId.size(); i++) {
            cells[i][0] = mediatorDB.getMoName(moId.get(i));

            List<Integer> list = mediatorDB.getNumAndOfferByV8h(moId.get(i));
//
            for (int j=1; j<columnNames.length-2; j++)
                cells[i][j] = list.get(j - 1);

            cells[i][columnNames.length-2] = "";
            cells[i][columnNames.length-1] = mediatorDB.getMoName(moId.get(i));
        }


        int sum = 0;

        for (int i=0; i< moId.size(); i++)
            sum += Integer.parseInt(cells[i][1].toString());

        cells[moId.size()][1] = sum;

        sum = 0;
        for (int j=24; j<26; j++) {

            sum = 0;
            for (int i=0; i< moId.size(); i++)
                sum += Integer.parseInt(cells[i][j].toString());

            cells[moId.size()][j] = sum;
        }

        sum = 0;

        for (int i=0; i< moId.size(); i++)
            sum += Integer.parseInt(cells[i][columnNames.length-3].toString());

        cells[moId.size()][columnNames.length-3] = sum;
    }

    public List<Integer> getMoId() {
        return moId;
    }

    public JTable getjTable() {
        return jTable;
    }

    public int[] getIdPC() {
        return idPC;
    }
}
