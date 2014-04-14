/*
 * Copyright (c) 2014 François Cabrol.
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

package com.cabrol.francois.localism.calibration.screen;

import com.leapmotion.leap.Pointable;
import com.leapmotion.leap.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: francois * Date: 2013-11-20
 */
public class FingerRelativeToScreen {
    private Vector projectionOfFingerWithDirection;
    private Vector projectionOfFinger;
    Vector planNormalVector;
    Vector frontFingerVector;
    AppScreenPlan screenPlan;
    private Boolean isTouching;
    private Pointable pointable;

    public FingerRelativeToScreen(Pointable pointable, AppScreenPlan screenPlan) {
        this.planNormalVector = screenPlan.getNormalVector();
        this.screenPlan = screenPlan;
        this.pointable = pointable;
    }

    public Vector getProjectionOfFingerWithDirection() {
        if (projectionOfFingerWithDirection == null)
            projectionOfFingerWithDirection = calculateProjectionOfFingerWithDirection(pointable, screenPlan);
        return projectionOfFingerWithDirection;
    }

    public Vector getProjectionOfFinger() {
        if(projectionOfFinger == null)
            projectionOfFinger = calculateProjectionOfFinger(pointable.tipPosition(), screenPlan);
        return projectionOfFinger;
    }

    public float getDistanceFromScreen() {
        float distance = getProjectionOfFinger().distanceTo(getFrontFingerVector());
        return distance;
    }

    public Vector getFrontFingerVector() {
        if (frontFingerVector == null)
            frontFingerVector = pointable.tipPosition();
        return frontFingerVector;
    }

    public boolean isTouching() {
        if (isTouching == null)
            isTouching = calculateIsTouching();
        return isTouching;
    }


    private Vector calculateProjectionOfFinger(Vector fingerPoint, AppScreenPlan screenPlan) {
        Vector p1 = screenPlan.getP1();
        float d = -(planNormalVector.getX() * p1.getX()
                + planNormalVector.getY() * p1.getY()
                + planNormalVector.getZ() * p1.getZ());
        float k = -(planNormalVector.getX() * fingerPoint.getX()
                    + planNormalVector.getY() * fingerPoint.getY()
                    + planNormalVector.getZ() * fingerPoint.getZ()+ d)
                / (planNormalVector.getX()* planNormalVector.getX()
                    + planNormalVector.getY()* planNormalVector.getY()
                    + planNormalVector.getZ()* planNormalVector.getZ()
                );
        Vector PF = new Vector(fingerPoint.getX()
                -(k* planNormalVector.getX()), fingerPoint.getY()
                -(k* planNormalVector.getY()), fingerPoint.getZ()
                -(k* planNormalVector.getZ()));
        return PF;
    }

    private Vector calculateProjectionOfFingerWithDirection(Pointable pointable, AppScreenPlan screenPlan) {
        //plane equation defined as  a.x + b.y + c.z + d = 0 ( 0 )
        //x0 is the project on the vector pointable.direction() on the plane
        Vector a = pointable.tipPosition();
        Vector b = pointable.direction().opposite().times(pointable.length());
        b = b.plus(pointable.tipPosition());
        Vector u = pointable.direction().times(pointable.length() * 1000);

        //calculation of d from the plane equation
        Vector p1 = screenPlan.getP2();
        Vector n = screenPlan.getNormalVector();
        float d = -(n.getX() * p1.getX()
                    + n.getY() * p1.getY()
                    + n.getZ() * p1.getZ());

        //calculation of k from the plane equation
        float k = (u.getX() * a.getX()
                    + u.getY() * a.getY()
                    + u.getZ() * a.getZ()
                    + d)
                / (u.getX() * u.getX()
                    + u.getY()* u.getY()
                    + u.getZ()* u.getZ());

        //calculation of x0
        float x1 = a.getX()-(k * u.getX());
        float x2 = a.getY()-(k * u.getY());
        float x3 = a.getZ()-(k * u.getZ());
        Vector x0 = new Vector(x1, x2, x3);
        return x0;
    }

    private boolean calculateIsTouching() {
        int offset = 100000;
        Vector p1 = screenPlan.getP1();
        Vector p1M = new Vector(getFrontFingerVector().getX()-p1.getX(), getFrontFingerVector().getY()-p1.getY(), getFrontFingerVector().getZ()-p1.getZ());
        float p = p1M.dot(planNormalVector);
        return (p > -offset) ? true : false;
    }

}
