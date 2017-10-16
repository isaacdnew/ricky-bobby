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
	
	VisionThread visionThread;
	final Object syncObject = new Object();
	double centerX = 0.0;
	double lastCenterX = 0.0;
	
	public GearCam(int port) {
		gearCam = new UsbCamera("GearCam", port);
		gearCam.setResolution(imgWidth, imgHeight);
		gearCam.setFPS(fps);
		gearCam.setBrightness(brightness);
	}
	
	public void startRecording() {
		CameraServer.getInstance().startAutomaticCapture(gearCam);
	}
	
	public void startProcessing() {
		CvSource outputStream = CameraServer.getInstance().putVideo("GearCam", imgWidth, imgHeight);
		visionThread = new VisionThread(gearCam, new FindGreenTape(), pipeline -> {
			//outputStream.putFrame(pipeline.maskOutput());
			outputStream.putFrame(pipeline.blurOutput());
			//outputStream.putFrame(pipeline.resizeImageOutput());
			if (!pipeline.filterContoursOutput().isEmpty()) {
	            Rect rect = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
	            synchronized (syncObject) {
	                centerX = rect.x + (rect.width / 2);
	            }
	            if (lastCenterX != centerX) {
					SmartDashboard.putNumber("Center X", centerX);
					SmartDashboard.putNumber("Target X", (centerX / imgWidth) - 1);
					lastCenterX = centerX;
				}
	        }
	    });
	    visionThread.start();
	}
	
	public double getCenterX() {
		synchronized (syncObject) {
			return centerX;
		}
	}
	
	public double getLastCenterX() {
		synchronized (syncObject) {
			return lastCenterX;
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

