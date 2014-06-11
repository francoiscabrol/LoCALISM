/*
 * Copyright (c) 2014 Francois Cabrol.
 *
 *  This file is part of LoCALISM.
 *
 *     LoCALISM is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     LoCALISM is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with LoCALISM.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.cabrol.francois.localism.example.mouse;

import com.cabrol.francois.localism.calibration.screen.AppScreenPlan;
import com.cabrol.francois.localism.calibration.screen.FingerRelativeToScreen;
import com.cabrol.francois.localism.calibration.listener.LeapListener;
import com.leapmotion.leap.CircleGesture;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.Pointable;

import java.awt.*;
import java.awt.event.InputEvent;

/**
 * Created with IntelliJ IDEA.
 * User: francois * Date: 2014-01-23
 */
public class MouseReplacement implements LeapListener {

    private static MouseReplacement INSTANCE = null;

    private AppScreenPlan appScreenPosition = new AppScreenPlan();
    private FingerRelativeToScreen fingerRelativeToScreen;
    private Robot robot;
    private boolean mouseOn = false;
    private boolean wasTouching = false;


    public MouseReplacement() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public synchronized static MouseReplacement getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MouseReplacement();
        }
        return INSTANCE;
    }

    public boolean isMouseOn() {
        return mouseOn;
    }

    public void setMouseOn(boolean mouseOn) {
        this.mouseOn = mouseOn;
    }

    public AppScreenPlan getAppScreenPosition() {
        return appScreenPosition;
    }

    public FingerRelativeToScreen getFingerRelativeToScreen() {
        return fingerRelativeToScreen;
    }

    public void setAppScreenPosition(AppScreenPlan appScreenPosition) {
        this.appScreenPosition = appScreenPosition;
    }

    @Override
    public void frontMostPointableListener(Pointable pointable) {
        fingerRelativeToScreen = appScreenPosition.getFingerRelativeToScreen(pointable);

        if(mouseOn){

            if(fingerRelativeToScreen!=null){
                if(!wasTouching && fingerRelativeToScreen.isTouching()){
                    //robot.mousePress(InputEvent.BUTTON1_MASK);
                }
                else if(wasTouching && !fingerRelativeToScreen.isTouching()){
                    //robot.mouseRelease(InputEvent.BUTTON1_MASK);
                }
            }
            wasTouching = fingerRelativeToScreen.isTouching();

            float xAdj = appScreenPosition.leapCoordToScreenCoordX(fingerRelativeToScreen.getProjectionOfFinger().getX());
            float yAdj = appScreenPosition.leapCoordToScreenCoordY(fingerRelativeToScreen.getProjectionOfFinger().getY());

            int intX = Math.round(xAdj);
            int intY = Math.round(yAdj);
            robot.mouseMove(intX, intY);
        }
    }

    @Override
    public void circleGestureListener(CircleGesture circle) {
        if(mouseOn){
            String clockwiseness;
            if (circle.state() == Gesture.State.STATE_STOP && circle.radius() < 10){
                if (circle.pointable().direction().angleTo(circle.normal()) <= Math.PI/4) {
                    // Clockwise if angle is less than 90 degrees
                    clockwiseness = "clockwise";
                    robot.mousePress(InputEvent.BUTTON1_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    robot.mousePress(InputEvent.BUTTON1_MASK);
                    robot.mouseRelease(InputEvent.BUTTON1_MASK);
                    Debug.getInstance().mouse("raduis:" + circle.radius());
                } else {
                    clockwiseness = "counterclockwise";
                    robot.mousePress(InputEvent.BUTTON3_MASK);
                    robot.mouseRelease(InputEvent.BUTTON3_MASK);
                }
                Debug.getInstance().mouse("end of swipe "+ clockwiseness);
            }
        }
    }

}
