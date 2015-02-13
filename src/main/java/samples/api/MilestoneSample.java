//  ------------------------------------------------------------
//  $Revision:   1.5  $
//  $Date:   09 May 2007 15:48:48  $
//  $Workfile:   MilestoneSample.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

/**
* This demo class illustrates how to use the helper objects to display the
* available milestones in various workflows of a queue.
*
* @since eProcess 4.1
* @see      Logger
* @see      QueueHelper
* @see      MilestoneHelper
*
*/
public class MilestoneSample extends Object
{
    
 /**
  * Constructor - performs initialization.
  *
  * @param vwSession a VWSession object.
  * @param logger the current Logger object.
  * @param queueName the name of the queue in which to search for milestones.
  */
  public MilestoneSample(VWSession vwSession, Logger logger, String queueName)
  {
    MilestoneHelper milestoneHelper = null;
    QueueHelper queueHelper = null;
    VWQueue     vwQueue = null;
    VWQueueQuery    qQuery = null;
    VWQueueElement  vwQueueElement = null;
    
    try
    {
      logger.logAndDisplay("\n~ Starting MilestoneSample execution.");

      // Create the milestone helper object.
      milestoneHelper = new MilestoneHelper(vwSession, logger);

      // Create the queue helper object.
      queueHelper = new QueueHelper(vwSession, logger);

      // Get the queue object for the queueName requested.
      // If no queueName was requested, get first queue 
      // in the list of queueNames that contains elements.
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

      // Do we have a VWQueue object?
      if (vwQueue == null)
      {
        logger.log("Unable to retrieve a queue!");
        return;
      }
      else
      {
        logger.log("Queue: " + vwQueue.toString());

        // Set the maximum number of items to be retrieved in each server fetch transaction.
        // In this case, setting the value to 25 will require less memory for each fetch
        // than the default setting (50).
        vwQueue.setBufferSize(25);

        // construct a queue query object and query for all elements.
        qQuery = vwQueue.createQuery(null, null, null, 0, null, null, VWFetchType.FETCH_TYPE_QUEUE_ELEMENT);

        // fetch the first queue element using the VWQueueQuery object
        vwQueueElement = (VWQueueElement)qQuery.next();
  	if (vwQueueElement == null)
	{
	  logger.log("\t Queue elements: none");
	}
        else
        {
          // iterate through the queue elements
          do
          {
            logger.log("\tWorkflow " + vwQueueElement.getWorkflowName());
            VWMilestoneDefinition[] msd = milestoneHelper.getMilestoneDefinitions(vwQueueElement);
            milestoneHelper.printMilestoneInfo(msd);
          }
          while ((vwQueueElement = (VWQueueElement)qQuery.next()) != null);
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
      logger.logAndDisplay("~ MilestoneSample execution complete.\n");
    }
  }
  
 /**
  * Creates the Logger and SessionHelper objects, then
  * instantiates the outer class.
  *
  * @param args a String array containing command line arguments.
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
        System.out.println("Usage:  MilestoneSample username password router_URL [queue_name] [output_file]");
        System.exit(1);
      }

      // the queue name is optional
      if (args.length > 3)
        queueName = args[3];

      // the file name (for output) is optional
      if (args.length > 4)
        fileName = args[4];
      else
        fileName = new String("MilestoneSample.out");

      // create and initialize the logger
      logger = new Logger(fileName);

      // create the session and log in
      sessionHelper = new SessionHelper(args[0], args[1], args[2], logger);
      vwSession = sessionHelper.logon();
      if (vwSession != null)
      {
        // create the sample class
        new MilestoneSample(vwSession, logger, queueName);
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
  
}