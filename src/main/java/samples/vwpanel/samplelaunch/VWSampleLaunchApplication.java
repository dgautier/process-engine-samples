//  ------------------------------------------------------------
//  VCS INFO:
//
//  $Revision:   1.7  $
//  $Date:   07 Nov 2003 20:04:00  $
//  $Workfile:   VWSampleLaunchApplication.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2003 FileNet Corp.
//  ------------------------------------------------------------

package samples.vwpanel.samplelaunch;

import javax.swing.*;

import java.awt.*;

import filenet.vw.base.*;
import filenet.vw.toolkit.utils.*;

import samples.vwpanel.samplelaunch.images.VWImageLoader;
import samples.vwpanel.samplelaunch.resources.VWResource;


/**
 * This is the sample Launch Step Processor application class.
 *
 * @version 1, 0
 * @since IWWS1.00
 */
public class VWSampleLaunchApplication extends VWBaseLaunchableApplication
{
    // declare constants
    private static final int            APP_WIDTH = 770;
    private static final int            APP_HEIGHT = 475;

    // declare variables
    private VWSampleLaunchPanel         m_sampleLaunchPanel = null;


    /**
     * The main method of the application.
     *
     * @param argv The command line argument to this application.
     * @since IWWS1.00
     */
    public static void main(String argv[])
    {
        try
        {
            // initialize our logger
            VWDebug.init(VWLogger.ERROR);

            // retrieve the command line parameters
            VWCommandLineArgs args = new VWCommandLineArgs(argv);

            // create the session information object
            VWSessionInfo sessionInfo = new VWSessionInfo(null, null, args);
            if (!sessionInfo.verifyLogon(null))
                System.exit(1);

            // create the application
            VWSampleLaunchApplication pApp = new VWSampleLaunchApplication();
            pApp.init(sessionInfo);

            // Init Online Help
            VWHelp.init(pApp);
        }
        catch (Exception ex)
        {
            VWDebug.logException(ex);
        }
    }

    //--------------------------------------
    // IVWLaunchableApp methods
    //--------------------------------------

    /**
     * Initialize the Workflow application.
     *
     * @param sessionInfo the session information
     * @since IWWS1.00
     */
    public void init(VWSessionInfo sessionInfo)
    {
        try
        {
            super.init(sessionInfo);

            // Set the title of the application.
            setTitle(VWResource.s_appTitle);

            // set the frame's icon
            ImageIcon icon = VWImageLoader.createImageIcon("launcher.gif");
            if (icon != null && icon.getImage() != null)
                setIconImage(icon.getImage());

            updatePosition(APP_WIDTH, APP_HEIGHT);

            // make sure we are visible
            show();

            // create the main panel
            m_sampleLaunchPanel = new VWSampleLaunchPanel();

            // add the controls
            getContentPane().setLayout(new BorderLayout(4,4));
            getContentPane().add(m_sampleLaunchPanel, BorderLayout.CENTER);

            // finish the initialization
            m_sampleLaunchPanel.init(m_sessionInfo);
        }
        catch (Exception ex)
        {
            VWDebug.logException(ex);
        }
    }

    //--------------------------------------
    // protected methods
    //--------------------------------------

    /**
     * Performs clean up.
     *
     * @return false if the application should not be destroyed
     * @since IWWS1.00
     */
    protected boolean destroy()
    {
        try
        {
            if (m_sampleLaunchPanel != null)
                m_sampleLaunchPanel.destroy();

            return super.destroy();
        }
        catch (Exception ex)
        {
            VWDebug.logException(ex);
        }

        return false;
    }

    //--------------------------------------
    // private methods
    //--------------------------------------

}
