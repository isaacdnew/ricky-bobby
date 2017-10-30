package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.ResizeBlurFlip;

import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.VisionThread;

/**
 *  The camera that looks at the shooter from the intake bar (for human use only)
 */
public class ShooterCam extends Subsystem {
	UsbCamera shooterCam;
	final int imgWidth = 160;
	final int imgHeight = 120;
	final int fps = 20;
	final int brightness = 20;
	final int exposure = 50;
	final int whiteBalance = 1000;
	
	VisionThread visionThread;
	final Object turnAngleSync = new Object();
	double centerX = 0.0;
	double lastTurnAngle = 0.0;
	double turnAngle = 0.0;
	
	
	public ShooterCam(int port) {
		shooterCam = new UsbCamera("ShooterCam", port);
		shooterCam.setResolution(imgWidth, imgHeight);
		shooterCam.setFPS(fps);
		shooterCam.setBrightness(brightness);
		shooterCam.setExposureManual(exposure);
		shooterCam.setWhiteBalanceManual(whiteBalance);
	}
	
	public void startRecording() {
		CameraServer.getInstance().startAutomaticCapture(shooterCam);
	}
	
	public void startProcessing() {
		CvSource outputStream = CameraServer.getInstance().putVideo("ShooterCam", imgWidth, imgHeight);
		visionThread = new VisionThread(shooterCam, new ResizeBlurFlip(), pipeline -> {
			outputStream.putFrame(pipeline.blurOutput());
			//outputStream.putFrame(pipeline.resizeImageOutput());
	    });
	    visionThread.start();
	}
	
	public void initDefaultCommand() {
	}
}

