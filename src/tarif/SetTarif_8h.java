package tarif;

import common.MediatorDB;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ShuryginAN
 * Date: 19.12.13
 * Time: 10:31
 */


public class SetTarif_8h extends JFrame {

    private JList<String> jListMoNames;
    private JTable tableTarif;
    private List<Integer> moId;
    private MediatorDB mediatorDB;

    private JButton refreshButton;
    private JButton saveButton;

    private int idSelectMo = 3;

    private DefaultTableModel dtm = new DefaultTableModel();
    private JTextField kTextField;

    private List<Integer> changedRowsIdPC = new ArrayList<Integer>();
    private List<Integer> changedRows = new ArrayList<Integer>();

    private int[] idPC = {10, 11, 18, 21, 27, 28, 29, 30, 47, 48, 49, 50, 51, 52, 53, 54, 62, 63, 73, 74, 75, 76};


    public SetTarif_8h(){

        mediatorDB = new MediatorDB();
        moId = new ArrayList<Integer>();
        moId = mediatorDB.getIdMos();

        String[] moNames = new String[moId.size()];

        for (int i=0; i< moId.size(); i++) {
            moNames[i] = mediatorDB.getMoName(moId.get(i));
        }


        jListMoNames = new JList<String>(moNames);
        jListMoNames.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jListMoNames.setSelectedIndex(0);


        iniDtm();

        tableTarif = new JTable(dtm);

        jListMoNames.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                    idSelectMo = moId.get(jListMoNames.getSelectedIndex());
//                    System.out.println(idSelectMo);
                    fillTableModel();
                    tableTarif.setModel(dtm);

            }
        });


        kTextField = new JTextField(5);

        JPanel kPanel = new JPanel();
        kPanel.setLayout(new BoxLayout(kPanel,BoxLayout.X_AXIS));
        kPanel.add(new JLabel("Коэффициент  "));
        kPanel.add(kTextField);


        setLayout(new FlowLayout());

        refreshButton = new JButton("Применить");
        saveButton = new JButton("Сохранить");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(refreshButton);
        buttonPanel.add(saveButton);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(new JScrollPane(tableTarif));
        rightPanel.add(kPanel);
        rightPanel.add(buttonPanel);

        JPanel leftPanel = new JPanel();
        leftPanel.add(new JScrollPane(jListMoNames));

        add(leftPanel);
        add(rightPanel);

        setTitle("Изменение тарифов днев. стац.");
//        setSize(400, 300);
        pack();
        setLocationByPlatform(true);
        setVisible(true);



        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double k = 0;
                int tarif;
                String tmp;

                if (kTextField.getText().equals("")) {
                    for (int i=0; i<idPC.length; i++) {
                        tmp = tableTarif.getValueAt(i,2).toString();

                        if (tmp.equals(""))
                            continue;
                        else {
                            k = Double.parseDouble(tmp);
                            tarif = Integer.parseInt(tableTarif.getValueAt(i,1).toString());
                            dtm.setValueAt(Math.round(k*tarif),i,1);

                            boolean ins = true;

                            for (Integer z: changedRowsIdPC) {
                                if (z == (idPC[i])){
                                    ins = false;
                                    break;
                                }
                            }

                            if (ins)    {
                                changedRowsIdPC.add(idPC[i]);
                                changedRows.add(i);
                            }


                        }
                    }
                }
                else {
                    k = Double.parseDouble(kTextField.getText());

                    for (int i=0; i<idPC.length; i++) {
                        tarif = Integer.parseInt(tableTarif.getValueAt(i,1).toString());
                        dtm.setValueAt(Math.round(k*tarif),i,1);

                        boolean ins = true;

                        for (Integer z: changedRowsIdPC) {
                            if (z == (idPC[i])){
                                ins = false;
                                break;
                            }
                        }

                        if (ins) {
                            changedRowsIdPC.add(idPC[i]);
                            changedRows.add(i);
                        }

                    }

                }

            }
        });





        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                List<String> queries = new ArrayList<String>();

                int timeIdent = mediatorDB.getTimeIdent(idSelectMo, "tarif_8h");

                for (int i=0; i< changedRowsIdPC.size(); i++) {
                    queries.add("UPDATE tarif_8h SET act = 0 WHERE id_mo = " + idSelectMo + " AND act = 1 AND profile = " + changedRowsIdPC.get(i) + ";");
                }

                for (int i=0; i< changedRowsIdPC.size(); i++) {
                    queries.add("INSERT INTO tarif_8h VALUES (NULL, " + idSelectMo + ", " + changedRowsIdPC.get(i) + ", " + tableTarif.getValueAt(changedRows.get(i), 1) + ", NOW(), 1, " + (timeIdent+1) + ");");
                }

                mediatorDB.setQuries(queries);

//                for (String str: queries)
//                    System.out.println(str);

                changedRowsIdPC.clear();


            }
        });




    }


    private void iniDtm() {

        dtm.setColumnIdentifiers(new String[]{"Профиль","Тариф","Коэф. изменения"});

        for (int i=0; i<idPC.length; i++)
            dtm.addRow(new Object[]{mediatorDB.getProfileName(idPC[i]),mediatorDB.getTarif8h(idSelectMo, idPC[i]),""});


//        for (int i=1; i<73; i++) {
//            dtm.addRow(new Object[]{mediatorDB.getProfileName(i),mediatorDB.getTarif24h(idSelectMo, i),""});
//        }
    }


    private void fillTableModel() {

        while (dtm.getRowCount()>0){
            dtm.removeRow(0);
        }

        for (int i=0; i<idPC.length; i++)
            dtm.addRow(new Object[]{mediatorDB.getProfileName(idPC[i]),mediatorDB.getTarif8h(idSelectMo, idPC[i]),""});

//        for (int i=1; i<73; i++) {
//            dtm.addRow(new Object[]{mediatorDB.getProfileName(i),mediatorDB.getTarif24h(idSelectMo, i),""});
//        }

    }

}




































