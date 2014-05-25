LoCALISM - Leap CALIbration SysteM
==============

A mouse replacement system using leap motion and based on a screen calibration tool.
--------------

*Written in Java*

## Why is Localism ?
The first idea was to create a mouse replacement system based on an home-made screen calibration system.
Now, I'd like to get localism work better and transform it in a simple screen calibration library for the leap Motion.


## Design
### Packages
The is two principal package:
	
1. _com.cabrol.francois.localism.calibration_ with the calibration system in.
2. _com.cabrol.francois.localism.example_ with all examples in.

And two modules in two different folders:

1. The folder *__src/__* contains Localism, the librairy.
2. The folder *__MouseExample/__* contains the mouse replacement example using Localism.

###Dependences
It needs LeapJava.jar to work. Was tested with the version 1.0.9+8391 of
the leap motion library for Java.


## How to use it?
The basic uses of the library is the follow:

1. Instanciate the AppScreenPlan object:

	
        AppScreenPlan appScreenPlan = new AppScreenPlan();

2. Open the calibration frame to bring the user to do the calibration process.

		CalibrationFrame calibrationFrame = new CalibrationFrame(MouseReplacement.getInstance().getAppScreenPosition());

3. After the calibration done, you can get the object FingerRelativeToScreen. The pointable parameters could be a Pointable, Finger or Tool object from the leap motion library.

		FingerRelativeToScreen fingerRelativeToScreen = appScreenPosition.getFingerRelativeToScreen(pointable);

4. Then you can get the distance of the finger from the screen calibrated, and the position relative to the screen as:

		if(fingerRelativeToScreen!=null){
			boolean isTouching = fingerRelativeToScreen.isTouching()
			float xScreenAdj = appScreenPosition.leapCoordToScreenCoordX(fingerRelativeToScreen.getProjectionOfFinger().getX());
           float yScreenAdj = appScreenPosition.leapCoordToScreenCoordY(fingerRelativeToScreen.getProjectionOfFinger().getY());
           
** I imagine that's could be really improved, even if I did my best, it's my first library. If you have proposals, you're welcome! **

##Versions
* The version 0.1.0 is the first release that show how the system could works.
* The version 0.2.0 is a refactoring that allows to use the calibration system as library easily in any java project.
* The version 0.3.0 will be an architecture improvement and maybe will use the new leap motion API to get a precise projection based on the finger direction vector (and not only with the normal vector as it is currently).

				

