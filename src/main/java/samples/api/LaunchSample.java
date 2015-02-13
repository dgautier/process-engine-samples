//  ------------------------------------------------------------
//  $Revision:   1.6  $
//  $Date:   09 May 2007 15:48:48  $
//  $Workfile:   LaunchSample.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

/**
* This demonstration sample class illustrates how to launch a
* workflow from a workflow definition.
*
* @since eProcess 4.1
* @see      SessionHelper
* @see      Logger
*/
public class LaunchSample extends Object
{
    // declare variables
    VWSession   m_vwSession = null;
    Logger      m_logger = null;


    /**
     * Constructor - performs initialization
     *
     * @param vwSession a VWSession object.
     * @param logger a Logger object.
     * @param wfDefFile a workflow definition filename.
     * @since eProcess 4.1
     */
    public LaunchSample(VWSession vwSession, Logger logger, String wfDefFile)
    {
        VWWorkflowDefinition    wflDef = null;
        String                  vwVersion = null;

        try
        {
            logger.logAndDisplay("\n~ Starting LaunchSample execution.");

            // initialize our global variables
            m_vwSession = vwSession;
            m_logger = logger;

            // create the workflow definition object
            wflDef = openDefinition(wfDefFile);
            if (wflDef == null)
                return;

            // transfer the workflow definition
            vwVersion = transferDefinition(wflDef);
            if (vwVersion == null)
                return;

            // launch the workflow
            launchDefinition(vwVersion);
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
                logger.logAndDisplay("~ LaunchSample execution complete.\n");
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
                System.out.println("Usage:  LaunchSample username password router_URL [wfDefinition_filename | wfDefinition_filename output_filename]");
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
                outputFileName = new String("LaunchSample.out");

            // create and initialize the logger
            logger = new Logger(outputFileName);

            // create the session and log in
            sessionHelper = new SessionHelper(args[0], args[1], args[2], logger);
            vwSession = sessionHelper.logon();
            if (vwSession != null)
            {
                // create the sample class
                new LaunchSample(vwSession, logger, wfDefFileName);
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

    /**
     * Creates a workflow definition object from a file
     *
     * @param defFile a string containing the workflow definition file path
     * @return a workflow definition object
     * @since eProcess 4.1
     */
    private VWWorkflowDefinition openDefinition(String wfDefFile)
    {
        VWWorkflowDefinition    wflDef = null;

        try
        {
            m_logger.log("Reading workflow file: " + wfDefFile);

            // read the workflow definition
           wflDef = VWWorkflowDefinition.readFromFile(wfDefFile);
	    }
	    catch (Exception ex)
	    {
	        if (m_logger != null)
                m_logger.log(ex);
            else
                ex.printStackTrace();
        }

        return wflDef;
   }

    /**
     * Transfers the workflow definition information
 	 *
 	 * @param wflDef a VWWorkflowDefinition object.
 	 * @return if successful, a string containing the vwversion. Null otherwise
 	 * @since eProcess 4.1
 	 */
    private String transferDefinition(VWWorkflowDefinition wflDef)
 	{
 	    String  vwVersion = null;

 	    try
 	    {
            m_logger.log("Begin transfer of the workflow.");

            // Transfer the workflow definition.  The second parameter is a unique id
            // which will be used to indentify this workflow.  We use the lib/docid/version
            // from content services for this value.
            VWTransferResult transferResult = m_vwSession.transfer(wflDef, "uniqueid", false, true);
            if (transferResult.success())
            {
                m_logger.log("The transfer was successful.");

                // retrieve the version information
                vwVersion = transferResult.getVersion();
            }
            else
            {
                // display the transfer errors
                String[] errorArray = transferResult.getErrors();
                if (errorArray != null)
                    m_logger.log("\tThe following transfer errors occured: ", errorArray);
                else
                    m_logger.log("\t\tError messages were not available.");
            }
        }
        catch (Exception ex)
        {
            if (m_logger != null)
                m_logger.log(ex);
            else
                ex.printStackTrace();
        }

        return vwVersion;
     }

     /**
     * Creates an instance of a transferred workflow, sets the comment, displays
 	 * the field information, then dispatches it.
 	 *
 	 * @param vwVersion a string containing the workflow version information
 	 * @since eProcess 4.1
 	 */
     private void launchDefinition(String vwVersion)
     {
        VWStepElement   launchStep = null;
        String[]        paramNames = null;
        Object          value = null;

         try
         {
            // launch the workflow based on the version string passed back from transfer.
           launchStep = m_vwSession.createWorkflow(vwVersion);
           launchStep.setComment("This is the sample workflow launch step comment");

             // display properties of the workflow / launch step (subject, workflow name,
             // comment, launch date, originator, step description, and parameter values.
            m_logger.log("\nLaunch Step information:\n");
            m_logger.log("\tWorkflow Name:  " + launchStep.getWorkflowName());
            m_logger.log("\tSubject:  " + launchStep.getSubject());
            m_logger.log("\tComment:  " + launchStep.getComment());
            m_logger.log("\tStep Description:  " + launchStep.getStepDescription());

            m_logger.log("\nParameters:\n");
            paramNames = launchStep.getParameterNames();
            if (paramNames == null)
            {
                m_logger.log("\t\t no parameters!");
            }
            else
            {
                // display the parameter names and their values
                for (int i = 0; i < paramNames.length; i++)
                {
                    if (paramNames[i] != null)
                    {
                        // retrieve the parameter value
                        value = launchStep.getParameterValue(paramNames[i]);

                        // write the information to the log
                        m_logger.log("\t" + paramNames[i] + " = ", value);
                    }
                }
            }

             // continue execution of this workflow.
            launchStep.doDispatch();

            m_logger.log("\nThe workflow has been launched successfully");
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
