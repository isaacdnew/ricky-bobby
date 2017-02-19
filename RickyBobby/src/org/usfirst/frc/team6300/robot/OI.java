package org.usfirst.frc.team6300.robot;

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
	
	public static int rightTrigger = 3;
	public static int leftTrigger = 2;
	
	public static Joystick gamepadDr = new Joystick(0);
	
	public static Joystick gamepadSh = new Joystick(1);
	public static Button buttonA = new JoystickButton(gamepadSh, 1);
	
	public OI() {
		buttonA.toggleWhenPressed(new TeleShooter());
	}
}
