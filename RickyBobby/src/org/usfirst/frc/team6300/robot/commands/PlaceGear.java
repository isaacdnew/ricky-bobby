package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.subsystems.Drivetrain;
import org.usfirst.frc.team6300.robot.subsystems.GearCam;

import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PlaceGear extends Command {
	Drivetrain drivetrain;
	GearCam gearCam;
	double slideSpeed = 0.0;
	double lastTurnAngle = 0.0;
	boolean isAuto;
	final boolean operatorSlideControl = true;
	
	public PlaceGear(Drivetrain drivetrain, GearCam gearCam, boolean isAuto) {
		this.drivetrain = drivetrain;
		this.gearCam = gearCam;
		this.isAuto = isAuto;
		requires(drivetrain);
		requires(gearCam);
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		if (!drivetrain.getPIDController().isEnabled()) {
			drivetrain.enable();
		}
		if (isAuto) {
			drivetrain.setDriveSpeeds(-0.2, 0);
		}
		System.out.println("Placing gear...");
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (gearCam.getTurnAngle() != lastTurnAngle) {
			lastTurnAngle = gearCam.getTurnAngle();
			drivetrain.pointTo(gearCam.getTurnAngle());
		}
		
		if (!isAuto && operatorSlideControl) {
			drivetrain.setDriveSpeeds(OI.gamepadDr.getRawAxis(OI.leftYAxis), OI.gamepadDr.getRawAxis(OI.leftXAxis));
		}
		else if (!isAuto && !operatorSlideControl) {
			slideSpeed = gearCam.getTurnAngle() * 5;
			drivetrain.setDriveSpeeds(OI.gamepadDr.getRawAxis(OI.leftYAxis), slideSpeed);
		}
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return false;
	}
	
	// Called once after isFinished returns true
	protected void end() {
		System.out.println("Done Placing Gear!");
		drivetrain.coast();
	}
	
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		System.out.println("Cancelled!");
		drivetrain.coast();
	}
}