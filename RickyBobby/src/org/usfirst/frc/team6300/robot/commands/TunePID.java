package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TunePID extends Command {

    public TunePID() {
        super();
        requires(Robot.drivetrain);
    }

    // Called once when the command executes
    @Override
    protected void initialize() {
    	Robot.drivetrain.enable();
    }
    
    @Override
    protected void execute() {
    	SmartDashboard.putNumber("Heading", Robot.drivetrain.getHeading());
    	SmartDashboard.putNumber("Setpoint", Robot.drivetrain.getSetpoint());
    }
    
    @Override
    protected boolean isFinished() {
    	return false;
    }
    
    @Override
    protected void end() {
    	
    }
    
    @Override
    protected void interrupted() {
    	
    }
}
