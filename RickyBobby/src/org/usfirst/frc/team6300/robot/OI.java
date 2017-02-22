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
	public static int rightX = 4;
	
	public static int leftX = 0;
	public static int leftY = 1;
	
	public static int rightTrig = 3;
	public static int leftTrig = 2;
	
	public static Joystick gamepadDr = new Joystick(0);
	public static Button drButtonA = new JoystickButton(gamepadDr, 1);
	
	public static Joystick gamepadSh = new Joystick(1);
	public static Button shButtonA = new JoystickButton(gamepadSh, 1);
	public static Button shButtonB = new JoystickButton(gamepadSh, 2);
	
	public OI() {
		shButtonB.toggleWhenPressed(new TeleAgitator());
		drButtonA.whenReleased(new SwitchFront());
		shButtonA.toggleWhenPressed(new TeleShooter());
	}
}
