package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {
	private SpeedController flyMotor;
	
	public Shooter() {
		flyMotor = new VictorSP(RobotMap.flyMotor);
	}
	
	public void spinUp(double speed) {
		flyMotor.set(speed);
	}
	
	public void stop() {
		flyMotor.stopMotor();
	}
	
	public void initDefaultCommand() {
	}
}

