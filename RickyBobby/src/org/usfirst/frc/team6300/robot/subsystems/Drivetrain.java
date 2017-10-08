package org.usfirst.frc.team6300.robot.subsystems;

import org.usfirst.frc.team6300.robot.Robot;
import org.usfirst.frc.team6300.robot.RobotMap;
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
 * The drivetrain, consisting of four drive motors.
 */
public class Drivetrain extends PIDSubsystem {
	//Robot robot;
	//motors:
	SpeedController lfMotor = new VictorSP(RobotMap.lfMotor);
	SpeedController rfMotor = new VictorSP(RobotMap.rfMotor);
	SpeedController lbMotor = new VictorSP(RobotMap.lbMotor);
	SpeedController rbMotor = new VictorSP(RobotMap.rbMotor);
	
	//motor speeds:
	double lfSpeed = 0;
	double rfSpeed = 0;
	double lbSpeed = 0;
	double rbSpeed = 0;
	
	//heading control:
	Gyro gyro;
	double lfOutput = 0;
	double rfOutput = 0;
	double lbOutput = 0;
	double rbOutput = 0;
	
	boolean gearIsFront = true;
	
	public Drivetrain(Robot robot) {
		super(0.04, 0.003, 0.1);
		//this.robot = robot;
		gyro = new ADXRS450_Gyro();
		getPIDController().setAbsoluteTolerance(0.5);
		getPIDController().setContinuous(true);
		getPIDController().setInputRange(0, 360);
		
		lfMotor.setInverted(RobotMap.lfInverted);
		rfMotor.setInverted(RobotMap.rfInverted);
		lbMotor.setInverted(RobotMap.lbInverted);
		rbMotor.setInverted(RobotMap.rbInverted);
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new TeleDrive(this));
	}
	
	
	
	
	
	
	
	
	
	
	/*-------------*/
	/* PID Methods */
	/*-------------*/
	
	protected double returnPIDInput() {
		double angle = gyro.getAngle();
		angle -= 360 * Math.floor(angle/360);
		return angle;
	}
	
	public double getHeading() {
		return returnPIDInput();
	}
	
	protected void usePIDOutput(double output) {
		lfOutput = -output;
		rfOutput = output;
		lbOutput = -output;
		rbOutput = output;
		
		lfOutput += lfSpeed;
		rfOutput += rfSpeed;
		lbOutput += lbSpeed;
		rbOutput += rbSpeed;
		
		if (lfOutput > 1 || rfOutput > 1 || lbOutput > 1 || rbOutput > 1) {
			double maxOutput = findMax(lfOutput, rfOutput, lbOutput, rbOutput);
			lfOutput /= maxOutput;
			rfOutput /= maxOutput;
			lbOutput /= maxOutput;
			rbOutput /= maxOutput;
		}
		
		lfMotor.set(lfOutput);
		rfMotor.set(rfOutput);
		lbMotor.set(lbOutput);
		rbMotor.set(rbOutput);
		
		SmartDashboard.putNumber("Heading", gyro.getAngle());
	}
	
	@Override
	public void disable() {
		super.disable();
		lfOutput = 0;
		rfOutput = 0;
		lbOutput = 0;
		rbOutput = 0;
		
		lfSpeed = 0;
		rfSpeed = 0;
		lbSpeed = 0;
		rbSpeed = 0;
		setSetpoint(0);
	}
	
	@Override
	public void enable() {
		gyro.reset();
		setSetpoint(0);
		super.enable();
	}
	
	public void calibrateGyro() {
		disable();
		System.out.println("Calibrating the gyro....");
		gyro.calibrate();
		setSetpoint(0);
		System.out.println("Done calibrating the gyro.");
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/*--------------------*/
	/* Autonomous Methods */
	/*--------------------*/
	
	public void goForward(double power, double seconds) {
		if (getPIDController().isEnabled()) {
			lfSpeed = power;
			rfSpeed = power;
			lbSpeed = power;
			rbSpeed = power;
		}
		else {
			lfMotor.set(power);
			rfMotor.set(power);
			lbMotor.set(power);
			rbMotor.set(power);
		}
		Timer.delay(seconds);
		stop();
	}
	
	public void turnRight(double degrees) {
		if (!getPIDController().isEnabled()) {
			enable();
		}
		setSetpoint(getSetpoint() + degrees);
		
		while (!onTarget()) {
			Timer.delay(0.1);
		}
		stop();
	}
	
	public void turnLeft(double degrees) {
		if (!getPIDController().isEnabled()) {
			enable();
		}
		setSetpoint(getSetpoint() + degrees);
		while (!getPIDController().onTarget()) {
			Timer.delay(0.1);
		}
		stop();
	}
	
	public void wiggleForward(double slidePower, double forwardPower, double amplitudeInSeconds, int iterations) {
		if (!getPIDController().isEnabled()) {
			enable();
		}
		for (int i = 0; i < (iterations * 2); i++) {	
			lfSpeed = -slidePower + forwardPower;
			rfSpeed = slidePower + forwardPower;
			lbSpeed = slidePower + forwardPower;
			rbSpeed = -slidePower + forwardPower;
			Timer.delay(amplitudeInSeconds);
			slidePower = -slidePower;
		}
		stop();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*-------------------------*/
	/* General Control Methods */
	/*-------------------------*/
	
	/**
	 * Stops the robot's movement forward, backward, and side to side.
	 * <p>This still leaves the heading control on.
	 */
	public void stop() {
		if (getPIDController().isEnabled()) {
			lfSpeed = 0;
			rfSpeed = 0;
			lbSpeed = 0;
			rbSpeed = 0;
		}
		else {
			coast();
		}
	}
	
	/**
	 * Disables the pid controller, so the robot actually coasts on all wheels.
	 */
	public void coast() {
		if (getPIDController().isEnabled()) {
			disable();
		}
		lfMotor.set(0);
		rfMotor.set(0);
		lbMotor.set(0);
		rbMotor.set(0);
	}
	
	private void updateMotors() {
		lfMotor.set(lfSpeed);
		rfMotor.set(rfSpeed);
		lbMotor.set(lbSpeed);
		rbMotor.set(rbSpeed);
		//System.out.println("Motor Speeds- lf: " + lfSpeed + " rf: " + rfSpeed + " lb: " + lbSpeed + " rb: " + rbSpeed);
	}
	
	private double findMax(double arg0, double arg1, double arg2, double arg3) {
		double semiMax0 = Math.max(arg0, arg1);
		double semiMax1 = Math.max(arg2, arg3);
		double max = Math.max(semiMax0, semiMax1);
		return max;
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
	
	
	
	
	
	
	
	
	
	
	
	
	/*----------------*/
	/* Teleop Methods */
	/*----------------*/
	
	/**
	 * Drives the robot with a joystick, without using the gyro.
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
		double forwardSpeed = addDeadZone(joy.getRawAxis(forwardAxis)) * power;
		
		lfSpeed = forwardSpeed;
		rfSpeed = forwardSpeed;
		lbSpeed = forwardSpeed;
		rbSpeed = forwardSpeed;
		
		//slide
		double slideSpeed = addDeadZone(joy.getRawAxis(slideAxis)) * power;
		lfSpeed -= slideSpeed;
		rfSpeed += slideSpeed;
		lbSpeed += slideSpeed;
		rbSpeed -= slideSpeed;
		
		//rotate
		double rotateSpeed = addDeadZone(joy.getRawAxis(rotateAxis)) / 2;
		lfSpeed -= rotateSpeed;
		rfSpeed += rotateSpeed;
		lbSpeed -= rotateSpeed;
		rbSpeed += rotateSpeed;
		
		if (lfSpeed > 1 || rfSpeed > 1 || lbSpeed > 1 || rbSpeed > 1) {
			double maxSpeed = findMax(lfSpeed, rfSpeed, lbSpeed, rbSpeed);
			lfSpeed /= maxSpeed;
			rfSpeed /= maxSpeed;
			lbSpeed /= maxSpeed;
			rbSpeed /= maxSpeed;
		}
		
		updateMotors();
	}
	
	/**
	 * Drives the robot with a joystick, and controls the heading with the gyro.
	 * @param joy The joystick to use
	 * @param forwardAxis The axis that, when positive, moves the robot forward
	 * @param slideAxis The axis that, when positive, slides the robot to its right
	 * @param rotationAxis The axis that, when positive, turns the robot clockwise
	 * @param throttleAxis The axis that, when positive, increases the power coefficient
	 * @param minPower The minimum throttle that the robot can go at
	 */
	public void telePIDDrive(Joystick joy, int forwardAxis, int slideAxis, int rotateAxis, int throttleAxis, double minPower) {
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
		double forwardSpeed = addDeadZone(joy.getRawAxis(forwardAxis)) * power;
		
		lfSpeed = forwardSpeed;
		rfSpeed = forwardSpeed;
		lbSpeed = forwardSpeed;
		rbSpeed = forwardSpeed;
		
		//slide
		double slideSpeed = addDeadZone(joy.getRawAxis(slideAxis)) * power;
		lfSpeed -= slideSpeed;
		rfSpeed += slideSpeed;
		lbSpeed += slideSpeed;
		rbSpeed -= slideSpeed;
		
		//rotate TODO test the code up to the end of this method.
		double rotateSpeed = addDeadZone(joy.getRawAxis(rotateAxis)) * 0.5;
		if (getPIDController().isEnabled() && rotateSpeed != 0) {
			disable();
		}
		else if (!getPIDController().isEnabled() && rotateSpeed == 0 && Math.abs(gyro.getRate()) <= 0.1) {
			enable();
		}
		lfSpeed -= rotateSpeed;
		rfSpeed += rotateSpeed;
		lbSpeed -= rotateSpeed;
		rbSpeed += rotateSpeed;
		
		if (!getPIDController().isEnabled()) {
			updateMotors();
		}
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
}
