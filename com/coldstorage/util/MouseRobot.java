package com.coldstorage.util;

import java.awt.Robot;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseRobot 
{

	public static MouseListener RobMouseListner() 
	{
		MouseListener ms = new MouseListener() 
		{
			public void mouseReleased(MouseEvent arg0) 
			{
				try 
				{
					Robot robot = new Robot();
					robot.mouseMove(0, 0);
				} 
				catch (Exception e)	{}
			}

			public void mousePressed(MouseEvent arg0) 
			{
				try 
				{
					Robot robot = new Robot();
					robot.mouseMove(0, 0);
				} 
				catch (Exception e) {}
			}

			public void mouseExited(MouseEvent arg0) 
			{
				try 
				{
					Robot robot = new Robot();
					robot.mouseMove(0, 0);
				} 
				catch (Exception e) {}
			}

			public void mouseEntered(MouseEvent arg0) 
			{
				try 
				{
					Robot robot = new Robot();
					robot.mouseMove(0, 0);
				} 
				catch (Exception e) {}
			}

			public void mouseClicked(MouseEvent arg0) 
			{
				try 
				{
					Robot robot = new Robot();
					robot.mouseMove(0, 0);
				} 
				catch (Exception e) {}
			}

		};

		return ms;
	}// ////// method end	
	
}
