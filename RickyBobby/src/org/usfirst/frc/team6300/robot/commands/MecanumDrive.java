package org.usfirst.frc.team6300.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.Robot;

/**
 *
 */
public class MecanumDrive extends Command {
	private static final boolean isPID = true;
	
	public MecanumDrive() {
		requires(Robot.drivetrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if (isPID) {
			Robot.drivetrain.enable();
			Robot.drivetrain.getPIDController().setOutputRange(-1,  1);
		}
		else {
			Robot.drivetrain.disable();
		}
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (isPID) {
			Robot.drivetrain.telePIDDrive(OI.gamepadDr, OI.leftYAxis, OI.leftXAxis, OI.rightXAxis, OI.rightTrigger, 0.5);
		}
		else {
			Robot.drivetrain.teleDrive(OI.gamepadDr, OI.leftYAxis, OI.leftXAxis, OI.rightXAxis, OI.rightTrigger, 0.5);
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
		Robot.drivetrain.coast();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		Robot.drivetrain.coast();
	}
}
