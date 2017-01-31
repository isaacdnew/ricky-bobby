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
	
	//drivetrain
	public static int lfMotor = 3; //
	public static int rfMotor = 2; //
	public static int lbMotor = 1; //good!
	public static int rbMotor = 0; //good
	
	public static boolean lfInverted = false;
	public static boolean rfInverted = true;
	public static boolean lbInverted = false;
	public static boolean rbInverted = true;
}
