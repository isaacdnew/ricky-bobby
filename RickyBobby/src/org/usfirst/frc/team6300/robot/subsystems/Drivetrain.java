package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;
import org.usfirst.frc.team6300.robot.commands.TeleDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * The drivetrain, consisting of four drive motors.
 */
public class Drivetrain extends Subsystem {
	//motors:
	static SpeedController lfMotor = new VictorSP(RobotMap.lfMotor);
	static SpeedController rfMotor = new VictorSP(RobotMap.rfMotor);
	static SpeedController lbMotor = new VictorSP(RobotMap.lbMotor);
	static SpeedController rbMotor = new VictorSP(RobotMap.rbMotor);
	
	//motor speeds:
	double lfSpeed = 0;
	double rfSpeed = 0;
	double lbSpeed = 0;
	double rbSpeed = 0;
	boolean gearIsFront = true;
	
	public Drivetrain() {
		lfMotor.setInverted(RobotMap.lfInverted);
		rfMotor.setInverted(RobotMap.rfInverted);
		lbMotor.setInverted(RobotMap.lbInverted);
		rbMotor.setInverted(RobotMap.rbInverted);
	}
	
	/**
	 * Drives the robot with a joystick.
	 * @param joy The joystick to use
	 * @param forwardAxis The axis that, when positive, moves the robot forward
	 * @param slideAxis The axis that, when positive, slides the robot to its right
	 * @param rotationAxis The axis that, when positive, turns the robot clockwise
	 * @param throttleAxis The axis that, when positive, increases the power coefficient
	 * @param minThrottle The minimum throttle that the robot can go at
	 */
	public void teleDrive(Joystick joy, int forwardAxis, int slideAxis, int rotateAxis, int throttleAxis, double minThrottle) {
		double forwardSpeed = joy.getRawAxis(forwardAxis);
		double slideSpeed = joy.getRawAxis(slideAxis);
		double rotateSpeed = joy.getRawAxis(rotateAxis);
		double throttle = joy.getRawAxis(throttleAxis);
		
		//set power coefficient
		double power = minThrottle + ((1 - minThrottle) * throttle);
		if (power > 1) {power = 1;}
		else if (power < -1) {power = -1;}
		if (!gearIsFront) {
			power = -power;
		}
		//power /= 3;
		
		//forward
		lfSpeed = forwardSpeed * power;
		rfSpeed = forwardSpeed * power;
		lbSpeed = forwardSpeed * power;
		rbSpeed = forwardSpeed * power;
		
		//slide
		lfSpeed -= slideSpeed * power * 2;
		rfSpeed += slideSpeed * power * 2;
		lbSpeed += slideSpeed * power * 2;
		rbSpeed -= slideSpeed * power * 2;
		
		//rotate
		lfSpeed -= rotateSpeed / 3;
		rfSpeed += rotateSpeed / 3;
		lbSpeed -= rotateSpeed / 3;
		rbSpeed += rotateSpeed / 3;
		
		updateMotors();
	}
	
	public void switchFront() {
			gearIsFront = !gearIsFront;
			if (gearIsFront) {
				System.out.println("The gear end is the front.");
			}
			else {
				System.out.println("The intake end is the front.");
			}
	}
	
	public void updateMotors() {
		lfMotor.set(lfSpeed);
		rfMotor.set(rfSpeed);
		lbMotor.set(lbSpeed);
		rbMotor.set(rbSpeed);
	}
	
	public void brake() {
		lfMotor.stopMotor();
		rfMotor.stopMotor();
		lbMotor.stopMotor();
		rbMotor.stopMotor();
	}
	
	public void coast() {
		lfMotor.set(0);
		rfMotor.set(0);
		lbMotor.set(0);
		rbMotor.set(0);
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new TeleDrive());
	}
}
