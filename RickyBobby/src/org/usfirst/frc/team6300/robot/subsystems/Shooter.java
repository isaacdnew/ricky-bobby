package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;
import org.usfirst.frc.team6300.robot.commands.TeleShooter;

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
		if (speed == 0) {
			System.out.println("Shooter off.");
		}
		System.out.println("Shooter on at power" + speed + ".");
	}
	
	public void stop() {
		flyMotor.stopMotor();
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new TeleShooter());
	}
}

