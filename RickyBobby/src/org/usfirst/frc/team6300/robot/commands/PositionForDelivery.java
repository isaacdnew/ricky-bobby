package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
//import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PositionForDelivery extends Command {
	private String station;
	private Robot robot;
	
	public PositionForDelivery(Robot robot) {
		this.robot = robot;
		requires(robot.drivetrain);
	}
	
	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}
	
	private void left(boolean isRed) {
		if (isRed) {
			robot.drivetrain.enable();
			robot.drivetrain.goForward(0.3, 1.3);
		}
		else {
			robot.drivetrain.enable();
			robot.drivetrain.goForward(0.3, 1.3);
		}
		robot.drivetrain.stop();
		Timer.delay(0.5);
		robot.drivetrain.turnRight(60);
	}
	
	private void right(boolean isRed) {
		if (isRed) {
			robot.drivetrain.enable();
			robot.drivetrain.goForward(0.3, 1.2);
		}
		else {
			robot.drivetrain.enable();
			robot.drivetrain.goForward(0.3, 1.27);
		}
		robot.drivetrain.stop();
		Timer.delay(0.5);
		robot.drivetrain.turnLeft(60);
	}
	
	@Override
	protected void execute() {
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}
	
	@Override
	protected void end() {
		robot.drivetrain.getPIDController().setOutputRange(-0.4, 0.4);
		station = robot.stationChooser.getSelected();
		System.out.println("Positioning from " + station + "...");
		switch (station) {
		case "center": {
			//System.out.println("center");
			break;
		}
		case "left": {
			//System.out.println("left");
			left(robot.colorChooser.getSelected());
			break;
		}
		case "right": {
			//System.out.println("right");
			right(robot.colorChooser.getSelected());
			break;
		}
		}
		robot.drivetrain.coast();
	}
	
	@Override
	protected void interrupted() {
		robot.drivetrain.coast();
	}
}