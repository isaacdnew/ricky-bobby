package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LowGoal extends Command {
	private boolean isRed;
	private Robot robot;
    public LowGoal(Robot robot) {
    	this.robot = robot;
    	requires(robot.agitator);
    	requires(robot.shooter);
    	requires(robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isRed = robot.colorChooser.getSelected();
    	robot.shooter.spinUp(1);
    	robot.agitator.spinUp(1);
    	if (isRed) {
    		Timer.delay(5);
    		robot.shooter.stop();
        	robot.agitator.stop();
    		robot.drivetrain.goForward(0.3, 1.2);
    	}
    	else {
    		Timer.delay(10);
        	robot.shooter.stop();
        	robot.agitator.stop();
        	robot.drivetrain.enable();
    		robot.drivetrain.goForward(-0.3, 1.2);
    		robot.drivetrain.turnRight(60);
    		robot.drivetrain.goForward(-0.2, 2.8);
    		robot.drivetrain.goForward(-0.1, 10);
    	}
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
