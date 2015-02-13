//  ------------------------------------------------------------
//  VCS INFO:
//  
//  $Revision:   1.1  $
//  $Date:   Oct 10 2001 16:49:24  $
//  $Workfile:   VWImageLoader.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-1999 FileNET Corp.
//  ------------------------------------------------------------

package samples.vwpanel.samplestep.images;

import javax.swing.*;

import java.awt.*;

import filenet.vw.toolkit.utils.images.VWBaseImageLoader;

/**
 * This class is used to load the images.
 * @version 1, 0
 * @since IWWS1.00
 */
public class VWImageLoader
{
	// declare constants

    
	/**
	 * Create an icon button object with the specified image.  Also set the size of the icon to 16 x 16.
	 * 
	 * @param imageFile The file that contains the image.
	 * @param toolTipText The tool tip for this button.
	 * @return The JButton that is created.
     * @since IWWS1.00
	 */
	public static JButton createIconButton(String imageFile, String toolTipText)
	{		  
		return VWBaseImageLoader.createIconButton(VWImageLoader.class, imageFile, toolTipText);
	}
	
	/**
	 * Create an image icon with the image from the specified file.
	 *
	 * @param imageFile The file that contains the image.
	 * @return The ImageIcon object that is created.
     * @since IWWS1.00
	 */
	public static ImageIcon createImageIcon(String imageFile) 
	{
       	return VWBaseImageLoader.createImageIcon(VWImageLoader.class, imageFile);
	}
}	
