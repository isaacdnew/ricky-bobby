package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.*;
import org.usfirst.frc.team6300.robot.commands.TeleDrive;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.PIDSubsystem;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The pid-controlled drivetrain, consisting of four drive motors and a gyro sensor.
 */
public class Drivetrain extends PIDSubsystem {
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
	
	private boolean gearIsFront = false;
	
	//gyro:
	static Gyro gyro = new ADXRS450_Gyro();
	double pidOutput = 0;
	
	public Drivetrain() {
		super("Drivetrain", 0.05, 0.0, 0.0);
		setAbsoluteTolerance(0.5);
		getPIDController().setContinuous(true);
		setInputRange(0, 360);
		setSetpoint(0);
		
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
	public void teleDrive(Joystick joy, int forwardAxis, int slideAxis, int rotateAxis, int throttleAxis, double minPower) {
		//set power coefficient
		double throttle = joy.getRawAxis(throttleAxis);
		if (minPower > 1) {
			minPower = 1;
		}
		else if (minPower < 0) {
			minPower = 0;
		}
		double power = minPower + ((1 - minPower) * throttle);
		if (!gearIsFront) {
			power = -power;
		}
		
		//rotate
		double rotateSpeed = (joy.getRawAxis(rotateAxis) / 3);
		if (rotateSpeed < -0.01 || 0.01 < rotateSpeed) {
			lfSpeed = rotateSpeed;
			rfSpeed = -rotateSpeed;
			lbSpeed = rotateSpeed;
			rbSpeed = -rotateSpeed;
			setSetpoint(gyro.getAngle());
		}
		SmartDashboard.putNumber("Setpoint", getSetpoint());
		SmartDashboard.putNumber("Heading", gyro.getAngle());
		lfSpeed += pidOutput;
		rfSpeed -= pidOutput;
		lbSpeed += pidOutput;
		rbSpeed -= pidOutput;
		
		//forward
		double forwardSpeed = joy.getRawAxis(forwardAxis);
		lfSpeed += forwardSpeed * power;
		rfSpeed += forwardSpeed * power;
		lbSpeed += forwardSpeed * power;
		rbSpeed += forwardSpeed * power;
		
		//slide
		double slideSpeed = joy.getRawAxis(slideAxis);
		lfSpeed -= slideSpeed * power;
		rfSpeed += slideSpeed * power;
		lbSpeed += slideSpeed * power;
		rbSpeed -= slideSpeed * power;
		
		updateMotors();
	}
	
	@Override
	protected void usePIDOutput(double output) {
		pidOutput = output;
	}
	
	public void updateMotors() {
		lfMotor.set(lfSpeed);
		rfMotor.set(rfSpeed);
		lbMotor.set(lbSpeed);
		rbMotor.set(rbSpeed);
	}
	
	public void coast() {
		lfMotor.set(0);
		rfMotor.set(0);
		lbMotor.set(0);
		rbMotor.set(0);
	}
	
	public void switchFront() {
		gearIsFront = !gearIsFront;
		if (gearIsFront) {
			System.out.println("The gear collector is the front.");
		}
		else {
			System.out.println("The intake is the front.");
		}
	}
	
	public void calibrateGyro() {
		coast();
		disable();
		System.out.println("Calibrating the gyro...");
		gyro.calibrate();
		System.out.println("Done!");
		enable();
	}
	
	/**
	 * Puts the current heading to the SmartDashboard.
	 */
	
	@Override
	public void disable() {
		super.disable();
		pidOutput = 0;
	}
	
	@Override
	protected double returnPIDInput() {
		return gyro.getAngle();
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new TeleDrive());
	}
	
	public void goForward(double power, double seconds) {
		lfSpeed = power;
		rfSpeed = power;
		lbSpeed = power;
		rbSpeed = power;
		updateMotors();
		if (getPIDController().isEnabled()) {
			for (int ms = 0; ms > seconds * 1000; ms += 5) {
				lfSpeed += pidOutput;
				rfSpeed -= pidOutput;
				lbSpeed += pidOutput;
				rbSpeed -= pidOutput;
				updateMotors();
				
				Timer.delay(0.005);
			}
		}
		else {
			Timer.delay(seconds);
		}
		coast();
	}
}
