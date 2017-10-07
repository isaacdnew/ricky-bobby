package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;

//import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TunePID extends Command {
	Robot robot;
	
    public TunePID(Robot robot) {
        super();
        this.robot = robot;
        requires(robot.drivetrain);
    }

    // Called once when the command executes
    @Override
    protected void initialize() {
    	robot.drivetrain.enable();
    }
    
    @Override
    protected void execute() {
    	SmartDashboard.putNumber("Heading", robot.drivetrain.getHeading());
    	SmartDashboard.putNumber("Setpoint", robot.drivetrain.getSetpoint());
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
