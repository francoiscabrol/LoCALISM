package com.cabrol.francois.localism.listener;

import com.leapmotion.leap.CircleGesture;
import com.leapmotion.leap.Pointable;

/**
 * Created with IntelliJ IDEA.
 * User: francois * Date: 2014-01-22
 */
public interface LeapListener {

    public void circleGestureListener(CircleGesture circle);

    public void frontMostPointableListener(Pointable pointable);

}
