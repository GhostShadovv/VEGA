package com.company;

import java.util.HashMap;

public class DataType {
    double ipc;
    double power;
    public int superscalar ;
    public int rename ;
    public int reorder ;
    public String rsbARhitecture;
    public boolean separateDispatch;
    public int rs_per_rsb;
    public int integer = 0;
    public int floating = 0;
    public int branch = 0;
    public int memory = 0;

    public HashMap<String, Integer> hmap = new HashMap<String, Integer>();

    public DataType()
    {
        hmap.put("superscalar", 0);
        hmap.put("rename", 1);
        hmap.put("reorder", 2);
        hmap.put("rsb_architecture", 3);
        hmap.put("rs_per_rsb", 4);
        hmap.put("separate_dispatch", 5);
        hmap.put("integer", 6);
        hmap.put("floating", 7);
        hmap.put("branch", 8);
        hmap.put("memory", 9);
    }
    public int getData(String input)
    {
        return hmap.get(input);
    }


    public String getdataString(String value)
    {
        switch(value) {
            case "0":
                return "distributed";
            case "1":
                return "centralized";
            case "2":
                return "hybrid";
            default:
                return "distributed";
        }
    }

    public String getdataBool(String value)
    {
        switch(value) {
            case "0":
                return "true";
            case "1":
                return "false";
            default:
                return "true";
        }

    }

}
