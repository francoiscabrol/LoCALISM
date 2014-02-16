package com.cabrol.francois.localism.calibration;

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
    private boolean isTouching;

    public FingerRelativeToScreen(Pointable pointable, AppScreenPlan screenPlan) {
        this.planNormalVector = screenPlan.getNormalVector();
        this.projectionOfFingerWithDirection = calculateProjectionOfFingerWithDirection(pointable, screenPlan);
        this.projectionOfFinger = calculateProjectionOfFinger(pointable.tipPosition(), screenPlan);
        this.frontFingerVector = pointable.tipPosition();
        this.screenPlan = screenPlan;
        this.isTouching = calculateIsTouching();
    }

    private Vector calculateProjectionOfFinger(Vector fingerPoint, AppScreenPlan screenPlan) {

        Vector p1 = screenPlan.getP1();

        float d = -(planNormalVector.getX() * p1.getX() + planNormalVector.getY() * p1.getY() + planNormalVector.getZ() * p1.getZ());

        float k = -(planNormalVector.getX() * fingerPoint.getX() + planNormalVector.getY() * fingerPoint.getY() + planNormalVector.getZ() * fingerPoint.getZ() + d)/(planNormalVector.getX()* planNormalVector.getX()+ planNormalVector.getY()* planNormalVector.getY()+ planNormalVector.getZ()* planNormalVector.getZ());

        Vector PF = new Vector(fingerPoint.getX()-(k* planNormalVector.getX()), fingerPoint.getY()-(k* planNormalVector.getY()), fingerPoint.getZ()-(k* planNormalVector.getZ()));

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
        float d = -(n.getX() * p1.getX() + n.getY() * p1.getY() + n.getZ() * p1.getZ());

        //calculation of k from the plane equation
        float k = -(u.getX() * a.getX() + u.getY() * a.getY() + u.getZ() * a.getZ() + d) / (u.getX() * u.getX()+ u.getY()* u.getY()+ u.getZ()* u.getZ());

        //calculation of x0
        float x1 = a.getX()-(k * u.getX());
        float x2 = a.getY()-(k * u.getY());
        float x3 = a.getZ()-(k * u.getZ());
        Vector x0 = new Vector(x1, x2, x3);

        System.out.println("x1:" + x1 + "x2:" + x2 + "x3:" + x3);

        return x0;
    }



    public Vector getProjectionOfFingerWithDirection() {
        return projectionOfFingerWithDirection;
    }

    public Vector getProjectionOfFinger() {
        return projectionOfFinger;
    }

    public float getDistanceFromScreen() {
        float distance = projectionOfFinger.distanceTo(frontFingerVector);
        return distance;
    }

    public Vector getFrontFingerVector() {
        return frontFingerVector;
    }

    public boolean calculateIsTouching() {
        int offset = 100000;

        boolean isTouching;

        Vector p1 = screenPlan.getP1();

        Vector p1M = new Vector(frontFingerVector.getX()-p1.getX(), frontFingerVector.getY()-p1.getY(), frontFingerVector.getZ()-p1.getZ());

        float p = p1M.dot(planNormalVector);
        if(p > -offset)
            isTouching = true;
        else
            isTouching = false;

        return isTouching;
    }

    public boolean isTouching() {
        return isTouching;
    }

}
