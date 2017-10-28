package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DrivePastBaseline extends Command {
	Drivetrain drivetrain;
	
    public DrivePastBaseline(Drivetrain drivetrain) {
    	this.drivetrain = drivetrain;
    	requires(drivetrain);
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
    	drivetrain.goForward(-0.3, 1.5);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	drivetrain.coast();
    }
}
