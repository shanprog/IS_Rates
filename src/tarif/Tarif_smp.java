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


public class Tarif_smp extends JFrame {

    private JTable jTable;
    private List<String> columnNamesTmp;
    private List<Integer> moId;

    private MediatorDB mediatorDB;
    private String[] columnNames;
    private Object[][] cells;

    public Tarif_smp() {

        mediatorDB = new MediatorDB();

        columnNamesTmp = new ArrayList<String>();

        // формирование заголовков таблицы
        namesColumn();

        // формирование данных в таблице
        cellsValues();

        jTable = new JTable(cells, columnNames);
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        add(new JScrollPane(jTable));

        setLocationByPlatform(true);
        setSize(500,400);
        setTitle("Тарифы - скорая помощь");
        setVisible(true);
    }

    private void namesColumn() {
        columnNamesTmp.add("Название МО");
        columnNamesTmp.add("Скорая помощь");
//        columnNamesTmp.add("Итого");


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

            List<Integer> list1 = mediatorDB.getListV_smp(moId.get(i));
            List<Double> list2 = mediatorDB.getListTarif_smp(moId.get(i));


            for (int j=1; j<columnNames.length; j++) {
                cells[i][j] = Math.round(list2.get(j-1) * list1.get(j - 1));
            }

        }





//        for (int i=0; i<moId.size(); i++) {
//            for (int j=1; j<columnNames.length-1; j++) {
//                sum += Integer.parseInt(cells[i][j].toString());
//            }
//
//            cells[i][columnNames.length-1] = sum;
//
//            sum = 0;
//        }

        long sum = 0;

//        for (int j=1; j<columnNames.length; j++) {

//            sum = 0;
            for (int i=0; i < moId.size(); i++)
                sum += Integer.parseInt(cells[i][1].toString());

            cells[moId.size()][1] = sum;
//        }

    }
}























