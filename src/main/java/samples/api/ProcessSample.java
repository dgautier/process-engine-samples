//  ------------------------------------------------------------
//  $Revision:   1.6  $
//  $Date:   09 May 2007 15:48:48  $
//  $Workfile:   ProcessSample.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

/**
* This demo class illustrates how to make use of the RosterHelper,
* SessionHelper, and Logger objects.
*
* @since eProcess 4.1
* @see      RosterHelper
* @see      SessionHelper
* @see      Logger
*/
public class ProcessSample extends Object
{
    // declare variables


    /**
     * Constructor - performs initialization
     *
     * @param vwSession a VWSession object.
     * @param logger a Logger object.
     * @since eProcess 4.1
     */
    public ProcessSample(VWSession vwSession, Logger logger)
    {
        RosterHelper    rosterHelper = null;

        try
        {
           logger.logAndDisplay("\n~ Starting ProcessSample execution.");

            // create the roster helper object
            rosterHelper = new RosterHelper(vwSession, logger);

            // display the process information in the roster
            rosterHelper.displayProcessInformation();
        }
        catch (Exception ex)
        {
            if (logger != null)
                logger.log(ex);
            else
                ex.printStackTrace();
        }
        finally
        {
            if (logger != null)
                logger.logAndDisplay("~ ProcessSample execution complete.\n");
        }
    }

    /**
     * Creates the Logger and SessionHelper objects, then
     * instantiates the outer class.
     *
     * @param args a String array containing command line arguments
     * @since eProcess 4.1
     */
    public static void main(String args[])
    {
        String          fileName = null;
        Logger          logger = null;
        SessionHelper   sessionHelper = null;
        VWSession       vwSession = null;

        try
        {
            // did the user supply enough arguments?
            if (args.length < 3 || (args.length > 0 && args[0].compareTo("?") == 0))
            {
                System.out.println("Usage:  ProcessSample username password router_URL [output_filename]");
                System.exit(1);
            }

            // the file name (for output) is optional
            if(args.length > 3)
                fileName = args[3];
            else
                fileName = new String("ProcessSample.out");

            // create and initialize the logger
            logger = new Logger(fileName);

            // create the session and log in
            sessionHelper = new SessionHelper(args[0], args[1], args[2], logger);
            vwSession = sessionHelper.logon();
            if (vwSession != null)
            {
                // create the sample class
                new ProcessSample(vwSession, logger);
            }
        }
        catch (Exception ex)
        {
            if (logger != null)
                logger.log(ex);
            else
                ex.printStackTrace();
        }
        finally
        {
            // logoff
            if (sessionHelper != null)
                sessionHelper.logoff();
        }
    }

    //--------------------------------------
    // private methods
    //--------------------------------------
}
