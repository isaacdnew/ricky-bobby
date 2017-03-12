package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class DeliverGear extends InstantCommand {
	private String station;
	
	public DeliverGear(String allianceStation) {
		requires(Robot.drivetrain);
		station = allianceStation;
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
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
		Robot.drivetrain.enable();
		Robot.drivetrain.goForward(0.3, 2);
		Timer.delay(0.5);
		Robot.drivetrain.turnRight(45);
	}
	
	private void deliverFromCenter() {
		Robot.drivetrain.enable();
		Robot.drivetrain.goForward(0.3, 2.5);
		Timer.delay(0.5);
		Robot.drivetrain.wiggleForward(0.2, 0.05, 0.3, 4);
	}
	
	private void deliverFromRight() {
		Robot.drivetrain.enable();
		Robot.drivetrain.goForward(0.3, 2);
		Timer.delay(0.5);
		Robot.drivetrain.turnLeft(45);
	}
}