package org.usfirst.frc.team6300.robot;

//import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.vision.VisionThread;

//import org.opencv.core.Mat;
//import org.opencv.imgproc.Imgproc;
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
	
	public static final Drivetrain drivetrain = new Drivetrain();
	public static final Shooter shooter = new Shooter();
	public static final Intake intake = new Intake();
	public static final Climber climber = new Climber();
	public static final Agitator agitator = new Agitator();
	
	Command autonomousCommand;
	private static final SendableChooser<Command> commandChooser = new SendableChooser<>();
	public static final SendableChooser<String> stationChooser = new SendableChooser<>();
	public static final SendableChooser<Boolean> colorChooser = new SendableChooser<>();
	
	private static final int imgWidth = 320;
	private static final int imgHeight = 240;
	
	private static UsbCamera gearCam;
	private VisionThread visionThread;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		
		commandChooser.addDefault("Deliver Gear", new DeliverGear());
		commandChooser.addObject("Tune PID", new TunePID());
		commandChooser.addObject("Shoot Low Goals", new LowGoal());
		
		stationChooser.addDefault("Center", "center");
		stationChooser.addObject("Left", "left");
		stationChooser.addObject("Right", "right");
		
		colorChooser.addDefault("Red", true);
		colorChooser.addObject("Blue", false);
		
		SmartDashboard.putData("Auto Command Chooser", commandChooser);
		SmartDashboard.putData("Alliance Station Chooser", stationChooser);
		SmartDashboard.putData("Alliance Color Chooser", colorChooser);
		
		//TODO test this vision thread again
		gearCam = new UsbCamera("Gear Camera", 0);
		gearCam.setResolution(imgWidth, imgHeight);
		gearCam.setFPS(20);
		gearCam.setBrightness(5);
		CvSource outputStream = CameraServer.getInstance().putVideo("GearCam B&W", imgWidth, imgHeight);
		visionThread = new VisionThread(gearCam, new FindGreenTape(), pipeline -> {
			//outputStream.putFrame(pipeline.maskOutput());
			//outputStream.putFrame(pipeline.blurOutput());
			outputStream.putFrame(pipeline.resizeImageOutput());
	    });
	    visionThread.start();
		
		drivetrain.calibrateGyro();
	}
	
	@Override
	public void robotPeriodic() {
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
