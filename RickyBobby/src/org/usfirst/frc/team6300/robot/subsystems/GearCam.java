package org.usfirst.frc.team6300.robot.subsystems;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team6300.robot.FindReflectiveTape;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 *
 */
public class GearCam extends Subsystem {
	UsbCamera gearCam;
	final int imgWidth = 160;
	final int imgHeight = 120;
	final double fieldOfView = 60;
	final int fps = 20;
	final int brightness = 5;
	final int exposure = 25;
	final int whiteBalance = 1000;
	
	VisionThread visionThread;
	final Object turnAngleSync = new Object();
	double centerX = 0.0;
	double lastTurnAngle = 0.0;
	double turnAngle = 0.0;
	
	public GearCam(int port) {
		gearCam = new UsbCamera("GearCam", port);
		gearCam.setResolution(imgWidth, imgHeight);
		gearCam.setFPS(fps);
		gearCam.setBrightness(brightness);
		gearCam.setExposureManual(exposure);
		gearCam.setWhiteBalanceManual(whiteBalance);
	}
	
	public void startRecording() {
		CameraServer.getInstance().startAutomaticCapture(gearCam);
	}
	
	public void startProcessing() {
		CvSource outputStream = CameraServer.getInstance().putVideo("GearCam", imgWidth, imgHeight);
		CvSource thresholdStream = CameraServer.getInstance().putVideo("GearCam Threshold", imgWidth, imgHeight);
		visionThread = new VisionThread(gearCam, new FindReflectiveTape(), pipeline -> {
			outputStream.putFrame(pipeline.cvRectangleOutput());
			thresholdStream.putFrame(pipeline.hslThresholdOutput());
			if (pipeline.filterContoursOutput().size() > 1) {
	            Rect rectL = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            Rect rectR = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            double leftX = (rectL.x + (rectL.width / 2));
	            double rightX = (rectR.x + (rectR.width / 2));
	            centerX = (rightX + leftX) / 2;
	            if (Math.abs(rightX - leftX) > 5 || true) {
	            	synchronized(turnAngleSync) {
	            		turnAngle = ((centerX - (imgWidth / 2)) / imgWidth) * 8;
	            	}
	            }
	        }
			else {
				synchronized(turnAngleSync) {
					turnAngle = 0;
				}
			}
			SmartDashboard.putNumber("Turn Angle", turnAngle);
			SmartDashboard.putNumber("Center X", centerX);
	    });
	    visionThread.start();
	}
	
	public double getTurnAngle() {
		synchronized(turnAngleSync) {
			return turnAngle;
		}
	}
	
	public void initDefaultCommand() {
	}
}
// Logic for the vision pipeline (it's here because it could be overwritten when new code is generated):

//if (filterContoursOutput().size() > 1) {
//	Rect r0 = Imgproc.boundingRect(filterContoursOutput.get(0));
//	Rect r1 = Imgproc.boundingRect(filterContoursOutput.get(1));
//	
//	double newPoint0X = r0.x;
//	double newPoint0Y = r0.y;
//	newPoint(newPoint0X, newPoint0Y, newPoint0Output);
//	
//	double newPoint1X = r1.x + r1.width;
//	double newPoint1Y = r1.y + r1.height;
//	newPoint(newPoint1X, newPoint1Y, newPoint1Output);
//}
//else {
//	// Step New_Point0:
//	
//	double newPoint0X = 10;
//	double newPoint0Y = 10;
//	newPoint(newPoint0X, newPoint0Y, newPoint0Output);
//	
//	// Step New_Point1:
//	double newPoint1X = 150;
//	double newPoint1Y = 110;
//	newPoint(newPoint1X, newPoint1Y, newPoint1Output);
//}
