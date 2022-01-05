package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.moeaframework.algorithm.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        try {
            int scalar;
            int rename;
            int reorder;
            List<String> rsbArhitecture = new ArrayList<>();
            rsbArhitecture.add("centralized");
            rsbArhitecture.add("hybrid");
            rsbArhitecture.add("distributed");
            int rsPerBuffer;
            List<String> executionArhit = new ArrayList<>();
            executionArhit.add("standard");
            executionArhit.add("simple");
            executionArhit.add("complex");
            HashMap<Double,DataType> dataTypes = new HashMap<>();
            int ct = 0;
            boolean separateDispatch = false;
            for (scalar = 1; scalar <= 16; scalar++) {
                for (rename = 1; rename <= 255; rename++) {
                    ct++;
                    System.out.println(ct+" din 4080");
                    for (reorder = 1; reorder <= 255; reorder++) {
                        for (int sepDispatch = 0; sepDispatch <= 1; sepDispatch++) {
                            for (int rsbArhit = 0; rsbArhit <= 2; rsbArhit++) {
                                for (rsPerBuffer = 1; rsPerBuffer <= 8; rsPerBuffer++) {
                                    for (int execArhit = 0; execArhit <= 2; execArhit++) {
                                        if (execArhit == 0) {
                                            for (int integer = 1; integer <= 8; integer++) {
                                                for (int floating = 1; floating <= 8; floating++) {
                                                    for (int branch = 1; branch <= 8; branch++) {
                                                        for (int memory = 1; memory <= 8; memory++) {
                                                            String filepath = "VEGA/PSATSim/default_cfg.xml";
                                                            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                                                            DocumentBuilder builder = factory.newDocumentBuilder();
                                                            Document document = builder.parse(filepath);

                                                            Element sortie = (Element) document.getElementsByTagName("general").item(0);

                                                            sortie.getAttributeNode("superscalar").setNodeValue(Integer.toString(scalar));
                                                            sortie.getAttributeNode("rename").setNodeValue(Integer.toString(rename));
                                                            sortie.getAttributeNode("reorder").setNodeValue(Integer.toString(reorder));
                                                            sortie.getAttributeNode("separate_dispatch").setNodeValue(sepDispatch == 0 ? "false" : "true");
                                                            sortie.getAttributeNode("rs_per_rsb").setNodeValue(Integer.toString(rsPerBuffer));

                                                            Element sortieExec = (Element) document.getElementsByTagName("execution").item(0);

                                                            sortieExec.getAttributeNode("architecture").setNodeValue(executionArhit.get(execArhit));
                                                            sortieExec.getAttributeNode("integer").setNodeValue(Integer.toString(integer));
                                                            sortieExec.getAttributeNode("floating").setNodeValue(Integer.toString(floating));
                                                            sortieExec.getAttributeNode("branch").setNodeValue(Integer.toString(branch));
                                                            sortieExec.getAttributeNode("memory").setNodeValue(Integer.toString(memory));


                                                            TransformerFactory transformerFactory = TransformerFactory.newInstance();
                                                            Transformer transformer = transformerFactory.newTransformer();
                                                            DOMSource source = new DOMSource(document);
                                                            StreamResult result = new StreamResult(new File(filepath));
                                                            transformer.transform(source, result);


                                                            Process t = Runtime.getRuntime().exec("cmd /c D:\\GitHub\\VEGA\\VEGA\\PSATSim/command.bat", null, new File("VEGA/PSATSim"));
                                                            t.waitFor();

                                                            double ipc = 0;
                                                            double power = 0;
                                                            File xmlFile = new File("VEGA/PSATSim/output.xml");

                                                            Reader fileReader = new FileReader(xmlFile);
                                                            BufferedReader bufReader = new BufferedReader(fileReader);

                                                            String line;
                                                            int lineNumber = 0;
                                                            while ((line = bufReader.readLine()) != null) {
                                                                if (lineNumber == 29)
                                                                    ipc = Double.parseDouble(line.replaceAll("[a-zA-Z>\"=]", ""));
                                                                if (lineNumber == 31) {
                                                                    power = Double.parseDouble(line.replaceAll("[a-zA-Z>\"=]", ""));
                                                                    bufReader.close();
                                                                    break;
                                                                }
                                                                lineNumber++;
                                                            }
                                                            dataTypes.put((double)dataTypes.size(), new DataType(ipc, power, scalar,rename,reorder, executionArhit.get(execArhit), separateDispatch,integer,
                                                                    floating, branch, memory));
                                                        }
                                                    }
                                                }
                                            }
                                        }

                                        if (execArhit == 1) {
                                            for (int alu = 1; alu <= 8; alu++) {
                                                for (int branch = 1; branch <= 8; branch++) {
                                                    for (int memory = 1; memory <= 8; memory++) {
                                                        String filepath = "VEGA/PSATSim/default_cfg.xml";
                                                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                                                        DocumentBuilder builder = factory.newDocumentBuilder();
                                                        Document document = builder.parse(filepath);

                                                        Element sortie = (Element) document.getElementsByTagName("general").item(0);

                                                        sortie.getAttributeNode("superscalar").setNodeValue(Integer.toString(scalar));
                                                        sortie.getAttributeNode("rename").setNodeValue(Integer.toString(rename));
                                                        sortie.getAttributeNode("reorder").setNodeValue(Integer.toString(reorder));
                                                        sortie.getAttributeNode("separate_dispatch").setNodeValue(sepDispatch == 0 ? "false" : "true");
                                                        sortie.getAttributeNode("rs_per_rsb").setNodeValue(Integer.toString(rsPerBuffer));

                                                        Element sortieExec = (Element) document.getElementsByTagName("execution").item(0);

                                                        sortieExec.getAttributeNode("architecture").setNodeValue(executionArhit.get(execArhit));
                                                        sortieExec.getAttributeNode("alu").setNodeValue(Integer.toString(alu));
                                                        sortieExec.getAttributeNode("branch").setNodeValue(Integer.toString(branch));
                                                        sortieExec.getAttributeNode("memory").setNodeValue(Integer.toString(memory));


                                                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                                                        Transformer transformer = transformerFactory.newTransformer();
                                                        DOMSource source = new DOMSource(document);
                                                        StreamResult result = new StreamResult(new File(filepath));
                                                        transformer.transform(source, result);


                                                        Process t = Runtime.getRuntime().exec("cmd /c D:\\GitHub\\VEGA\\VEGA\\PSATSim/command.bat", null, new File("VEGA/PSATSim"));
                                                        t.waitFor();

                                                        double ipc = 0;
                                                        double power = 0;
                                                        File xmlFile = new File("VEGA/PSATSim/output.xml");

                                                        Reader fileReader = new FileReader(xmlFile);
                                                        BufferedReader bufReader = new BufferedReader(fileReader);

                                                        String line;
                                                        int lineNumber = 0;
                                                        while ((line = bufReader.readLine()) != null) {
                                                            if (lineNumber == 29)
                                                                ipc = Double.parseDouble(line.replaceAll("[a-zA-Z>\"=]", ""));
                                                            if (lineNumber == 31) {
                                                                power = Double.parseDouble(line.replaceAll("[a-zA-Z>\"=]", ""));
                                                                bufReader.close();
                                                                break;
                                                            }
                                                            lineNumber++;
                                                        }
                                                        dataTypes.put((double)dataTypes.size(), new DataType(ipc, power, scalar,rename,reorder, executionArhit.get(execArhit), separateDispatch,alu,
                                                                branch, memory));

                                                    }
                                                }
                                            }
                                        }

                                        if (execArhit == 2) {
                                            for (int iadd = 1; iadd <= 8; iadd++) {
                                                for (int imult = 1; imult <= 8; imult++) {
                                                    for (int idiv = 1; idiv <= 8; idiv++) {
                                                        for (int fpadd = 1; fpadd <= 8; fpadd++) {
                                                            for (int fpmult = 1; fpmult <= 8; fpmult++) {
                                                                for (int fpdiv = 1; fpdiv <= 8; fpdiv++) {
                                                                    for (int fpsqrt = 1; fpsqrt <= 8; fpsqrt++) {
                                                                        for (int branch = 1; branch <= 8; branch++) {
                                                                            for (int load = 1; load <= 8; load++) {
                                                                                for (int store = 1; store <= 8; store++) {
                                                                                    String filepath = "VEGA/PSATSim/default_cfg.xml";
                                                                                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                                                                                    DocumentBuilder builder = factory.newDocumentBuilder();
                                                                                    Document document = builder.parse(filepath);

                                                                                    Element sortie = (Element) document.getElementsByTagName("general").item(0);

                                                                                    sortie.getAttributeNode("superscalar").setNodeValue(Integer.toString(scalar));
                                                                                    sortie.getAttributeNode("rename").setNodeValue(Integer.toString(rename));
                                                                                    sortie.getAttributeNode("reorder").setNodeValue(Integer.toString(reorder));
                                                                                    sortie.getAttributeNode("separate_dispatch").setNodeValue(sepDispatch == 0 ? "false" : "true");
                                                                                    sortie.getAttributeNode("rs_per_rsb").setNodeValue(Integer.toString(rsPerBuffer));

                                                                                    Element sortieExec = (Element) document.getElementsByTagName("execution").item(0);

                                                                                    sortieExec.getAttributeNode("architecture").setNodeValue(executionArhit.get(execArhit));
                                                                                    sortieExec.getAttributeNode("iadd").setNodeValue(Integer.toString(iadd));
                                                                                    sortieExec.getAttributeNode("imult").setNodeValue(Integer.toString(imult));
                                                                                    sortieExec.getAttributeNode("idiv").setNodeValue(Integer.toString(idiv));
                                                                                    sortieExec.getAttributeNode("fpadd").setNodeValue(Integer.toString(fpadd));
                                                                                    sortieExec.getAttributeNode("fpmult").setNodeValue(Integer.toString(fpmult));
                                                                                    sortieExec.getAttributeNode("fpdiv").setNodeValue(Integer.toString(fpdiv));
                                                                                    sortieExec.getAttributeNode("fpsqrt").setNodeValue(Integer.toString(fpsqrt));
                                                                                    sortieExec.getAttributeNode("branch").setNodeValue(Integer.toString(branch));
                                                                                    sortieExec.getAttributeNode("load").setNodeValue(Integer.toString(load));
                                                                                    sortieExec.getAttributeNode("store").setNodeValue(Integer.toString(store));


                                                                                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                                                                                    Transformer transformer = transformerFactory.newTransformer();
                                                                                    DOMSource source = new DOMSource(document);
                                                                                    StreamResult result = new StreamResult(new File(filepath));
                                                                                    transformer.transform(source, result);


                                                                                    Process t = Runtime.getRuntime().exec("cmd /c D:\\GitHub\\VEGA\\VEGA\\PSATSim/command.bat", null, new File("VEGA/PSATSim"));
                                                                                    t.waitFor();

                                                                                    double ipc = 0;
                                                                                    double power = 0;
                                                                                    File xmlFile = new File("VEGA/PSATSim/output.xml");

                                                                                    Reader fileReader = new FileReader(xmlFile);
                                                                                    BufferedReader bufReader = new BufferedReader(fileReader);

                                                                                    String line;
                                                                                    int lineNumber = 0;
                                                                                    while ((line = bufReader.readLine()) != null) {
                                                                                        if (lineNumber == 29)
                                                                                            ipc = Double.parseDouble(line.replaceAll("[a-zA-Z>\"=]", ""));
                                                                                        if (lineNumber == 31) {
                                                                                            power = Double.parseDouble(line.replaceAll("[a-zA-Z>\"=]", ""));
                                                                                            bufReader.close();
                                                                                            break;
                                                                                        }
                                                                                        lineNumber++;
                                                                                    }
                                                                                    dataTypes.put((double)dataTypes.size(), new DataType(ipc, power, scalar,rename,reorder, executionArhit.get(execArhit), separateDispatch,
                                                                                            iadd, imult, idiv, fpadd, fpmult, fpdiv, fpsqrt, branch, load, store));


                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } catch (ParserConfigurationException | InterruptedException | IOException | TransformerException pce) {
            pce.printStackTrace();
        } catch (SAXException sae) {
            //sae.printStackTrace();
        }
    }
}
