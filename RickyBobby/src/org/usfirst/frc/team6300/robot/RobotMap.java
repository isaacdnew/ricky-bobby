package org.usfirst.frc.team6300.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {	
	//drivetrain
	public static final int lfMotor = 3;
	public static final int rfMotor = 2;
	public static final int lbMotor = 0;
	public static final int rbMotor = 1;
	
	public static final boolean lfInverted = false;
	public static final boolean rfInverted = true;
	public static final boolean lbInverted = false;
	public static final boolean rbInverted = true;
	
	//cameras
	public static final int gearCamPort = 0;
	public static final int shooterCamPort = 1;
	
	//shooter
	public static final int flyMotor = 6;
	public static final boolean flyInverted = false;
	
	//intake
	public static final int intakeMotor = 4;
	public static final boolean intakeInverted = true;
	
	//climber
	public static final int climberMotor = 5;
	public static final boolean climberInverted = true;
	
	//agitator controller
	public static final int agitatorMotor = 7;
	public static final boolean agitatorInverted = false;
}
