package org.usfirst.frc.team6300.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.vision.VisionThread;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.Rect;

import org.usfirst.frc.team6300.robot.commands.*;
import org.usfirst.frc.team6300.robot.subsystems.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	public static OI oi;
	
	public final Drivetrain drivetrain = new Drivetrain();
	public final Shooter shooter = new Shooter();
	public final Intake intake = new Intake();
	public final Climber climber = new Climber(this);
	public final Agitator agitator = new Agitator();
	
	Command autonomousCommand;
	private final SendableChooser<Command> commandChooser = new SendableChooser<>();
	public final SendableChooser<String> stationChooser = new SendableChooser<>();
	public final SendableChooser<Boolean> colorChooser = new SendableChooser<>();
	
	private static final int imgWidth = 160;
	private static final int imgHeight = 120;
	
	private UsbCamera gearCam;
	private VisionThread visionThread;
	private final Object imgLock = new Object();
	private double centerX = 0.0;
	private long startTime;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI(this);
		
		commandChooser.addDefault("Deliver Gear", new DeliverGear(this));
		commandChooser.addObject("Tune PID", new TunePID(drivetrain));
		commandChooser.addObject("Shoot Low Goals", new LowGoal(this));
		
		stationChooser.addDefault("Center", "center");
		stationChooser.addObject("Left", "left");
		stationChooser.addObject("Right", "right");
		
		colorChooser.addDefault("Red", true);
		colorChooser.addObject("Blue", false);
		
		SmartDashboard.putData("Auto Command Chooser", commandChooser);
		SmartDashboard.putData("Alliance Station Chooser", stationChooser);
		SmartDashboard.putData("Alliance Color Chooser", colorChooser);
		
		gearCam = new UsbCamera("Gear Camera", 0);
		gearCam.setResolution(imgWidth, imgHeight);
		gearCam.setFPS(20);
		gearCam.setBrightness(5);
		CameraServer.getInstance().startAutomaticCapture(gearCam);
		startTime = System.currentTimeMillis();
		drivetrain.calibrateGyro();
	}
	
	@Override
	public void robotPeriodic() {
		if (visionThread == null && System.currentTimeMillis() > (startTime + 1000)) {
			CvSource outputStream = CameraServer.getInstance().putVideo("GearCam", imgWidth, imgHeight);
			visionThread = new VisionThread(gearCam, new FindGreenTape(), pipeline -> {
				double lastCenterX = 0.0;
				outputStream.putFrame(pipeline.maskOutput());
				//outputStream.putFrame(pipeline.blurOutput());
				//outputStream.putFrame(pipeline.resizeImageOutput());
				if (!pipeline.filterContoursOutput().isEmpty()) {
		            Rect r = Imgproc.boundingRect(pipeline.filterContoursOutput().get(0));
		            synchronized (imgLock) {
		                centerX = r.x + (r.width / 2);
		            }
		        }
				if (lastCenterX != centerX) {
					System.out.println("Center X: " + centerX);
					lastCenterX = centerX;
				}
		    });
		    visionThread.start();
		}
	}
	
	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	
	@Override
	public void disabledInit() {
		
	}
	
	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}
	
	@Override
	public void autonomousInit() {
		autonomousCommand = commandChooser.getSelected();
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
