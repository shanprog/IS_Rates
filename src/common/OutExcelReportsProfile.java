package common;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ShuryginAN
 * Date: 24.01.14
 * Time: 9:00
 */


public class OutExcelReportsProfile {

    private List<String> columnNamesTmp;
    private List<Integer> moId;

    private MediatorDB mediatorDB;
    private String[] columnNames;
    private Object[][] cells;

    ReportRaspredQuadro.TableType tableType;

    private int[] idPC;
    private int[] idPC8h= {10, 11, 18, 21, 27, 28, 29, 30, 47, 48, 49, 50, 51, 52, 53, 54, 62, 63, 73, 74, 75, 76};
    private int[] idPCAmbul = {6,7,8,9,10,11,18,21,27,28,29,30,45,46,49,50,51,52,53,54,59,60,73,74,75,76,77,78,79,80,83,84,85,86,87,88,94,95,96};
    private int[] idPCStom = {81,82};
    private int[] idPCSmp = {97};


    public OutExcelReportsProfile() {

        mediatorDB  = new MediatorDB();
        columnNamesTmp = new ArrayList<String>();

        try {

            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("reportProfile.xls"));

            HSSFWorkbook wb = new HSSFWorkbook(fs);

            HSSFSheet sheet = null;
            HSSFRow row = null;
            HSSFCell cell = null;

            for (ReportRaspredQuadro.TableType tt: ReportRaspredQuadro.TableType.values()) {

                tableType = tt;

                switch (tableType) {
                    case H24:
                        sheet = wb.getSheetAt(0);
                        break;
                    case H8:
                        sheet = wb.getSheetAt(1);
                        break;
                    case AMBULPROF:
                        sheet = wb.getSheetAt(2);
                        break;
                    case AMBULNEOT:
                        sheet = wb.getSheetAt(3);
                        break;
                    case AMBULZAB:
                        sheet = wb.getSheetAt(4);
                        break;
                    case STOMATPROF:
                        sheet = wb.getSheetAt(5);
                        break;
                    case STOMATNEOT:
                        sheet = wb.getSheetAt(6);
                        break;
                    case STOMATZAB:
                        sheet = wb.getSheetAt(7);
                        break;
                    case SMP:
                        sheet = wb.getSheetAt(8);
                        break;
                }


                namesColumn();
                cellsValues();

                for (int i=0; i < cells.length; i++) {
                    row = sheet.createRow(i);
                    for (int j=0; j < cells[i].length; j++) {
                        cell = row.createCell(j);



                        if (j != 0 && i != 0)
                            cell.setCellValue(Integer.parseInt(cells[i][j].toString()));
                        else
                            cell.setCellValue(cells[i][j].toString());


//                        if (i == cells.length-1) {
//                            if (j != 0) {
//                                String columnName = MyConstants.NamesColumnExcel[j];
//                                cell.setCellFormula("SUM(" + columnName + "6:" + columnName + (6+cells.length-2) + ")");
//                            }
//                            else
//                                cell.setCellValue("Итого");
//
//                        }
                    }
                }


                // Вывод информации
//                for (int i = 0; i < cells.length; i++) {
//                    for (int j = 0; j < cells[i].length; j++) {
//                        System.out.print(cells[i][j] + " ");
//                    }
//                    System.out.println();
//                }
            }


            FileOutputStream fileOut = new FileOutputStream("outputProfile.xls");
            wb.write(fileOut);
            fileOut.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }





    }

    private void namesColumn() {
        moId = mediatorDB.getIdMosRaspr();

        columnNamesTmp.add("Профиль");

        for (int i=0; i < moId.size(); i++){
            columnNamesTmp.add(mediatorDB.getMoName(moId.get(i)));
        }


        // список в строковый массив
        columnNames = new String[columnNamesTmp.size()];

        for (int i=0; i<columnNamesTmp.size(); i++) {
            columnNames[i] = columnNamesTmp.get(i);
        }

    }

    private void cellsValues() {

        String tableName = "";
        MyConstants.TypeRasprTable typeRasprTable;

        switch (tableType) {
            case H24:
                idPC = new int[72];
                for (int i=1; i < 73; i++) {
                    idPC[i-1] = i;
                }
                tableName = "rasprv_24h";
                typeRasprTable = MyConstants.TypeRasprTable.SIMPLE;
                break;
            case H8:
                idPC = idPC8h;
                tableName = "rasprv_8h";
                typeRasprTable = MyConstants.TypeRasprTable.SIMPLE;
                break;
            case AMBULPROF:
                idPC = idPCAmbul;
                tableName = "rasprv_ambulprof";
                typeRasprTable = MyConstants.TypeRasprTable.SIMPLE;
                break;
            case AMBULNEOT:
                idPC = idPCAmbul;
                tableName = "rasprv_ambulneot";
                typeRasprTable = MyConstants.TypeRasprTable.SIMPLE;
                break;
            case AMBULZAB:
                idPC = idPCAmbul;
                tableName = "rasprv_ambulzab";
                typeRasprTable = MyConstants.TypeRasprTable.AMBULZAB;
                break;
            case STOMATPROF:
                idPC = idPCStom;
                tableName = "rasprv_stomatprof";
                typeRasprTable = MyConstants.TypeRasprTable.STOMAT;
                break;
            case STOMATNEOT:
                idPC = idPCStom;
                tableName = "rasprv_stomatneot";
                typeRasprTable = MyConstants.TypeRasprTable.STOMAT;
                break;
            case STOMATZAB:
                idPC = idPCStom;
                tableName = "rasprv_stomatzab";
                typeRasprTable = MyConstants.TypeRasprTable.STOMATZAB;
                break;

            case SMP:
                idPC = idPCSmp;
                tableName = "rasprv_smp";
                typeRasprTable = MyConstants.TypeRasprTable.SIMPLE;
                break;
            default:
                idPC = new int[72];
                for (int i=1; i < 73; i++) {
                    idPC[i-1] = i;
                }
                tableName = "rasprv_24h";
                typeRasprTable = MyConstants.TypeRasprTable.SIMPLE;
                break;
        }


        List<String> profiles = new ArrayList<String>();


        switch (typeRasprTable) {
            case SIMPLE:

                cells = new Object[idPC.length+1][moId.size()+1];

                for (int i = 1; i<idPC.length+1; i++) {
                    cells[i][0] = mediatorDB.getProfileName(idPC[i-1]);
                }
                break;
            case AMBULZAB:

                profiles = new ArrayList<String>();
                cells = new Object[idPC.length*2+1][moId.size()+1];

                for (int i=0; i<idPC.length; i++) {
                    profiles.add(mediatorDB.getProfileName(idPC[i]) + " обр.");
                    profiles.add(mediatorDB.getProfileName(idPC[i]) + " пос.");
                }

                for (int i = 1; i<profiles.size()+1; i++)
                    cells[i][0] = profiles.get(i-1);

                break;

            case STOMAT:

                cells = new Object[idPC.length*2+1][moId.size()+1];

                profiles = new ArrayList<String>();

                for (int i=0; i<idPC.length; i++) {
                    profiles.add(mediatorDB.getProfileName(idPC[i]) + " пос.");
                    profiles.add(mediatorDB.getProfileName(idPC[i]) + " УЕТ");
                }

                for (int i = 1; i<profiles.size()+1; i++)
                    cells[i][0] = profiles.get(i-1);

                break;

            case STOMATZAB:

                cells = new Object[idPC.length*3+1][moId.size()+1];

                profiles = new ArrayList<String>();

                for (int i=0; i<idPC.length; i++) {
                    profiles.add(mediatorDB.getProfileName(idPC[i]) + " обр.");
                    profiles.add(mediatorDB.getProfileName(idPC[i]) + " пос.");
                    profiles.add(mediatorDB.getProfileName(idPC[i]) + " УЕТ");
                }

                for (int i = 1; i<profiles.size()+1; i++)
                    cells[i][0] = profiles.get(i-1);

                break;


            default:
                for (int i = 1; i<idPC.length+1; i++) {
                    cells[i][0] = mediatorDB.getProfileName(idPC[i-1]);
                }
        }


        for (int i=0; i<moId.size()+1; i++) {
            cells[0][i] = columnNames[i];
        }


        for (int j = 1; j < moId.size()+1; j++) {
            for (int i = 1; i < cells.length; i++) {
                cells[i][j] = mediatorDB.getRasprProfile(typeRasprTable,tableName,moId.get(j-1)).get(i-1);
            }
        }


//        for (int i=1; i < idPC8h.length+1; i++) {
//
//            for (int j=1; j < moId.size()+1; j++){
//                cells[i][j] = 0;
//            }
//        }

    }

}
