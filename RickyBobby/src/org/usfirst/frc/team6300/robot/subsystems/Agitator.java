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
	
	public Agitator() {
		motors.setInverted(RobotMap.agitatorInverted);
	}
	
	public void spinUp(double speed) {
		motors.set(speed);
		System.out.println("Agitators on at power " + speed + ".");
	}
	
	public void stop() {
		motors.stopMotor();
		System.out.println("Agitators off.");
	}
	
	public void initDefaultCommand() {
	}
}