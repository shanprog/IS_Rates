package common;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ShuryginAN
 * Date: 18.12.13
 * Time: 13:49
 */


public class LoadFirstTarif {

    private JTable jTable;
    private List<String> columnNamesTmp;

    private List<String> queries;
    private List<Integer> moId;
    private MediatorDB mediatorDB;


    private String[] columnNames;
    private Object[][] cells;


    private int[] idPC8h = {10, 11, 18, 21, 27, 28, 29, 30, 47, 48, 49, 50, 51, 52, 53, 54, 62, 63, 73, 74, 75, 76};
    private int[] idPCAmbul = {6,7,8,9,10,11,18,21,27,28,29,30,45,46,49,50,51,52,53,54,59,60,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87,88,94,95,96};
    private int[] idPCOther = {89,90,91,92};

    public LoadFirstTarif() {

        moId = new ArrayList<Integer>();
        queries = new ArrayList<String>();
        mediatorDB = new MediatorDB();
        moId = mediatorDB.getIdMos();

        for (int i=0; i < moId.size(); i++) {
            for (int j=1; j<73; j++) {

                List<Double> tarifs = new ArrayList<Double>();
                tarifs = mediatorDB.getForFirstTarif_24h(j);
                long tarif =  Math.round( (tarifs.get(0) * tarifs.get(1) / tarifs.get(3) ) * tarifs.get(2) );

                queries.add("INSERT INTO tarif_24h VALUES(NULL, '" + moId.get(i) + "', '" + j + "', '" + tarif + "', NOW(), 1, 1);");
            }
        }

        for (int i=0; i < moId.size(); i++) {
            for (int j=0; j < idPC8h.length; j++) {

                List<Double> tarifs = new ArrayList<Double>();
                tarifs = mediatorDB.getForFirstTarif_8h(idPC8h[j]);
                long tarif =  Math.round( tarifs.get(0) * tarifs.get(1) * tarifs.get(2) );

                queries.add("INSERT INTO tarif_8h VALUES(NULL, '" + moId.get(i) + "', '" + idPC8h[j] + "', '" + tarif + "', NOW(), 1, 1);");
            }
        }

        for (int i=0; i < moId.size(); i++) {
            for (int j=0; j < idPCAmbul.length; j++) {

                List<Double> tarifs = mediatorDB.getForFirstTarif_ambul(idPCAmbul[j]);

                long tarifProf = Math.round( tarifs.get(0) * tarifs.get(1) );
                long tarifDis = Math.round( tarifs.get(0) * tarifs.get(2) );
                long tarifNeot = Math.round( tarifs.get(0) * tarifs.get(3) );

                queries.add("INSERT INTO tarif_ambul VALUES(NULL, '" + moId.get(i) + "', '" + idPCAmbul[j] + "', '" + tarifProf + "', " + tarifDis + ", " + tarifNeot + ", NOW(), 1, 1);");
            }
        }

        for (int i=0; i < moId.size(); i++) {
            queries.add("INSERT INTO tarif_smp VALUES(NULL, " + moId.get(i) + ", '2113.4', NOW(), 1, 1);") ;
        }

        for (int i=0; i < moId.size(); i++) {
            for (int j=0; j < idPCOther.length; j++) {

                if (idPCOther[j] == 92) {
                    queries.add("INSERT INTO tarif_other VALUES(NULL, " + moId.get(i) + ", " + idPCOther[j] + ", 0, 0, 175577.2, NOW(), 1, 1);");
                }
                else {
                    queries.add("INSERT INTO tarif_other VALUES(NULL, " + moId.get(i) + ", " + idPCOther[j] + ", 9086, 9086, 0, NOW(), 1, 1);");
                }

            }
        }


        mediatorDB.setQuries(queries);

        System.out.println("The End");

    }
}
