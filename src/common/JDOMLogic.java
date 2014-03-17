package common;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JDOMLogic {

    private static MediatorDB mediatorDB = new MediatorDB();

    public static Document create() {
        Element root = new Element("medorgs");


        List<Integer> mos = mediatorDB.getIdMosRaspr();

        for (Integer mo : mos) {
            ArrayList<List<Integer>> tarifs = mediatorDB.getArrayTarifs("tarif_24h", mo);

            Element moElement = new Element("mo");
            moElement.setAttribute("id", mo.toString());
            moElement.setAttribute("mcod", mediatorDB.getMCOD(mo));

            for (int i = 0; i < tarifs.get(0).size(); i++) {
                Element tarifElement = new Element("tarif");
                tarifElement.setAttribute("profile", tarifs.get(0).get(i).toString());
                tarifElement.setAttribute("age", tarifs.get(1).get(i).toString());
                tarifElement.addContent(tarifs.get(2).get(i).toString());

                moElement.addContent(tarifElement);
            }

            root.addContent(moElement);
        }


        return new Document(root);
    }

    public static boolean saveDocument(String fileName, Document doc) {
        boolean complete = true;
        XMLOutputter outputter = new XMLOutputter();
        try {
            outputter.output(doc, new FileOutputStream(fileName));
        }
        catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            complete = false;
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
            complete = false;
        }

        return complete;
    }

}
