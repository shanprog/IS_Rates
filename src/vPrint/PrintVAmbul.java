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
 * Time: 14:40
 */


public class PrintVAmbul extends JFrame {

    private JTable jTable;
    private String[] columnNames;
    private Object[][] cells;

    private MediatorDB mediatorDB;
    private List<Integer> moId;

    public PrintVAmbul() {
        setTitle("В амбулаторных условиях - печать");
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
        columnNames = new String[15];

        columnNames[0] = "Название больницы";
        columnNames[1] = "Кол-во посещений профилактика";
        columnNames[2] = "Кол-во УЕТ профилактика";
        columnNames[3] = "Кол-во посещений неотлож.";
        columnNames[4] = "Кол-во УЕТ неотлож.";
        columnNames[5] = "Кол-во обращений забол.";
        columnNames[6] = "Кол-во посещений забол.";
        columnNames[7] = "Кол-во УЕТ забол.";
        columnNames[8] = "Предложения посещения проф.";
        columnNames[9] = "Предложения УЕТ проф.";
        columnNames[10] = "Предложения посещений неотл.";
        columnNames[11] = "Предложения УЕТ неотл.";
        columnNames[12] = "Предложения обращений заб.";
        columnNames[13] = "Предложения посещений заб.";
        columnNames[14] = "Предложения УЕТ заб.";

        moId = mediatorDB.getIdMos();

        cells = new Object[moId.size()+1][columnNames.length];

        cells[moId.size()][0] = "Итого";

        for (int i=0; i < moId.size(); i++) {
            cells[i][0] = mediatorDB.getMoName(moId.get(i));


            List<Integer> list = mediatorDB.getPrintVAmbul(moId.get(i));

            for (int j=1; j<columnNames.length; j++)
                cells[i][j] = list.get(j - 1);

//            cells[i][columnNames.length-1] = "";
        }



        int sum = 0;

        for (int j=1; j<15; j++) {

            sum = 0;
            for (int i=0; i< moId.size(); i++)
                sum += Integer.parseInt(cells[i][j].toString());

            cells[moId.size()][j] = sum;
        }



        jTable = new JTable(cells,columnNames);


    }
}
