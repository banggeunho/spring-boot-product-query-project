//package com.example.techlabs.csv;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class CsvTransfer {
//
//    private List<String[]> csvStringList;
//
//    private List<CsvBean> csvList;
//
//    public CsvTransfer() {}
//
//    public List<String[]> getCsvStringList() {
//        if (csvStringList != null) return csvStringList;
//        return new ArrayList<String[]>();
//    }
//
//    public void addLine(String[] line) {
//        if (this.csvList == null) this.csvStringList = new ArrayList<>();
//        this.csvStringList.add(line);
//    }
//
//    public void setCsvStringList(List<String[]> csvStringList) {
//        this.csvStringList = csvStringList;
//    }
//
//    public <T extends CsvBean> void setCsvList(List<T> csvList) {
//        this.csvList = csvList;
//    }
//
//    public <T extends CsvBean> List<T> getCsvList() {
//        if (csvList != null) return csvList;
//        return new ArrayList<T>();
//    }
//}
