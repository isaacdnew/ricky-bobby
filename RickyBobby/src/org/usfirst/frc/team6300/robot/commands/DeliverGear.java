package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DeliverGear extends Command {
	private String station = "center";
	
	public DeliverGear() {
		requires(Robot.drivetrain);
		station = Robot.stationChooser.getSelected();
	}
	
	// Called just before this Command runs the first time
	@Override
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
	
	@Override
	protected void execute() {
		SmartDashboard.putNumber("Heading", Robot.drivetrain.getHeading());
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
	}
	
	@Override
	protected void interrupted() {
	}
	
	private void deliverFromLeft() {
		Robot.drivetrain.enable();
		Robot.drivetrain.goForward(-0.3, 1.2);
		Timer.delay(0.5);
		Robot.drivetrain.turnRight(45);
		Robot.drivetrain.goForward(-0.3, 0.8);
	}
	
	private void deliverFromCenter() {
		Robot.drivetrain.enable();
		Robot.drivetrain.goForward(-0.3, 1);
		Robot.drivetrain.goForward(-0.2, 10);
		//Timer.delay(0.5);
		//Robot.drivetrain.wiggleForward(0.2, 0.05, 0.3, 4);
	}
	
	private void deliverFromRight() {
		Robot.drivetrain.enable();
		Robot.drivetrain.goForward(-0.3, 1.2);
		Timer.delay(0.5);
		Robot.drivetrain.turnLeft(45);
		Robot.drivetrain.goForward(-0.3, 0.8);
	}
}