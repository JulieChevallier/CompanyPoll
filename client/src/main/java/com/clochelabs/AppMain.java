package com.clochelabs;


import java.util.ArrayList;

public class AppMain {

    private static boolean animation = true;

    public static void animate(){
        new Thread(() -> {
            char[] symbols = {'|', '/', '-', '\\'};
            int i = 0;
            while (true) {
                System.out.print("\rConnecting " + symbols[i]);
                i = (i + 1) % symbols.length;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (!animation) {
                    break;
                }
            }
        }).start();
    }


    public static void main(String[] args) {
        // animate();
        ScrutinRepository repo = ScrutinRepository.getInstance();
        repo.add(new ScrutinDataObject(0,"test1", "test2", "Petit test", "01/05/2025", "01/05/2020"));
        repo.add(new ScrutinDataObject(0,"test2", "test3", "Petit test 2", "01/05/2025", "01/05/2020"));
        ArrayList<Scrutin> scrutin = repo.getScrutinsAsList();
        repo.close(1);
        System.out.println(scrutin.toString());

    }


}
