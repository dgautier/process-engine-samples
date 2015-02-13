//  ------------------------------------------------------------
//  $Revision:   1.6  $
//  $Date:   09 May 2007 15:48:48  $
//  $Workfile:   RosterSample.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

/**
* This demonstration sample class illustrates how to use
* the helper objects to display the contents of a roster.
*
* @since eProcess 4.1
* @see      SessionHelper
* @see      Logger
* @see      RosterHelper
*/
public class RosterSample extends Object
{
    // declare variables


    /**
     * Constructor - performs initialization
     *
     * @param vwSession a VWSession object.
     * @param logger a Logger object.
     * @since eProcess 4.1
     */
    public RosterSample(VWSession vwSession, Logger logger)
    {
        RosterHelper    rosterHelper = null;

        try
        {
            logger.logAndDisplay("\n~ Starting RosterSample execution.");

            // create the roster helper object
            rosterHelper = new RosterHelper(vwSession, logger);

            // display the contents of the roster
            rosterHelper.displayRosterContents();
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
            logger.logAndDisplay("~ RosterSample execution complete.\n");
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
            System.out.println("Usage:  RosterSample username password router_URL [output_filename]");
            System.exit(1);
        }
        
            // the file name (for output) is optional
            if (args.length > 3)
                fileName = args[3];
            else
                fileName = new String("RosterSample.out");
    
            // create and initialize the logger
            logger = new Logger(fileName);
    
            // create the session and log in
            sessionHelper = new SessionHelper(args[0], args[1], args[2], logger);
            vwSession = sessionHelper.logon();
            if (vwSession != null)
            {
                // create the sample class
                new RosterSample(vwSession, logger);
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
