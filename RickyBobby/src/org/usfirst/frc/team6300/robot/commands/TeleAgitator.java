package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.subsystems.Agitator;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleAgitator extends Command {
	char button;
	Agitator agitator;
	
    public TeleAgitator(Agitator agitator, char buttonID) {
    	this.agitator = agitator;
    	button = buttonID;
        requires(agitator);
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
    		if (agitator.getSpeed() < 0) {
    			agitator.stop();
    		}
    		else {
    			agitator.spinUp(1);
    		}
    	}
    	else if (button == 'y') {
    		if (agitator.getSpeed() > 0) {
    			agitator.stop();
    		}
    		else {
    			agitator.spinUp(-1);
    		}
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	agitator.stop();
    }
}
