/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author dacastro
 */
public class FileXML {

    public static boolean crearArchivoXML(Map<Integer, LinkedList<Persona>> m) {

        boolean t = false;
        try {
            Element personas = new Element("personas");
            Document doc = new Document(personas);

            Iterator<Map.Entry<Integer, LinkedList<Persona>>> it
                    = m.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<Integer, LinkedList<Persona>> pair
                        = it.next();
                Element key = new Element("key");
                key.setAttribute(
                        new Attribute("id", String.valueOf(pair.getKey())));
                doc.getRootElement().addContent(key);
                for (int i = 0; i < pair.getValue().size(); i++) {
                    Persona objp = pair.getValue().get(i);
                    Element per = new Element("persona");
                    per.setAttribute(
                            new Attribute("nombre", objp.getNombre()));
                    per.setAttribute(
                            new Attribute("identificacion", objp.getId()));
                    per.setAttribute(
                            new Attribute("edad", String.valueOf(objp.getEdad())));
                    doc.getRootElement().addContent(per);
                }
            }

            // new XMLOutputter().output(doc, System.out);
            XMLOutputter xmlOutput = new XMLOutputter();

            // display nice nice
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter("fileP.xml"));
            t = true;

        } catch (IOException io) {
            System.out.println(io.getMessage());
        }

        return t;

    }

    public static Map<Integer, LinkedList<Persona>> leerXML() {
        Map<Integer, LinkedList<Persona>> m = new HashMap<>();
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File("fileP.xml");
        LinkedList<Persona> lp = null;
        int l = 0;
        try {

            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();

            List list = rootNode.getChildren("key");
            List list2 = rootNode.getChildren("persona");
            for (int i = 0; i < list.size(); i++) {
                lp = new LinkedList<>();
                Element node1 = (Element) list.get(i);
                int key1 = Integer.parseInt(node1.getAttributeValue("id"));

                for (int j = 0; j < list2.size(); j++) {

                    Element node = (Element) list2.get(j);
                    String nombre = node.getAttributeValue("nombre");
                    String identificacion = node.getAttributeValue("identificacion");
                    int edad = Integer.parseInt(node.getAttributeValue("edad"));

                    Persona objp = new Persona(nombre, identificacion, edad);
                    lp.add(objp);
                }

                m.put(key1, lp);

            }
        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }

        return m;
    }

}
