//  ------------------------------------------------------------
//  $Revision:   1.10  $
//  $Date:   09 May 2007 15:48:48  $
//  $Workfile:   LogSample.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

/**
* This demo class illustrates how to make use of the VWSession, VWLog,
* VWLogQuery, and VWLogElement objects.
*
* @since eProcess 4.1
* @see      SessionHelper
* @see      Logger
*/
public class LogSample extends Object
{
    /**
     * Constructor - performs initialization
     *
     * @param vwSession a VWSession object.
     * @param logger a Logger object.
     * @since eProcess 4.1
     */
    public LogSample(VWSession vwSession, Logger logger)
    {
        VWLog           vwLog = null;  // Note the distinction between "log" method (below)
                                           // and a "vwLog" VWLog object
        VWLogQuery      logQuery = null;
        VWLogElement    logElement = null;
        String[]        fieldNames = null;
        Object          value = null;

        try
        {
            logger.logAndDisplay("\n~ Starting LogSample execution.");
    
            // get the log object for the DefaultEventLog.
            vwLog = vwSession.fetchEventLog("DefaultEventLog");
            logger.log("Log: " + vwLog.toString());
    
            // Set the maximum number of elements to be retrieved in each server fetch transaction.
            // In this case, setting the value to 25 will require less memory for each fetch
            // than the default setting (50).
            vwLog.setBufferSize(25);
    
            // construct a log query object and query for all elements.
            logQuery = vwLog.startQuery(null, null, null, 0, null, null);
    
            // fetch the first log element using the VWLogQuery object
            logElement = logQuery.next();
    
            // check to see if there are any log elements
            if (logElement == null)
            {
                logger.log("\t Log elements: none");
            }
            else
            {
                // iterate through the log elements
                do
                {
                    logger.log("\t Log element:");
    
                    // display the fields
                    fieldNames = logElement.getFieldNames();
                    if (fieldNames == null)
                    {
                        logger.log("\t\t No fields!");
                    }
                    else
                    {
                        logger.log("\t\t Fields:");
    
                        // display the field names and their values
                        for (int i = 0; i < fieldNames.length; i++)
                        {
                            if (fieldNames[i] != null)
                            {
                                // retrieve the field value
                                value = logElement.getFieldValue(fieldNames[i]);
    
                                // displayt he field name and value(s)
                                logger.log("\t\t\t" + fieldNames[i] + " = ", value);
                            }
                        }
                    }
                }
                while ((logElement = logQuery.next()) != null);
            }
        }
        catch(Exception ex)
        {
            if (logger != null)
                logger.log(ex);
            else
                ex.printStackTrace();
            }
        finally
        {
            if (logger != null)
            logger.logAndDisplay("~ LogSample execution complete.\n");
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
                System.out.println("Usage:  LogSample username password router_URL [output_filename]");
                System.exit(1);
            }

            // the file name (for output) is optional
            if(args.length > 3)
                fileName = args[3];
            else
                fileName = new String("LogSample.out");

            // create and initialize the logger
            logger = new Logger(fileName);

            // create the session and log in
            sessionHelper = new SessionHelper(args[0], args[1], args[2], logger);
            vwSession = sessionHelper.logon();
            if (vwSession != null)
            {
                // create the sample class
                new LogSample(vwSession, logger);
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
