package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;
import org.usfirst.frc.team6300.robot.commands.TeleClimber;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 *
 */
public class Climber extends Subsystem {
	static SpeedController climberMotor = new VictorSP(RobotMap.climberMotor);
	
	public Climber() {
		climberMotor.setInverted(RobotMap.climberInverted);
	}
	
	public void stop() {
		climberMotor.stopMotor();
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new TeleClimber());
	}

	public void setSpeed(double speed) {
		climberMotor.set(speed);
	}
}

