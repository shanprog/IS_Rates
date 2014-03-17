package volumes;

import common.MediatorDB;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ShuryginAN
 * Date: 09.12.13
 * Time: 9:20
 */


public class Table_24h {

    private JTable jTable;
    private List<String> columnNamesTmp;
    private List<Integer> moId;

    private MediatorDB mediatorDB;
    private String[] columnNames;
    private Object[][] cells;

    public Table_24h() {

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

//        for (int i=1; i < 73; i++) {
//            columnNamesTmp.add(mediatorDB.getProfileName(i));
//        }
//
//        columnNamesTmp.add("Итого по профилям");
//        columnNamesTmp.add("Итого Ожидаемое за 2013 кол-во госп-ий в обл. МО");
//        columnNamesTmp.add("Итого Ожидаемое за 2013 кол-во госп-ий в межмуниц. Центры");
//        columnNamesTmp.add("Кол-во госп-ий на 2014");
//        columnNamesTmp.add("Итого Число к/дней на 2014");

        for (int i=1; i < 73; i++) {
            columnNamesTmp.add(mediatorDB.getProfileName(i));
        }

        columnNamesTmp.add("Итого кол-во госп");

        columnNamesTmp.add("Внесение изменений");
        columnNamesTmp.add("Название МО");

        // список в строковый массив
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
            cells[i][0] = mediatorDB.getMoName(moId.get(i));

            List<Integer> list = mediatorDB.getNumAndOfferByV24h(moId.get(i));

            for (int j=1; j<columnNames.length-2; j++)
                cells[i][j] = list.get(j - 1);

            cells[i][columnNames.length-2] = "";

            cells[i][columnNames.length-1] = mediatorDB.getMoName(moId.get(i));
        }



        int sum = 0;

//        for (int j=73; j<78; j++) {
//        for (int j=73; j<78; j++) {
//
//            sum = 0;
//            for (int i=0; i< moId.size(); i++)
//                sum += Integer.parseInt(cells[i][j].toString());
//
//            cells[moId.size()][j] = sum;
//        }
//
//        sum = 0;

        for (int i=0; i < moId.size(); i++)
            sum += Integer.parseInt(cells[i][columnNames.length-3].toString());

        cells[moId.size()][columnNames.length-3] = sum;


    }

    public List<Integer> getMoId() {
        return moId;
    }

    public JTable getjTable() {
        return jTable;
    }

}
