package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;
import org.usfirst.frc.team6300.robot.commands.MecanumDrive;

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
	 * @param minPower The minimum throttle that the robot can go at
	 */
	public void teleDrive(Joystick joy, int forwardAxis, int slideAxis, int rotateAxis, int throttleAxis, double minPower) {
		double throttle = joy.getRawAxis(throttleAxis);
		
		//set power coefficient
		double power = minPower + ((1 - minPower) * throttle);
		
		if (power > 1) {
			power = 1;
		}
		else if (power < -1) {
			power = -1;
		}
		
		//forward
		double forwardSpeed = joy.getRawAxis(forwardAxis);
		lfSpeed = forwardSpeed * power;
		rfSpeed = forwardSpeed * power;
		lbSpeed = forwardSpeed * power;
		rbSpeed = forwardSpeed * power;
		
		//slide
		double slideSpeed = joy.getRawAxis(slideAxis);
		lfSpeed -= slideSpeed * power;
		rfSpeed += slideSpeed * power;
		lbSpeed += slideSpeed * power;
		rbSpeed -= slideSpeed * power;
		
		//rotate
		double rotateSpeed = joy.getRawAxis(rotateAxis) / 3;
		lfSpeed -= rotateSpeed;
		rfSpeed += rotateSpeed;
		lbSpeed -= rotateSpeed;
		rbSpeed += rotateSpeed;
		
		updateMotors();
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
		setDefaultCommand(new MecanumDrive());
	}
}
