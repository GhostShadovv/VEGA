package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.charset.StandardCharsets;

import static java.nio.charset.StandardCharsets.ISO_8859_1;

public class Main {

    public static void main(String[] args) {
	// write your code here
        try {
            Path file = Paths.get("PSATSim/output.xml");
            BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
            FileTime compare;
            do {
                String filepath = "PSATSim/default_cfg.xml";
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(filepath);

                Element sortie = (Element) document.getElementsByTagName("general").item(0);
                sortie.getAttributeNode("superscalar").setNodeValue("1");

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(new File(filepath));
                transformer.transform(source, result);

                Runtime.getRuntime().exec("cmd /c start C:/Users/razva/IdeaProjects/VEGA/PSATSim/command.bat", null, new File("PSATSim"));

                Path xmlPath = Paths.get("PSATSim/output.xml");
                byte[] bytes = Files.readAllBytes(xmlPath);
                String xml = new String(bytes, ISO_8859_1);
                xml = xml.replaceAll("^\\s*<(w+)[^>]*>[^<]+$", "$0</$1>");
                StringReader stringReader = new StringReader(xml);
                XMLInputFactory factory1 =  XMLInputFactory.newInstance();
                XMLEventReader reader = factory1.createXMLEventReader(stringReader);

                // Parse then print the XML, to ensure there are no errors
                 document = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                        .parse(new InputSource(new StringReader(xml)));
                TransformerFactory.newInstance().newTransformer()
                        .transform(new DOMSource(document), new StreamResult(System.out));

                filepath = "PSATSim/output.xml";
                factory = DocumentBuilderFactory.newInstance();
                builder = factory.newDocumentBuilder();
                document = builder.parse(filepath);
                sortie = (Element) document.getElementsByTagName("general").item(0);
                String power = sortie.getAttributeNode("power").getValue();

                Path file2 = Paths.get("PSATSim/output.xml");
                BasicFileAttributes attr2 = Files.readAttributes(file, BasicFileAttributes.class);
                compare = attr2.lastModifiedTime();
            }while(attr.lastModifiedTime()!=compare);

        }

        catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            //sae.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
