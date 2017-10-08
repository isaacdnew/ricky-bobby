package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.subsystems.Drivetrain;

//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TunePID extends Command {
	Drivetrain drivetrain;
	
    public TunePID(Drivetrain drivetrain) {
        super();
        this.drivetrain = drivetrain;
        requires(drivetrain);
    }

    // Called once when the command executes
    @Override
    protected void initialize() {
    	drivetrain.enable();
    }
    
    @Override
    protected void execute() {
    	SmartDashboard.putNumber("Heading", drivetrain.getHeading());
    	SmartDashboard.putNumber("Setpoint", drivetrain.getSetpoint());
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
