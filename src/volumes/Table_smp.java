package volumes;

import common.MediatorDB;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ShuryginAN
 * Date: 12.12.13
 * Time: 14:13
 */


public class Table_smp {

    private JTable jTable;
    private List<String> columnNamesTmp;
    private List<Integer> moId;

    private MediatorDB mediatorDB;
    private String[] columnNames;
    private Object[][] cells;


    public JTable getjTable() {
        return jTable;
    }

    public Table_smp() {

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
        columnNamesTmp.add("Численность прикр. нас.");
        columnNamesTmp.add("Число вызовов по норм.");
        columnNamesTmp.add("Предложение");
        columnNamesTmp.add("Внесение изменений");

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

            List<Integer> list = mediatorDB.getNumAndOfferByVSmp(moId.get(i));

            for (int j=1; j<columnNames.length-1; j++)
                cells[i][j] = list.get(j - 1);

            cells[i][columnNames.length-1] = "";
        }


        int sum = 0;

        for (int j=2; j<4; j++) {

            sum = 0;
            for (int i=0; i< moId.size(); i++)
                sum += Integer.parseInt(cells[i][j].toString());

            cells[moId.size()][j] = sum;
        }
    }

    public List<Integer> getMoId() {
        return moId;
    }
}
