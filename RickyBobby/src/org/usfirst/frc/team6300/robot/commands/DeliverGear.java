package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DeliverGear extends Command {
	private String station;
	
	public DeliverGear(String allianceStation) {
		requires(Robot.drivetrain);
		station = allianceStation;
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}
	
	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return true;
	}
	
	// Called once after isFinished returns true
	protected void end() {
		switch (station) {
		case "left": {
			deliverFromLeft();
			break;
		}
		case "center": {
			deliverFromCenter();
			break;
		}
		case "right": {
			deliverFromRight();
			break;
		}
		}
	}
	
	private void deliverFromLeft() {
		Robot.drivetrain.goForward(0.5, 5);
		//Robot.drivetrain.turnRight(0.5, 0.2);
	}
	
	private void deliverFromCenter() {
		Robot.drivetrain.goForward(0.5, 5);
		//Robot.drivetrain.wiggle(0.5, 0.3, 4);
	}
	
	private void deliverFromRight() {
		Robot.drivetrain.goForward(0.5, 5);
		//Robot.drivetrain.turnLeft(0.5, 0.2);
	}
	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}