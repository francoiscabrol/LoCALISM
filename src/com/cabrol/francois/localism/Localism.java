package com.cabrol.francois.localism;

import com.cabrol.francois.localism.listener.LeapMonitor;
import com.cabrol.francois.localism.mouse.MouseReplacement;
import com.cabrol.francois.localism.view.ConfigFrame;
import com.leapmotion.leap.Controller;

public class Localism {

    public static void main(String[] args) {
        LeapMonitor.getInstance().addLeapListener(MouseReplacement.getInstance());
        Controller controller = new Controller(LeapMonitor.getInstance());

        ConfigFrame frame = new ConfigFrame();
    }

}
