package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleAgitator extends Command {
	char button;
	
    public TeleAgitator(char buttonID) {
    	button = buttonID;
        requires(Robot.agitator);
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
    	if (button == 'b') {
    		if (Robot.agitator.getSpeed() < 0) {
    			Robot.agitator.stop();
    		}
    		else {
    			Robot.agitator.spinUp(1);
    		}
    	}
    	else if (button == 'y') {
    		if (Robot.agitator.getSpeed() > 0) {
    			Robot.agitator.stop();
    		}
    		else {
    			Robot.agitator.spinUp(-1);
    		}
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.agitator.stop();
    }
}
