package volumes;

import common.MediatorDB;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ShuryginAN
 * Date: 13.12.13
 * Time: 10:22
 */


public class Table_other {

    private JTable jTable;
    private List<String> columnNamesTmp;
    private List<Integer> moId;

    private MediatorDB mediatorDB;
    private String[] columnNames;
    private Object[][] cells;

    private int[] idPC = {89, 90, 91, 92};

    public Table_other() {

        mediatorDB = new MediatorDB();

        columnNamesTmp = new ArrayList<String>();

        // формирование заголовков таблицы
        namesColumn();

        // формирование данных в таблице
        cellsValues();

        jTable = new JTable(cells, columnNames);
    }


    private void namesColumn() {

        columnNamesTmp.add("Название МО");      // 0

        for (int i=0; i < idPC.length-1; i++) {
            columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]) + "кругл. стац-р");
        }

        for (int i=0; i < idPC.length-1; i++) {
            columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]) + "амбул.");
        }

        columnNamesTmp.add(mediatorDB.getProfileName(92));

        for (int i=0; i < idPC.length-1; i++) {
            columnNamesTmp.add("Изменение для " + mediatorDB.getProfileName(idPC[i]) + " стационар");
        }

        for (int i=0; i < idPC.length-1; i++) {
            columnNamesTmp.add("Изменение для " + mediatorDB.getProfileName(idPC[i]) + " амбулатория");
        }

        columnNamesTmp.add("Изменение для " + mediatorDB.getProfileName(92));


        columnNames = new String[columnNamesTmp.size()];

        for (int i=0; i<columnNamesTmp.size(); i++) {
            columnNames[i] = columnNamesTmp.get(i);
        }

    }

    private void cellsValues() {

        moId = mediatorDB.getIdMos();

        cells = new Object[moId.size()+1][columnNames.length];

        cells[moId.size()][0] = "Итого";

        for (int i=0; i < moId.size(); i++) {
            for (int j=0; j<columnNames.length; j++)  {
                cells[i][j] = i + j + "";
            }
        }

//        List<Integer> list = mediatorDB.getNumAndOfferByVOther(1);

//        for (Integer k: list)
//            System.out.print(k + " ");
//
//        System.out.println();

        for (int i=0; i < moId.size(); i++) {
            cells[i][0] = mediatorDB.getMoName(moId.get(i));

            List<Integer> list = mediatorDB.getNumAndOfferByVOther(moId.get(i));
//
            for (int j=1; j<columnNames.length-7; j++)
                cells[i][j] = list.get(j - 1);

            for (int j = columnNames.length-7; j < columnNames.length; j++)
                cells[i][j] = "";
        }

        int sum = 0;

        for (int j=1; j<8; j++) {

            sum = 0;
            for (int i=0; i< moId.size(); i++)
                sum += Integer.parseInt(cells[i][j].toString());

            cells[moId.size()][j] = sum;
        }
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
