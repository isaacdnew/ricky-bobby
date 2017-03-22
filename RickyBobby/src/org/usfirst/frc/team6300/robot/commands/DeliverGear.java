package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DeliverGear extends Command {
	private String station;
	
	public DeliverGear() {
		requires(Robot.drivetrain);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		station = Robot.stationChooser.getSelected();
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
	}
	
	@Override
	protected boolean isFinished() {
		return false;
	}
	
	@Override
	protected void end() {
		Robot.drivetrain.disable();
	}
	
	@Override
	protected void interrupted() {
		Robot.drivetrain.disable();
	}
	
	private void deliverFromLeft() {
		Robot.drivetrain.enable();
		Robot.drivetrain.goForward(-0.3, 1.75);
		Timer.delay(0.5);
		Robot.drivetrain.turnRight(60);
		Robot.drivetrain.goForward(-0.2, 4);
		//Robot.drivetrain.goForward(-0.2, 10);
	}
	
	private void deliverFromCenter() {
		Robot.drivetrain.enable();
		Robot.drivetrain.goForward(-0.3, 1.75);
		//Robot.drivetrain.goForward(-0.3, 1);
		//Robot.drivetrain.goForward(-0.2, 10);
	}
	
	private void deliverFromRight() {
		Robot.drivetrain.enable();
		Robot.drivetrain.goForward(-0.3, 1.75);
		Timer.delay(0.5);
		Robot.drivetrain.turnRight(-60);
		Robot.drivetrain.goForward(-0.2, 4);
		//Robot.drivetrain.goForward(-0.2, 10);
	}
}