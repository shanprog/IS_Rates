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


public class BaseTarif_smp extends JFrame {

    private JTable jTable;
    private List<String> columnNamesTmp;
    private List<Integer> moId;

    private MediatorDB mediatorDB;
    private String[] columnNames;
    private Object[][] cells;


    public BaseTarif_smp() {

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
        setTitle("Базовые тарифы - Скорая помощь");
        setVisible(true);
    }

    private void namesColumn() {
        columnNamesTmp.add("Название МО");

        columnNamesTmp.add("Тариф");


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

            List<Double> list = mediatorDB.getBaseTarifSmp(moId.get(i));

            for (int j=1; j<columnNames.length; j++) {
                cells[i][j] = list.get(j - 1);
            }


        }

    }



}
