package com.clochelabs;

public class UpdateServer extends Thread{
    private int delay;
    public UpdateServer(){
        delay = 3000;
    }
    @Override
    public void run() {
        while(true){
            Urne.getInstance().Update();
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
