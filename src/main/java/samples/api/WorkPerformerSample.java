//  ------------------------------------------------------------
//  $Revision:   1.2  $
//  $Date:   09 May 2007 15:48:50  $
//  $Workfile:   WorkPerformerSample.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

/**
* This sample class illustrates how to retrieve, modify, and complete a step
* using the VWWorkObject class.
*
* @since eProcess 4.1
* @see      SessionHelper
* @see      QueueHelper
* @see      Logger
*/
public class WorkPerformerSample extends Object implements Runnable
{
	// declare variables
	private Logger          m_logger = null;
	private VWQueue         m_vwQueue = null;
	private boolean         m_bDone = false;
    
    
    /**
     * Constructor - performs initialization
     *
     * @param vwSession a VWSession object.
     * @param logger a Logger object.
     * @param queueName the name of the queue to display.
     * @since eProcess 4.1
     */
	public WorkPerformerSample(VWSession vwSession, Logger logger, String queueName)
	{
	    int nCh = 0;
	    
		try
		{
		    m_logger = logger;
		    
		    if (m_logger != null)
    		    m_logger.logAndDisplay("\n~ Starting WorkPerformerSample execution.");

            // create the helper class
            QueueHelper queueHelper = new QueueHelper(vwSession, logger);
            
            // get the requested queue
            m_vwQueue = queueHelper.getQueue(queueName);
            if (m_vwQueue != null)
            {
                // start the process thread
                Thread thread = new Thread(this, "WorkPerformerSample");
                thread.start();
                
                // wait for key press
                System.out.print("Hit Enter key to exit:");
                while (!m_bDone)
                {
                    try
                    {
                        nCh = System.in.read();
                        if (nCh < 0 || (char)nCh == '\n')
                            m_bDone = true;
                    }
                    catch(java.io.IOException e)
                    {
                        m_bDone = true;
                    }
                }
                
                // wait for the thread to finish
                System.out.print("Finishing processing - please wait.");
                while (thread.isAlive());
            }
            else
            {
		        if (m_logger != null)
        		    m_logger.logAndDisplay("\n  Unable to retrieve queue: " + queueName);
            }
		}
		catch(Exception ex)
		{
	        if (m_logger != null)
	            m_logger.log(ex);
            else
			    ex.printStackTrace();
		}
		finally
		{
	        if (m_logger != null)
        		m_logger.logAndDisplay("~ WorkPerformerSample execution complete.\n");
		}
	}

    /**
     * Creates the Logger and SessionHelper objects, then
     * instantiates the outer class.
     *
     * @param args a String array contianing command line arguments
     * @since eProcess 4.1
     */
    public static void main(String args[])
	{
		String              outputFileName = null;
	    Logger              logger = null;
	    SessionHelper       sessionHelper = null;
	    VWSession           vwSession = null;
	    
	    try
	    {
	        // did the user supply enough arguments?
            if (args.length < 4 || (args.length > 0 && args[0].compareTo("?") == 0))
	        {
 			    System.out.println("Usage:  WorkPerformerSample username password router_URL queueName [output_filename]");
                System.exit(1);
   	        }
       	    
		    // the file name (for output) is optional
	        if (args.length > 4)
			    outputFileName = args[4];
		    else
			    outputFileName = new String("WorkPerformerSample.out");

	        // create and initialize the logger
	        logger = new Logger(outputFileName);
	        
	        // create the session and log in
	        sessionHelper = new SessionHelper(args[0], args[1], args[2], logger);
	        vwSession = sessionHelper.logon();
	        if (vwSession != null)
	        {
	            // create the sample class
		        new WorkPerformerSample(vwSession, logger, args[3]);
		    }
	    }
	    catch (Exception ex)
	    {
	        if (logger != null)
	            logger.log(ex);
            else
			    ex.printStackTrace();
			    
			System.exit(1);
        }
        finally
        {
	        // logoff
	        if (sessionHelper != null)
	            sessionHelper.logoff();
        }

    	System.exit(0);
 	}

    //--------------------------------------
    // Runnable methods
    //--------------------------------------
    
    /**
     * Starts the thread's execution
     * 
     * @since eProcess 4.1
     */
    public void run()
    {
        try
        {
            while (!m_bDone)
            {
                // search the queue
                processQueue(m_vwQueue);
                
                // let some time go by (30 seconds)
                if (!m_bDone)
                    Thread.sleep(30000);
            }
        }
        catch(InterruptedException ie)
        {
        }
        catch (Exception ex)
        {
			if (m_logger != null)
			    m_logger.log(ex);
            else
			    ex.printStackTrace();
        }
    } 

    //--------------------------------------
 	// private methods
    //--------------------------------------

    /**
     * Retrieves all work objects from the specified queue
     *
     * @param vwQueue a VWQueue object.
     * @since eProcess 4.1
     */
	private void processQueue(VWQueue vwQueue)
	{
	    VWQueueQuery    qQuery = null;
	    VWWorkObject    workObject = null;

		try
		{
			// Set the maximum number of items to be retrieved in each server fetch transaction.
			// In this case, setting the value to 25 will require less memory for each fetch 
			// than the default setting (50).
			vwQueue.setBufferSize(25);

			// construct a queue query object and query for all step elements.
			qQuery = vwQueue.createQuery(null, null, null, 0, null, null, VWFetchType.FETCH_TYPE_WORKOBJECT);
			if (qQuery != null)
			{
			    while (qQuery.hasNext())
			    {
			        // get the work object
			        workObject = (VWWorkObject)qQuery.next();
                    if (workObject != null)
                        processWork(workObject);
			    }
			}
	    }
		catch (Exception ex)
		{
			if (m_logger != null)
			    m_logger.log(ex);
            else
			    ex.printStackTrace();
		}
    }

    /**
     * Processes the work object
     *
     * @param workObject the work object to process.
     * @since eProcess 4.1
     */
	private void processWork(VWWorkObject workObject)
	{
		try
		{
            // lock the record
            workObject.doLock(true);
                    
            // extract the data field values
            if (workObject.hasFieldName("AppID"))
            {
                Integer appID = (Integer)workObject.getFieldValue("AppID");
            }
                
            if (workObject.hasFieldName("Title"))
            {
                String title = (String)workObject.getFieldValue("Title");
            }

            // set the comment
            if (workObject.hasFieldName("F_Comment"))
            {
                workObject.setFieldValue("F_Comment", "Processed by WorkPerformer", true);
            }

            // complete the step
            if (m_logger != null)
                m_logger.log("Completing step: " + workObject.getStepName());
                
            workObject.doDispatch();
		}
		catch(Exception ex)
		{
	        if (m_logger != null)
	            m_logger.log(ex);
            else
			    ex.printStackTrace();
		}
	}

}