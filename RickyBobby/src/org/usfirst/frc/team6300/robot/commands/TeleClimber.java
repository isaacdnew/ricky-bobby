package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.Robot;
import org.usfirst.frc.team6300.robot.subsystems.Climber;
import org.usfirst.frc.team6300.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleClimber extends Command {
	Climber climber;
	Drivetrain drivetrain;
	boolean climbing = false;
	
    public TeleClimber(Robot robot) {
        climber = robot.climber;
        drivetrain = robot.drivetrain;
    	requires(climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (OI.gamepadDr.getRawAxis(OI.leftTrigger) > 0 && Timer.getMatchTime() >= 100) {
    		climbing = true;
    		climber.setSpeed(OI.gamepadDr.getRawAxis(OI.leftTrigger));
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	climber.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	climber.stop();
    }
    
    public boolean isClimbing() {
    	return climbing;
    }
}
