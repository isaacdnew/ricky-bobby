package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.subsystems.Shooter;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleShooter extends Command {
	Shooter shooter;
	char button;
	
    public TeleShooter(Shooter shooter, char buttonID) {
    	this.shooter = shooter;
    	button = buttonID;
        requires(shooter);
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
    		if (shooter.getSpeed() > 0) {
    			shooter.stop();
    		}
    		else {
    			shooter.spinUp(0.85);
    		}
    	}
    	else if (button == 'x') {
    		if (shooter.getSpeed() < 0) {
    			shooter.stop();
    		}
    		else {
    			shooter.spinUp(-0.5);
    		}
    	}
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
