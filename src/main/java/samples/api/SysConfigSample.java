//  ------------------------------------------------------------
//  $Revision:   1.9  $
//  $Date:   09 May 2007 15:48:50  $
//  $Workfile:   SysConfigSample.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

/**
* This demo class illustrates how to make use of the VWSession, VWSystemConfiguration,
* VWQueueDefinition, VWExposedFieldDefinition, and VWIndexDefinition objects.
*
* @since eProcess 4.1
* @see      Logger
* @see      SessionHelper
*/
public class SysConfigSample extends Object
{
    // declare constants
    public static final String     QUEUE_NAME = "API_Sample";

    // declare variables


    /**
    * Constructor - performs initialization
    *
    * @param vwSession a VWSession object.
    * @param logger a Logger object.
    * @since eProcess 4.1
    */
    public SysConfigSample(VWSession vwSession, Logger logger)
    {
        VWSystemConfiguration       sysConfig = null;
        VWQueueDefinition           queueDef = null;

        try
        {
            logger.logAndDisplay("\n~ Starting SysConfigSample execution.");

            // fetch the SysConfig object
            sysConfig = vwSession.fetchSystemConfiguration();

            // display various SysConfig parameters
            logger.log("Logging state = " + sysConfig.getLoggingState());
            logger.log("Max DB operations = " + sysConfig.getMaxDBOperations());
            logger.log("Max instructions = " + sysConfig.getMaxInstructions());

            // create a test queue, add one exposed field (integer) and one index
            queueDef = sysConfig.createQueueDefinition(QUEUE_NAME, VWQueue.QUEUE_TYPE_PROCESS);
            if (queueDef != null)
            {
                // add the exposed field
                queueDef.createFieldDefinition("field1", 1, 0);

                // create the index
                String[] fNames = {new String("field1")};
                queueDef.createIndexDefinition("index1", fNames);

                logger.log("The queue '" + QUEUE_NAME + "' was created with one exposed field ('field1') and one index ('index1').");

                // commit the SysConfig changes
                String[] errors = sysConfig.commit();
                if (errors != null)
                {
                    logger.log("Errors: ", errors);
                }
                else
                {
                    logger.log("All changes have been committed.");
                }
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
            if (logger != null)
                logger.logAndDisplay("~ SysConfigSample execution complete.\n");
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
        String             fileName = null;
        Logger             logger = null;
        SessionHelper      sessionHelper = null;
        VWSession          vwSession = null;

        try
        {
            // did the user supply enough arguments?
            if (args.length < 3 || (args.length > 0 && args[0].compareTo("?") == 0))
            {
                System.out.println("Usage:  SysConfigSample username password router_URL [output_filename]");
                System.exit(1);
            }

            // the file name (for output) is optional
            if(args.length > 3)
                fileName = args[3];
            else
                fileName = new String("SysConfigSample.out");

            // create and initialize the logger
            logger = new Logger(fileName);

            // create the session and log in
            sessionHelper = new SessionHelper(args[0], args[1], args[2], logger);
            vwSession = sessionHelper.logon();
            if (vwSession != null)
            {
                // create the sample class
                new SysConfigSample(vwSession, logger);
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
