package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 *
 */
public class TunePID extends InstantCommand {

    public TunePID() {
        super();
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.drivetrain);
    }

    // Called once when the command executes
    protected void initialize() {
    	Robot.drivetrain.enable();
    }
}
