package common;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ShuryginAN
 * Date: 13.01.14
 * Time: 16:09
 */


public class ReportRaspredProfile extends JFrame {

    public enum TableType {H24, H8, AMBULPROF, AMBULNEOT, AMBULZAB, STOMATPROF, STOMATNEOT, STOMATZAB, SMP} ;

    private int[] idPC;
    private int[] idPC8h= {10, 11, 18, 21, 27, 28, 29, 30, 47, 48, 49, 50, 51, 52, 53, 54, 62, 63, 73, 74, 75, 76};
    private int[] idPCAmbul = {6,7,8,9,10,11,18,21,27,28,29,30,45,46,49,50,51,52,53,54,59,60,73,74,75,76,77,78,79,80,83,84,85,86,87,88,94,95,96};
    private int[] idPCStom = {81,82};
    private int[] idPCSmp = {97};
//    private int[] idPCOther = {89, 90, 91, 92};


    private JTable jTable;
    private List<String> columnNamesTmp;
    private List<Integer> moId;

    private MediatorDB mediatorDB;
    private String[] columnNames;
    private Object[][] cells;

    TableType tableType;

    public ReportRaspredProfile(TableType tableType) {

        mediatorDB = new MediatorDB();

        columnNamesTmp = new ArrayList<String>();



        this.tableType = tableType;

        switch (tableType) {
            case H24:
                setTitle(MyConstants.REPORT_PROFILE_H24);
                break;
            case H8:
                setTitle(MyConstants.REPORT_PROFILE_H8);
                break;
            case AMBULPROF:
                setTitle(MyConstants.REPORT_PROFILE_AMBULPROF);
                break;
            case AMBULNEOT:
                setTitle(MyConstants.REPORT_PROFILE_AMBULNEOT);
                break;
            case AMBULZAB:
                setTitle(MyConstants.REPORT_PROFILE_AMBULZAB);
                break;
            case STOMATPROF:
                setTitle(MyConstants.REPORT_PROFILE_STOMATPROF);
                break;
            case STOMATNEOT:
                setTitle(MyConstants.REPORT_PROFILE_STOMATNEOT);
                break;
            case STOMATZAB:
                setTitle(MyConstants.REPORT_PROFILE_STOMATZAB);
                break;
            case SMP:
                setTitle(MyConstants.REPORT_PROFILE_SMP);
                break;
        }

        namesColumn();
        cellsValues();

        jTable = new JTable(cells, columnNames);
        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);


        add(new JScrollPane(jTable));


        setSize(600, 400);


        setLocationByPlatform(true);
        setVisible(true);

    }

    private void namesColumn() {
        columnNamesTmp.add("Название МО");

        switch (tableType) {
            case H24:
                idPC = new int[72];
                for (int i=1; i < 73; i++) {
                    idPC[i-1] = i;
                    columnNamesTmp.add(mediatorDB.getProfileName(i));
                }
                columnNamesTmp.add("Название МО");
                break;
            case H8:
                idPC = idPC8h;
                for (int i=0; i<idPC.length; i++) {
                    columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]));
                }
                columnNamesTmp.add("Название МО");
                break;
            case AMBULPROF:
            case AMBULNEOT:
                idPC = idPCAmbul;
                for (int i=0; i<idPC.length; i++) {
                    columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]));
                }
                columnNamesTmp.add("Название МО");
                break;
            case AMBULZAB:
                idPC = idPCAmbul;
                for (int i=0; i<idPC.length; i++) {
                    columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]) + " обр.");
                    columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]) + " пос.");
                }
                columnNamesTmp.add("Название МО");
                break;
            case STOMATPROF:
            case STOMATNEOT:
                idPC = idPCStom;
                for (int i=0; i<idPC.length; i++) {
                    columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]) + " пос.");
                    columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]) + " УЕТ");
                }
                columnNamesTmp.add("Название МО");
                break;
            case STOMATZAB:
                idPC = idPCStom;
                for (int i=0; i<idPC.length; i++) {
                    columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]) + " обр.");
                    columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]) + " пос.");
                    columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]) + " УЕТ");
                }
                columnNamesTmp.add("Название МО");
                break;
            case SMP:
                idPC = idPCSmp;
                for (int i=0; i<idPC.length; i++) {
                    columnNamesTmp.add(mediatorDB.getProfileName(idPC[i]));
                }
                columnNamesTmp.add("Название МО");
                break;

        }

//        columnNamesTmp.add("Название МО");

        // список в строковый массив
        columnNames = new String[columnNamesTmp.size()];

        for (int i=0; i<columnNamesTmp.size(); i++) {
            columnNames[i] = columnNamesTmp.get(i);
        }
    }

    private void cellsValues() {

        moId = mediatorDB.getIdMosRaspr();
        cells = new Object[moId.size()+1][columnNames.length];


        cells[moId.size()][0] = "Итого";
        cells[moId.size()][columnNames.length-1] = "Итого";

//        for (int i=0; i<cells.length; i++)
//            for (int j=0; j<cells[i].length; j++)
//                cells[i][j] = 0;

        String tableName = "";
        MyConstants.TypeRasprTable typeRasprTable = MyConstants.TypeRasprTable.SIMPLE;

        switch (tableType) {
            case H24:
                tableName = "rasprv_24h";
                typeRasprTable = MyConstants.TypeRasprTable.SIMPLE;

//                setCellsValues(cells, moId, tableName, typeRasprTable);
                break;
            case H8:
                tableName = "rasprv_8h";
                typeRasprTable = MyConstants.TypeRasprTable.SIMPLE;

//                setCellsValues(cells, moId, tableName, typeRasprTable);
                break;
            case AMBULPROF:
                tableName = "rasprv_ambulprof";
                typeRasprTable = MyConstants.TypeRasprTable.SIMPLE;

//                setCellsValues(cells, moId, tableName, typeRasprTable);
                break;
            case AMBULNEOT:
                tableName = "rasprv_ambulneot";
                typeRasprTable = MyConstants.TypeRasprTable.SIMPLE;

//                setCellsValues(cells, moId, tableName, typeRasprTable);
                break;
            case AMBULZAB:
                tableName = "rasprv_ambulzab";
                typeRasprTable = MyConstants.TypeRasprTable.AMBULZAB;

//                setCellsValuesAmbulZab(cells, moId);
//                setNullCellValues(cells, moId, tableName, typeRasprTable);
                break;
            case STOMATPROF:
                tableName = "rasprv_stomatprof";
                typeRasprTable = MyConstants.TypeRasprTable.STOMAT;
                break;
            case STOMATNEOT:
                tableName = "rasprv_stomatneot";
                typeRasprTable = MyConstants.TypeRasprTable.STOMAT;
                break;
            case STOMATZAB:
                tableName = "rasprv_stomatzab";
                typeRasprTable = MyConstants.TypeRasprTable.STOMATZAB;
                break;
            case SMP:
                tableName = "rasprv_smp";
                typeRasprTable = MyConstants.TypeRasprTable.SIMPLE;
//                setCellsValues(cells, moId, tableName, typeRasprTable);

                break;
        }



        for (int i=0; i<moId.size(); i++) {
            cells[i][0] = mediatorDB.getMoName(moId.get(i));

            for (int j=1; j<columnNames.length-1; j++) {
                cells[i][j] = mediatorDB.getRasprProfile(typeRasprTable,tableName,moId.get(i)).get(j-1);
            }


//            if ((typeRasprTable != MyConstants.TypeRasprTable.SMP) || (typeRasprTable != MyConstants.TypeRasprTable.STOMAT) || (typeRasprTable != MyConstants.TypeRasprTable.STOMATZAB)) {
//                for (int j=1; j<columnNames.length-1; j++) {
//                    cells[i][j] = mediatorDB.getRasprProfile(typeRasprTable,tableName,moId.get(i)).get(j-1);
//                }
//            }
//            else {
//                for (int j=1; j<columnNames.length; j++) {
//                    cells[i][j] = mediatorDB.getRasprProfile(typeRasprTable,tableName,moId.get(i)).get(j-1);
//                }
//            }
//
//            if ((typeRasprTable != MyConstants.TypeRasprTable.SMP) || (typeRasprTable != MyConstants.TypeRasprTable.STOMAT) || (typeRasprTable != MyConstants.TypeRasprTable.STOMATZAB))
                cells[i][columnNames.length-1] = mediatorDB.getMoName(moId.get(i));
        }




        long sum = 0;

        for (int j=1; j<columnNames.length-1; j++) {

            sum = 0;
            for (int i=0; i < moId.size(); i++)
                sum += Integer.parseInt(cells[i][j].toString());

            cells[moId.size()][j] = sum;
        }


    }



//    private void setNullCellValues(Object[][] cells, List<Integer> moId, String tableName, MyConstants.TypeRasprTable typeRasprTable) {
//        for (int i=0; i<moId.size(); i++) {
//            cells[i][0] = mediatorDB.getMoName(moId.get(i));
//
//            for (int j=1; j<columnNames.length-1; j++) {
//                cells[i][j] = 0;
//            }
//
//
//            cells[i][columnNames.length-1] = mediatorDB.getMoName(moId.get(i));
//        }
//    }

//    private void setCellsValuesAmbulZab(Object[][] cells, List<Integer> moId) {
//
//        for (int i=0; i<moId.size(); i++) {
//            cells[i][0] = mediatorDB.getMoName(moId.get(i));
//
//            for (int prof = 0; prof < (columnNames.length-2)/2; prof++)  {
//                for (int j=1; j<columnNames.length-1; j+=2) {
////                    cells[i][j] = mediatorDB.getRasprProfileAmbulZab(moId.get(i), idPC[prof], tableName, MyConstants.TypeRasprTable.AMBULZAB).get(0);
////                    cells[i][j+1] = mediatorDB.getRasprProfileAmbulZab(moId.get(i), idPC[prof], tableName, MyConstants.TypeRasprTable.AMBULZAB).get(1);
//                }
//            }
//
//            cells[i][columnNames.length-1] = mediatorDB.getMoName(moId.get(i));
//        }
//
//    }

//    private void setCellsValues(Object[][] cells, List<Integer> moId, String tableName, MyConstants.TypeRasprTable typeRasprTable) {
//        for (int i=0; i<moId.size(); i++) {
//            cells[i][0] = mediatorDB.getMoName(moId.get(i));
//
//            if (typeRasprTable != MyConstants.TypeRasprTable.SMP) {
//                for (int j=1; j<columnNames.length-1; j++) {
//                    cells[i][j] = mediatorDB.getRasprProfile(moId.get(i), idPC[j-1], tableName, typeRasprTable);
//                }
//            }
//            else {
//                for (int j=1; j<columnNames.length; j++) {
//                    cells[i][j] = mediatorDB.getRasprProfile(moId.get(i), idPC[j-1], tableName, typeRasprTable);
//                }
//            }
//
//
//
//            if (typeRasprTable != MyConstants.TypeRasprTable.SMP)
//                cells[i][columnNames.length-1] = mediatorDB.getMoName(moId.get(i));
//        }
//    }
}
