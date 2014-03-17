package vPrint;

import common.MediatorDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ShuryginAN
 * Date: 16.12.13
 * Time: 12:15
 */


public class PrintV8h extends JFrame {

    private JTable jTable;
    private String[] columnNames;
    private Object[][] cells;

    private MediatorDB mediatorDB;
    private List<Integer> moId;

    public PrintV8h() {
        setTitle("Дневной стационар - печать");
        setSize(300, 200);

        mediatorDB = new MediatorDB();

        createTable();

        JButton printJButton = new JButton("Печать");
        printJButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    jTable.print();
                }
                catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        });

        jTable.getColumnModel().getColumn(0).setWidth(300);

        setLayout(new BorderLayout());

        add(new JScrollPane(jTable));
        add((printJButton), BorderLayout.SOUTH);

        setLocationByPlatform(true);
        setVisible(true);

    }

    private void createTable() {
        moId = new ArrayList<Integer>();
        columnNames = new String[3];

        columnNames[0] = "Название больницы";
        columnNames[1] = "Выбывшие больные";
        columnNames[2] = "Предложения";


        moId = mediatorDB.getIdMos();

        cells = new Object[moId.size()+1][columnNames.length];

        cells[moId.size()][0] = "Итого";

        for (int i=0; i < moId.size(); i++) {
            cells[i][0] = mediatorDB.getMoName(moId.get(i));


            List<Integer> list = mediatorDB.getPrintV8h(moId.get(i));

            for (int j=1; j<columnNames.length; j++)
                cells[i][j] = list.get(j - 1);

//            cells[i][columnNames.length-1] = "";
        }

        int sum = 0;

        for (int j=1; j<3; j++) {

            sum = 0;
            for (int i=0; i< moId.size(); i++)
                sum += Integer.parseInt(cells[i][j].toString());

            cells[moId.size()][j] = sum;
        }



        jTable = new JTable(cells,columnNames);
    }
}
