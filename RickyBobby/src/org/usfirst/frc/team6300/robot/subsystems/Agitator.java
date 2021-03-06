package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Agitator extends Subsystem {
	static SpeedController agitatorMotor = new VictorSP(RobotMap.agitatorMotor);
	double speed;
	
	public Agitator() {
		agitatorMotor.setInverted(RobotMap.agitatorInverted);
	}
	
	public void spinUp(double newSpeed) {
		speed = newSpeed;
		agitatorMotor.set(speed);
		System.out.println("Agitators on at power " + speed + ".");
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public void stop() {
		speed = 0;
		agitatorMotor.stopMotor();
		System.out.println("Agitator off.");
	}
	
	public void initDefaultCommand() {
	}
}