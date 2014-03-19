/**
 * User: ShuryginAN
 * Date: 27.11.13
 * Time: 15:33
 */

import common.*;
import org.jdom2.Document;
import tarif.*;
import vPrint.*;
import volumes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class MainWindow extends JFrame {


    private JMenuBar menuBar;
    private Table_24h table_24h;
    private JTable tmpTable_24h;
    private Table_8h table_8h;
    private JTable tmpTable_8h;
    private Table_smp table_smp;
    private JTable tmpTable_smp;
    private Table_ambul table_ambul;
    private JTable tmpTable_ambul;
    private Table_other table_other;
    private JTable tmpTable_other;

    private java.util.List<Integer> editRows;

    private MediatorDB mediatorDB;

    JMenuItem t_allsetTarif_24h;
    JMenuItem t_allsetTarif_8h;
    JMenuItem t_allsetTarif_ambul;
    JMenuItem t_allsetTarif_smp;
    JMenuItem t_allsetTarif_other;

    public MainWindow() {
        setSize(500, 400);
        setTitle("IS Rates");

//        MyConstants myConstants;

        mediatorDB = new MediatorDB();
        editRows = new ArrayList<Integer>();

        menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu fileMenu = new JMenu("Файл");
        JMenu vPrint = new JMenu("Печать итогов");
        JMenu tarifs = new JMenu("Тарифы");
        JMenu reports = new JMenu("Отчеты");
        JMenu loads = new JMenu("Загрузки");

        JMenu adminMenu = new JMenu("Администрирование");



        menuBar.add(fileMenu);
        menuBar.add(vPrint);
        menuBar.add(tarifs);
        menuBar.add(reports);
        menuBar.add(loads);

//        menuBar.add(adminMenu);




//        menuBar.add(settingsMenu);


        JMenuItem l_loadUO = new JMenuItem("Загрузка УО");

        JMenuItem a_tarifLoad = new JMenuItem("Загрузка первых тарифов");
        JMenuItem a_load = new JMenuItem("Загрузка");
        JMenuItem a_loadVolumesMO = new JMenuItem("Загрузка распределения объемов от МО");
        JMenuItem a_outXMLTarif = new JMenuItem("Выгрузка тарифов в XML");

        JMenuItem f_outExcelReportsProfile = new JMenuItem("Выгрузка в Excel отчетов по профилям");
        JMenuItem f_outExcelReportsQuadro = new JMenuItem("Выгрузка в Excel отчетов по кварталам");
        JMenuItem f_exit = new JMenuItem("Выход");
//        JMenuItem s_load = new JMenuItem("Загрузка");

        JMenuItem v24hprint = new JMenuItem("Круглосуточный стационар");
        JMenuItem v8hPrint = new JMenuItem("Дневной стационар");
        JMenuItem vAmbulPrint = new JMenuItem("Амбул-поликлин.");
        JMenuItem vsmpPrint = new JMenuItem("СМП");
        JMenuItem vOtherPrint = new JMenuItem("Другое");

        JMenuItem t_setTarif_24h = new JMenuItem("Изменение тарифов кругл.стац.");
        JMenuItem t_setTarif_8h = new JMenuItem("Изменение тарифов днев.стац.");
        JMenuItem t_setTarif_ambul = new JMenuItem("Изменение тарифов поликл.");
        JMenuItem t_setTarif_smp = new JMenuItem("Изменение тарифов СМП");
        JMenuItem t_setTarif_other = new JMenuItem("Изменение тарифов остальных");

        JMenuItem t_baseTarif_24h = new JMenuItem("Тарифы кругл.стац.");
        JMenuItem t_baseTarif_8h = new JMenuItem("Тарифы дневн.стац.");
        JMenuItem t_baseTarif_ambul = new JMenuItem("Тарифы поликлин.");
        JMenuItem t_baseTarif_smp = new JMenuItem("Тарифы скорая помощь");
        JMenuItem t_baseTarif_other = new JMenuItem("Тарифы другое");

        JMenuItem t_Tarif_24h = new JMenuItem("Свод т-в по кругл. стац.");
        JMenuItem t_Tarif_8h = new JMenuItem("Свод т-в по дневн. стац.");
        JMenuItem t_Tarif_ambul = new JMenuItem("Свод т-в по поликлин.");
        JMenuItem t_Tarif_smp = new JMenuItem("Свод т-в по СМП");
        JMenuItem t_Tarif_other = new JMenuItem("Свод т-в по другим");

        t_allsetTarif_24h = new JMenuItem("Изменение тарифов для всех кругл.стац.");
        t_allsetTarif_8h = new JMenuItem("Изменение тарифов для всех днев.стац.");
        t_allsetTarif_ambul = new JMenuItem("Изменение тарифов для всех амбул.");
        t_allsetTarif_smp = new JMenuItem("Изменение тарифов для всех СМП");
        t_allsetTarif_other = new JMenuItem("Изменение тарифов для всех других");


        JMenuItem r_quadro24h = new JMenuItem(MyConstants.REPORT_QUADRO_H24);
        JMenuItem r_quadro8h = new JMenuItem(MyConstants.REPORT_QUADRO_H8);
        JMenuItem r_quadroAmbulProf = new JMenuItem(MyConstants.REPORT_QUADRO_AMBULPROF);
        JMenuItem r_quadroAmbulNeot = new JMenuItem(MyConstants.REPORT_QUADRO_AMBULNEOT);
        JMenuItem r_quadroAmbulZab = new JMenuItem(MyConstants.REPORT_QUADRO_AMBULZAB);
        JMenuItem r_quadroStomatProf = new JMenuItem(MyConstants.REPORT_QUADRO_STOMATPROF);
        JMenuItem r_quadroStomatNeot = new JMenuItem(MyConstants.REPORT_QUADRO_STOMATNEOT);
        JMenuItem r_quadroStomatZab = new JMenuItem(MyConstants.REPORT_QUADRO_STOMATZAB);
        JMenuItem r_quadroSmp = new JMenuItem(MyConstants.REPORT_QUADRO_SMP);

        JMenuItem r_profile24h = new JMenuItem(MyConstants.REPORT_PROFILE_H24);
        JMenuItem r_profile8h = new JMenuItem(MyConstants.REPORT_PROFILE_H8);
        JMenuItem r_profileAmbulProf = new JMenuItem(MyConstants.REPORT_PROFILE_AMBULPROF);
        JMenuItem r_profileAmbulNeot = new JMenuItem(MyConstants.REPORT_PROFILE_AMBULNEOT);
        JMenuItem r_profileAmbulZab = new JMenuItem(MyConstants.REPORT_PROFILE_AMBULZAB);
        JMenuItem r_profileStomatProf = new JMenuItem(MyConstants.REPORT_PROFILE_STOMATPROF);
        JMenuItem r_profileStomatNeot = new JMenuItem(MyConstants.REPORT_PROFILE_STOMATNEOT);
        JMenuItem r_profileStomatZab = new JMenuItem(MyConstants.REPORT_PROFILE_STOMATZAB);
        JMenuItem r_profileSmp = new JMenuItem(MyConstants.REPORT_PROFILE_SMP);





        loads.add(l_loadUO);

        vPrint.add(v24hprint);
        vPrint.add(v8hPrint);
        vPrint.add(vAmbulPrint);
        vPrint.add(vsmpPrint);
        vPrint.add(vOtherPrint);

        fileMenu.add(f_outExcelReportsProfile);
        fileMenu.add(f_outExcelReportsQuadro);
        fileMenu.addSeparator();
        fileMenu.add(f_exit);

        adminMenu.add(a_tarifLoad);
        adminMenu.add(a_load);
        adminMenu.add(a_loadVolumesMO);
        adminMenu.add(a_outXMLTarif);


        tarifs.add(t_setTarif_24h);
        tarifs.add(t_setTarif_8h);
        tarifs.add(t_setTarif_ambul);
        tarifs.add(t_setTarif_smp);
        tarifs.add(t_setTarif_other);
        tarifs.addSeparator();
        tarifs.add(t_baseTarif_24h);
        tarifs.add(t_baseTarif_8h);
        tarifs.add(t_baseTarif_ambul);
        tarifs.add(t_baseTarif_smp);
        tarifs.add(t_baseTarif_other);
        tarifs.addSeparator();
        tarifs.add(t_Tarif_24h);
        tarifs.add(t_Tarif_8h);
        tarifs.add(t_Tarif_ambul);
        tarifs.add(t_Tarif_smp);
        tarifs.add(t_Tarif_other);
        tarifs.addSeparator();
        tarifs.add(t_allsetTarif_24h);
        tarifs.add(t_allsetTarif_8h);
        tarifs.add(t_allsetTarif_ambul);
        tarifs.add(t_allsetTarif_smp);
//        tarifs.add(t_allsetTarif_other);


        reports.add(r_quadro24h);
        reports.add(r_quadro8h);
        reports.add(r_quadroAmbulProf);
        reports.add(r_quadroAmbulNeot);
        reports.add(r_quadroAmbulZab);
        reports.add(r_quadroStomatProf);
        reports.add(r_quadroStomatNeot);
        reports.add(r_quadroStomatZab);
        reports.add(r_quadroSmp);
        reports.addSeparator();
        reports.add(r_profile24h);
        reports.add(r_profile8h);
        reports.add(r_profileAmbulProf);
        reports.add(r_profileAmbulNeot);
        reports.add(r_profileAmbulZab);
        reports.add(r_profileStomatProf);
        reports.add(r_profileStomatNeot);
        reports.add(r_profileStomatZab);
        reports.add(r_profileSmp);



        t_allsetTarif_24h.addActionListener(new ActionAllSetTarif());
        t_allsetTarif_8h.addActionListener(new ActionAllSetTarif());
        t_allsetTarif_ambul.addActionListener(new ActionAllSetTarif());
        t_allsetTarif_smp.addActionListener(new ActionAllSetTarif());
        t_allsetTarif_other.addActionListener(new ActionAllSetTarif());


        f_outExcelReportsProfile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OutExcelReportsProfile();
            }
        });

        f_outExcelReportsQuadro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OutExcelReportsQuadro();
            }
        });


        class WinRaspr implements ActionListener {

            ReportRaspredQuadro.TableType tableType;

            @Override
            public void actionPerformed(ActionEvent e) {


                if (e.getActionCommand().equals(MyConstants.REPORT_QUADRO_H24) )
                    this.tableType = ReportRaspredQuadro.TableType.H24;
                else if (e.getActionCommand().equals(MyConstants.REPORT_QUADRO_H8))
                    this.tableType = ReportRaspredQuadro.TableType.H8;
                else if (e.getActionCommand().equals(MyConstants.REPORT_QUADRO_AMBULPROF))
                    this.tableType = ReportRaspredQuadro.TableType.AMBULPROF;
                else if (e.getActionCommand().equals(MyConstants.REPORT_QUADRO_AMBULNEOT))
                    this.tableType = ReportRaspredQuadro.TableType.AMBULNEOT;
                else if (e.getActionCommand().equals(MyConstants.REPORT_QUADRO_AMBULZAB))
                    this.tableType = ReportRaspredQuadro.TableType.AMBULZAB;
                else if (e.getActionCommand().equals(MyConstants.REPORT_QUADRO_STOMATPROF))
                    this.tableType = ReportRaspredQuadro.TableType.STOMATPROF;
                else if (e.getActionCommand().equals(MyConstants.REPORT_QUADRO_STOMATNEOT))
                    this.tableType = ReportRaspredQuadro.TableType.STOMATNEOT;
                else if (e.getActionCommand().equals(MyConstants.REPORT_QUADRO_STOMATZAB))
                    this.tableType = ReportRaspredQuadro.TableType.STOMATZAB;
                else if (e.getActionCommand().equals(MyConstants.REPORT_QUADRO_SMP))
                    this.tableType = ReportRaspredQuadro.TableType.SMP;

                new ReportRaspredQuadro(tableType);
            }
        }

        WinRaspr winRaspr = new WinRaspr();


        r_quadro24h.addActionListener(winRaspr);
        r_quadro8h.addActionListener(winRaspr);
        r_quadroAmbulProf.addActionListener(winRaspr);
        r_quadroAmbulNeot.addActionListener(winRaspr);
        r_quadroAmbulZab.addActionListener(winRaspr);
        r_quadroStomatProf.addActionListener(winRaspr);
        r_quadroStomatNeot.addActionListener(winRaspr);
        r_quadroStomatZab.addActionListener(winRaspr);
        r_quadroSmp.addActionListener(winRaspr);


        class WinRasprProfile implements ActionListener {

            ReportRaspredProfile.TableType tableType;


            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(MyConstants.REPORT_PROFILE_H24) )
                    this.tableType = ReportRaspredProfile.TableType.H24;
                else if (e.getActionCommand().equals(MyConstants.REPORT_PROFILE_H8))
                    this.tableType = ReportRaspredProfile.TableType.H8;
                else if (e.getActionCommand().equals(MyConstants.REPORT_PROFILE_AMBULPROF))
                    this.tableType = ReportRaspredProfile.TableType.AMBULPROF;
                else if (e.getActionCommand().equals(MyConstants.REPORT_PROFILE_AMBULNEOT))
                    this.tableType = ReportRaspredProfile.TableType.AMBULNEOT;
                else if (e.getActionCommand().equals(MyConstants.REPORT_PROFILE_AMBULZAB))
                    this.tableType = ReportRaspredProfile.TableType.AMBULZAB;
                else if (e.getActionCommand().equals(MyConstants.REPORT_PROFILE_STOMATPROF))
                    this.tableType = ReportRaspredProfile.TableType.STOMATPROF;
                else if (e.getActionCommand().equals(MyConstants.REPORT_PROFILE_STOMATNEOT))
                    this.tableType = ReportRaspredProfile.TableType.STOMATNEOT;
                else if (e.getActionCommand().equals(MyConstants.REPORT_PROFILE_STOMATZAB))
                    this.tableType = ReportRaspredProfile.TableType.STOMATZAB;
                else if (e.getActionCommand().equals(MyConstants.REPORT_PROFILE_SMP))
                    this.tableType = ReportRaspredProfile.TableType.SMP;

                new ReportRaspredProfile(tableType);
            }
        }

        WinRasprProfile winRasprProfile = new WinRasprProfile();

        r_profile24h.addActionListener(winRasprProfile);
        r_profile8h.addActionListener(winRasprProfile);
        r_profileAmbulNeot.addActionListener(winRasprProfile);
        r_profileAmbulProf.addActionListener(winRasprProfile);
        r_profileAmbulZab.addActionListener(winRasprProfile);
        r_profileSmp.addActionListener(winRasprProfile);
        r_profileStomatNeot.addActionListener(winRasprProfile);
        r_profileStomatProf.addActionListener(winRasprProfile);
        r_profileStomatZab.addActionListener(winRasprProfile);


//        settingsMenu.add(s_load);

        t_baseTarif_24h.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BaseTarif_24h();
            }
        });

        t_baseTarif_8h.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BaseTarif_8h();
            }
        });

        t_baseTarif_ambul.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BaseTarif_ambul();
            }
        });

        t_baseTarif_smp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BaseTarif_smp();
            }
        });

        t_baseTarif_other.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new BaseTarif_other();
            }
        });

        t_Tarif_24h.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Tarif_24h();
            }
        });

        t_Tarif_8h.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Tarif_8h();
            }
        });

        t_Tarif_ambul.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Tarif_ambul();
            }
        });

        t_Tarif_smp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Tarif_smp();
            }
        });

        t_Tarif_other.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Tarif_other();
            }
        });

        t_setTarif_24h.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SetTarif_24h();
            }
        });

        t_setTarif_8h.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SetTarif_8h();
            }
        });

        t_setTarif_ambul.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SetTarif_ambul();
            }
        });

        t_setTarif_smp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SetTarif_smp();
            }
        });

        t_setTarif_other.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SetTarif_other();
            }
        });


        a_tarifLoad.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoadFirstTarif();
            }
        });

        v24hprint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PrintV24h();
            }
        });

        v8hPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PrintV8h();
            }
        });

        vAmbulPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PrintVAmbul();
            }
        });

        vsmpPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PrintVSmp();
            }
        });

        vOtherPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new PrintVOther();
            }
        });


        l_loadUO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoadUO();
            }
        });

        a_outXMLTarif.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Document doc = JDOMLogic.create();

                if (JDOMLogic.saveDocument("tarifs.xml",doc))
                    System.out.println("Документ создан");
                else
                    System.out.println("Документ НЕ создан");
            }
        });

        a_load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoadFileWindow();
            }
        });

        a_loadVolumesMO.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new LoadVolumesMO();
            }
        });

        f_exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });


        JToolBar tools1 = new JToolBar();
        JToolBar tools2 = new JToolBar();
        JToolBar tools3 = new JToolBar();
        JToolBar tools4 = new JToolBar();
        JToolBar tools5 = new JToolBar();

        JButton refreshButton_24h = new JButton("Обновить отображение");
        JButton saveButton_24h = new JButton("Сохранить");

        JButton refreshButton_8h = new JButton("Обновить отображение");
        JButton saveButton_8h = new JButton("Сохранить");

        JButton refreshButton_smp = new JButton("Обновить отображение");
        JButton saveButton_smp = new JButton("Сохранить");

        JButton refreshButton_ambul = new JButton("Обновить отображение");
        JButton saveButton_ambul = new JButton("Сохранить");

        JButton refreshButton_other = new JButton("Обновить отображение");
        JButton saveButton_other = new JButton("Сохранить");

        refreshButton_other.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tmpTable_other = table_other.getjTable();

                String tmp1, tmp2, tmp3, tmp4, tmp5, tmp6, tmp7;
                int k1, k2, k3, k4, k5, k6, k7;

                for (int i = 0; i < tmpTable_other.getRowCount()-1; i++) {
                    tmp1 = tmpTable_other.getValueAt(i, 8).toString();
                    tmp2 = tmpTable_other.getValueAt(i, 9).toString();
                    tmp3 = tmpTable_other.getValueAt(i, 10).toString();
                    tmp4 = tmpTable_other.getValueAt(i, 11).toString();
                    tmp5 = tmpTable_other.getValueAt(i, 12).toString();
                    tmp6 = tmpTable_other.getValueAt(i, 13).toString();
                    tmp7 = tmpTable_other.getValueAt(i, 14).toString();

                    if (tmp1.equals("") )
                        k1 = 0;
                    else {
                        k1 = Integer.parseInt(tmp1);
                    }

                    if (tmp2.equals("") )
                        k2 = 0;
                    else {
                        k2 = Integer.parseInt(tmp2);
                    }

                    if (tmp3.equals("") )
                        k3 = 0;
                    else {
                        k3 = Integer.parseInt(tmp3);
                    }

                    if (tmp4.equals("") )
                        k4 = 0;
                    else {
                        k4 = Integer.parseInt(tmp4);
                    }

                    if (tmp5.equals("") )
                        k5 = 0;
                    else {
                        k5 = Integer.parseInt(tmp5);
                    }

                    if (tmp6.equals("") )
                        k6 = 0;
                    else {
                        k6 = Integer.parseInt(tmp6);
                    }

                    if (tmp7.equals("") )
                        k7 = 0;
                    else {
                        k7 = Integer.parseInt(tmp7);
                    }

                    if (!tmp7.equals("") || !tmp6.equals("") || !tmp5.equals("") || !tmp4.equals("") || !tmp3.equals("") || !tmp2.equals("") || !tmp1.equals("") ) {
                        editRows.add(i);
                    }

                    tmpTable_other.setValueAt(k1, i, 1);
                    tmpTable_other.setValueAt(k2, i, 2);
                    tmpTable_other.setValueAt(k3, i, 3);
                    tmpTable_other.setValueAt(k4, i, 4);
                    tmpTable_other.setValueAt(k5, i, 5);
                    tmpTable_other.setValueAt(k6, i, 6);
                    tmpTable_other.setValueAt(k7, i, 7);


                    for (int n=8; n<15; n++)
                        tmpTable_other.setValueAt("", i, n);
                }



                for (int j = 1; j < 8; j++) {
                    long sum = 0;
                    for (int i = 0; i < tmpTable_other.getRowCount()-1; i++) {
                        sum += Integer.parseInt(tmpTable_other.getValueAt(i, j).toString());
                    }

                    tmpTable_other.setValueAt(sum, tmpTable_other.getRowCount()-1, j);
                }

                editRows.clear();
                for (int i=0; i<tmpTable_other.getRowCount(); i++)
                    editRows.add(i);

            }
        });

        refreshButton_ambul.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tmpTable_ambul = table_ambul.getjTable();

                String tmp1, tmp2, tmp3, tmp4, tmp5, tmp6, tmp7;
                double k1, k2, k3, k4, k5, k6, k7;
                double ku1=0, ku2=0, ku3=0, ku4=0, ku5=0, ku6=0, ku7=0;

                for (int i = 0; i < tmpTable_ambul.getRowCount()-1; i++) {

                    tmp1 = tmpTable_ambul.getValueAt(i, 185).toString();
                    tmp2 = tmpTable_ambul.getValueAt(i, 186).toString();
                    tmp3 = tmpTable_ambul.getValueAt(i, 187).toString();
                    tmp4 = tmpTable_ambul.getValueAt(i, 188).toString();
                    tmp5 = tmpTable_ambul.getValueAt(i, 189).toString();
                    tmp6 = tmpTable_ambul.getValueAt(i, 190).toString();
                    tmp7 = tmpTable_ambul.getValueAt(i, 191).toString();

                    int j1 = Integer.parseInt(tmpTable_ambul.getValueAt(i, 178).toString());
                    int j2 = Integer.parseInt(tmpTable_ambul.getValueAt(i, 179).toString());
                    int j3 = Integer.parseInt(tmpTable_ambul.getValueAt(i, 180).toString());
                    int j4 = Integer.parseInt(tmpTable_ambul.getValueAt(i, 181).toString());
                    int j5 = Integer.parseInt(tmpTable_ambul.getValueAt(i, 182).toString());
                    int j6 = Integer.parseInt(tmpTable_ambul.getValueAt(i, 183).toString());
                    int j7 = Integer.parseInt(tmpTable_ambul.getValueAt(i, 184).toString());


                    if (tmp1.equals("") )
                        k1 = 0;
                    else {
                        k1 = Double.parseDouble(tmp1);
                    }

                    if (tmp2.equals("") )
                        k2 = 0;
                    else {
                        k2 = Double.parseDouble(tmp2);
                    }

                    if (tmp3.equals("") )
                        k3 = 0;
                    else {
                        k3 = Double.parseDouble(tmp3);
                    }

                    if (tmp4.equals("") )
                        k4 = 0;
                    else {
                        k4 = Double.parseDouble(tmp4);
                    }

                    if (tmp5.equals("") )
                        k5 = 0;
                    else {
                        k5 = Double.parseDouble(tmp5);
                    }

                    if (tmp6.equals("") )
                        k6 = 0;
                    else {
                        k6 = Double.parseDouble(tmp6);
                    }

                    if (tmp7.equals("") )
                        k7 = 0;
                    else {
                        k7 = Double.parseDouble(tmp7);
                    }

                    if (!tmp7.equals("") || !tmp6.equals("") || !tmp5.equals("") || !tmp4.equals("") || !tmp3.equals("") || !tmp2.equals("") || !tmp1.equals("") ) {
                        editRows.add(i);
                    }


                    if (j1 != 0)
                        ku1 = k1 / j1;

                    if (j2 != 0)
                        ku2 = k2 / j2;

                    if (j3 != 0)
                        ku3 = k3 / j3;

                    if (j4 != 0)
                        ku4 = k4 / j4;

                    if (j5 != 0)
                        ku5 = k5 / j5;

                    if (j6 != 0)
                        ku6 = k6 / j6;

                    if (j7 != 0)
                        ku7 = k7 / j7;


                    if (ku1 != 0) {

                        for (int n = 8; n < 49; n++) {

                            int y = Integer.parseInt(tmpTable_ambul.getValueAt(i, n).toString());
                            tmpTable_ambul.setValueAt(Math.round(y * ku1), i, n);
                        }
                    }

                    if (ku2 != 0) {

                        for (int n = 49; n < 51; n++) {

                            int y = Integer.parseInt(tmpTable_ambul.getValueAt(i, n).toString());
                            tmpTable_ambul.setValueAt(Math.round(y * ku2), i, n);
                        }
                    }

                    if (ku3 != 0) {

                        for (int n = 51; n < 92; n++) {

                            int y = Integer.parseInt(tmpTable_ambul.getValueAt(i, n).toString());
                            tmpTable_ambul.setValueAt(Math.round(y * ku3), i, n);
                        }
                    }

                    if (ku4 != 0) {

                        for (int n = 92; n < 94; n++) {

                            int y = Integer.parseInt(tmpTable_ambul.getValueAt(i, n).toString());
                            tmpTable_ambul.setValueAt(Math.round(y * ku4), i, n);
                        }
                    }

                    if (ku5 != 0) {

                        for (int n = 94; n < 135; n++) {

                            int y = Integer.parseInt(tmpTable_ambul.getValueAt(i, n).toString());
                            tmpTable_ambul.setValueAt(Math.round(y * ku5), i, n);
                        }
                    }

                    if (ku6 != 0) {

                        for (int n = 135; n < 176; n++) {

                            int y = Integer.parseInt(tmpTable_ambul.getValueAt(i, n).toString());
                            tmpTable_ambul.setValueAt(Math.round(y * ku6), i, n);
                        }
                    }

                    if (ku7 != 0) {

                        for (int n = 176; n < 178; n++) {

                            int y = Integer.parseInt(tmpTable_ambul.getValueAt(i, n).toString());
                            tmpTable_ambul.setValueAt(Math.round(y * ku7), i, n);
                        }
                    }




                    int sum1 = 0;
                    for (int n = 8; n < 49; n++) {
                        int y = Integer.parseInt(tmpTable_ambul.getValueAt(i, n).toString());
                        sum1 += Math.round(y);
                        tmpTable_ambul.setValueAt(sum1, i, 178);
                    }

                    int sum2 = 0;
                    for (int n = 49; n < 51; n++) {
                        int y = Integer.parseInt(tmpTable_ambul.getValueAt(i, n).toString());
                        sum2 += Math.round(y);
                        tmpTable_ambul.setValueAt(sum2, i, 179);
                    }

                    int sum3 = 0;
                    for (int n = 51; n < 92; n++) {
                        int y = Integer.parseInt(tmpTable_ambul.getValueAt(i, n).toString());
                        sum3 += Math.round(y);
                        tmpTable_ambul.setValueAt(sum3, i, 180);
                    }

                    int sum4 = 0;
                    for (int n = 92; n < 94; n++) {
                        int y = Integer.parseInt(tmpTable_ambul.getValueAt(i, n).toString());
                        sum4 += Math.round(y);
                        tmpTable_ambul.setValueAt(sum4, i, 181);
                    }


                    int sum5 = 0;
                    for (int n = 94; n < 135; n++) {
                        int y = Integer.parseInt(tmpTable_ambul.getValueAt(i, n).toString());
                        sum5 += Math.round(y);
                        tmpTable_ambul.setValueAt(sum5, i, 182);
                    }

                    int sum6 = 0;
                    for (int n = 135; n < 176; n++) {
                        int y = Integer.parseInt(tmpTable_ambul.getValueAt(i, n).toString());
                        sum6 += Math.round(y);
                        tmpTable_ambul.setValueAt(sum6, i, 183);
                    }

                    int sum7 = 0;
                    for (int n = 176; n < 178; n++) {
                        int y = Integer.parseInt(tmpTable_ambul.getValueAt(i, n).toString());
                        sum7 += Math.round(y);
                        tmpTable_ambul.setValueAt(sum7, i, 184);
                    }




                    for (int n=185; n<192; n++)
                        tmpTable_ambul.setValueAt("", i, n);

                }



                for (int j = 178; j < 185; j++) {
                    long sum = 0;
                    for (int i = 0; i < tmpTable_ambul.getRowCount()-1; i++ ) {
                        sum += Integer.parseInt(tmpTable_ambul.getValueAt(i, j).toString());
                    }

                    tmpTable_ambul.setValueAt(sum, tmpTable_ambul.getRowCount()-1, j);
                }

                editRows.clear();
                for (int i=0; i<tmpTable_ambul.getRowCount()-1; i++) {
                    editRows.add(i);
                }

            }
        });

        refreshButton_smp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tmpTable_smp = table_smp.getjTable();

                String tmp;
                int k = 0;

                for (int i=0; i<tmpTable_smp.getRowCount()-1; i++) {
                    tmp = tmpTable_smp.getValueAt(i, 4).toString();

                    if (tmp.equals(""))
                        k = 0;
                    else {
                        k = Integer.parseInt(tmp);
                        editRows.add(i);
                    }

                    if (k != 0) {
                        tmpTable_smp.setValueAt(k, i, 3);
                    }

                    tmpTable_smp.setValueAt("", i, 4);
                }

                long sum = 0;
                for (int i=0; i<tmpTable_smp.getRowCount()-1; i++) {
                    sum += Integer.parseInt(tmpTable_smp.getValueAt(i, 3).toString());
                }

                tmpTable_smp.setValueAt(sum, tmpTable_smp.getRowCount()-1, 3);

                editRows.clear();
                for (int i=0; i<tmpTable_smp.getRowCount()-1; i++) {
                    editRows.add(i);
                }

            }
        });

        refreshButton_8h.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tmpTable_8h = table_8h.getjTable();

                String tmp;
                double k = 0;
                double ku = 0;

                for (int i = 0; i < tmpTable_8h.getRowCount()-1; i++) {
                    tmp = tmpTable_8h.getValueAt(i, 49).toString();

                    int j = Integer.parseInt(tmpTable_8h.getValueAt(i, 48).toString());

                    if (tmp.equals(""))
                        k = 0;
                    else {
                        k = Double.parseDouble(tmp);
                        editRows.add(i);
                    }

                    if (j != 0)
                        ku = k / j;

                    if (ku != 0) {

                        for (int n = 26; n < 48; n++) {

                            int y = Integer.parseInt(tmpTable_8h.getValueAt(i, n).toString());
                            tmpTable_8h.setValueAt(Math.round(y * ku), i, n);
                        }
                    }



                    int sum = 0;
                    for (int n = 26; n < 48; n++) {
                        int y = Integer.parseInt(tmpTable_8h.getValueAt(i, n).toString());
                        sum += Math.round(y);
                        tmpTable_8h.setValueAt(sum, i, 48);
                    }

                    tmpTable_8h.setValueAt("", i, 49);
                }

                long sum = 0;
                for (int i = 0; i < tmpTable_8h.getRowCount()-1; i++) {
                    sum += Integer.parseInt(tmpTable_8h.getValueAt(i, 48).toString());

                }

                tmpTable_8h.setValueAt(sum, tmpTable_8h.getRowCount()-1, 48);


                editRows.clear();
                for (int i=0; i<tmpTable_8h.getRowCount()-1; i++) {
                    editRows.add(i);
                }
            }
        });


        refreshButton_24h.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                tmpTable_24h = table_24h.getjTable();

                String tmp;
                double k = 0;
                double ku = 0;

                for (int i = 0; i < tmpTable_24h.getRowCount()-1; i++) {
//                    tmp = tmpTable_24h.getValueAt(i, 151).toString();
                    tmp = tmpTable_24h.getValueAt(i, 74).toString();
                    int j = Integer.parseInt(tmpTable_24h.getValueAt(i, 73).toString());

                    if (tmp.equals(""))
                        k = 0;
                    else {
                        k = Double.parseDouble(tmp);
//                        editRows.add(i);
                    }



                    if (j != 0)
                        ku = k / j;

                    if (ku != 0) {

//                        for (int n = 78; n < 150; n++) {
                        for (int n = 1; n < 73; n++) {

                            int y = Integer.parseInt(tmpTable_24h.getValueAt(i, n).toString());
                            tmpTable_24h.setValueAt(Math.round(y * ku), i, n);
                        }
                    }

                    int sum = 0;
//                    for (int n = 78; n < 150; n++) {
                    for (int n = 1; n < 73; n++) {
                        int y = Integer.parseInt(tmpTable_24h.getValueAt(i, n).toString());
                        sum += Math.round(y);
                        tmpTable_24h.setValueAt(sum, i, 73);
//                        tmpTable_24h.setValueAt(sum, i, 150);
                    }

                    tmpTable_24h.setValueAt("", i, 74);
//                    tmpTable_24h.setValueAt("", i, 151);
                }

                long sum = 0;
                for (int i = 0; i < tmpTable_24h.getRowCount()-1; i++) {
//                    sum += Integer.parseInt(tmpTable_24h.getValueAt(i, 150).toString());
//                    tmpTable_24h.setValueAt(sum, tmpTable_24h.getRowCount()-1, 150);
                    sum += Integer.parseInt(tmpTable_24h.getValueAt(i, 73).toString());
                    tmpTable_24h.setValueAt(sum, tmpTable_24h.getRowCount()-1, 73);
                }

                editRows.clear();
                for (int i=0; i < tmpTable_24h.getRowCount()-1; i++) {
                    editRows.add(i);
                }

            }


        });

        saveButton_other.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.List<Integer> idMo = table_smp.getMoId();
                java.util.List<String> queries = new ArrayList<String>();

                int[] idPC =  table_other.getIdPC();

                for (int i=0; i<editRows.size(); i++) {

                    for (int j=1; j < 4; j++) {
                        int a = Integer.parseInt(tmpTable_other.getValueAt(editRows.get(i), j).toString());
                        queries.add("UPDATE `v_other` SET a_num_24h = " + a + " WHERE mo_name = " + idMo.get(editRows.get(i)) + " AND profile_care = " + idPC[j-1] + ";");
                    }

                    for (int j=4; j < 7; j++) {
                        int a = Integer.parseInt(tmpTable_other.getValueAt(editRows.get(i), j).toString());
                        queries.add("UPDATE `v_other` SET a_num_ambul = " + a + " WHERE mo_name = " + idMo.get(editRows.get(i)) + " AND profile_care = " + idPC[j-1] + ";");
                    }

                    queries.add("UPDATE `v_other` SET a_num_8h = " + Integer.parseInt(tmpTable_other.getValueAt(editRows.get(i), 7).toString()) + " WHERE mo_name = " + idMo.get(editRows.get(i)) + " AND profile_care = " + idPC[3] + ";");

                }

//                for (String str: queries)
//                System.out.println(str);

                mediatorDB.setQuries(queries);
                editRows.clear();
            }
        });



        saveButton_ambul.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.List<Integer> idMo = table_8h.getMoId();
                java.util.List<String> queries = new ArrayList<String>();

                int[] idPC =  table_ambul.getIdPC();

                for (int i=0; i < editRows.size(); i++) {


                    for (int j=8; j < 49; j++) {
                        int a = Integer.parseInt(tmpTable_ambul.getValueAt(editRows.get(i), j).toString());
                        queries.add("UPDATE `v_ambul` SET a_offers_prof = " + a + " WHERE mo_name = " + idMo.get(editRows.get(i)) + " AND profile_care = " + idPC[j-8] + ";");
                    }

                    for (int j=49; j < 51; j++) {
                        int a = Integer.parseInt(tmpTable_ambul.getValueAt(editRows.get(i), j).toString());
                        queries.add("UPDATE `v_ambul` SET a_offers_profuet = " + a + " WHERE mo_name = " + idMo.get(editRows.get(i)) + " AND profile_care = " + (81 + (j-49)) + ";");
                    }

                    for (int j=51; j < 92; j++) {
                        int a = Integer.parseInt(tmpTable_ambul.getValueAt(editRows.get(i), j).toString());
                        queries.add("UPDATE `v_ambul` SET a_offers_neot = " + a + " WHERE mo_name = " + idMo.get(editRows.get(i)) + " AND profile_care = " + idPC[j-51] + ";");
                    }

                    for (int j=92; j < 94; j++) {
                        int a = Integer.parseInt(tmpTable_ambul.getValueAt(editRows.get(i), j).toString());
                        queries.add("UPDATE `v_ambul` SET a_offers_neotuet = " + a + " WHERE mo_name = " + idMo.get(editRows.get(i)) + " AND profile_care = " + (81 + (j-92)) + ";");
                    }

                    for (int j=94; j < 135; j++) {
                        int a = Integer.parseInt(tmpTable_ambul.getValueAt(editRows.get(i), j).toString());
                        queries.add("UPDATE `v_ambul` SET a_offers_dis = " + a + " WHERE mo_name = " + idMo.get(editRows.get(i)) + " AND profile_care = " + idPC[j-94] + ";");
                    }

                    for (int j=135; j < 176; j++) {
                        int a = Integer.parseInt(tmpTable_ambul.getValueAt(editRows.get(i), j).toString());
                        queries.add("UPDATE `v_ambul` SET a_offers_disnum = " + a + " WHERE mo_name = " + idMo.get(editRows.get(i)) + " AND profile_care = " + idPC[j-135] + ";");
                    }

                    for (int j=176; j < 178; j++) {
                        int a = Integer.parseInt(tmpTable_ambul.getValueAt(editRows.get(i), j).toString());
                        queries.add("UPDATE `v_ambul` SET a_offers_disuet = " + a + " WHERE mo_name = " + idMo.get(editRows.get(i)) + " AND profile_care = " + (81 + (j-176)) + ";");
                    }
                }



                mediatorDB.setQuries(queries);
                editRows.clear();
            }
        });


        saveButton_smp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.List<Integer> idMo = table_smp.getMoId();
                java.util.List<String> queries = new ArrayList<String>();

                for (int i=0; i<editRows.size(); i++) {

                    int a = Integer.parseInt(tmpTable_smp.getValueAt(editRows.get(i), 3).toString());

                    queries.add("UPDATE `v_smc` SET `a_offers`=" + a + " WHERE mo_name = " + idMo.get(editRows.get(i)) + ";");

                }



                mediatorDB.setQuries(queries);
                editRows.clear();

            }
        });

        saveButton_8h.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.List<Integer> idMo = table_8h.getMoId();
                java.util.List<String> queries = new ArrayList<String>();

                int[] idPC =  table_8h.getIdPC();

                for (int i=0; i < editRows.size(); i++) {

                    for (int j=26; j < 48; j++) {
                        int a = Integer.parseInt(tmpTable_8h.getValueAt(editRows.get(i), j).toString());


                        queries.add("UPDATE v_8hours SET a_offers_out = " + a + " WHERE mo_name = " + idMo.get(editRows.get(i)) + " AND profile_care = " + idPC[j-26] + ";");

//                        UPDATE `v_24hours` SET `a_offers_nums`=173 WHERE  `id_v24h`=1;

                    }
                }


                mediatorDB.setQuries(queries);
                editRows.clear();


//                for (String str: queries)
//                    System.out.println(str);
            }
        });

        saveButton_24h.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                java.util.List<Integer> idMo = table_24h.getMoId();
                java.util.List<String> queries = new ArrayList<String>();

//                for (int i=0; i<tmpTable_24h.getRowCount(); i++) {
                for (int i=0; i<editRows.size(); i++) {
//                    for (int j=78; j < 150; j++) {
                    for (int j=1; j < 73; j++) {
                        int a = Integer.parseInt(tmpTable_24h.getValueAt(editRows.get(i), j).toString());

                        queries.add("UPDATE v_24hours SET a_offers_nums=" + a + " WHERE mo_name = " + idMo.get(editRows.get(i)) + " AND profile_care = " + j + ";");
//                        queries.add("UPDATE v_24hours SET a_offers_nums=" + a + " WHERE mo_name = " + idMo.get(editRows.get(i)) + " AND profile_care = " + (j-77) + ";");

//                        UPDATE `v_24hours` SET `a_offers_nums`=173 WHERE  `id_v24h`=1;

                    }
                }

//                for (String stri: queries)
//                    System.out.println(stri);

                mediatorDB.setQuries(queries);
                editRows.clear();


            }
        });





        tools1.add(refreshButton_24h);
        tools1.add(saveButton_24h);

        tools2.add(refreshButton_8h);
        tools2.add(saveButton_8h);

        tools3.add(refreshButton_ambul);
        tools3.add(saveButton_ambul);

        tools4.add(refreshButton_smp);
        tools4.add(saveButton_smp);

        tools5.add(refreshButton_other);
        tools5.add(saveButton_other);

        setLayout(new BorderLayout());

//        add(tools1, BorderLayout.NORTH);


        JTabbedPane jTabbedPane = new JTabbedPane();


        // первая вкладка
        table_24h = new Table_24h();
        table_24h.getjTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table_24h.getjTable().getTableHeader().setReorderingAllowed(false);
        JScrollPane scroll1 = new JScrollPane(table_24h.getjTable(), ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        JPanel panel_24h = new JPanel();
        panel_24h.setLayout(new BorderLayout());
        panel_24h.add(tools1, BorderLayout.NORTH);
        panel_24h.add(scroll1);
//        panel_24h.add(new Button("Button"), BorderLayout.EAST);

        jTabbedPane.addTab("Стац.", panel_24h);




        // вторая вкладка

        table_8h = new Table_8h();
        table_8h.getjTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table_8h.getjTable().getTableHeader().setReorderingAllowed(false);
        JScrollPane scroll2 = new JScrollPane(table_8h.getjTable(), ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        JPanel panel_8h = new JPanel();
        panel_8h.setLayout(new BorderLayout());
        panel_8h.add(tools2, BorderLayout.NORTH);
        panel_8h.add(scroll2);

        jTabbedPane.addTab("Днев. стац.", panel_8h);


        // третья вкладка

        table_ambul = new Table_ambul();
        table_ambul.getjTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table_ambul.getjTable().getTableHeader().setReorderingAllowed(false);
        JScrollPane scroll3 = new JScrollPane(table_ambul.getjTable(), ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        JPanel panel_ambul = new JPanel();
        panel_ambul.setLayout(new BorderLayout());
        panel_ambul.add(tools3, BorderLayout.NORTH);
        panel_ambul.add(scroll3);

        jTabbedPane.addTab("Амбул.-поликл.", panel_ambul);


        // четвертая вкладка
        table_smp = new Table_smp();
        table_smp.getjTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table_smp.getjTable().getTableHeader().setReorderingAllowed(false);
        JScrollPane scroll4 = new JScrollPane(table_smp.getjTable(), ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        JPanel panel_smp = new JPanel();
        panel_smp.setLayout(new BorderLayout());
        panel_smp.add(tools4, BorderLayout.NORTH);
        panel_smp.add(scroll4);

        jTabbedPane.addTab("СМП", panel_smp);


        // пятая вкладка
        table_other = new Table_other();
        table_other.getjTable().setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table_other.getjTable().getTableHeader().setReorderingAllowed(false);
        JScrollPane scroll5 = new JScrollPane(table_other.getjTable(), ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        JPanel panel_other = new JPanel();
        panel_other.setLayout(new BorderLayout());
        panel_other.add(tools5, BorderLayout.NORTH);
        panel_other.add(scroll5);

        jTabbedPane.addTab("Другое", panel_other);

        add(jTabbedPane);



        setLocationByPlatform(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }


    class ActionAllSetTarif implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == t_allsetTarif_24h)
                new AllSetTarif(MyConstants.TypeMOCare.H24);
            else if (e.getSource() == t_allsetTarif_8h)
                new AllSetTarif(MyConstants.TypeMOCare.H8);
            else if (e.getSource() == t_allsetTarif_ambul)
                new AllSetTarif(MyConstants.TypeMOCare.AMBUL);
            else if (e.getSource() == t_allsetTarif_smp)
                new AllSetTarif(MyConstants.TypeMOCare.SMP);
//            else if (e.getSource() == t_allsetTarif_other)
//                new AllSetTarif(MyConstants.TypeMOCare.OTHER);
        }
    }
}
























