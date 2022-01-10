package com.company;


import org.moeaframework.Executor;
import org.moeaframework.core.NondominatedPopulation;
import org.moeaframework.core.Solution;

import java.awt.*;
import java.io.*;


public class Main {
    public static void main(String[] args) throws IOException {

        try (PrintWriter writer = new PrintWriter(new FileWriter("VEGA/graph.csv"))) {
            StringBuilder sb = new StringBuilder();
            sb.append("IPC");
            sb.append(';');
            sb.append("Energy:");
            sb.append(';');
            sb.append("superscalar:");
            sb.append(';');
            sb.append("rename:");
            sb.append(';');
            sb.append("reorder:");
            sb.append(';');
            sb.append("rsb_architecture:");
            sb.append(';');
            sb.append("rs_per_rsb");
            sb.append(';');
            sb.append("separate_dispatch");
            sb.append(';');
            sb.append("integer:");
            sb.append(';');
            sb.append("floating:");
            sb.append(';');
            sb.append("branch:");
            sb.append(';');
            sb.append("memory:");
            sb.append('\n');

            writer.write(sb.toString());
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }


        NondominatedPopulation result = new Executor()
                .withAlgorithm("VEGA")
                .withProblemClass(Simulator.class)
                .withMaxEvaluations(30)
                .withProperty("populationSize",1000)
                .withProperty("HUX.rate", 20) //Crossover : SBX, PCX, SPX, UNDX, HUX, PMX, SSX
                .withProperty("UM.rate", 1.0/10000) //Mutation: PM, UM, HUX, PMX
                .distributeOn(8)
                .run();

        for (Solution solution : result) {
            if (!solution.violatesConstraints()) {
                System.out.format("%10.3f      %10.3f   ",
                       1 / solution.getObjective(0),
                        solution.getObjective(1));
                System.out.print(" superscalar "+ solution.getVariable(0)+
                        " rename "+ solution.getVariable(1)+
                        " reorder "+ solution.getVariable(2)+
                        " rsb_architecture "+ solution.getVariable(3)+
                        " rs_per_rsb "+ solution.getVariable(4)+
                        " separate_dispatch "+ solution.getVariable(5)+
                        " integer "+ solution.getVariable(6)+
                        " floating "+ solution.getVariable(7)+
                        " branch "+ solution.getVariable(8)+
                        " memory "+ solution.getVariable(9)+"\n"
                 );
            }
        }

        File file = new File("VEGA/Chart.xlsx");
        Desktop.getDesktop().open(file);
        System.out.println();
    }
}

