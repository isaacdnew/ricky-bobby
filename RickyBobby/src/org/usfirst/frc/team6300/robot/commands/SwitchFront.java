package org.usfirst.frc.team6300.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team6300.robot.subsystems.Drivetrain;


/**
 *  Switches which end of the robot is treated as the front.
 */
public class SwitchFront extends Command {
	private Drivetrain drivetrain;
	public SwitchFront(Drivetrain drivetrain) {
		this.drivetrain = drivetrain;
		requires(drivetrain);
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
		drivetrain.switchFront();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
