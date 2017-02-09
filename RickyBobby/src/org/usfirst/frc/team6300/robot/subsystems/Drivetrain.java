package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.OI;
import org.usfirst.frc.team6300.robot.RobotMap;
import org.usfirst.frc.team6300.robot.commands.TeleDrive;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SpeedController;
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
	
	//gyro:
	static Gyro gyro = new ADXRS450_Gyro();
	
	String driveMode = "";
	
	public Drivetrain() {
		super("Drivetrain", 0.05, 0.0, 0.0);
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
	public void teleDrive(Joystick joy, int forwardAxis, int slideAxis, int rotateAxis, int throttleAxis, double minThrottle) {
		double forwardSpeed = joy.getRawAxis(forwardAxis);
		double slideSpeed = joy.getRawAxis(slideAxis);
		double rotateSpeed = joy.getRawAxis(rotateAxis);
		double throttle = joy.getRawAxis(throttleAxis);
		
		//set power coefficient
		double power = throttle + minThrottle;
		if (power > 1) {power = 1;}
		else if (power < -1) {power = -1;}
		//power /= 3;
		
		//forward
		lfSpeed += forwardSpeed * power;
		rfSpeed += forwardSpeed * power;
		lbSpeed += forwardSpeed * power;
		rbSpeed += forwardSpeed * power;
		
		//slide
		lfSpeed -= slideSpeed * power;
		rfSpeed += slideSpeed * power;
		lbSpeed += slideSpeed * power;
		rbSpeed -= slideSpeed * power;
		
		//rotate
		if (rotateSpeed > -0.01 || 0.01 > rotateSpeed) {
			setSetpoint(getSetpoint() + rotateSpeed * 5);
		}
		SmartDashboard.putNumber("Setpoint:", getSetpoint());
		updateMotors();
	}
	
	@Override
	protected void usePIDOutput(double output) {
		lfSpeed = -output;
		rfSpeed = output;
		lbSpeed = -output;
		rbSpeed = output;
		switch (driveMode) {
		case "TeleOp": {
			teleDrive(OI.gamepadDr, RobotMap.forwardAxis, RobotMap.slideAxis, RobotMap.rotateAxis, RobotMap.throttleAxis, 1);
			break;
		}
		
		case "Autonomous": {
			break;
		}
		}
	}
	
	public void updateMotors() {
		lfMotor.set(lfSpeed);
		rfMotor.set(rfSpeed);
		lbMotor.set(lbSpeed);
		rbMotor.set(rbSpeed);
	}
	
	public void setDriveMode(String newDriveMode) {
		driveMode = newDriveMode;
	}
	
	public void coast() {
		lfMotor.set(0);
		rfMotor.set(0);
		lbMotor.set(0);
		rbMotor.set(0);
	}
	
	public void calibrateGyro() {
		coast();
		System.out.println("Calibrating the gyro...");
		gyro.calibrate();
		System.out.println("Done!");
	}
	
	/**
	 * Puts the current heading to the SmartDashboard.
	 */
	public void putGyroValue() {
		SmartDashboard.putNumber("Heading", gyro.getAngle());
	}
	
	@Override
	protected double returnPIDInput() {
		return gyro.getAngle();
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new TeleDrive());
	}
}
