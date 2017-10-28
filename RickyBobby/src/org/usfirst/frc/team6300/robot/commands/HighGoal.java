package org.usfirst.frc.team6300.robot.commands;

import org.usfirst.frc.team6300.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class HighGoal extends CommandGroup {
	boolean isRed;
	
    public HighGoal(Robot robot) {
    	isRed = robot.colorChooser.getSelected();
        addSequential(new LowGoal(robot));
        if (isRed) {
        	addSequential(new DeliverGear(robot));
        }
        else {
        	addSequential(new DrivePastBaseline(robot.drivetrain));
        }
    }
}
