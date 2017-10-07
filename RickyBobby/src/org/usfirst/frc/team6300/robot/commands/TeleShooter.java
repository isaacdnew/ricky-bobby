package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleShooter extends Command {
	Robot robot;
	char button;
	
    public TeleShooter(Robot robot, char buttonID) {
    	this.robot = robot;
    	button = buttonID;
        requires(robot.shooter);
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
    	if (button == 'a') {
    		if (robot.shooter.getSpeed() > 0) {
    			robot.shooter.stop();
    		}
    		else {
    			robot.shooter.spinUp(1);
    		}
    	}
    	else if (button == 'x') {
    		if (robot.shooter.getSpeed() < 0) {
    			robot.shooter.stop();
    		}
    		else {
    			robot.shooter.spinUp(-0.5);
    		}
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
