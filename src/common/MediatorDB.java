package common;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * User: ShuryginAN
 * Date: 29.11.13
 * Time: 15:09
 */


public class MediatorDB {
    private Connection con;
    private Statement st;


    public MediatorDB() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();


            st = con.createStatement();
        }
        catch (Exception e) {

        }

    }


    public int getIdMo(String regnum) {

        int res = 0;

        try {
            ResultSet rs = st.executeQuery("SELECT id_mo FROM medicalorganizations WHERE register_number=" + regnum);

            while (rs.next()) {
                res = rs.getInt("id_mo");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return res;

    }

    public int getIdPC(String name, String age) {
        int res = 0;


//            SELECT profilescare.id_pc FROM profilescare, age, listprofiles
//            WHERE
//            age.id_age = profilescare.age AND
//            listprofiles.id_profile = profilescare.name AND
//            listprofiles.name = 'Эндокринология' AND
//            age.name = 'Дети';


        try {
            ResultSet rs = st.executeQuery("SELECT profilescare.id_pc FROM profilescare, age, listprofiles " +
                    "WHERE " +
                    "age.id_age = profilescare.age AND " +
                    "listprofiles.id_profile = profilescare.name AND " +
                    "listprofiles.name = '" + name + "' AND " +
                    "age.name = '" + age + "';");

            while (rs.next()) {
                res = rs.getInt("id_pc");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }


    public String getProfileName(int id) {

        String profileName = "";

        try {
            ResultSet rs = st.executeQuery("SELECT listprofiles.name, age.name FROM listprofiles, age, profilescare " +
                    "WHERE profilescare.age = age.id_age AND profilescare.name = listprofiles.id_profile AND profilescare.id_pc = " + id);

            while (rs.next()) {
                profileName = rs.getString("listprofiles.name") + " (" + rs.getString("age.name") + ")";
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return profileName;
    }







    public void setQuries (java.util.List<String> list) {
        try {
            for (String str: list) {
                st.executeUpdate(str);
            }
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }


    public String getMCOD(int idMo) {

        String mcod = "";

        try {
            ResultSet rs = st.executeQuery("SELECT register_number FROM medicalorganizations WHERE id_mo = " + idMo);

            while (rs.next()) {
                mcod = rs.getString("register_number");
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return mcod;

    }

    public List<Integer> getIdMos() {

        List<Integer> l = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT DISTINCT medicalorganizations.id_mo FROM v_24hours, medicalorganizations WHERE medicalorganizations.id_mo = v_24hours.mo_name ORDER BY medicalorganizations.sort_for_rates, medicalorganizations.register_number");

            while (rs.next()) {
                l.add(rs.getInt("medicalorganizations.id_mo"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return l;
    }

    public List<Integer> getIdMosRaspr() {

        List<Integer> l = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT DISTINCT medicalorganizations.id_mo FROM rasprv_24h, medicalorganizations WHERE medicalorganizations.id_mo = rasprv_24h.mo ORDER BY medicalorganizations.sort_for_rates_2;");

            while (rs.next()) {
                l.add(rs.getInt("medicalorganizations.id_mo"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return l;
    }

    public List<Integer> getNumAndOfferByVAmbul(int idMo) {

        List<Integer> res = new ArrayList<Integer>();
        List<Integer> l1 = new ArrayList<Integer>();
        List<Integer> l2 = new ArrayList<Integer>();
        List<Integer> l3 = new ArrayList<Integer>();
        List<Integer> l4 = new ArrayList<Integer>();
        List<Integer> l5 = new ArrayList<Integer>();
        List<Integer> l6 = new ArrayList<Integer>();
        List<Integer> l7 = new ArrayList<Integer>();
        List<Integer> l8 = new ArrayList<Integer>();
        List<Integer> l9 = new ArrayList<Integer>();

        l1 = getSumEFHIKLMbyAmbul(idMo);

        for (Integer i: l1)
            res.add(i);

        try {
            ResultSet rs = st.executeQuery("SELECT a_offers_prof, a_offers_neot, a_offers_dis, a_offers_disnum FROM v_ambul WHERE mo_name = '" + idMo + "' AND profile_care <> 0 ORDER BY profile_care;");

            while (rs.next()) {
                l2.add(rs.getInt("a_offers_prof"));
//                l3.add(rs.getInt("a_offers_profuet"));
                l4.add(rs.getInt("a_offers_neot"));
//                l5.add(rs.getInt("a_offers_neotuet"));
                l6.add(rs.getInt("a_offers_dis"));
                l7.add(rs.getInt("a_offers_disnum"));
//                l8.add(rs.getInt("a_offers_disuet"));
//                l1.add(rs.getInt("num_out"));           // F
//                l2.add(rs.getInt("a_offers_out"));      // H
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        for (Integer i: l2)
            res.add(i);

        l9 = getUetVales(idMo);

        res.add(l9.get(0));
        res.add(l9.get(3));

        for (Integer i: l4)
            res.add(i);

        res.add(l9.get(1));
        res.add(l9.get(4));

        for (Integer i: l6)
            res.add(i);

        for (Integer i: l7)
            res.add(i);

        res.add(l9.get(2));
        res.add(l9.get(5));

        l9 = getSumNOPQRSTbyAmbul(idMo);

        for (Integer i: l9)
            res.add(i);

        return res;
    }

    public List<Integer> getNumAndOfferByVOther(int idMo) {
        List<Integer> res = new ArrayList<Integer>();
        List<Integer> l1 = new ArrayList<Integer>();

        l1 = getOtherVal(idMo);

        res.add(l1.get(0));
        res.add(l1.get(2));
        res.add(l1.get(4));
        res.add(l1.get(1));
        res.add(l1.get(3));
        res.add(l1.get(5));

        try {
            ResultSet rs = st.executeQuery("SELECT a_num_8h FROM v_other WHERE mo_name = '" + idMo + "' AND (profile_care = 92);");


            while (rs.next()) {
                res.add(rs.getInt("a_num_8h"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Integer> getNumAndOfferByVSmp(int idMo) {

        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT numberattached.num_smp, v_smc.a_offers FROM v_smc, numberattached WHERE numberattached.id_mo = '" + idMo + "' AND v_smc.mo_name = '" + idMo + "';");

            while (rs.next()) {
                res.add(rs.getInt("numberattached.num_smp"));
                res.add((int)Math.round(rs.getInt("numberattached.num_smp")*0.33));
                res.add(rs.getInt("v_smc.a_offers"));
                //l1.add(rs.getInt("num_out"));           // F
                //l2.add(rs.getInt("a_offers_out"));      // H
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Integer> getNumAndOfferByV8h(int idMo) {

        List<Integer> res = new ArrayList<Integer>();
        List<Integer> l1 = new ArrayList<Integer>();
        List<Integer> l2 = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT num_out, a_offers_out FROM v_8hours WHERE mo_name = '" + idMo + "' ORDER BY profile_care;");

            while (rs.next()) {
                l1.add(rs.getInt("num_out"));           // F
                l2.add(rs.getInt("a_offers_out"));      // H
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        res.add(getSumEF(idMo).get(0));

        for (Integer i: l1)
            res.add(i);

        res.add(getSumEF(idMo).get(1));

        res.add(getSumGHfrom8h(idMo).get(0));

        for (Integer i: l2)
            res.add(i);

        res.add(getSumGHfrom8h(idMo).get(1));

        return res;
    }

    public List<Integer> getNumAndOfferByV24h(int idMo){

        List<Integer> res = new ArrayList<Integer>();
        List<Integer> l1 = new ArrayList<Integer>();
        List<Integer> l2 = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT num_norm, a_offers_nums FROM v_24hours WHERE profile_care <> 0 AND mo_name = '" + idMo + "' ORDER BY profile_care;");

            while (rs.next()) {
//                l1.add(rs.getInt("num_norm"));
                l2.add(rs.getInt("a_offers_nums"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

//        for (Integer i: l1)
//            res.add(i);
//
//        for (Integer i: getSumEFGI(idMo))
//            res.add(i);

        for (Integer i: l2)
            res.add(i);

        res.add(getSumJ(idMo));


        return res;
    }

    private List<Integer> getOtherVal (int idMo) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT a_num_24h, a_num_ambul FROM v_other WHERE v_other.mo_name = " + idMo + " AND (profile_care = 89 OR profile_care = 90 OR profile_care = 91)");

            while (rs.next()) {
                res.add(rs.getInt("a_num_24h"));
                res.add(rs.getInt("a_num_ambul"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    private List<Integer> getUetVales (int idMo) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT a_offers_profuet, a_offers_neotuet, a_offers_disuet FROM v_ambul WHERE mo_name = " + idMo + " AND (profile_care = 81 OR profile_care = 82)");

            while (rs.next()) {
                res.add(rs.getInt("a_offers_profuet"));
                res.add(rs.getInt("a_offers_neotuet"));
                res.add(rs.getInt("a_offers_disuet"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;


//        SELECT v_ambul.a_offers_profuet FROM v_ambul WHERE v_ambul.mo_name = 1 AND (v_ambul.profile_care = 81 OR v_ambul.profile_care = 82)
    }

    private List<Integer> getSumEFHIKLMbyAmbul (int idMo) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT SUM(num_prof), SUM(num_profuet), SUM(num_neot), SUM(num_neotuet), SUM(num_dis), SUM(num_disnum), SUM(num_disuet) FROM v_ambul WHERE mo_name = '" + idMo + "';");

            while (rs.next()) {
                res.add(rs.getInt("SUM(num_prof)"));
                res.add(rs.getInt("SUM(num_profuet)"));
                res.add(rs.getInt("SUM(num_neot)"));
                res.add(rs.getInt("SUM(num_neotuet)"));
                res.add(rs.getInt("SUM(num_dis)"));
                res.add(rs.getInt("SUM(num_disnum)"));
                res.add(rs.getInt("SUM(num_disuet)"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    private List<Integer> getSumNOPQRSTbyAmbul(int idMo) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT SUM(a_offers_prof), SUM(a_offers_profuet), SUM(a_offers_neot), SUM(a_offers_neotuet), SUM(a_offers_dis), SUM(a_offers_disnum), SUM(a_offers_disuet) FROM v_ambul WHERE mo_name = '" + idMo + "';");

            while (rs.next()) {
                res.add(rs.getInt("SUM(a_offers_prof)"));
                res.add(rs.getInt("SUM(a_offers_profuet)"));
                res.add(rs.getInt("SUM(a_offers_neot)"));
                res.add(rs.getInt("SUM(a_offers_neotuet)"));
                res.add(rs.getInt("SUM(a_offers_dis)"));
                res.add(rs.getInt("SUM(a_offers_disnum)"));
                res.add(rs.getInt("SUM(a_offers_disuet)"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    private List<Integer> getSumEF(int idMo) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT SUM(num_norm), SUM(num_out) FROM v_8hours WHERE mo_name = '" + idMo + "';");

            while (rs.next()) {
                res.add(rs.getInt("SUM(num_norm)"));
                res.add(rs.getInt("SUM(num_out)"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    private List<Integer> getSumEFGI(int idMo) {

        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT SUM(num_norm), SUM(num_regcentr), SUM(num_intcentr), SUM(kdays) FROM v_24hours WHERE mo_name = '" + idMo + "';");

            while (rs.next()) {
                res.add(rs.getInt("SUM(num_norm)"));
                res.add(rs.getInt("SUM(num_regcentr)"));
                res.add(rs.getInt("SUM(num_intcentr)"));
                res.add(rs.getInt("SUM(num_norm)") - rs.getInt("SUM(num_regcentr)") + rs.getInt("SUM(num_intcentr)"));
                res.add(rs.getInt("SUM(kdays)"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;

    }


    private List<Integer> getSumGHfrom8h(int idMo) {

        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT SUM(a_offers_nums), SUM(a_offers_out) FROM v_8hours WHERE mo_name = '" + idMo + "';");

            while (rs.next()) {
                res.add(rs.getInt("SUM(a_offers_nums)"));
                res.add(rs.getInt("SUM(a_offers_out)"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    private int getSumJ(int idMo) {
        int res = 0;

        try {
            ResultSet rs = st.executeQuery("SELECT SUM(a_offers_nums) FROM v_24hours WHERE mo_name = '" + idMo + "';");

            while (rs.next()) {
                res = rs.getInt("SUM(a_offers_nums)");
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }






    public String getMoName(int idMo) {
        String str = "";

        try {
            ResultSet rs = st.executeQuery("SELECT name_short FROM medicalorganizations WHERE id_mo = '" + idMo + "';");

            while (rs.next()) {
                str = rs.getString("name_short");
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return str;
    }










//////////////////////////////////////////////////
//    Печать таблиц


    public List<Integer> getPrintV24h(int idMo) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT SUM(num_norm), SUM(num_regcentr), SUM(num_intcentr), SUM(a_offers_nums) FROM v_24hours WHERE mo_name = '" + idMo + "';");

            while (rs.next()) {
                res.add(rs.getInt("SUM(num_norm)"));
                res.add(rs.getInt("SUM(num_regcentr)"));
                res.add(rs.getInt("SUM(num_intcentr)"));
                res.add(rs.getInt("SUM(num_norm)") - rs.getInt("SUM(num_regcentr)") + rs.getInt("SUM(num_intcentr)"));
                res.add(rs.getInt("SUM(a_offers_nums)"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Integer> getPrintV8h (int idMo) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT SUM(num_out), SUM(a_offers_out) FROM v_8hours WHERE mo_name = '" + idMo + "';");

            while (rs.next()) {
                res.add(rs.getInt("SUM(num_out)"));
                res.add(rs.getInt("SUM(a_offers_out)"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Integer> getPrintVAmbul(int idMo) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT " +
                                                "SUM(num_prof), SUM(num_profuet), SUM(num_neot), SUM(num_neotuet), SUM(num_dis), SUM(num_disnum), SUM(num_disuet), " +
                                                "SUM(a_offers_prof), SUM(a_offers_profuet), SUM(a_offers_neot), SUM(a_offers_neotuet), SUM(a_offers_dis), SUM(a_offers_disnum), SUM(a_offers_disuet) " +
                                            "FROM v_ambul " +
                                            "WHERE mo_name = '" + idMo + "';");

            while (rs.next()) {
                res.add(rs.getInt("SUM(num_prof)"));
                res.add(rs.getInt("SUM(num_profuet)"));
                res.add(rs.getInt("SUM(num_neot)"));
                res.add(rs.getInt("SUM(num_neotuet)"));
                res.add(rs.getInt("SUM(num_dis)"));
                res.add(rs.getInt("SUM(num_disnum)"));
                res.add(rs.getInt("SUM(num_disuet)"));
                res.add(rs.getInt("SUM(a_offers_prof)"));
                res.add(rs.getInt("SUM(a_offers_profuet)"));
                res.add(rs.getInt("SUM(a_offers_neot)"));
                res.add(rs.getInt("SUM(a_offers_neotuet)"));
                res.add(rs.getInt("SUM(a_offers_dis)"));
                res.add(rs.getInt("SUM(a_offers_disnum)"));
                res.add(rs.getInt("SUM(a_offers_disuet)"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Integer> getPrintVSmp(int idMo) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT numberattached.num_smp, v_smc.a_offers FROM v_smc, numberattached WHERE numberattached.id_mo = '" + idMo + "' AND v_smc.mo_name = '" + idMo + "'");

            while (rs.next()) {
                res.add(rs.getInt("numberattached.num_smp"));
                res.add((int) (rs.getInt("numberattached.num_smp")*0.33));
                res.add(rs.getInt("v_smc.a_offers"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Integer> getPrintVOther(int idMo) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT a_num_24h, a_num_ambul FROM v_other WHERE mo_name = '" + idMo + "';");

            while (rs.next()) {
                res.add(rs.getInt("a_num_24h"));
                res.add(rs.getInt("a_num_ambul"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        try {
            ResultSet rs = st.executeQuery("SELECT a_num_8h FROM v_other WHERE mo_name = '" + idMo + "';");

            while (rs.next()) {
                res.add(rs.getInt("a_num_8h"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }


        return res;
    }





    public List<Double> getForFirstTarif_24h(int profile) {

        List<Double> res = new ArrayList<Double>();

        try {
            ResultSet rs = st.executeQuery("SELECT cost, k, avertime, fk FROM tarif_norm_24h WHERE id_profile = '" + profile + "';");

            while (rs.next()) {
                res.add(rs.getDouble("cost"));
                res.add(rs.getDouble("k"));
                res.add(rs.getDouble("avertime"));
                res.add(rs.getDouble("fk"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;

    }



    public List<Double> getForFirstTarif_8h(int profile) {

        List<Double> res = new ArrayList<Double>();

        try {
            ResultSet rs = st.executeQuery("SELECT cost, k, avertime FROM tarif_norm_8h WHERE id_profile = '" + profile + "';");

            while (rs.next()) {
                res.add(rs.getDouble("cost"));
                res.add(rs.getDouble("k"));
                res.add(rs.getDouble("avertime"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;

    }

    public List<Double> getForFirstTarif_ambul(int profile) {

        List<Double> res = new ArrayList<Double>();

        try {
            ResultSet rs = st.executeQuery("SELECT k, costprof, costdis, costneot FROM tarif_norm_ambul WHERE id_profile = '" + profile + "';");

            while (rs.next()) {
                res.add(rs.getDouble("k"));
                res.add(rs.getDouble("costprof"));
                res.add(rs.getDouble("costdis"));
                res.add(rs.getDouble("costneot"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;

    }


    public int getTarif24h(int idMo, int idProf) {
        int res = 0;

        try {
            ResultSet rs = st.executeQuery("SELECT tarif FROM tarif_24h WHERE id_mo = " + idMo + " AND profile = " + idProf + " AND act = 1;");

            while (rs.next()) {
                res = rs.getInt("tarif");
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }


    public int getTarif8h(int idMo, int idProf) {
        int res = 0;

        try {
            ResultSet rs = st.executeQuery("SELECT tarif FROM tarif_8h WHERE id_mo = " + idMo + " AND profile = " + idProf + " AND act = 1;");

            while (rs.next()) {
                res = rs.getInt("tarif");
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }



    public List<Integer> getTarifAmbul(int idMo, int idProf) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT tarifprof, tarifdis, tarifneot FROM tarif_ambul WHERE id_mo = " + idMo + " AND profile = " + idProf + " AND act = 1;");

            while (rs.next()) {
                res.add(rs.getInt("tarifprof"));
                res.add(rs.getInt("tarifdis"));
                res.add(rs.getInt("tarifneot"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }



    public List<Double> getTarifOther(int idMo, int idProf) {
        List<Double> res = new ArrayList<Double>();

        try {
            ResultSet rs = st.executeQuery("SELECT tarif24h, tarifambul, tarif8h FROM tarif_other WHERE id_mo = " + idMo + " AND profile = " + idProf + " AND act = 1;");

            while (rs.next()) {
                res.add(rs.getDouble("tarif24h"));
                res.add(rs.getDouble("tarifambul"));
                res.add(rs.getDouble("tarif8h"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public double getTarifSmp(int idMo) {
        double res = 0;

        try {
            ResultSet rs = st.executeQuery("SELECT tarif FROM tarif_smp WHERE id_mo = " + idMo + " AND act = 1;");

            while (rs.next()) {
                res = rs.getDouble("tarif");
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public double getTarif(int idMo, int idProf, String from) {
        double res = 0;

        try {
            ResultSet rs = st.executeQuery("SELECT tarif FROM " + from + " WHERE id_mo = " + idMo + " AND profile = " + idProf + " AND act = 1;");

            while (rs.next()) {
                res = rs.getDouble("tarif");
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public double getTarif(int idMo, int idProf, String from, String what) {
        double res = 0;

        try {
            ResultSet rs = st.executeQuery("SELECT " + what + " FROM " + from + " WHERE id_mo = " + idMo + " AND profile = " + idProf + " AND act = 1;");

            while (rs.next()) {
                res = rs.getDouble(what);
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }


    public List<Integer> getBaseTarif24h(int idMo) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT tarif FROM tarif_24h WHERE id_mo = '" + idMo + "' AND act = 1 ORDER BY profile;");

            while (rs.next()) {
                res.add(rs.getInt("tarif"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Integer> getBaseTarif8h(int idMo) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT tarif FROM tarif_8h WHERE id_mo = '" + idMo + "' AND act = 1 ORDER BY profile;");

            while (rs.next()) {
                res.add(rs.getInt("tarif"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Double> getBaseTarifOther(int idMo) {
        List<Double> res = new ArrayList<Double>();

        try {
            ResultSet rs = st.executeQuery("SELECT tarif24h FROM tarif_other WHERE id_mo = '" + idMo + "' AND profile <> 92 AND act=1 ORDER BY profile;");

            while (rs.next()) {
                res.add(rs.getDouble("tarif24h"));
            }

            rs = st.executeQuery("SELECT tarifambul FROM tarif_other WHERE id_mo = '" + idMo + "' AND profile <> 92 AND act=1 ORDER BY profile;");

            while (rs.next()) {
                res.add(rs.getDouble("tarifambul"));
            }

            rs = st.executeQuery("SELECT tarif8h FROM tarif_other WHERE id_mo = '" + idMo + "' AND profile = 92 AND act=1;");

            while (rs.next()) {
                res.add(rs.getDouble("tarif8h"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Double> getBaseTarifSmp(int idMo) {
        List<Double> res = new ArrayList<Double>();

        try {
            ResultSet rs = st.executeQuery("SELECT tarif FROM tarif_smp WHERE id_mo = '" + idMo + "' AND act = 1;");

            while (rs.next()) {
                res.add(rs.getDouble("tarif"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Integer> getBaseTarifAmbul(int idMo, String type) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT " + type + " FROM tarif_ambul WHERE id_mo = '" + idMo + "' AND act = 1 ORDER BY profile;");

            while (rs.next()) {
                res.add(rs.getInt(type));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Integer> getListV_24h(int idMo) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT a_offers_nums FROM v_24hours WHERE mo_name = '" + idMo + "' ORDER BY profile_care;");

            while (rs.next()) {
                res.add(rs.getInt("a_offers_nums"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Integer> getListV_smp(int idMo) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT a_offers FROM v_smc WHERE mo_name = '" + idMo + "';");

            while (rs.next()) {
                res.add(rs.getInt("a_offers"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Integer> getListV_8h(int idMo) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT a_offers_out FROM v_8hours WHERE mo_name = '" + idMo + "' ORDER BY profile_care;");

            while (rs.next()) {
                res.add(rs.getInt("a_offers_out"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Integer> getListV_other(int idMo) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT a_num_24h FROM v_other WHERE mo_name = '"+idMo+"' AND profile_care <> 92 ORDER BY profile_care;");

            while (rs.next()) {
                res.add(rs.getInt("a_num_24h"));
            }

            rs = st.executeQuery("SELECT a_num_ambul FROM v_other WHERE mo_name = '"+idMo+"' AND profile_care <> 92 ORDER BY profile_care;");

            while (rs.next()) {
                res.add(rs.getInt("a_num_ambul"));
            }

            rs = st.executeQuery("SELECT a_num_8h FROM v_other WHERE mo_name = '"+idMo+"' AND profile_care = 92 ORDER BY profile_care;");

            while (rs.next()) {
                res.add(rs.getInt("a_num_8h"));
            }

        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Integer> getListV_ambul(int idMo, String type) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT " + type + " FROM v_ambul WHERE profile_care <> 0 AND profile_care <> 81 AND profile_care <> 82 AND  mo_name = '" + idMo + "' ORDER BY profile_care;");

            while (rs.next()) {
                res.add(rs.getInt(type));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Integer> getListV_ambulUET(int idMo, String type) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT " + type + " FROM v_ambul WHERE (profile_care = 81 OR profile_care = 82) AND  mo_name = '" + idMo + "' ORDER BY profile_care;");

            while (rs.next()) {
                res.add(rs.getInt(type));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }




    public List<Integer> getListTarif_24h(int idMo) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT tarif FROM tarif_24h WHERE act = 1 AND id_mo = '" + idMo + "' ORDER BY profile;");

            while (rs.next()) {
                res.add(rs.getInt("tarif"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Double> getListTarif_smp(int idMo) {
        List<Double> res = new ArrayList<Double>();

        try {
            ResultSet rs = st.executeQuery("SELECT tarif FROM tarif_smp WHERE act=1 AND id_mo = '" + idMo + "';");

            while (rs.next()) {
                res.add(rs.getDouble("tarif"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Integer> getListTarif_8h(int idMo) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT tarif FROM tarif_8h WHERE act = 1 AND id_mo = '" + idMo + "' ORDER BY profile;");

            while (rs.next()) {
                res.add(rs.getInt("tarif"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

//    public List<Integer> getListTarif_other(int idMo) {
//        List<Integer> res = new ArrayList<Integer>();
//
//        try {
//            ResultSet rs = st.executeQuery("SELECT tarif FROM tarif_8h WHERE id_mo = '" + idMo + "' ORDER BY profile;");
//
//            while (rs.next()) {
//                res.add(rs.getInt("tarif"));
//            }
//        }
//        catch (SQLException sqle) {
//            sqle.printStackTrace();
//        }
//
//        return res;
//    }

    public List<Integer> getListTarif_ambul(int idMo, String type) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT " + type + " FROM tarif_ambul WHERE profile <> 81 AND profile <> 82 AND act = 1 AND  id_mo = '" + idMo + "' ORDER BY profile;");

            while (rs.next()) {
                res.add(rs.getInt(type));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }

    public List<Integer> getListTarif_ambulUET(int idMo, String type) {
        List<Integer> res = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT " + type + " FROM tarif_ambul WHERE (profile = 81 OR profile = 82) AND act = 1 AND  id_mo = '" + idMo + "' ORDER BY profile;");

            while (rs.next()) {
                res.add(rs.getInt(type));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }





    public int getTimeIdent(int idMo, String table) {
        int res = 0;

        try {
            ResultSet rs = st.executeQuery("SELECT MAX(timeident) FROM " + table + " WHERE id_mo = " + idMo + ";");

            while (rs.next()) {
                res = rs.getInt("MAX(timeident)");
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }









    public List<Integer> getRasprQuadro(int idMo, String tableName, MyConstants.TypeRasprTable typeRasprTable) {

        List<Integer> res = new ArrayList<Integer>();

        try {

            int sv_obr_I = 0;
            int sv_obr_II = 0;
            int sv_obr_III = 0;
            int sv_obr_IV = 0;
            int sv_uet_I = 0;
            int sv_uet_II = 0;
            int sv_uet_III = 0;
            int sv_uet_IV = 0;
            int sv_pos_I = 0;
            int sv_pos_II = 0;
            int sv_pos_III = 0;
            int sv_pos_IV = 0;

            int dms_uet_I = 0;
            int dms_uet_II = 0;
            int dms_uet_III = 0;
            int dms_uet_IV = 0;
            int dms_obr_I = 0;
            int dms_pos_I = 0;
            int dms_obr_II = 0;
            int dms_pos_II = 0;
            int dms_obr_III = 0;
            int dms_pos_III = 0;
            int dms_obr_IV = 0;
            int dms_pos_IV = 0;



            ResultSet rs;

            switch (typeRasprTable) {
                case SIMPLE:
                    rs = st.executeQuery("SELECT SUM(dms_I), SUM(sv_I),  SUM(dms_II), SUM(sv_II), SUM(dms_III), SUM(sv_III), SUM(dms_IV), SUM(sv_IV) FROM " + tableName + " WHERE mo='" + idMo + "';");

                    while (rs.next()) {

                        dms_obr_I = rs.getInt("SUM(dms_I)");
                        sv_obr_I = rs.getInt("SUM(sv_I)");
                        dms_obr_II = rs.getInt("SUM(dms_II)");
                        sv_obr_II = rs.getInt("SUM(sv_II)");
                        dms_obr_III = rs.getInt("SUM(dms_III)");
                        sv_obr_III = rs.getInt("SUM(sv_III)");
                        dms_obr_IV = rs.getInt("SUM(dms_IV)");
                        sv_obr_IV = rs.getInt("SUM(sv_IV)");


                        res.add(dms_obr_I);
                        res.add(sv_obr_I);
                        res.add(dms_obr_I + sv_obr_I);

                        res.add(dms_obr_II);
                        res.add(sv_obr_II);
                        res.add(dms_obr_II + sv_obr_II);

                        res.add(dms_obr_III);
                        res.add(sv_obr_III);
                        res.add(dms_obr_III + sv_obr_III);

                        res.add(dms_obr_IV);
                        res.add(sv_obr_IV);
                        res.add(dms_obr_IV + sv_obr_IV);

                        res.add(dms_obr_I + dms_obr_II + dms_obr_III + dms_obr_IV);
                        res.add(sv_obr_I + sv_obr_II + sv_obr_III + sv_obr_IV);

                        res.add(dms_obr_I + dms_obr_II + dms_obr_III + dms_obr_IV + sv_obr_I + sv_obr_II + sv_obr_III + sv_obr_IV);
                    }
                    break;

                case AMBULZAB:

                    rs = st.executeQuery("SELECT SUM(dms_obr_I), SUM(dms_pos_I),  SUM(sv_obr_I), SUM(sv_pos_I), " +
                            "SUM(dms_obr_II), SUM(dms_pos_II),  SUM(sv_obr_II), SUM(sv_pos_II), " +
                            "SUM(dms_obr_III), SUM(dms_pos_III),  SUM(sv_obr_III), SUM(sv_pos_III)," +
                            "SUM(dms_obr_IV), SUM(dms_pos_IV),  SUM(sv_obr_IV), SUM(sv_pos_IV)" +
                            "FROM " + tableName + " WHERE mo='" + idMo + "';");

                    while (rs.next()) {

                        dms_obr_I = rs.getInt("SUM(dms_obr_I)");
                        dms_pos_I = rs.getInt("SUM(dms_pos_I)");
                        sv_obr_I = rs.getInt("SUM(sv_obr_I)");
                        sv_pos_I = rs.getInt("SUM(sv_pos_I)");
                        dms_obr_II = rs.getInt("SUM(dms_obr_II)");
                        dms_pos_II = rs.getInt("SUM(dms_pos_II)");
                        sv_obr_II = rs.getInt("SUM(sv_obr_II)");
                        sv_pos_II = rs.getInt("SUM(sv_pos_II)");
                        dms_obr_III = rs.getInt("SUM(dms_obr_III)");
                        dms_pos_III = rs.getInt("SUM(dms_pos_III)");
                        sv_obr_III = rs.getInt("SUM(sv_obr_III)");
                        sv_pos_III = rs.getInt("SUM(sv_pos_III)");
                        dms_obr_IV = rs.getInt("SUM(dms_obr_IV)");
                        dms_pos_IV = rs.getInt("SUM(dms_pos_IV)");
                        sv_obr_IV = rs.getInt("SUM(sv_obr_IV)");
                        sv_pos_IV = rs.getInt("SUM(sv_pos_IV)");

                        res.add(dms_obr_I);
                        res.add(dms_pos_I);
                        res.add(sv_obr_I);
                        res.add(sv_pos_I);
                        res.add(dms_obr_I + sv_obr_I);
                        res.add(dms_pos_I + sv_pos_I);

                        res.add(dms_obr_II);
                        res.add(dms_pos_II);
                        res.add(sv_obr_II);
                        res.add(sv_pos_II);
                        res.add(dms_obr_II + sv_obr_II);
                        res.add(dms_pos_II + sv_pos_II);

                        res.add(dms_obr_III);
                        res.add(dms_pos_III);
                        res.add(sv_obr_III);
                        res.add(sv_pos_III);
                        res.add(dms_obr_III + sv_obr_III);
                        res.add(dms_pos_III + sv_pos_III);

                        res.add(dms_obr_IV);
                        res.add(dms_pos_IV);
                        res.add(sv_obr_IV);
                        res.add(sv_pos_IV);
                        res.add(dms_obr_IV + sv_obr_IV);
                        res.add(dms_pos_IV + sv_pos_IV);

                        res.add(dms_obr_I + dms_obr_II + dms_obr_III + dms_obr_IV);
                        res.add(dms_pos_I + dms_pos_II + dms_pos_III + dms_pos_IV);

                        res.add(sv_obr_I + sv_obr_II + sv_obr_III + sv_obr_IV);
                        res.add(sv_pos_I + sv_pos_II + sv_pos_III + sv_pos_IV);

                        res.add(dms_obr_I + dms_obr_II + dms_obr_III + dms_obr_IV + sv_obr_I + sv_obr_II + sv_obr_III + sv_obr_IV);
                        res.add(dms_pos_I + dms_pos_II + dms_pos_III + dms_pos_IV + sv_pos_I + sv_pos_II + sv_pos_III + sv_pos_IV);

                    }

                    break;

                case STOMAT:

                    rs = st.executeQuery("SELECT SUM(dms_pos_I), SUM(dms_uet_I), SUM(sv_pos_I), SUM(sv_uet_I), " +
                            "SUM(dms_pos_II), SUM(dms_uet_II), SUM(sv_pos_II), SUM(sv_uet_II), " +
                            "SUM(dms_pos_III), SUM(dms_uet_III), SUM(sv_pos_III), SUM(sv_uet_III), " +
                            "SUM(dms_pos_IV), SUM(dms_uet_IV), SUM(sv_pos_IV), SUM(sv_uet_IV) " +
                            "FROM " + tableName + " WHERE mo='" + idMo + "';");

                    while (rs.next()) {

                        dms_pos_I = rs.getInt("SUM(dms_pos_I)");
                        dms_uet_I = rs.getInt("SUM(dms_uet_I)");
                        sv_pos_I = rs.getInt("SUM(sv_pos_I)");
                        sv_uet_I = rs.getInt("SUM(sv_uet_I)");
                        dms_pos_II = rs.getInt("SUM(dms_pos_II)");
                        dms_uet_II = rs.getInt("SUM(dms_uet_II)");
                        sv_pos_II = rs.getInt("SUM(sv_pos_II)");
                        sv_uet_II = rs.getInt("SUM(sv_uet_II)");
                        dms_pos_III = rs.getInt("SUM(dms_pos_III)");
                        dms_uet_III = rs.getInt("SUM(dms_uet_III)");
                        sv_pos_III = rs.getInt("SUM(sv_pos_III)");
                        sv_uet_III = rs.getInt("SUM(sv_uet_III)");
                        dms_pos_IV = rs.getInt("SUM(dms_pos_IV)");
                        dms_uet_IV = rs.getInt("SUM(dms_uet_IV)");
                        sv_pos_IV = rs.getInt("SUM(sv_pos_IV)");
                        sv_uet_IV = rs.getInt("SUM(sv_uet_IV)");

                        res.add(dms_pos_I);
                        res.add(dms_uet_I);
                        res.add(sv_pos_I);
                        res.add(sv_uet_I);
                        res.add(dms_pos_I + sv_pos_I);
                        res.add(dms_uet_I + sv_uet_I);

                        res.add(dms_pos_II);
                        res.add(dms_uet_II);
                        res.add(sv_pos_II);
                        res.add(sv_uet_II);
                        res.add(dms_pos_II + sv_pos_II);
                        res.add(dms_uet_II + sv_uet_II);

                        res.add(dms_pos_III);
                        res.add(dms_uet_III);
                        res.add(sv_pos_III);
                        res.add(sv_uet_III);
                        res.add(dms_pos_III + sv_pos_III);
                        res.add(dms_uet_III + sv_uet_III);

                        res.add(dms_pos_IV);
                        res.add(dms_uet_IV);
                        res.add(sv_pos_IV);
                        res.add(sv_uet_IV);
                        res.add(dms_pos_IV + sv_pos_IV);
                        res.add(dms_uet_IV + sv_uet_IV);

                        res.add(dms_pos_I + dms_pos_II + dms_pos_III + dms_pos_IV);
                        res.add(dms_uet_I + dms_uet_II + dms_uet_III + dms_uet_IV);
                        res.add(sv_pos_I + sv_pos_II + sv_pos_III + sv_pos_IV);
                        res.add(sv_uet_I + sv_uet_II + sv_uet_III + sv_uet_IV);

                        res.add(dms_pos_I + dms_pos_II + dms_pos_III + dms_pos_IV + sv_pos_I + sv_pos_II + sv_pos_III + sv_pos_IV);
                        res.add(dms_uet_I + dms_uet_II + dms_uet_III + dms_uet_IV + sv_uet_I + sv_uet_II + sv_uet_III + sv_uet_IV);
                    }

                    break;

                case STOMATZAB:

                    rs = st.executeQuery("SELECT SUM(dms_obr_I), SUM(dms_pos_I), SUM(dms_uet_I), SUM(sv_obr_I), SUM(sv_pos_I), SUM(sv_uet_I), " +
                            "SUM(dms_obr_II), SUM(dms_pos_II), SUM(dms_uet_II), SUM(sv_obr_II), SUM(sv_pos_II), SUM(sv_uet_II), " +
                            "SUM(dms_obr_III), SUM(dms_pos_III), SUM(dms_uet_III), SUM(sv_obr_III), SUM(sv_pos_III), SUM(sv_uet_III), " +
                            "SUM(dms_obr_IV), SUM(dms_pos_IV), SUM(dms_uet_IV), SUM(sv_obr_IV), SUM(sv_pos_IV), SUM(sv_uet_IV) " +
                            "FROM " + tableName + " WHERE mo='" + idMo + "';");




                    while (rs.next()) {

                        dms_obr_I = rs.getInt("SUM(dms_obr_I)");
                        dms_pos_I = rs.getInt("SUM(dms_pos_I)");
                        dms_uet_I = rs.getInt("SUM(dms_uet_I)");
                        sv_obr_I = rs.getInt("SUM(sv_obr_I)");
                        sv_pos_I = rs.getInt("SUM(sv_pos_I)");
                        sv_uet_I = rs.getInt("SUM(sv_uet_I)");

                        dms_obr_II = rs.getInt("SUM(dms_obr_II)");
                        dms_pos_II = rs.getInt("SUM(dms_pos_II)");
                        dms_uet_II = rs.getInt("SUM(dms_uet_II)");
                        sv_obr_II = rs.getInt("SUM(sv_obr_II)");
                        sv_pos_II = rs.getInt("SUM(sv_pos_II)");
                        sv_uet_II = rs.getInt("SUM(sv_uet_II)");

                        dms_obr_III = rs.getInt("SUM(dms_obr_III)");
                        dms_pos_III = rs.getInt("SUM(dms_pos_III)");
                        dms_uet_III = rs.getInt("SUM(dms_uet_III)");
                        sv_obr_III = rs.getInt("SUM(sv_obr_III)");
                        sv_pos_III = rs.getInt("SUM(sv_pos_III)");
                        sv_uet_III = rs.getInt("SUM(sv_uet_III)");

                        dms_obr_IV = rs.getInt("SUM(dms_obr_IV)");
                        dms_pos_IV = rs.getInt("SUM(dms_pos_IV)");
                        dms_uet_IV = rs.getInt("SUM(dms_uet_IV)");
                        sv_obr_IV = rs.getInt("SUM(sv_obr_IV)");
                        sv_pos_IV = rs.getInt("SUM(sv_pos_IV)");
                        sv_uet_IV = rs.getInt("SUM(sv_uet_IV)");

                        res.add(dms_obr_I);
                        res.add(dms_pos_I);
                        res.add(dms_uet_I);
                        res.add(sv_obr_I);
                        res.add(sv_pos_I);
                        res.add(sv_uet_I);
                        res.add(dms_obr_I + sv_obr_I);
                        res.add(dms_pos_I + sv_pos_I);
                        res.add(dms_uet_I + sv_uet_I);

                        res.add(dms_obr_II);
                        res.add(dms_pos_II);
                        res.add(dms_uet_II);
                        res.add(sv_obr_II);
                        res.add(sv_pos_II);
                        res.add(sv_uet_II);
                        res.add(dms_obr_II + sv_obr_II);
                        res.add(dms_pos_II + sv_pos_II);
                        res.add(dms_uet_II + sv_uet_II);

                        res.add(dms_obr_III);
                        res.add(dms_pos_III);
                        res.add(dms_uet_III);
                        res.add(sv_obr_III);
                        res.add(sv_pos_III);
                        res.add(sv_uet_III);
                        res.add(dms_obr_III + sv_obr_III);
                        res.add(dms_pos_III + sv_pos_III);
                        res.add(dms_uet_III + sv_uet_III);

                        res.add(dms_obr_IV);
                        res.add(dms_pos_IV);
                        res.add(dms_uet_IV);
                        res.add(sv_obr_IV);
                        res.add(sv_pos_IV);
                        res.add(sv_uet_IV);
                        res.add(dms_obr_IV + sv_obr_IV);
                        res.add(dms_pos_IV + sv_pos_IV);
                        res.add(dms_uet_IV + sv_uet_IV);

                        res.add(dms_obr_I + dms_obr_II + dms_obr_III + dms_obr_IV);
                        res.add(dms_pos_I + dms_pos_II + dms_pos_III + dms_pos_IV);
                        res.add(dms_uet_I + dms_uet_II + dms_uet_III + dms_uet_IV);
                        res.add(sv_obr_I + sv_obr_II + sv_obr_III + sv_obr_IV);
                        res.add(sv_pos_I + sv_pos_II + sv_pos_III + sv_pos_IV);
                        res.add(sv_uet_I + sv_uet_II + sv_uet_III + sv_uet_IV);

                        res.add(dms_obr_I + dms_obr_II + dms_obr_III + dms_obr_IV + sv_obr_I + sv_obr_II + sv_obr_III + sv_obr_IV);
                        res.add(dms_pos_I + dms_pos_II + dms_pos_III + dms_pos_IV + sv_pos_I + sv_pos_II + sv_pos_III + sv_pos_IV);
                        res.add(dms_uet_I + dms_uet_II + dms_uet_III + dms_uet_IV + sv_uet_I + sv_uet_II + sv_uet_III + sv_uet_IV);
                    }

                    break;
            }



        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;
    }


    public List<Integer> getRasprProfile(MyConstants.TypeRasprTable typeRasprTable, String table, int idMo) {

        List<Integer> res = new ArrayList<Integer>();

        String what = "";

        try {
            ResultSet rs;

            switch (typeRasprTable) {
                case SIMPLE:
                    what = "sum_all";
                    break;
                case AMBULZAB:
                    what = "sum_obr, sum_pos";
                    break;
                case STOMAT:
                    what = "sum_pos, sum_uet";
                    break;
                case STOMATZAB:
                    what = "sum_obr, sum_pos, sum_uet";
                    break;
            }


            rs = st.executeQuery("SELECT " + what + " FROM " + table + " WHERE mo = '" + idMo + "' ORDER BY profile;");

            while (rs.next()) {
                switch (typeRasprTable) {
                    case SIMPLE:
                        res.add(rs.getInt("sum_all"));
                        break;
                    case AMBULZAB:
                        res.add(rs.getInt("sum_obr"));
                        res.add(rs.getInt("sum_pos"));
                        break;
                    case STOMAT:
                        res.add(rs.getInt("sum_pos"));
                        res.add(rs.getInt("sum_uet"));
                        break;
                    case STOMATZAB:
                        res.add(rs.getInt("sum_obr"));
                        res.add(rs.getInt("sum_pos"));
                        res.add(rs.getInt("sum_uet"));
                        break;
                }
            }

        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return res;

    }

    public List<Integer> getForExcelRasprProfile(MyConstants.TypeRasprTable typeRasprTable, String table, int idPC) {

        List<Integer> res = new ArrayList<Integer>();
        String what = "";

        try {
            ResultSet rs;

            switch (typeRasprTable) {
                case SIMPLE:
                    what = "sum_all";
                    break;
                case AMBULZAB:
                    what = "sum_obr, sum_pos";
                    break;
                case STOMAT:
                    what = "sum_pos, sum_uet";
                    break;
                case STOMATZAB:
                    what = "sum_obr, sum_pos, sum_uet";
                    break;
            }

            rs = st.executeQuery("SELECT " + table + "." + what + " FROM " + table + ", medicalorganizations WHERE rasprv_8h.profile = 21 AND rasprv_8h.mo = medicalorganizations.id_mo  ORDER BY medicalorganizations.sort_for_rates, medicalorganizations.register_number;");

        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }



        return res;

    }





    // Для 8h и 24h
    public ArrayList<List<Integer>> getArrayTarifs(String tableName, int idMo) {

        ArrayList<List<Integer>> res = new ArrayList<List<Integer>>();
        List<Integer> profiles = new ArrayList<Integer>();
        List<Integer> ages = new ArrayList<Integer>();
        List<Integer> tarifs = new ArrayList<Integer>();

        try {
            ResultSet rs = st.executeQuery("SELECT lp.id_profile, a.id_age, t.tarif FROM " + tableName + " t, age a, profilescare pc, listprofiles lp " +
                    "WHERE t.act = 1 AND t.profile = pc.id_pc AND pc.age = a.id_age AND pc.name = lp.id_profile AND t.id_mo = " + idMo);

            while (rs.next()) {
                profiles.add(rs.getInt("lp.id_profile"));
                ages.add(rs.getInt("a.id_age"));
                tarifs.add(rs.getInt("t.tarif"));
            }
        }
        catch (SQLException sqle) {
            sqle.printStackTrace();
        }


        res.add(profiles);
        res.add(ages);
        res.add(tarifs);

        return res;
    }

















































}
