package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.subsystems.Intake;

import edu.wpi.first.wpilibj.command.Command;

/**
 *  The ball intake.
 */
public class TeleIntake extends Command {
	Intake intake;
    public TeleIntake(Intake intake) {
    	this.intake = intake;
        requires(intake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	intake.setSpeed(OI.gamepadSh.getRawAxis(OI.rightTrigger) - OI.gamepadSh.getRawAxis(OI.leftTrigger));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	intake.stop();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	intake.stop();
    }
}
