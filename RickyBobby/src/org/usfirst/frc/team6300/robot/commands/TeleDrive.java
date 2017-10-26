package org.usfirst.frc.team6300.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.subsystems.Drivetrain;

/**
 *
 */
public class TeleDrive extends Command {
	private final boolean isPID = true;
	private Drivetrain drivetrain;
	String lastFront;
	
	public TeleDrive(Drivetrain drivetrain) {
		this.drivetrain = drivetrain;
		requires(drivetrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		if (isPID) {
			drivetrain.enable();
			drivetrain.getPIDController().setOutputRange(-1,  1);
		}
		else {
			drivetrain.disable();
		}
		lastFront = drivetrain.front();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		if (isPID) {
			drivetrain.telePIDDrive(OI.gamepadDr, OI.leftYAxis, OI.leftXAxis, OI.rightXAxis, OI.rightTrigger, 0.5);
		}
		else {
			drivetrain.teleDrive(OI.gamepadDr, OI.leftYAxis, OI.leftXAxis, OI.rightXAxis, OI.rightTrigger, 0.5);
		}
		if (lastFront != drivetrain.front()) {
			System.out.println("The " + drivetrain.front() + " end is the front.");
			lastFront = drivetrain.front();
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		drivetrain.coast();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
		drivetrain.coast();
	}
}
