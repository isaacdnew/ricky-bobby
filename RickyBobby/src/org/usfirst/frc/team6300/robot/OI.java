package org.usfirst.frc.team6300.robot;

import org.usfirst.frc.team6300.robot.commands.SwitchFront;
import org.usfirst.frc.team6300.robot.commands.TeleAgitator;
import org.usfirst.frc.team6300.robot.commands.TeleShooter;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//generic joystick mappings
	public static int leftXAxis = 0;
	public static int leftYAxis = 1;
	public static int leftTrigger = 2;
	public static int rightTrigger = 3;
	public static int rightXAxis = 4;
	public static int rightYAxis = 5;
	
	public static int a = 1;
	public static int b = 2;
	public static int x = 3;
	public static int y = 4;
	public static int lTrigButton = 5;
	public static int rTrigButton = 6;
	
	public static Joystick gamepadDr = new Joystick(0);
	public static Button drButtonA = new JoystickButton(gamepadDr, a);
	
	public static Joystick gamepadSh = new Joystick(1);
	public static Button shButtonA = new JoystickButton(gamepadSh, a);
	public static Button shButtonB = new JoystickButton(gamepadSh, b);
	public static Button shButtonX = new JoystickButton(gamepadSh, x);
	public static Button shButtonY = new JoystickButton(gamepadSh, y);
	
	public OI() {
		shButtonB.whenReleased(new TeleAgitator('b'));
		shButtonY.whenReleased(new TeleAgitator('y'));
		
		drButtonA.whenReleased(new SwitchFront());
		
		shButtonA.whenReleased(new TeleShooter('a'));
		shButtonX.whenReleased(new TeleShooter('x'));
	}
}
