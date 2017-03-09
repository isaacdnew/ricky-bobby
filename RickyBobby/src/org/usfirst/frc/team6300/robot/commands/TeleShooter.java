package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleShooter extends Command {
	char button;
	
    public TeleShooter(char buttonID) {
    	button = buttonID;
        requires(Robot.shooter);
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
    		if (Robot.shooter.getSpeed() > 0) {
    			Robot.shooter.stop();
    		}
    		else {
    			Robot.shooter.spinUp(1);
    		}
    	}
    	else if (button == 'x') {
    		if (Robot.shooter.getSpeed() < 0) {
    			Robot.shooter.stop();
    		}
    		else {
    			Robot.shooter.spinUp(-0.5);
    		}
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
