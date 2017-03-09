package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TeleShooter extends Command {

    public TeleShooter() {
        requires(Robot.shooter);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (OI.gamepadSh.getRawButton(OI.a)) {
    		Robot.shooter.spinUp(1);
//   		while (OI.gamepadSh.getRawButton(OI.a)) {
//				Timer.delay(0.005);
//			}
		}
		else if (OI.gamepadSh.getRawButton(OI.x)) {
			Robot.shooter.spinUp(-0.5);
//			while (OI.gamepadSh.getRawButton(OI.x)) {
//				Timer.delay(0.005);
//			}
		}
		else {
			Robot.shooter.stop();
			//System.out.println("Shooter off.");
//			while (!OI.gamepadSh.getRawButton(OI.a) && !OI.gamepadSh.getRawButton(OI.x)) {
//				Timer.delay(0.005);
//			}
		}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.shooter.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.shooter.stop();
    }
}
