package org.usfirst.frc.team6300.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.Robot;

/**
 *
 */
public class MecanumDrive extends Command {
	private static final boolean isPID = true;
	private Robot robot;
	
	public MecanumDrive(Robot robot) {
		this.robot = robot;
		requires(robot.drivetrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if (isPID) {
			robot.drivetrain.enable();
			robot.drivetrain.getPIDController().setOutputRange(-1,  1);
		}
		else {
			robot.drivetrain.disable();
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (isPID) {
			robot.drivetrain.telePIDDrive(OI.gamepadDr, OI.leftYAxis, OI.leftXAxis, OI.rightXAxis, OI.rightTrigger, 0.5);
		}
		else {
			robot.drivetrain.teleDrive(OI.gamepadDr, OI.leftYAxis, OI.leftXAxis, OI.rightXAxis, OI.rightTrigger, 0.5);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		robot.drivetrain.coast();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		robot.drivetrain.coast();
	}
}
