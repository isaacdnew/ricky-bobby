package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.RobotMap;
import org.usfirst.frc.team6300.robot.commands.TeleShooter;

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
		if (OI.gamepadSh.getRawButton(OI.a)) {
			while (OI.gamepadSh.getRawButton(OI.a)) {
				Timer.delay(0.005);
			}
			spinUp(1);
		}
		else if (OI.gamepadSh.getRawButton(OI.x)) {
			while (OI.gamepadSh.getRawButton(OI.x)) {
				Timer.delay(0.005);
			}
			spinUp(-0.5);
		}
		else {
			stop();
			System.out.println("Shooter off.");
			while (!OI.gamepadSh.getRawButton(OI.a) && !OI.gamepadSh.getRawButton(OI.x)) {
				Timer.delay(0.005);
			}
		}
	}
	
	private void spinUp(double speed) {
		flyMotor.set(speed);
		System.out.println("Shooter on at power" + speed + ".");
	}
	
	public void stop() {
		flyMotor.stopMotor();
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new TeleShooter());
	}
}

