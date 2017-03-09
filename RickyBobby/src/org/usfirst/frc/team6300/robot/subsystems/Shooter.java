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
	double speed;
	
	public Shooter() {
		flyMotor.setInverted(RobotMap.flyInverted);
	}
	
	public void spinUp(double newSpeed) {
		speed = newSpeed;
		flyMotor.set(speed);
		System.out.println("Shooter on at power " + speed + ".");
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void stop() {
		speed = 0;
		flyMotor.stopMotor();
		System.out.println("Shooter off.");
	}
	
	public void initDefaultCommand() {
	}
}

