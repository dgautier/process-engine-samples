//  ------------------------------------------------------------
//  $Revision:   1.10  $
//  $Date:   09 May 2007 15:48:48  $
//  $Workfile:   MainSample.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

/**
* This demo class creates, launches, completes, and displays the log information
* of a workflow.
*
* @since eProcess 4.1
*/
public class MainSample extends Object
{
    // declare constants
    private static final String     QUEUE_NAME = "Inbox";

    // declare variables
    private Logger                  m_logger = null;
    private VWSession               m_vwSession = null;


    /**
     * Constructor - performs initialization and performs the operations
     *
     * @param vwSession a VWSession object.
     * @param logger a Logger object.
     * @param userName the user's name.
     * @param wfDefFile the workflow definition file name.
     * @since eProcess 4.1
     */
    public MainSample(VWSession vwSession, Logger logger, String userName, String wfDefFile)
    {
        try
        {
            logger.logAndDisplay("\n~~ Starting the main sample.");

            // initialize our global variables
            m_vwSession = vwSession;
            m_logger = logger;

            // perform the configuration operations
            performConfigurationOperations();

            // perform the design-time operations
            if (performDesignTimeOperations(userName, wfDefFile))
            {
                // perform the runtime operations
                performRuntimeOperations();
            }
        }
        catch (Exception ex)
        {
            if (m_logger != null)
                m_logger.log(ex);
            else
                ex.printStackTrace();
        }
        finally
        {
            if (logger != null)
                logger.logAndDisplay("~~ MainSample execution complete.\n");
        }
    }

    /**
     * Creates the Logger and SessionHelper objects, then
     * instantiates the outer class.
     *
     * @param args a String array containing command line arguments.
     * @since eProcess 4.1
     */
    public static void main(String args[])
    {
        String          wfDefFileName = null;
        String          outputFileName = null;
        Logger          logger = null;
        SessionHelper   sessionHelper = null;
        VWSession       vwSession = null;

        try
        {
            // did the user supply enough arguments?
            if (args.length < 3 || (args.length > 0 && args[0].compareTo("?") == 0))
            {
                System.out.println("Usage:  MainSample username password router_URL [wfDefinition_filename | wfDefinition_filename output_filename]");
                System.exit(1);
            }

            // the file name for workflow definition to launch is optional
            if(args.length > 3)
                wfDefFileName = args[3];
            else
                wfDefFileName = new String("Sample.pep");

            // the file name for output is optional
            if(args.length > 4)
                outputFileName = args[4];
            else
                outputFileName = new String("MainSample.out");

            // create and initialize the logger
            logger = new Logger(outputFileName);

            // create the session and log in
            sessionHelper = new SessionHelper(args[0], args[1], args[2], logger);
            vwSession = sessionHelper.logon();
            if (vwSession != null)
            {
                // create the sample class
                new MainSample(vwSession, logger, args[0], wfDefFileName);
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

            System.exit(0);
        }
    }

     /**
     * Performs the configuration operations.
     *
     * @since eProcess 4.1
     */
    private void performConfigurationOperations()
    {
        try
        {
            // run the configuation sample
            new SysConfigSample(m_vwSession, m_logger);

            // display a list of queues
            QueueHelper queueHelper = new QueueHelper(m_vwSession, m_logger);
            queueHelper.displayQueueNames();
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
     * Performs the design-time operations.
     *
     * @param userName the user's name.
     * @param wfDefFile the workflow definition file name.
     * @return true if no error occurred, false otherwise
     * @since eProcess 4.1
     */
    private boolean performDesignTimeOperations(String userName, String wfDefFile)
    {
        try
        {
            // run the workflow definition sample
            new WFDefinitionSample(m_vwSession, m_logger, userName, wfDefFile);

            // run the launch sample
            new LaunchSample(m_vwSession, m_logger, wfDefFile);

            // success!
            return true;
        }
        catch (Exception ex)
        {
            if (m_logger != null)
                m_logger.log(ex);
            else
                ex.printStackTrace();
        }

        return false;
    }

    /**
     * Performs the runtime operations.
     *
     * @since eProcess 4.1
     */
    private void performRuntimeOperations()
    {
        try
        {
            // run the roster sample
            new RosterSample(m_vwSession, m_logger);

            // run the step processor sample
            new StepProcessorSample(m_vwSession, m_logger, QUEUE_NAME);

            // run the queue sample
            new QueueSample(m_vwSession, m_logger, QUEUE_NAME);

            // run the milestones sample
            new MilestoneSample(m_vwSession, m_logger, QUEUE_NAME);

            // run the operations sample
            new OperationsSample(m_vwSession, m_logger, QUEUE_NAME);

            // run the system step sample
            new SystemStepSample(m_vwSession, m_logger, QUEUE_NAME);

            // run the step processor sample
            new StepProcessorSample(m_vwSession, m_logger, QUEUE_NAME);

            // run the process sample
            new ProcessSample(m_vwSession, m_logger);

            // run the step processor sample
            new StepProcessorSample(m_vwSession, m_logger, QUEUE_NAME);

            // run the log sample
            new LogSample(m_vwSession, m_logger);
        }
        catch (Exception ex)
        {
            if (m_logger != null)
                m_logger.log(ex);
            else
                ex.printStackTrace();
        }
    }
}


