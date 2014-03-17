package common;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ShuryginAN
 * Date: 13.01.14
 * Time: 16:09
 */


public class ReportRaspredQuadro extends JFrame {

    public enum TableType {H24, H8, AMBULPROF, AMBULNEOT, AMBULZAB, STOMATPROF, STOMATNEOT, STOMATZAB, SMP} ;

    private JTable jTable;
    private List<String> columnNamesTmp;
    private List<Integer> moId;

    private MediatorDB mediatorDB;
    private String[] columnNames;
    private Object[][] cells;

    TableType tableType;

    public ReportRaspredQuadro(TableType tableType ) {

        mediatorDB = new MediatorDB();

        columnNamesTmp = new ArrayList<String>();

        this.tableType = tableType;

        switch (tableType) {
            case H24:
                setTitle(MyConstants.REPORT_QUADRO_H24);
                break;
            case H8:
                setTitle(MyConstants.REPORT_QUADRO_H8);
                break;
            case AMBULPROF:
                setTitle(MyConstants.REPORT_QUADRO_AMBULPROF);
                break;
            case AMBULNEOT:
                setTitle(MyConstants.REPORT_QUADRO_AMBULNEOT);
                break;
            case AMBULZAB:
                setTitle(MyConstants.REPORT_QUADRO_AMBULZAB);
                break;
            case STOMATPROF:
                setTitle(MyConstants.REPORT_QUADRO_STOMATPROF);
                break;
            case STOMATNEOT:
                setTitle(MyConstants.REPORT_QUADRO_STOMATNEOT);
                break;
            case STOMATZAB:
                setTitle(MyConstants.REPORT_QUADRO_STOMATZAB);
                break;
            case SMP:
                setTitle(MyConstants.REPORT_QUADRO_SMP);
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
            case H8:
            case AMBULPROF:
            case AMBULNEOT:
            case SMP:
                columnNamesTmp.add("I кв (ДМС)");
                columnNamesTmp.add("I кв (СВ)");
                columnNamesTmp.add("I кв (ИТОГО)");
                columnNamesTmp.add("II кв (ДМС)");
                columnNamesTmp.add("II кв (СВ)");
                columnNamesTmp.add("II кв (ИТОГО)");
                columnNamesTmp.add("III кв (ДМС)");
                columnNamesTmp.add("III кв (СВ)");
                columnNamesTmp.add("III кв (ИТОГО)");
                columnNamesTmp.add("IV кв (ДМС)");
                columnNamesTmp.add("IV кв (СВ)");
                columnNamesTmp.add("IV кв (ИТОГО)");
                columnNamesTmp.add("Год (ДМС)");
                columnNamesTmp.add("Год (СВ)");
                columnNamesTmp.add("Год (ИТОГО)");
                break;
            case AMBULZAB:
                columnNamesTmp.add("I кв (ДМС) обращ.");
                columnNamesTmp.add("I кв (ДМС) посещ.");
                columnNamesTmp.add("I кв (СВ) обращ.");
                columnNamesTmp.add("I кв (СВ) посещ.");
                columnNamesTmp.add("ИТОГО I кв обращ.");
                columnNamesTmp.add("ИТОГО I кв посещ.");
                columnNamesTmp.add("II кв (ДМС) обращ.");
                columnNamesTmp.add("II кв (ДМС) посещ.");
                columnNamesTmp.add("II кв (СВ) обращ.");
                columnNamesTmp.add("II кв (СВ) посещ.");
                columnNamesTmp.add("ИТОГО II кв обращ.");
                columnNamesTmp.add("ИТОГО II кв посещ.");
                columnNamesTmp.add("III кв (ДМС) обращ.");
                columnNamesTmp.add("III кв (ДМС) посещ.");
                columnNamesTmp.add("III кв (СВ) обращ.");
                columnNamesTmp.add("III кв (СВ) посещ.");
                columnNamesTmp.add("ИТОГО III кв обращ.");
                columnNamesTmp.add("ИТОГО III кв посещ.");
                columnNamesTmp.add("IV кв (ДМС) обращ.");
                columnNamesTmp.add("IV кв (ДМС) посещ.");
                columnNamesTmp.add("IV кв (СВ) обращ.");
                columnNamesTmp.add("IV кв (СВ) посещ.");
                columnNamesTmp.add("ИТОГО IV кв обращ.");
                columnNamesTmp.add("ИТОГО IV кв посещ.");
                columnNamesTmp.add("Год (ДМС) обращ.");
                columnNamesTmp.add("Год (ДМС) посещ.");
                columnNamesTmp.add("Год (СВ) обращ.");
                columnNamesTmp.add("Год (СВ) посещ.");
                columnNamesTmp.add("ИТОГО обращ.");
                columnNamesTmp.add("ИТОГО посещ.");
                break;
            case STOMATPROF:
            case STOMATNEOT:
                columnNamesTmp.add("I кв (ДМС) посещ.");
                columnNamesTmp.add("I кв (ДМС) УЕТ");
                columnNamesTmp.add("I кв (СВ) посещ.");
                columnNamesTmp.add("I кв (СВ) УЕТ");
                columnNamesTmp.add("ИТОГО I кв посещ.");
                columnNamesTmp.add("ИТОГО I кв УЕТ");
                columnNamesTmp.add("II кв (ДМС) посещ.");
                columnNamesTmp.add("II кв (ДМС) УЕТ");
                columnNamesTmp.add("II кв (СВ) посещ.");
                columnNamesTmp.add("II кв (СВ) УЕТ");
                columnNamesTmp.add("ИТОГО II кв посещ.");
                columnNamesTmp.add("ИТОГО II кв УЕТ");
                columnNamesTmp.add("III кв (ДМС) посещ.");
                columnNamesTmp.add("III кв (ДМС) УЕТ");
                columnNamesTmp.add("III кв (СВ) посещ.");
                columnNamesTmp.add("III кв (СВ) УЕТ");
                columnNamesTmp.add("ИТОГО III кв посещ.");
                columnNamesTmp.add("ИТОГО III кв УЕТ");
                columnNamesTmp.add("IV кв (ДМС) посещ.");
                columnNamesTmp.add("IV кв (ДМС) УЕТ");
                columnNamesTmp.add("IV кв (СВ) посещ.");
                columnNamesTmp.add("IV кв (СВ) УЕТ");
                columnNamesTmp.add("ИТОГО IV кв посещ.");
                columnNamesTmp.add("ИТОГО IV кв УЕТ");
                columnNamesTmp.add("Год (ДМС) посещ.");
                columnNamesTmp.add("Год (ДМС) УЕТ");
                columnNamesTmp.add("Год (СВ) посещ.");
                columnNamesTmp.add("Год (СВ) УЕТ");
                columnNamesTmp.add("ИТОГО Год посещ.");
                columnNamesTmp.add("ИТОГО Год УЕТ");
                break;
            case STOMATZAB:
                columnNamesTmp.add("I кв (ДМС) обращ.");
                columnNamesTmp.add("I кв (ДМС) посещ.");
                columnNamesTmp.add("I кв (ДМС) УЕТ");
                columnNamesTmp.add("I кв (СВ) обращ.");
                columnNamesTmp.add("I кв (СВ) посещ.");
                columnNamesTmp.add("I кв (СВ) УЕТ");
                columnNamesTmp.add("ИТОГО I кв обращ.");
                columnNamesTmp.add("ИТОГО I кв посещ.");
                columnNamesTmp.add("ИТОГО I кв УЕТ");
                columnNamesTmp.add("II кв (ДМС) обращ.");
                columnNamesTmp.add("II кв (ДМС) посещ.");
                columnNamesTmp.add("II кв (ДМС) УЕТ");
                columnNamesTmp.add("II кв (СВ) обращ.");
                columnNamesTmp.add("II кв (СВ) посещ.");
                columnNamesTmp.add("II кв (СВ) УЕТ");
                columnNamesTmp.add("ИТОГО II кв обращ.");
                columnNamesTmp.add("ИТОГО II кв посещ.");
                columnNamesTmp.add("ИТОГО II кв УЕТ");
                columnNamesTmp.add("III кв (ДМС) обращ.");
                columnNamesTmp.add("III кв (ДМС) посещ.");
                columnNamesTmp.add("III кв (ДМС) УЕТ");
                columnNamesTmp.add("III кв (СВ) обращ.");
                columnNamesTmp.add("III кв (СВ) посещ.");
                columnNamesTmp.add("III кв (СВ) УЕТ");
                columnNamesTmp.add("ИТОГО III кв обращ.");
                columnNamesTmp.add("ИТОГО III кв посещ.");
                columnNamesTmp.add("ИТОГО III кв УЕТ");
                columnNamesTmp.add("IV кв (ДМС) обращ.");
                columnNamesTmp.add("IV кв (ДМС) посещ.");
                columnNamesTmp.add("IV кв (ДМС) УЕТ");
                columnNamesTmp.add("IV кв (СВ) обращ.");
                columnNamesTmp.add("IV кв (СВ) посещ.");
                columnNamesTmp.add("IV кв (СВ) УЕТ");
                columnNamesTmp.add("ИТОГО IV кв обращ.");
                columnNamesTmp.add("ИТОГО IV кв посещ.");
                columnNamesTmp.add("ИТОГО IV кв УЕТ");
                columnNamesTmp.add("Год (ДМС) обращ.");
                columnNamesTmp.add("Год (ДМС) посещ.");
                columnNamesTmp.add("Год (ДМС) УЕТ");
                columnNamesTmp.add("Год (СВ) обращ.");
                columnNamesTmp.add("Год (СВ) посещ.");
                columnNamesTmp.add("Год (СВ) УЕТ");
                columnNamesTmp.add("ИТОГО Год обращ.");
                columnNamesTmp.add("ИТОГО Год посещ.");
                columnNamesTmp.add("ИТОГО Год УЕТ");
                break;

        }

        columnNamesTmp.add("Название МО");

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

        String tableName = "";
        MyConstants.TypeRasprTable typeRasprTable = MyConstants.TypeRasprTable.SIMPLE;

        switch (tableType) {
            case H24:
                tableName = "rasprv_24h";
                typeRasprTable = MyConstants.TypeRasprTable.SIMPLE;
                break;
            case H8:
                tableName = "rasprv_8h";
                typeRasprTable = MyConstants.TypeRasprTable.SIMPLE;
                break;
            case AMBULPROF:
                tableName = "rasprv_ambulprof";
                typeRasprTable = MyConstants.TypeRasprTable.SIMPLE;
                break;
            case AMBULNEOT:
                tableName = "rasprv_ambulneot";
                typeRasprTable = MyConstants.TypeRasprTable.SIMPLE;
                break;
            case AMBULZAB:
                tableName = "rasprv_ambulzab";
                typeRasprTable = MyConstants.TypeRasprTable.AMBULZAB;
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
                break;
        }


        for (int i=0; i<moId.size(); i++) {
            cells[i][0] = mediatorDB.getMoName(moId.get(i));

            List<Integer> list = mediatorDB.getRasprQuadro(moId.get(i),tableName, typeRasprTable);

            for (int j=1; j<columnNames.length-1; j++) {
                cells[i][j] = list.get(j - 1);
            }

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
}
