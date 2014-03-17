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


public class Tarif_8h extends JFrame {

    private JTable jTable;
    private List<String> columnNamesTmp;
    private List<Integer> moId;

    private MediatorDB mediatorDB;
    private String[] columnNames;
    private Object[][] cells;

    private int[] idPC = {10, 11, 18, 21, 27, 28, 29, 30, 47, 48, 49, 50, 51, 52, 53, 54, 62, 63, 73, 74, 75, 76};

    public Tarif_8h() {

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
        setTitle("Тарифы - днев.стац.");
        setVisible(true);
    }

    private void namesColumn() {
        columnNamesTmp.add("Название МО");

        for (int i=0; i < idPC.length; i++) {
            columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]));
        }

        columnNamesTmp.add("Итого");

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
        cells[moId.size()][columnNames.length-1] = "Итого";

        for (int i=0; i < moId.size(); i++) {
            cells[i][0] = mediatorDB.getMoName(moId.get(i));

            List<Integer> list1 = mediatorDB.getListV_8h(moId.get(i));
            List<Integer> list2 = mediatorDB.getListTarif_8h(moId.get(i));


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























