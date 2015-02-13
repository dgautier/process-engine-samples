//  ------------------------------------------------------------
//  VCS INFO:
//
//  $Revision:   1.9  $
//  $Date:   04 Nov 2003 18:50:18  $
//  $Workfile:   VWSampleStepApplet.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2003 FileNet Corp.
//  ------------------------------------------------------------

package samples.vwpanel.samplestep;

import javax.swing.*;

import java.awt.BorderLayout;

import filenet.vw.base.VWDebug;
import filenet.vw.toolkit.runtime.step.IVWStepProcessor;
import filenet.vw.toolkit.utils.*;


/**
 * This is the sample Step Processor applet class.
 *
 * @version 1, 0
 * @since IWWS1.00
 */
public class VWSampleStepApplet extends VWBaseAppLauncherApplet implements IVWStepProcessor
{
    // declare variables
    private VWSampleStepPanel      m_sampleStepPanel = null;


    /**
     * Initializes the applet.
     * @since IWWS1.00
     */
    public void init()
    {
        try
        {
            super.init();

            // add additional initialization code here
        }
        catch (Exception ex)
        {
            VWDebug.logException(ex);
        }
    }

    /**
     * Called by the browser or applet viewer to inform this applet that it should start its execution.
     *
     * @since IWWS1.00
     */
    public void start()
    {
        try
        {
            // verify that the user is logged on
            if (!m_sessionInfo.verifyLogon(null))
                abort();

            // create main panel
            m_sampleStepPanel = new VWSampleStepPanel();

            // initialize the main container
            getContentPane().setLayout(new BorderLayout(4,4));
            getContentPane().add(m_sampleStepPanel, BorderLayout.CENTER);

            // finish the initalization
            m_sampleStepPanel.init(m_sessionInfo);
        }
        catch (Exception ex)
        {
            VWDebug.logException(ex);
        }
    }

    /**
     * Closes the applet window
     * @since IWWS1.00
     */
    public void destroy()
    {
        try
        {
            if (m_sampleStepPanel != null)
                m_sampleStepPanel.destroy();

            super.destroy();
        }
        catch (Exception ex)
        {
            VWDebug.logException(ex);
        }
    }

    //--------------------------------------
    // IVWStepProcessor methods
    //--------------------------------------

    /**
     * Sets the window title
     *
     * @param windowTitle the window title
     * @since IWWS1.00
     */
    public void setTitle(String windowTitle)
    {
        // add code here to set your browser title (if desired)
    }

    //--------------------------------------
    // private methods
    //--------------------------------------

}


