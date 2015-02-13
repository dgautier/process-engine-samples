//  ------------------------------------------------------------
//  $Revision:   1.8  $
//  $Date:   09 May 2007 15:48:48  $
//  $Workfile:   QueueSample.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

/**
* This demonstration sample class illustrates how to use
* the helper objects to display the contents of a queue.
*
* @since eProcess 4.1
* @see      SessionHelper
* @see      Logger
* @see      QueueHelper
*/
public class QueueSample extends Object
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
    public QueueSample(VWSession vwSession, Logger logger, String queueName)
    {
        QueueHelper queueHelper = null;
        VWQueue     vwQueue = null;

        try
        {
            logger.logAndDisplay("\n~ Starting QueueSample execution.");

                // create the roster helper object
            queueHelper = new QueueHelper(vwSession, logger);

            // get the queue object for the queueName requested.
            // If no queueName requested, get first queue in the
            // list of queueNames that contains elements.
            if (queueName != null)
            {
                vwQueue = vwSession.getQueue(queueName);
            }
            else
            {
                String[] queueNames = queueHelper.getQueueNames(false);
                if (queueNames == null || queueNames.length == 0)
                    {
                    logger.log("No queues found.");
                    return;
                    }
                else
                {
                    // iteratively get queues until first one with available elements is found.
                    for (int i = 0; i < queueNames.length; i++)
                    {
                        vwQueue = vwSession.getQueue(queueNames[i]);
                        if (vwQueue != null)
                        {
                            if (vwQueue.fetchCount() > 0)
                            break;
                        }
                        // clear our reference
                        vwQueue = null;
                    }
                }
            }

            // do we have a VWQueue object?
            if (vwQueue == null)
            {
                logger.log("Unable to retrieve a queue!");
                return;
            }
            else
            {
                    // display the contents of the queue
                queueHelper.displayQueueContents(vwQueue);
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
                logger.logAndDisplay("~ QueueSample execution complete.\n");
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
        String          queueName = null;
        String          fileName = null;
        Logger          logger = null;
        SessionHelper   sessionHelper = null;
        VWSession       vwSession = null;

        try
        {
            // did the user supply enough arguments?
        if (args.length < 3 || (args.length > 0 && args[0].compareTo("?") == 0))
        {
            System.out.println("Usage:  QueueSample username password router_URL [queue_name] [output_file]");
            System.exit(1);
        }

                // the queue name is optional
            if (args.length > 3)
                queueName = args[3];

                // the file name (for output) is optional
            if (args.length > 4)
                fileName = args[4];
            else
                fileName = new String("QueueSample.out");

            // create and initialize the logger
            logger = new Logger(fileName);

            // create the session and log in
            sessionHelper = new SessionHelper(args[0], args[1], args[2], logger);
            vwSession = sessionHelper.logon();
            if (vwSession != null)
            {
                // create the sample class
                new QueueSample(vwSession, logger, queueName);
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
