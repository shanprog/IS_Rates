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
 * Time: 16:07
 */


public class PrintVOther extends JFrame {
    private JTable jTable;
    private String[] columnNames;
    private Object[][] cells;

    private MediatorDB mediatorDB;
    private List<Integer> moId;

    public PrintVOther() {
        setTitle("Другое - печать");
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

        setLayout(new BorderLayout());

        add(new JScrollPane(jTable));
        add((printJButton), BorderLayout.SOUTH);




        setLocationByPlatform(true);
        setVisible(true);
    }

    private void createTable() {

        moId = new ArrayList<Integer>();
        columnNames = new String[8];

        columnNames[0] = "Название больницы";
        columnNames[1] = "Гемодиализ стац.";
        columnNames[2] = "Гемодиализ амбул.";
        columnNames[3] = "Перитонеальный диализ взрос. стац.";
        columnNames[4] = "Перитонеальный диализ взрос. абул.";
        columnNames[5] = "Перитонеальный диализ детс. стац.";
        columnNames[6] = "Перитонеальный диализ детс. амбул.";
        columnNames[7] = "Применение (ЭКО)";


        moId = mediatorDB.getIdMos();

        cells = new Object[moId.size()+1][columnNames.length];

        cells[moId.size()][0] = "Итого";

        for (int i=0; i < moId.size(); i++) {
            cells[i][0] = mediatorDB.getMoName(moId.get(i));


            List<Integer> list = mediatorDB.getPrintVOther(moId.get(i));

            for (int j=1; j<columnNames.length; j++)
                cells[i][j] = list.get(j - 1);

//            cells[i][columnNames.length-1] = "";
        }



        int sum = 0;

        for (int j=1; j<7; j++) {

            sum = 0;
            for (int i=0; i< moId.size(); i++)
                sum += Integer.parseInt(cells[i][j].toString());

            cells[moId.size()][j] = sum;
        }



        jTable = new JTable(cells,columnNames);


    }
}
