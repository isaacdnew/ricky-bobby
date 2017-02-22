package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Agitator extends Subsystem {
	static SpeedController motors = new VictorSP(RobotMap.agitatorController);
	double speed;
	
	public Agitator() {
		motors.setInverted(RobotMap.agitatorInverted);
	}
	
	public void spinUp(double newSpeed) {
		speed = newSpeed;
		motors.set(speed);
		System.out.println("Agitators on at power " + speed + ".");
	}
	
	public void switchDirections() {
		speed = -speed;
		spinUp(speed);
	}
	
	public void stop() {
		motors.stopMotor();
		System.out.println("Agitators off.");
	}
	
	public void initDefaultCommand() {
	}
}