//  ------------------------------------------------------------
//  $Revision:   1.6  $
//  $Date:   09 May 2007 15:48:48  $
//  $Workfile:   OperationsSample.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

/**
* This sample class demonstrates how to use API class VWSession and
* sample helper classes Logger, QueueHelper, and OperationsHelper to
* configure and report on queue operations.
*
*
* @since eProcess 4.1
* @see      Logger
* @see      QueueHelper
* @see      OperationsHelper
*
*/
public class OperationsSample
{

 /**
  * Constructor - performs initialization.
  *
  * @param vwSession a VWSession object.
  * @param logger the current Logger object.
  * @param queueName the name of the queue in which to create
  * and enumerate operations.
  */
  public OperationsSample(VWSession vwSession, Logger logger, String queueName) {

    QueueHelper 		queueHelper = null;
    OperationsHelper 		operationsHelper = null;
    VWQueue      		vwQueue = null;
    VWSystemConfiguration 	sysConfig;
    
    try
    {
      logger.logAndDisplay("\n~ Starting OperationsSample execution.");

      // create the operations helper object
      operationsHelper = new OperationsHelper(vwSession, logger);

      // create the queue helper object
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
          vwQueue = vwSession.getQueue(queueNames[1]);
      }
      // do we have a VWQueue object?
      if (vwQueue == null)
      {
        logger.log("Unable to retrieve a queue!");
        return;
      }
      else
      {
        logger.log("Phase 1 : Configuration\n");

        VWQueueDefinition vwQueueDef = null;
        VWOperationDefinition opDef = null;

        // get access to the queue definition
        vwQueueDef = vwQueue.fetchQueueDefinition();

        // it is important to modify the queue definition retrieved directly
        // from the queue (instead of creating or copying a definition)
        opDef = vwQueueDef.createOperation("SampleOperation");
        logger.log("Created new operation definition");

        // add some parameters to the operation
        // A boolean parameter sent by the operation which is not an array
        opDef.createParameter("BoolParameter", 2, 4, false);
        // An integer parameter returned to the operation which is not an array
        opDef.createParameter("IntParameter", 1, 1, false);
        opDef.setDescription("Created by OperationsSample example application");
        logger.log("Configured operation definition");

        sysConfig = vwSession.fetchSystemConfiguration();
        // the modified queue definition must be updated by the system
        sysConfig.updateQueueDefinition(vwQueueDef);

        // don't forget to commit changes!
        String[] errors = sysConfig.commit();
        if (errors != null)
          logger.log("Errors: ", errors);
        else
          logger.log("Committed configuration changes.");


        logger.log("\nPhase 2 : Reporting\n");

        // get a list of operations defined on this queue
        String[] operationNames = vwQueue.fetchOperationNames();
        VWOperationDefinition vwOpDef = null;
        for (int i=0;i<operationNames.length;i++)
        {
          // get configuration information about each defined operation
          vwOpDef = operationsHelper.getOperationDefinition(operationNames[i], vwQueue);
          operationsHelper.printOperationDetails(vwOpDef);
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
      logger.logAndDisplay("~ OperationsSample execution complete.\n");
    }
  }

 /**
  * Creates the Logger and OperationsHelper objects, then
  * instantiates the outer class
  *
  * @param args a String array containing command line arguments
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
        System.out.println("Usage:  OperationsSample username password router_URL [queue_name] [output_file]");
        System.exit(1);
      }

      // the queue name is optional
      if (args.length > 3)
        queueName = args[3];

      // the file name (for output) is optional
      if (args.length > 4)
        fileName = args[4];
      else
        fileName = new String("OperationsSample.out");

      // create and initialize the logger
      logger = new Logger(fileName);

      // create the session and log in
      sessionHelper = new SessionHelper(args[0], args[1], args[2], logger);
      vwSession = sessionHelper.logon();
      if (vwSession != null)
      {
        // create the sample class
        new OperationsSample(vwSession, logger, queueName);
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