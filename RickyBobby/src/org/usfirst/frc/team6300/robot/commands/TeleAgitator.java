package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.Robot;
import org.usfirst.frc.team6300.robot.RobotMap;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleAgitator extends Command {

    public TeleAgitator() {
        requires(Robot.agitator);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.agitator.spinUp(1);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (OI.gamepadSh.getRawButton(RobotMap.y)) {
    		while (OI.gamepadSh.getRawButton(RobotMap.y)) {
    			Timer.delay(0.005);
    		}
    		Robot.agitator.switchDirections();
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.agitator.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.agitator.stop();
    }
}
