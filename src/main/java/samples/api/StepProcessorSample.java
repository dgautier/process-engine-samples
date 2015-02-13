//  ------------------------------------------------------------
//  $Revision:   1.5  $
//  $Date:   09 May 2007 15:48:48  $
//  $Workfile:   StepProcessorSample.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

/**
* This demonstration sample class illustrates how to retrieve, modify,
* and complete a step using the VWStepElement class.
*
* @since eProcess 4.1
* @see      Logger
* @see      QueueHelper
* @see      SessionHelper
* @see	    VWSession
* @see	    VWStepElement
*/
public class StepProcessorSample extends Object
{
    // declare variables

    /**
     * Constructor - performs initialization
     *
     * @param vwSession a VWSession object.
     * @param logger a Logger object.
     * @param queueName the name of the queue to display.
     * @since eProcess 4.1
     */
    public StepProcessorSample(VWSession vwSession, Logger logger, String queueName)
    {
        QueueHelper     queueHelper = null;
        VWQueue         vwQueue = null;
        VWStepElement   vwStepElement = null;

        try
        {
            logger.logAndDisplay("\n~ Starting StepProcessorSample execution.");

            // create the helper class
            queueHelper = new QueueHelper(vwSession, logger);

            // get the requested queue
            vwQueue = queueHelper.getQueue(queueName);
            if (vwQueue != null)
            {
                // get a step element
                vwStepElement = queueHelper.getStepElement(vwQueue);
                if (vwStepElement != null)
                {
                    // lock the record
                    vwStepElement.doLock(true);
    
                    // set the comments
                    vwStepElement.setComment("This is the user's comment.");
    
                    // display the step prossor information
                    logger.displayStepElementInfo(vwStepElement);
    
                    // complete the step
                    logger.log("Completing step: " + vwStepElement.getOperationName());
                    vwStepElement.doDispatch();
                }
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
                logger.logAndDisplay("~ StepProcessorSample execution complete.\n");
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
        String                  outputFileName = null;
        Logger                  logger = null;
        SessionHelper           sessionHelper = null;
        VWSession               vwSession = null;

        try
        {
            // did the user supply enough arguments?
            if (args.length < 4 || (args.length > 0 && args[0].compareTo("?") == 0))
            {
                System.out.println("Usage:  StepProcessorSample username password router_URL queueName [output_filename]");
                System.exit(1);
            }

                // the file name (for output) is optional
            if (args.length > 4)
                outputFileName = args[4];
           else
                outputFileName = new String("StepProcessorSample.out");

            // create and initialize the logger
            logger = new Logger(outputFileName);

            // create the session and log in
            sessionHelper = new SessionHelper(args[0], args[1], args[2], logger);
            vwSession = sessionHelper.logon();
            if (vwSession != null)
            {
                // create the sample class
                new StepProcessorSample(vwSession, logger, args[3]);
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