package at.htlklu.app;

import at.htlklu.persistence.Dao;

public class MainApp {
    public static void main(String[] args) {
        System.out.println(Dao.searchParts("HTL-101-349"));
        System.out.println(Dao.updatePartCount("HTL-101-349", 4));
        System.out.println(Dao.searchParts("HTL-101-349"));
    }
}
