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


public class SetTarif_other extends JFrame {

    private JList<String> jListMoNames;
    private JTable tableTarif;
    private List<Integer> moId;
    private MediatorDB mediatorDB;

    private JButton refreshButton;
    private JButton saveButton;

    private int idSelectMo = 3;

    private DefaultTableModel dtm = new DefaultTableModel();
    private JTextField kTextField1, kTextField2, kTextField3;

    private List<Integer> changedRowsiDpc = new ArrayList<Integer>();
    private List<Integer> changedRows = new ArrayList<Integer>();

    private int[] idPC = {89, 90, 91, 92};


    public SetTarif_other(){

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


        kTextField1 = new JTextField(5);
        kTextField2 = new JTextField(5);
        kTextField3 = new JTextField(5);

        JPanel kPanel = new JPanel();
        kPanel.setLayout(new BoxLayout(kPanel,BoxLayout.X_AXIS));
        kPanel.add(new JLabel("Коэффициент  "));
        kPanel.add(kTextField1);
        kPanel.add(kTextField2);
        kPanel.add(kTextField3);


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

        setTitle("Изменение тарифов другое");
//        setSize(400, 300);
        pack();
        setLocationByPlatform(true);
        setVisible(true);



        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                double k1 = 0, k2 = 0, k3 = 0;
                double tarif1, tarif2, tarif3;
                String tmp1, tmp2, tmp3;

//                if (kTextField1.getText().equals("")  ) {
                for (int i=0; i<idPC.length; i++) {
                    tmp1 = tableTarif.getValueAt(i,4).toString();
                    tmp2 = tableTarif.getValueAt(i,5).toString();
                    tmp3 = tableTarif.getValueAt(i,6).toString();

                    if (tmp1.equals("") && tmp2.equals("") && tmp3.equals(""))
                        continue;
                    else {

                        if (!tmp1.equals(""))
                            k1 = Double.parseDouble(tmp1);
                        else
                            k1 = 1;

                        if (!tmp2.equals(""))
                            k2 = Double.parseDouble(tmp2);
                        else
                            k2 = 1;

                        if (!tmp3.equals(""))
                            k3 = Double.parseDouble(tmp3);
                        else
                            k3 = 1;

                        tarif1 = Double.parseDouble(tableTarif.getValueAt(i, 1).toString());
                        tarif2 = Double.parseDouble(tableTarif.getValueAt(i, 2).toString());
                        tarif3 = Double.parseDouble(tableTarif.getValueAt(i, 3).toString());

                        dtm.setValueAt((k1*tarif1),i,1);
                        dtm.setValueAt((k2*tarif2),i,2);
                        dtm.setValueAt((k3*tarif3),i,3);


                        boolean ins = true;

                        for (Integer z: changedRowsiDpc) {
                            if (z == (idPC[i])){
                                ins = false;
                                break;
                            }
                        }

                        if (ins) {
                            changedRowsiDpc.add(idPC[i]);
                            changedRows.add(i);
                        }


                    }
                }
//                }
//                else {




                if (!kTextField1.getText().equals(""))
                    k1 = Double.parseDouble(kTextField1.getText());
                else
                    k1 = 1;

                if (!kTextField2.getText().equals(""))
                    k2 = Double.parseDouble(kTextField2.getText());
                else
                    k2 = 1;

                if (!kTextField3.getText().equals(""))
                    k3 = Double.parseDouble(kTextField3.getText());
                else
                    k3 = 1;



                for (int i=0; i<idPC.length; i++) {
                    tarif1 = Double.parseDouble(tableTarif.getValueAt(i, 1).toString());
                    tarif2 = Double.parseDouble(tableTarif.getValueAt(i, 2).toString());
                    tarif3 = Double.parseDouble(tableTarif.getValueAt(i, 3).toString());

                    dtm.setValueAt((k1*tarif1),i,1);
                    dtm.setValueAt((k2*tarif2),i,2);
                    dtm.setValueAt((k3*tarif3),i,3);

                    boolean ins = true;

                    for (Integer z: changedRowsiDpc) {
                        if (z == (idPC[i])){
                            ins = false;
                            break;
                        }
                    }

                    if (ins) {
                        changedRowsiDpc.add(idPC[i]);
                        changedRows.add(i);
                    }

                }

//                }

            }
        });







        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                List<String> queries = new ArrayList<String>();

                int timeIdent = mediatorDB.getTimeIdent(idSelectMo, "tarif_ambul");

                for (int i=0; i< changedRowsiDpc.size(); i++) {
                    queries.add("UPDATE tarif_other SET act = 0 WHERE id_mo = " + idSelectMo + " AND act=1 AND profile = " + changedRowsiDpc.get(i) + ";");
                }

                for (int i=0; i< changedRowsiDpc.size(); i++) {
                    queries.add("INSERT INTO tarif_other VALUES (NULL, " + idSelectMo + ", " + changedRowsiDpc.get(i) + ", " + tableTarif.getValueAt(i, 1) + ", " + tableTarif.getValueAt(i, 2) + ", " + tableTarif.getValueAt(i, 3) + ", NOW(), 1, " + (timeIdent+1) + ");");
                }

                mediatorDB.setQuries(queries);

                changedRowsiDpc.clear();

//                for (String str: queries)
//                    System.out.println(str);

            }
        });
             


    }


    private void iniDtm() {

        dtm.setColumnIdentifiers(new String[]{"Профиль","Тариф кругл.стац.", "Тариф амбул.", "Тариф днев.стац.", "Коэф. изменения кругл.стац.", "Коэф. изменения амбул.", "Коэф. изменения дневн.стац."});

        List<Double> tarif = new ArrayList<Double>();

        for (int i=0; i<idPC.length; i++) {
            tarif = mediatorDB.getTarifOther(idSelectMo, idPC[i]);
            dtm.addRow(new Object[]{mediatorDB.getProfileName(idPC[i]), tarif.get(0),tarif.get(1),tarif.get(2),"","",""});
        }

//            dtm.addRow(new Object[]{,mediatorDB.getTarif8h(idSelectMo, idPC[i]),""});


//        for (int i=1; i<73; i++) {
//            dtm.addRow(new Object[]{mediatorDB.getProfileName(i),mediatorDB.getTarif24h(idSelectMo, i),""});
//        }
    }


    private void fillTableModel() {

        while (dtm.getRowCount()>0){
            dtm.removeRow(0);
        }

        List<Double> tarif = new ArrayList<Double>();

        for (int i=0; i<idPC.length; i++) {
            tarif = mediatorDB.getTarifOther(idSelectMo, idPC[i]);
            dtm.addRow(new Object[]{mediatorDB.getProfileName(idPC[i]), tarif.get(0),tarif.get(1),tarif.get(2),"","",""});
        }

//        for (int i=1; i<73; i++) {
//            dtm.addRow(new Object[]{mediatorDB.getProfileName(i),mediatorDB.getTarif24h(idSelectMo, i),""});
//        }

    }

}




































