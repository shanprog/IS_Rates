package tarif;

import common.MediatorDB;
import common.MyConstants;

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
 * Date: 16.01.14
 * Time: 8:39
 */


public class AllSetTarif extends JFrame {

    private JList<String> jListMoNames;
    private JTable tableTarif;
    private java.util.List<Integer> moId;
    private MediatorDB mediatorDB;

    private String tableTarifInDB = "";
    int[] chArr;
    int[] idSelMO;

    private JButton refreshButton;
    private JButton saveButton;

    private JCheckBox jCheckBox;

    private JLabel labelCountSelectMO;

    private DefaultTableModel dtm = new DefaultTableModel();
    private JTextField kTextField1, kTextField2, kTextField3;

    private List<Integer> changedRows = new ArrayList<Integer>();

    MyConstants.TypeMOCare typeMOCare;

    private int countSelMO;

    private int[] idPC;
    private int[] idPC8h= {10, 11, 18, 21, 27, 28, 29, 30, 47, 48, 49, 50, 51, 52, 53, 54, 62, 63, 73, 74, 75, 76};
    private int[] idPCAmbul = {6,7,8,9,10,11,18,21,27,28,29,30,45,46,49,50,51,52,53,54,59,60,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,94,95,96};
//    private int[] idPCOther = {89, 90, 91, 92};

    public AllSetTarif(MyConstants.TypeMOCare typeMOCare) {

        mediatorDB = new MediatorDB();
        moId = new ArrayList<Integer>();
        moId = mediatorDB.getIdMos();
        this.typeMOCare = typeMOCare;

        idSelMO = new int[]{3};

        labelCountSelectMO = new JLabel("Количество выбранных МО: 1");

        String[] moNames = new String[moId.size()];

        String addTitle = "";
        switch (typeMOCare) {
            case H24:
                addTitle = "кругл.стац.";
                tableTarifInDB = "tarif_24h";
                idPC = new int[72];
                for (int i=1; i<73; i++) {
                    idPC[i-1] = i;
                }
                break;
            case H8:
                addTitle = "дневн.стац.";
                tableTarifInDB = "tarif_8h";
                idPC = idPC8h;
                break;
            case AMBUL:
                addTitle = "амбул.";
                tableTarifInDB = "tarif_ambul";
                idPC = idPCAmbul;
                break;
            case SMP:
                addTitle = "СМП";
                tableTarifInDB = "tarif_smp";
                idPC = new int[1];
                idPC[0] = 97;
                break;
//            case OTHER:
//                addTitle = "другое";
//                idPC = idPCOther;
//                break;
        }


        for (int i=0; i < moId.size(); i++) {
            moNames[i] = mediatorDB.getMoName(moId.get(i));
        }

        jListMoNames = new JList<String>(moNames);

        iniDtm();

        tableTarif = new JTable(dtm);

        kTextField1 = new JTextField(5);
        kTextField2 = new JTextField(5);
        kTextField3 = new JTextField(5);



        JPanel kPanel = new JPanel();
        kPanel.setLayout(new BoxLayout(kPanel,BoxLayout.X_AXIS));
        kPanel.add(new JLabel("Коэффициент  "));

        if (typeMOCare == MyConstants.TypeMOCare.AMBUL) {
            kPanel.add(kTextField1);
            kPanel.add(kTextField2);
            kPanel.add(kTextField3);
        }
        else
            kPanel.add(kTextField1);



        saveButton = new JButton("Сохранить");



        setLayout(new FlowLayout());

        JPanel buttonPanel = new JPanel();

        buttonPanel.add(saveButton);



        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.add(new JScrollPane(tableTarif));
        rightPanel.add(kPanel);
        rightPanel.add(buttonPanel);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(new JScrollPane(jListMoNames));
        leftPanel.add(jCheckBox = new JCheckBox("Применить для всех больниц"));
        leftPanel.add(labelCountSelectMO);

//        jCheckBox.setEnabled(true);

        add(leftPanel);
        add(rightPanel);



        class SaveActionAmbul implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> queries = new ArrayList<String>();

                double k1 = 0, k2 = 0, k3 = 0;
                double tarif1, tarif2, tarif3;
                String tmp1, tmp2, tmp3;

                if (jCheckBox.isSelected()) {
                    if (kTextField1.getText().equals("") && kTextField2.getText().equals("") && kTextField3.getText().equals("")) {
                        for (int p = 0; p < moId.size(); p++) {
                            for (int i = 0; i < idPC.length; i++) {

                                tmp1 = tableTarif.getValueAt(i, 1).toString();
                                tmp2 = tableTarif.getValueAt(i, 2).toString();
                                tmp3 = tableTarif.getValueAt(i, 3).toString();

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

                                    queries.add("UPDATE " + tableTarifInDB + " SET act = 0 WHERE id_mo = " + moId.get(p) + " AND act = 1 AND profile = " + idPC[i] + ";");
                                    int timeIdent = mediatorDB.getTimeIdent(moId.get(p), tableTarifInDB);

                                    tarif1 = mediatorDB.getTarif(moId.get(p), idPC[i], tableTarifInDB, "tarifprof");
                                    tarif2 = mediatorDB.getTarif(moId.get(p), idPC[i], tableTarifInDB, "tarifdis");
                                    tarif3 = mediatorDB.getTarif(moId.get(p), idPC[i], tableTarifInDB, "tarifneot");

                                    queries.add("INSERT INTO " + tableTarifInDB + " VALUES (NULL, " + moId.get(p) + ", " + idPC[i] + ", " + Math.round(k1 * tarif1) + ", " + Math.round(k2 * tarif2) + ", " + Math.round(k3 * tarif3) + ", NOW(), 1, " + (timeIdent + 1) + ");");

                                }
                            }
                        }
                    }
                    else {

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

                        for (int p = 0; p < moId.size(); p++) {
                            for (int i = 0; i < idPC.length; i++) {
                                queries.add("UPDATE " + tableTarifInDB + " SET act = 0 WHERE id_mo = " + moId.get(p) + " AND act = 1 AND profile = " + idPC[i] + ";");
                                int timeIdent = mediatorDB.getTimeIdent(moId.get(p), tableTarifInDB);

                                tarif1 = mediatorDB.getTarif(moId.get(p), idPC[i], tableTarifInDB, "tarifprof");
                                tarif2 = mediatorDB.getTarif(moId.get(p), idPC[i], tableTarifInDB, "tarifdis");
                                tarif3 = mediatorDB.getTarif(moId.get(p), idPC[i], tableTarifInDB, "tarifneot");

                                queries.add("INSERT INTO " + tableTarifInDB + " VALUES (NULL, " + moId.get(p) + ", " + idPC[i] + ", " + Math.round(k1 * tarif1) + ", " + Math.round(k2 * tarif2) + ", " + Math.round(k3 * tarif3) + ", NOW(), 1, " + (timeIdent + 1) + ");");
                            }
                        }
                    }
                }
                else {
                    if (kTextField1.getText().equals("") && kTextField2.getText().equals("") && kTextField3.getText().equals("")) {
                        for (int p = 0; p < idSelMO.length; p++) {
                            for (int i = 0; i < idPC.length; i++) {
                                tmp1 = tableTarif.getValueAt(i, 1).toString();
                                tmp2 = tableTarif.getValueAt(i, 2).toString();
                                tmp3 = tableTarif.getValueAt(i, 3).toString();

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

                                    queries.add("UPDATE " + tableTarifInDB + " SET act = 0 WHERE id_mo = " + idSelMO[p] + " AND act = 1 AND profile = " + idPC[i] + ";");
                                    int timeIdent = mediatorDB.getTimeIdent(idSelMO[p], tableTarifInDB);

                                    tarif1 = mediatorDB.getTarif(idSelMO[p], idPC[i], tableTarifInDB, "tarifprof");
                                    tarif2 = mediatorDB.getTarif(idSelMO[p], idPC[i], tableTarifInDB, "tarifdis");
                                    tarif3 = mediatorDB.getTarif(idSelMO[p], idPC[i], tableTarifInDB, "tarifneot");

                                    queries.add("INSERT INTO " + tableTarifInDB + " VALUES (NULL, " + idSelMO[p] + ", " + idPC[i] + ", " + Math.round(k1 * tarif1) + ", " + Math.round(k2 * tarif2) + ", " + Math.round(k3 * tarif3) + ", NOW(), 1, " + (timeIdent + 1) + ");");
                                }
                            }
                        }
                    }
                    else {
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

                        for (int p = 0; p < idSelMO.length; p++) {
                            for (int i = 0; i < idPC.length; i++) {
                                queries.add("UPDATE " + tableTarifInDB + " SET act = 0 WHERE id_mo = " + idSelMO[p] + " AND act = 1 AND profile = " + idPC[i] + ";");
                                int timeIdent = mediatorDB.getTimeIdent(idSelMO[p], tableTarifInDB);

                                tarif1 = mediatorDB.getTarif(idSelMO[p], idPC[i], tableTarifInDB, "tarifprof");
                                tarif2 = mediatorDB.getTarif(idSelMO[p], idPC[i], tableTarifInDB, "tarifdis");
                                tarif3 = mediatorDB.getTarif(idSelMO[p], idPC[i], tableTarifInDB, "tarifneot");

                                queries.add("INSERT INTO " + tableTarifInDB + " VALUES (NULL, " + idSelMO[p] + ", " + idPC[i] + ", " + Math.round(k1 * tarif1) + ", " + Math.round(k2 * tarif2) + ", " + Math.round(k3 * tarif3) + ", NOW(), 1, " + (timeIdent + 1) + ");");
                            }
                        }
                    }
                }

                mediatorDB.setQuries(queries);

//                for (String str: queries)
//                    System.out.println(str);

            }
        }

        class SaveActionDif implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {

                List<String> queries = new ArrayList<String>();

                double k = 0;
                double tarif;
                String tmp;

                if (jCheckBox.isSelected()) {
                    if (kTextField1.getText().equals("")) {
                        for (int p = 0; p < moId.size(); p++) {
                            for (int i = 0; i < idPC.length; i++) {
                                tmp = tableTarif.getValueAt(i, 1).toString();

                                if (tmp.equals(""))
                                    continue;
                                else {
                                    k = Double.parseDouble(tmp);

                                    queries.add("UPDATE " + tableTarifInDB + " SET act = 0 WHERE id_mo = " + moId.get(p) + " AND act = 1 AND profile = " + idPC[i] + ";");
                                    int timeIdent = mediatorDB.getTimeIdent(moId.get(p), tableTarifInDB);
                                    tarif = mediatorDB.getTarif(moId.get(p), idPC[i], tableTarifInDB);
                                    queries.add("INSERT INTO " + tableTarifInDB + " VALUES (NULL, " + moId.get(p) + ", " + idPC[i] + ", " + Math.round(k * tarif) + ", NOW(), 1, " + (timeIdent + 1) + ");");
                                }
                            }
                        }

                    } else {
                        k = Double.parseDouble(kTextField1.getText());

                        for (int p = 0; p < moId.size(); p++) {
                            for (int i = 0; i < idPC.length; i++) {
                                queries.add("UPDATE " + tableTarifInDB + " SET act = 0 WHERE id_mo = " + moId.get(p) + " AND act = 1 AND profile = " + idPC[i] + ";");
                                int timeIdent = mediatorDB.getTimeIdent(moId.get(p), tableTarifInDB);
                                tarif = mediatorDB.getTarif(moId.get(p), idPC[i], tableTarifInDB);
                                queries.add("INSERT INTO " + tableTarifInDB + " VALUES (NULL, " + moId.get(p) + ", " + idPC[i] + ", " + Math.round(k * tarif) + ", NOW(), 1, " + (timeIdent + 1) + ");");
                            }
                        }
                    }
                } else {
                    if (kTextField1.getText().equals("")) {
                        for (int p = 0; p < idSelMO.length; p++) {
                            for (int i = 0; i < idPC.length; i++) {
                                tmp = tableTarif.getValueAt(i, 1).toString();

                                if (tmp.equals(""))
                                    continue;
                                else {
                                    k = Double.parseDouble(tmp);

                                    queries.add("UPDATE " + tableTarifInDB + " SET act = 0 WHERE id_mo = " + idSelMO[p] + " AND act = 1 AND profile = " + idPC[i] + ";");
                                    int timeIdent = mediatorDB.getTimeIdent(idSelMO[p], tableTarifInDB);
                                    tarif = mediatorDB.getTarif(idSelMO[p], idPC[i], tableTarifInDB);
                                    queries.add("INSERT INTO " + tableTarifInDB + " VALUES (NULL, " + idSelMO[p] + ", " + idPC[i] + ", " + Math.round(k * tarif) + ", NOW(), 1, " + (timeIdent + 1) + ");");
                                }
                            }
                        }
                    } else {
                        k = Double.parseDouble(kTextField1.getText());

                        for (int p = 0; p < idSelMO.length; p++) {
                            for (int i = 0; i < idPC.length; i++) {
                                queries.add("UPDATE " + tableTarifInDB + " SET act = 0 WHERE id_mo = " + idSelMO[p] + " AND act = 1 AND profile = " + idPC[i] + ";");
                                int timeIdent = mediatorDB.getTimeIdent(idSelMO[p], tableTarifInDB);
                                tarif = mediatorDB.getTarif(idSelMO[p], idPC[i], tableTarifInDB);
                                queries.add("INSERT INTO " + tableTarifInDB + " VALUES (NULL, " + idSelMO[p] + ", " + idPC[i] + ", " + Math.round(k * tarif) + ", NOW(), 1, " + (timeIdent + 1) + ");");
                            }
                        }
                    }
                }


                mediatorDB.setQuries(queries);

//                for (String str: queries)
//                    System.out.println(str);

//                for (int m: idSelMO)
//                    System.out.println(m);

            }
        }



        switch (typeMOCare) {
            case H24:
            case H8:
            case SMP:
                saveButton.addActionListener(new SaveActionDif());
                break;
            case AMBUL:
                saveButton.addActionListener(new SaveActionAmbul());
                break;

            case OTHER:
                break;
        }


        jListMoNames.setSelectedIndex(0);

        jListMoNames.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                chArr = jListMoNames.getSelectedIndices();

                idSelMO = new int[chArr.length];

                for (int m=0; m < chArr.length; m++) {
                    idSelMO[m] = moId.get(chArr[m]);
                }

                countSelMO = chArr.length;
                labelCountSelectMO.setText("Количество выбранных МО: " + countSelMO);

//                if (countSelMO == 1)
//                    addColInDtm();
//                else
//                    iniDtm();

            }
        });

        setTitle("Изменение тарифов для нескольких МО " + addTitle);
        pack();
        setLocationByPlatform(true);
        setVisible(true);

    }

    private void iniDtm() {

        switch (typeMOCare) {
            case H24:
            case H8:
            case SMP:
                dtm.setColumnIdentifiers(new String[]{"Профиль","Коэф. изменения"});

                for (int i=0; i<idPC.length; i++)
                    dtm.addRow(new Object[]{mediatorDB.getProfileName(idPC[i]),""});
                break;

            case AMBUL:
                dtm.setColumnIdentifiers(new String[]{"Профиль","Профил.", "Забол.", "Неотлож."});

                for (int i=0; i<idPC.length; i++)
                    dtm.addRow(new Object[]{mediatorDB.getProfileName(idPC[i]),"","",""});
                break;

            case OTHER:
                break;
        }

    }

    private void addColInDtm() {

        String table="";

        switch (typeMOCare) {
            case H24:
                table = "tarif_24h";
                break;
            case H8:
                table = "tarif_8h";
                break;
            case SMP:
                break;
            case AMBUL:
                table = "tarif_ambul";
                break;
            case OTHER:
                break;
        }

        switch (typeMOCare) {
            case H24:
            case H8:
            case SMP:
                dtm.setColumnIdentifiers(new String[]{"Профиль","Тариф","Коэф. изменения"});

                for (int i=0; i<idPC.length; i++)
                    dtm.addRow(new Object[]{mediatorDB.getProfileName(idPC[i]),mediatorDB.getTarif(idSelMO[0],idPC[i],table),""});
                break;

            case AMBUL:
                dtm.setColumnIdentifiers(new String[]{"Профиль","Tarif","Профил.", "Забол.", "Неотлож."});

                for (int i=0; i<idPC.length; i++)
                    dtm.addRow(new Object[]{mediatorDB.getProfileName(idPC[i]),"","","",""});
                break;

            case OTHER:
                break;
        }
    }

}
