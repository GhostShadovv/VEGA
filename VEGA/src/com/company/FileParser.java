package com.company;

import org.moeaframework.core.Solution;
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
import java.util.concurrent.TimeUnit;

public class FileParser {

    DataType dates = new DataType();

    public double[] outputFile(Solution solution, int nrFisier){
        double[] atributes = new double[2];
        try {
            String filepath = "VEGA/PSATSim/default_cfg.xml";
            String filepathtemp = "VEGA/PSATSim/default_cfg_tmp.xml";
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(filepath);

            Element sortie = (Element) document.getElementsByTagName("general").item(0);

            sortie.getAttributeNode("superscalar").setNodeValue(solution.getVariable(dates.getData("superscalar")).toString());
            sortie.getAttributeNode("rename").setNodeValue(solution.getVariable(dates.getData("rename")).toString());
            sortie.getAttributeNode("reorder").setNodeValue(solution.getVariable(dates.getData("reorder")).toString());
            sortie.getAttributeNode("rs_per_rsb").setNodeValue(solution.getVariable(dates.getData("rs_per_rsb")).toString());
            sortie.getAttributeNode("rsb_architecture").setNodeValue(dates.getdataString(solution.getVariable(dates.getData("rsb_architecture")).toString()));
            sortie.getAttributeNode("separate_dispatch").setNodeValue(dates.getdataBool(solution.getVariable(dates.getData("separate_dispatch")).toString()));


            Element sortieExec = (Element) document.getElementsByTagName("execution").item(0);

            sortieExec.getAttributeNode("branch").setNodeValue(solution.getVariable(dates.getData("branch")).toString());
            sortieExec.getAttributeNode("floating").setNodeValue(solution.getVariable(dates.getData("floating")).toString());
            sortieExec.getAttributeNode("integer").setNodeValue(solution.getVariable(dates.getData("integer")).toString());
            sortieExec.getAttributeNode("memory").setNodeValue(solution.getVariable(dates.getData("memory")).toString());



            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);
            StreamResult result = new StreamResult(new File(filepathtemp));
            transformer.transform(source, result);

            String outputName = "output"+nrFisier+".xml";

            long start = System.currentTimeMillis();
            Process t = Runtime.getRuntime().exec("cmd /c psatsim_con default_cfg.xml "+outputName+" -t 8 -cg", null, new File("VEGA/PSATSim"));
            t.waitFor();
            long finish = System.currentTimeMillis();

            System.out.println(finish-start + " " + nrFisier);

            File outputFile = new File("VEGA/PSATSim/"+outputName);
            if(!outputFile.exists() || !(outputFile.length() > 0))
            {
                atributes[0] = 5;
                atributes[1] = 5;
                //System.out.println("EROARE "+outputName+ ":"+ solution.getVariable(0).toString()+" "+  solution.getVariable(1).toString()+" " + solution.getVariable(2).toString());
                outputFile.delete();
                return atributes;
            }

            Reader fileReader = new FileReader("VEGA/PSATSim/"+outputName);
            BufferedReader bufReader = new BufferedReader(fileReader);
            String line;
            int lineNumber = 0;
            while ((line = bufReader.readLine()) != null) {
                if (lineNumber == 29)
                    atributes[0] = 1 / Double.parseDouble(line.replaceAll("[a-zA-Z>\"=]", ""));
                if (lineNumber == 30) {
                    atributes[1] = Double.parseDouble(line.replaceAll("[a-zA-Z>\"=]", ""));
                    bufReader.close();
                    break;
                }
                lineNumber++;
            }
            nrFisier++;
            outputFile.delete();
//            System.out.println("IPC " +atributes[0] + " Energy:" +atributes[1] + " superscalar: " +
//                    solution.getVariable(0).toString()+" rename: "+
//                    solution.getVariable(1).toString()+" reorder: " +
//                    solution.getVariable(2).toString()+" rsb_architecture: " +
//                    solution.getVariable(3).toString()+" rs_per_rsb: " +
//                    solution.getVariable(4).toString()+" separate_dispatch: " +
//                    solution.getVariable(5).toString()+" integer: " +
//                    solution.getVariable(6).toString()+" floating: " +
//                    solution.getVariable(7).toString()+" branch: " +
//                    solution.getVariable(8).toString()+" memory: " +
//                    solution.getVariable(9).toString());

            try (PrintWriter writer = new PrintWriter(new FileWriter("VEGA/graph.csv", true))) {
                StringBuilder sb = new StringBuilder();
                sb.append(String.format("%.5f", 1/atributes[0]));
                sb.append(';');
                sb.append(String.format("%.5f", 1*atributes[1]));
                sb.append(';');
                sb.append( solution.getVariable(0).toString());
                sb.append(';');
                sb.append( solution.getVariable(1).toString());
                sb.append(';');
                sb.append(solution.getVariable(2).toString());
                sb.append(';');
                sb.append(solution.getVariable(3).toString());
                sb.append(';');
                sb.append(solution.getVariable(4).toString());
                sb.append(';');
                sb.append(solution.getVariable(5).toString());
                sb.append(';');
                sb.append(solution.getVariable(6).toString());
                sb.append(';');
                sb.append(solution.getVariable(7).toString());
                sb.append(';');
                sb.append(solution.getVariable(8).toString());
                sb.append(';');
                sb.append(solution.getVariable(9).toString());
                sb.append('\n');

                writer.write(sb.toString());
            } catch (FileNotFoundException e) {
                System.out.println(e.getMessage());
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return atributes;
    }
    private boolean isCompletelyWritten(File file) throws InterruptedException {
        Long fileSizeBefore = file.length();

        if (fileSizeBefore > 0) {
            return true;
        }
        return false;
    }
}
