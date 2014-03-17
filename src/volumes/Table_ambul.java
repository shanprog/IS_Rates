package volumes;

import common.MediatorDB;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ShuryginAN
 * Date: 12.12.13
 * Time: 15:36
 */


public class Table_ambul {

    private JTable jTable;
    private List<String> columnNamesTmp;
    private List<Integer> moId;

    private MediatorDB mediatorDB;
    private String[] columnNames;
    private Object[][] cells;

    private int[] idPC = {6,7,8,9,10,11,18,21,27,28,29,30,45,46,49,50,51,52,53,54,59,60,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,94,95,96};

    public Table_ambul() {

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

        columnNamesTmp.add("Итого пос-ий с проф. целью");          // 1
        columnNamesTmp.add("Итого УЕТ проф.");          // 2
        columnNamesTmp.add("Итого пос-ий неот.");          // 3
        columnNamesTmp.add("Итого УЕТ неот.");          // 4
        columnNamesTmp.add("Итого обр-ий забол.");          // 5
        columnNamesTmp.add("Итого пос-ий забол");          // 6
        columnNamesTmp.add("Итого УЕТ забол.");          // 7

        for (int i=0; i < idPC.length; i++) {
            columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]));         // 8 - 48
        }

        columnNamesTmp.add("УЕТ проф. (взрослые)");                                     // 49
        columnNamesTmp.add("УЕТ проф. (дети)");                                     // 50

        for (int i=0; i < idPC.length; i++) {
            columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]));         // 51 - 91
        }

        columnNamesTmp.add("Неот. УЕТ (взрослые)");                                     // 92
        columnNamesTmp.add("Неот. УЕТ (дети)");                                     // 93

        for (int i=0; i < idPC.length; i++) {
            columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]));         // 94 - 134
        }

        for (int i=0; i < idPC.length; i++) {
            columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]));         // 135 - 175
        }

        columnNamesTmp.add("Заб. УЕТ (взрослые)");                                     // 176
        columnNamesTmp.add("Заб. УЕТ (дети)");                                     // 177

        for (int i=1; i < 8; i++) {
            columnNamesTmp.add("Итого " + i);                              // 178 - 184
        }

        columnNamesTmp.add("Внесение изменений 1");                         // 185
        columnNamesTmp.add("Внесение изменений 2");                         // 186
        columnNamesTmp.add("Внесение изменений 3");                         // 187
        columnNamesTmp.add("Внесение изменений 4");                         // 188
        columnNamesTmp.add("Внесение изменений 5");                         // 189
        columnNamesTmp.add("Внесение изменений 6");                         // 190
        columnNamesTmp.add("Внесение изменений 7");                         // 191
        columnNamesTmp.add("Название МО");


        columnNames = new String[columnNamesTmp.size()];

        for (int i=0; i<columnNamesTmp.size(); i++) {
            columnNames[i] = columnNamesTmp.get(i);
        }

    }


    private void cellsValues() {

//        moId = mediatorDB.getIdMos();
//
//        cells = new Object[moId.size()][columnNames.length];
//
//        for (int i=0; i < moId.size(); i++) {
//            for (int j=0; j<columnNames.length; j++) {
//                cells[i][j] = i + "";
//            }
//        }


        moId = mediatorDB.getIdMos();

        cells = new Object[moId.size()+1][columnNames.length];

        cells[moId.size()][0] = "Итого";

        for (int i=0; i < moId.size(); i++) {
            cells[i][0] = mediatorDB.getMoName(moId.get(i));

            List<Integer> list = mediatorDB.getNumAndOfferByVAmbul(moId.get(i));
//
            for (int j=1; j<columnNames.length-8; j++)
                cells[i][j] = list.get(j - 1);

            for (int j = columnNames.length-8; j < columnNames.length; j++)
                cells[i][j] = "";

            cells[i][columnNames.length-1] = mediatorDB.getMoName(moId.get(i));
        }


        int sum = 0;

        for (int j=1; j<8; j++) {

            sum = 0;
            for (int i=0; i < moId.size(); i++)
                sum += Integer.parseInt(cells[i][j].toString());

            cells[moId.size()][j] = sum;
        }

        sum = 0;

        for (int j=columnNames.length-15; j<columnNames.length-8; j++) {

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
