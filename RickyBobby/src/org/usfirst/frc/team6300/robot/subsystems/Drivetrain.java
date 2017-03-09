package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.RobotMap;
import org.usfirst.frc.team6300.robot.commands.MecanumDrive;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
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
	 * @param minPower The minimum throttle that the robot can go at
	 */
	public void teleDrive(Joystick joy, int forwardAxis, int slideAxis, int rotateAxis, int throttleAxis, double minPower) {
		//set power coefficient
		if (minPower > 1) {
			minPower = 1;
		}
		else if (minPower < 0) {
			minPower = 0;
		}
		
		double throttle = 1 - joy.getRawAxis(throttleAxis);
		double power = minPower + ((1 - minPower) * throttle);
		if (!gearIsFront) {
			power = -power;
		}
		
		//forward
		double forwardSpeed = addDeadZone(joy.getRawAxis(forwardAxis));
		
		lfSpeed = forwardSpeed * power;
		rfSpeed = forwardSpeed * power;
		lbSpeed = forwardSpeed * power;
		rbSpeed = forwardSpeed * power;
		
		//slide
		double slideSpeed = addDeadZone(joy.getRawAxis(slideAxis));
		lfSpeed -= slideSpeed * power;
		rfSpeed += slideSpeed * power;
		lbSpeed += slideSpeed * power;
		rbSpeed -= slideSpeed * power;
		
		//rotate
		double rotateSpeed = addDeadZone(joy.getRawAxis(rotateAxis)) / 3;
		lfSpeed -= rotateSpeed;
		rfSpeed += rotateSpeed;
		lbSpeed -= rotateSpeed;
		rbSpeed += rotateSpeed;
		
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
		//System.out.println("Motor Speeds- lf: " + lfSpeed + " rf: " + rfSpeed + " lb: " + lbSpeed + " rb: " + rbSpeed);
	}
	
	public void coast() {
		lfMotor.set(0);
		rfMotor.set(0);
		lbMotor.set(0);
		rbMotor.set(0);
	}
	
	private double addDeadZone(double rawAxisValue) {
		double newAxisValue;
		double deadZoneRadius = 0.2;
		if (deadZoneRadius < rawAxisValue) {
			newAxisValue = rawAxisValue - deadZoneRadius;
		}
		else if (rawAxisValue < -deadZoneRadius) {
			newAxisValue = rawAxisValue + deadZoneRadius;
		}
		else {
			newAxisValue = 0;
		}
		return newAxisValue;
	}
	
	public void goForward(double power, double seconds) {
		lfMotor.set(power);
		rfMotor.set(power);
		lbMotor.set(power);
		rbMotor.set(power);
		Timer.delay(seconds);
		coast();
	}
	
	public void turnRight(double power, double seconds) {
		lfMotor.set(power);
		rfMotor.set(power);
		lbMotor.set(-power);
		rbMotor.set(-power);
		Timer.delay(seconds);
		coast();
	}
	
	public void turnLeft(double power, double seconds) {
		lfMotor.set(-power);
		rfMotor.set(-power);
		lbMotor.set(power);
		rbMotor.set(power);
		Timer.delay(seconds);
		coast();
	}
	
	public void wiggleForward(double slidePower, double forwardPower, double amplitudeInSeconds, int iterations) {
		for (int i = 0; i < (iterations * 2); i++) {	
			lfMotor.set(-slidePower + forwardPower);
			rfMotor.set(slidePower + forwardPower);
			lbMotor.set(slidePower + forwardPower);
			rbMotor.set(-slidePower + forwardPower);
			Timer.delay(amplitudeInSeconds);
			slidePower = -slidePower;
		}
		coast();
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new MecanumDrive());
	}
}
