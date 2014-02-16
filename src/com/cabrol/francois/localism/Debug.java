package com.cabrol.francois.localism;

/**
 * Created with IntelliJ IDEA.
 * User: francois * Date: 2014-01-23
 */
public class Debug {

    private static Debug INSTANCE = null;

    private boolean view = true;

    public Debug() {
    }

    public synchronized static Debug getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Debug();
        }
        return INSTANCE;
    }

    public void view(String msg){
        if(view)
            System.out.println("[VIEW] " + msg);
    }


}
