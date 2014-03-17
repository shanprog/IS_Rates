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


public class BaseTarif_8h extends JFrame {

    private JTable jTable;
    private List<String> columnNamesTmp;
    private List<Integer> moId;

    private MediatorDB mediatorDB;
    private String[] columnNames;
    private Object[][] cells;

    private int[] idPC = {10, 11, 18, 21, 27, 28, 29, 30, 47, 48, 49, 50, 51, 52, 53, 54, 62, 63, 73, 74, 75, 76};

    public BaseTarif_8h() {

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
        setTitle("Базовые тарифы - дневн.стац.");
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

    private void cellsValues() {

        moId = mediatorDB.getIdMos();

        cells = new Object[moId.size()][columnNames.length];


        for (int i=0; i < moId.size(); i++) {
            cells[i][0] = mediatorDB.getMoName(moId.get(i));

            List<Integer> list = mediatorDB.getBaseTarif8h(moId.get(i));



            for (int j=1; j<columnNames.length-1; j++) {
                cells[i][j] = list.get(j - 1);
            }

            cells[i][columnNames.length-1] = mediatorDB.getMoName(moId.get(i));

        }




    }



}
