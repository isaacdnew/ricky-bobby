package org.usfirst.frc.team6300.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team6300.robot.Robot;


/**
 *
 */
public class SwitchFront extends Command {
	private Robot robot;
	public SwitchFront(Robot robot) {
		this.robot = robot;
		requires(robot.drivetrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return true;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		robot.drivetrain.switchFront();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
