package com.cabrol.francois.localism.listener;

import com.leapmotion.leap.*;

import java.util.ArrayList;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: francois
 * Date: 2013-11-19
 * Time: 15:52
 * To change this template use File | Settings | File Templates.
 */
public class LeapMonitor extends Listener {

    private static LeapMonitor INSTANCE = null;

    List<LeapListener> listeners = new ArrayList<LeapListener>();

    public LeapMonitor() {

    }

    public synchronized static LeapMonitor getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LeapMonitor();
        }
        return INSTANCE;
    }

    @Override
    public void onInit(Controller controller) {
        System.out.println("Init");
        controller.setPolicyFlags(Controller.PolicyFlag.swigToEnum(1));
        controller.enableGesture(Gesture.Type.TYPE_CIRCLE);
    }

    @Override
    public void onExit(Controller controller) {
        System.out.println("Exit");
    }

    @Override
    public void onFrame(Controller controller){
            Frame frame = controller.frame(); //The latest frame
            processFrame(frame);
    }

    public void addLeapListener(LeapListener listener){
        listeners.add(listener);
    }

    private void processFrame(Frame frame )
    {
        if(!frame.pointables().isEmpty())
            notifyFrontMostPointable(frame.pointables().frontmost());

        GestureList gestures = frame.gestures();
        for (int i = 0; i < gestures.count(); i++) {
            Gesture gesture = gestures.get(i);
            switch (gesture.type()) {
                case TYPE_CIRCLE:
                    CircleGesture circle = new CircleGesture(gesture);
                    notifyCircleGesture(circle);
                    break;
            }
        }
    }

    private void notifyCircleGesture(CircleGesture circle) {
        for(LeapListener l : listeners)
            l.circleGestureListener(circle);
    }

    private void notifyFrontMostPointable(Pointable pointable) {
        for(LeapListener l : listeners)
            l.frontMostPointableListener(pointable);
    }


}
