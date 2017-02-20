package org.usfirst.frc.team6300.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
	//oi
	public static int gamepadDr = 0;
	public static int forwardAxis = 1;
	public static int slideAxis = 0;
	public static int rotateAxis = 4;
	public static int throttleAxis = 3;
	
	public static int gamepadSh = 1;
	public static int shooterButton = 1;
	public static int agitatorButton = 2;
	public static int pullAxis = 3;
	public static int pushAxis = 2;
	
	//drivetrain
	public static int lfMotor = 3;
	public static int rfMotor = 2;
	public static int lbMotor = 0;
	public static int rbMotor = 1;
	
	public static boolean lfInverted = false;
	public static boolean rfInverted = true;
	public static boolean lbInverted = false;
	public static boolean rbInverted = true;
	
	//shooter
	public static int flyMotor = 4;
	public static boolean flyInverted = false;
	
	//intake
	public static int intakeMotor = 5;
	public static boolean intakeInverted = true;
	
	//agitator controller
	public static int agitatorController = 6;
	public static boolean agitatorInverted = false;
}
