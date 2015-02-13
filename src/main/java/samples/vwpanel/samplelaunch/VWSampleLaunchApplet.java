//  ------------------------------------------------------------
//  VCS INFO:
//
//  $Revision:   1.9  $
//  $Date:   04 Nov 2003 18:50:38  $
//  $Workfile:   VWSampleLaunchApplet.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2003 FileNet Corp.
//  ------------------------------------------------------------

package samples.vwpanel.samplelaunch;

import javax.swing.*;

import java.awt.BorderLayout;

import filenet.vw.base.VWDebug;
import filenet.vw.toolkit.utils.*;


/**
 * This is the sample step processor applet class.
 * @version 1, 0
 * @since IWWS1.00
 */
public class VWSampleLaunchApplet extends VWBaseAppLauncherApplet
{
    // declare variables
    private VWSampleLaunchPanel    m_sampleLaunchPanel = null;


    /**
     * Initializes the applet.
     *
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
            m_sampleLaunchPanel = new VWSampleLaunchPanel();

            // initialize the main container
            getContentPane().setLayout(new BorderLayout(4, 4));
            getContentPane().add(m_sampleLaunchPanel, BorderLayout.CENTER);

            // finish the initalization
            m_sampleLaunchPanel.init(m_sessionInfo);
        }
        catch (Exception ex)
        {
            VWDebug.logException(ex);
        }
    }

    /**
     * Closes the applet window
     *
     * @since IWWS1.00
     */
    public void destroy()
    {
        try
        {
            if (m_sampleLaunchPanel != null)
                m_sampleLaunchPanel.destroy();

            super.destroy();
        }
        catch (Exception ex)
        {
            VWDebug.logException(ex);
        }
    }

    //--------------------------------------
    // private methods
    //--------------------------------------

}


