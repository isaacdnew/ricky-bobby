package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.subsystems.Drivetrain;
import org.usfirst.frc.team6300.robot.subsystems.GearCam;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class PlaceGear extends Command {
	Drivetrain drivetrain;
	GearCam gearCam;
	double slideDistance = 0.0;
	double lastCenterX = 0.0;
	boolean isAuto;
	
    public PlaceGear(Drivetrain drivetrain, GearCam gearCam, boolean isAuto) {
    	this.drivetrain = drivetrain;
    	this.gearCam = gearCam;
    	this.isAuto = isAuto;
    	requires(drivetrain);
    	requires(gearCam);
    }
    
    // Called just before this Command runs the first time
    protected void initialize() {
    	if (!drivetrain.getPIDController().isEnabled()) {
    		drivetrain.enable();
    	}
    	if (isAuto) {
    		drivetrain.setDriveSpeeds(-0.2, 0);
    	}
    	lastCenterX = gearCam.getCenterX();
    	System.out.println("Placing Gear...");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (gearCam.getCenterX() != lastCenterX) {
    		lastCenterX = gearCam.getCenterX();
    		double turnAngle = ((gearCam.getCenterX() - (gearCam.getImgWidth() / 1.25)) / (gearCam.getImgWidth()) * (gearCam.getFieldOfView() / 5));
    		double slideSpeed = (gearCam.getCenterX() - (gearCam.getImgWidth() / 1.25)) / gearCam.getImgWidth();
    		if (isAuto) {
    			drivetrain.setDriveSpeeds(-0.2, slideSpeed);
    		}
    		else {
    			drivetrain.setDriveSpeeds(OI.gamepadDr.getRawAxis(OI.leftYAxis), OI.gamepadDr.getRawAxis(OI.leftXAxis));
    		}
    		drivetrain.turnRight(turnAngle);
        	System.out.println("Turning " + turnAngle + " degrees.");
        	SmartDashboard.putNumber("turnAngle", turnAngle);
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        /*if (gearCam.getCenterX() == 50) {
        	return true;
        }
        else {
        	return false;
        }*/
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Done Placing Gear!");
    	drivetrain.coast();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	System.out.println("Cancelled!");
    	drivetrain.coast();
    }
}
