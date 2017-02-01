package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Shooter extends Subsystem {
	static SpeedController flyMotor = new VictorSP(RobotMap.flyMotor);
	
	public Shooter() {
		flyMotor.setInverted(RobotMap.flyInverted);
	}
	
	public void spinUp(double speed) {
		flyMotor.set(speed);
		System.out.println("Spinning up!");
	}
	
	public void stop() {
		flyMotor.stopMotor();
		System.out.println("Stopping!");
	}
	
	public void initDefaultCommand() {
	}
}

