package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.RobotMap;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
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
	
	public void update() {
		if (OI.gamepadSh.getRawButton(1)) {
			while (OI.gamepadSh.getRawButton(1)) {
				Timer.delay(0.005);
			}
			spinUp(1);
		}
		else if (OI.gamepadSh.getRawButton(3)) {
			while (OI.gamepadSh.getRawButton(3)) {
				Timer.delay(0.005);
			}
			spinUp(-0.5);
		}
		else {
			Timer.delay(0.005);
		}
	}
	
	private void spinUp(double speed) {
		flyMotor.set(speed);
		System.out.println("Spinning up the shooter!");
	}
	
	public void stop() {
		flyMotor.stopMotor();
	}
	
	public void initDefaultCommand() {
	}
}

