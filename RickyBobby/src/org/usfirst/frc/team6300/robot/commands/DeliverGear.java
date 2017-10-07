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
		Robot.drivetrain.getPIDController().setOutputRange(-0.4, 0.4);
		station = Robot.stationChooser.getSelected();
		switch (station) {
		case "center": {
			Robot.drivetrain.enable();
			Robot.drivetrain.goForward(0.3, 1.6);
			Robot.drivetrain.goForward(0.1, 10);
			break;
		}
		case "leftRed": {
			leftRedOrRightBlue(true);
			break;
		}
		case "leftBlue": {
			leftBlueOrRightRed(false);
			break;
		}
		case "rightRed": {
			leftBlueOrRightRed(true);
			break;
		}
		case "rightBlue": {
			leftRedOrRightBlue(false);
			break;
		}
		}
	}
	
	private void leftRedOrRightBlue(boolean isRed) {
		Robot.drivetrain.enable();
		Robot.drivetrain.goForward(0.3, 1.27);
		Timer.delay(0.5);
		if (isRed) {
			Robot.drivetrain.turnRight(60);
		}
		else {
			Robot.drivetrain.turnRight(-60);
		}
		Robot.drivetrain.goForward(0.2, 2.1);
	}
	
	private void leftBlueOrRightRed(boolean isRed) {
		Robot.drivetrain.enable();
		Robot.drivetrain.goForward(0.3, 1.2);
		Timer.delay(0.5);
		if (isRed) {
			Robot.drivetrain.turnRight(-60);
		}
		else {
			Robot.drivetrain.turnRight(60);
		}
		Robot.drivetrain.goForward(0.2, 2.8);
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
}