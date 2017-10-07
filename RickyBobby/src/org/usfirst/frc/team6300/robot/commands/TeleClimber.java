package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleClimber extends Command {
	Robot robot;
	
    public TeleClimber(Robot robot) {
        this.robot = robot;
    	requires(robot.climber);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	robot.climber.setSpeed(OI.gamepadDr.getRawAxis(OI.leftTrigger));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	robot.climber.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	robot.climber.stop();
    }
}
