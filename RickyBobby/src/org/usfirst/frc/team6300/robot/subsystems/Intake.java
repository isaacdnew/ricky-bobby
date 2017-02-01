package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;
import org.usfirst.frc.team6300.robot.commands.TeleIntake;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Intake extends Subsystem {
	static SpeedController intakeMotor = new VictorSP(RobotMap.intakeMotor);
	
	public Intake() {
		intakeMotor.setInverted(RobotMap.intakeInverted);
	}
	
	public void stop() {
		intakeMotor.stopMotor();
		System.out.println("Stopping!");
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new TeleIntake());
	}

	public void setSpeed(double speed) {
		intakeMotor.set(speed);
	}
}

