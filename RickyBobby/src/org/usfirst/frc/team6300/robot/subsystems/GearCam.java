package org.usfirst.frc.team6300.robot.subsystems;

import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team6300.robot.FindGreenTape;

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
	final int exposure = 30;
	final int whiteBalance = 1000;
	
	VisionThread visionThread;
	final Object turnAngleSync = new Object();
	final Object lastTurnAngleSync = new Object();
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
		CvSource maskStream = CameraServer.getInstance().putVideo("GearCam Mask", imgWidth, imgHeight);
		visionThread = new VisionThread(gearCam, new FindGreenTape(), pipeline -> {
			maskStream.putFrame(pipeline.maskOutput());
			outputStream.putFrame(pipeline.blurOutput());
			//outputStream.putFrame(pipeline.resizeImageOutput());
			if (pipeline.filterContoursOutput().size() == 1) {
				Rect rect = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
				centerX = (rect.x + (rect.width / 2));
				synchronized (turnAngleSync) {
					turnAngle = ((centerX / imgWidth) - 0.5) * 4/* * (fieldOfView / 2)*/;
				}
			}
			else if (pipeline.filterContoursOutput().size() == 2) {
	            Rect rectL = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            Rect rectR = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            double leftX = (rectL.x + (rectL.width / 2));
	            double rightX = (rectR.x + (rectR.width / 2));
	            centerX = (rightX + leftX) / 2;
	            synchronized(turnAngleSync) {
	            	turnAngle = ((centerX / imgWidth) - 0.5) * 4/* * (fieldOfView / 2)*/;
	            }
	        }
			if (lastTurnAngle != turnAngle) {
				SmartDashboard.putNumber("Center X", centerX);
				//SmartDashboard.putNumber("Turn Angle", (centerX / imgWidth) * fieldOfView);
				synchronized(lastTurnAngleSync) {
					lastTurnAngle = turnAngle;
				}
			}
	    });
	    visionThread.start();
	}
	
	public double getTurnAngle() {
		synchronized(turnAngleSync) {
			return turnAngle;
		}
	}
	
	public double getLastTurnAngle() {
		synchronized(lastTurnAngleSync) {
			return lastTurnAngle;
		}
	}
	
	public double getImgWidth() {
		return imgWidth;
	}
	
	public double getFieldOfView() {
		return fieldOfView;
	}
	
	public void initDefaultCommand() {
	}
}

