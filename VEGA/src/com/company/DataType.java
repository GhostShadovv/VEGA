package com.company;

public class DataType {
    double ipc;
    double power;
    public int superscalar = 0;
    public int rename = 0;
    public int reorder = 0;
    public String rsbARhitecture;
    public boolean separateDispatch;
    public int integer = 0;
    public int floaring = 0;
    public int branch = 0;
    public int memory = 0;
    public int alu = 0;
    public int iadd = 0;
    public int imult = 0;
    public int idiv = 0;
    public int fpadd = 0;
    public int fpmult = 0;
    public int fpdiv = 0;
    public int fqsqrt = 0;
    public int load = 0;
    public int store = 0;

    public DataType(double ipc, double pow, int superscalar, int rename, int reorder, String rsbARhitecture, boolean separateDispatch,
                    int integer, int floaring, int branch, int memory, int alu, int iadd, int imult, int idiv,
                    int fpadd, int fpmult, int fpdiv , int fqsqrt, int load, int store) {
        this.ipc= ipc;
        this.power = pow;
        this.superscalar = superscalar;
        this.rename = rename;
        this.reorder = reorder;
        this.rsbARhitecture = rsbARhitecture;
        this.separateDispatch = separateDispatch;
        this.integer = integer;
        this.floaring = floaring;
        this.branch = branch;
        this.memory = memory;
        this.alu = alu;
        this.iadd = iadd;
        this.imult = imult;
        this.idiv = idiv;
        this.fpadd = fpadd;
        this.fpmult = fpmult;
        this.fpdiv = fpdiv;
        this.fqsqrt = fqsqrt;
        this.load = load;
        this.store = store;
    }

    public DataType(double ipc, double pow, int scalar, int rename, int reorder, String rsbArhit, boolean separateDispatch, int integer, int floating, int branch, int memory) {
        this.ipc= ipc;
        this.power = pow;
        this.superscalar = scalar;
        this.rename = rename;
        this.reorder = reorder;
        this.rsbARhitecture = rsbArhit;
        this.separateDispatch = separateDispatch;
        this.integer = integer;
        this.floaring = floating;
        this.branch = branch;
        this.memory = memory;
    }

    public DataType(double ipc, double pow, int scalar, int rename, int reorder, String rsbArhit, boolean separateDispatch, int alu, int branch, int memory) {
        this.ipc= ipc;
        this.power = pow;
        this.superscalar = scalar;
        this.rename = rename;
        this.reorder = reorder;
        this.rsbARhitecture = rsbArhit;
        this.separateDispatch = separateDispatch;
        this.alu = alu;
        this.branch = branch;
        this.memory = memory;
    }

    public DataType(double ipc, double pow, int scalar, int rename, int reorder, String s, boolean separateDispatch, int iadd, int imult, int idiv, int fpadd, int fpmult,
                    int fpdiv, int fpsqrt, int branch, int load, int store) {
        this.ipc= ipc;
        this.power = pow;
        this.superscalar = scalar;
        this.rename = rename;
        this.reorder = reorder;
        this.rsbARhitecture = s;
        this.separateDispatch = separateDispatch;
        this.branch = branch;
        this.iadd = iadd;
        this.imult = imult;
        this.idiv = idiv;
        this.fpadd = fpadd;
        this.fpmult = fpmult;
        this.fpdiv = fpdiv;
        this.fqsqrt = fpsqrt;
        this.load = load;
        this.store = store;
    }
}
