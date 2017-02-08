package org.usfirst.frc.team6300.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.Robot;
import org.usfirst.frc.team6300.robot.RobotMap;

/**
 *
 */
public class MecanumDrive extends Command {
	public MecanumDrive() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.drivetrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.drivetrain.calibrateGyro();
		Robot.drivetrain.enable();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.drivetrain.teleDrive(OI.gamepadDr, RobotMap.forwardAxis, RobotMap.slideAxis, RobotMap.rotateAxis, RobotMap.throttleAxis, 1/3);
		Robot.drivetrain.putGyroValue();
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
