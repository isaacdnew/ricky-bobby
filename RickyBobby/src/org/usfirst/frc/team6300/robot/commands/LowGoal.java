package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class LowGoal extends Command {
	private boolean isRed;
	
    public LowGoal() {
    	requires(Robot.agitator);
    	requires(Robot.shooter);
    	requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	isRed = Robot.colorChooser.getSelected();
    	Robot.shooter.spinUp(1);
    	Robot.agitator.spinUp(1);
    	if (isRed) {
    		Timer.delay(5);
    		Robot.shooter.stop();
        	Robot.agitator.stop();
    		Robot.drivetrain.goForward(0.3, 1.2);
    	}
    	else {
    		Timer.delay(10);
        	Robot.shooter.stop();
        	Robot.agitator.stop();
        	Robot.drivetrain.enable();
    		Robot.drivetrain.goForward(-0.3, 1.2);
    		Robot.drivetrain.turnRight(60);
    		Robot.drivetrain.goForward(-0.2, 2.8);
    		Robot.drivetrain.goForward(-0.1, 10);
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
