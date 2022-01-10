package com.company;


import org.moeaframework.core.Problem;
import org.moeaframework.core.Solution;
import org.moeaframework.core.Variable;
import org.moeaframework.core.spi.ProblemProvider;
import org.moeaframework.core.variable.BinaryIntegerVariable;
import org.moeaframework.core.variable.RealVariable;
import org.moeaframework.problem.AbstractProblem;
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
import java.util.HashMap;
import java.util.List;

public class Simulator extends AbstractProblem {

    DataType dates;
    public Simulator() {
        super(10, 2);
        dates = new DataType();
    }

    static volatile int nrFisier = 0;


    @Override
    public void evaluate(Solution solution) {

        FileParser parsare = new FileParser();
        double[] result = parsare.outputFile(solution, increment());

        solution.setObjective(0, result[0]);
        solution.setObjective(1, result[1]);

    }

    public synchronized int increment(){
        return nrFisier++;
    }
    @Override
    public Solution newSolution( ) {
        Solution solution = new Solution(getNumberOfVariables(),
                getNumberOfObjectives());

        solution.setVariable(dates.getData("superscalar"), new BinaryIntegerVariable(1,1, 16));
        solution.setVariable(dates.getData("rename"), new BinaryIntegerVariable(1,1,512));
        solution.setVariable(dates.getData("reorder"), new BinaryIntegerVariable(1,1,512));
        solution.setVariable(dates.getData("rsb_architecture"), new BinaryIntegerVariable(0,0,2));
        solution.setVariable(dates.getData("separate_dispatch"), new BinaryIntegerVariable(0,0,1));
        solution.setVariable(dates.getData("integer"), new BinaryIntegerVariable(1,1,8));
        solution.setVariable(dates.getData("floating"), new BinaryIntegerVariable(1,1,8));
        solution.setVariable(dates.getData("branch"), new BinaryIntegerVariable(1,1,8));
        solution.setVariable(dates.getData("memory"), new BinaryIntegerVariable(1,1,8));
        solution.setVariable(dates.getData("rs_per_rsb"), new BinaryIntegerVariable(1,1,8));


        return solution;
    }

    public String getName() {

        return "Simulator";
    }

    public int getNumberOfVariables() {

        return 10;
    }

    public int getNumberOfObjectives() {

        return 2;
    }

    public int getNumberOfConstraints() {

        return 0;
    }

}
