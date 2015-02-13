//  ------------------------------------------------------------
//  $Revision:   1.9  $
//  $Date:   09 May 2007 15:48:50  $
//  $Workfile:   WFDefinitionSample.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

/**
* This demonstration sample class illustrates how to make use of
* the VWWorkflowDefinition, VWVWMapDefinition, VWStepDefinition,
* and VWValidationError[] objects.
*
* @since eProcess 4.1
*/
public class WFDefinitionSample extends Object
{
    // declare variables
    Logger      m_logger = null;


    /**
     * Constructor - performs initialization
     *
     * @param vwSession a VWSession object.
     * @param logger a Logger object.
     * @param userName
     * @param wfDefFile
     * @since eProcess 4.1
     */
    public WFDefinitionSample(VWSession vwSession, Logger logger, String userName, String wfDefFile)
    {
        VWWorkflowDefinition    workflowDef = null;
        VWMapDefinition         mapDef = null;
        VWMapNode               currentStepDef = null;
        VWMapNode               precedingStepDef = null;

        try
        {
            logger.logAndDisplay("\n~ Starting WFDefinitionSample execution.");

            m_logger = logger;

            // create the default workflow definition
            workflowDef = new VWWorkflowDefinition();

            // set some properties in the workflow definition.
            workflowDef.setSubject("\"This is the sample workflow definition subject\"");
            workflowDef.setDescription("This is the sample workflow definition description");
            workflowDef.setName("Sample Workflow");

            // create several fields
            workflowDef.createFieldUsingString("Field1_Integer", "99", VWFieldType.FIELD_TYPE_INT, false);
            workflowDef.createFieldUsingString("Field2_String", "{\"\"}", VWFieldType.FIELD_TYPE_STRING, true);

            // set some properties for the map
            mapDef = workflowDef.getMainMap();
            mapDef.setDescription("This is the sample workflow map");

            // get the launch step and set the description
            currentStepDef = mapDef.getStartStep();
            currentStepDef.setDescription("This is the description for the launch step.");

            precedingStepDef = currentStepDef;

            // create some steps in the "Workflow" (main) map.
            for (int i = 0; i < 3; i++)
            {
                currentStepDef = addStep(mapDef, "Step" + (i + 1), userName);
                if (currentStepDef != null)
                {
                    // create route
                    precedingStepDef.createRoute(currentStepDef.getStepId());

                    // reset the preceding step
                    precedingStepDef = currentStepDef;
                }
            }

            // validate the workflow definition
            if (validate(workflowDef, vwSession))
            {
                logger.log("Writing workflow definition to file: " + wfDefFile);
                workflowDef.writeToFile(wfDefFile);
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
            logger.logAndDisplay("~ WFDefinitionSample execution complete.\n");
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
        String              wfDefFileName = null;
        String              outputFileName = null;
        Logger              logger = null;
        SessionHelper       sessionHelper = null;
        VWSession           vwSession = null;

        try
        {
            // did the user supply enough arguments?
            if (args.length < 3 || (args.length > 0 && args[0].compareTo("?") == 0))
            {
                System.out.println("Usage:  WFDefinitionSample username password router_URL [wfDefinition_filename | wfDefinition_filename output_filename]");
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
                outputFileName = new String("WFDefinitionSample.out");

            // create and initialize the logger
            logger = new Logger(outputFileName);

            // create the session and log in
            sessionHelper = new SessionHelper(args[0], args[1], args[2], logger);
            vwSession = sessionHelper.logon();
            if (vwSession != null)
            {
                // create the sample class
                new WFDefinitionSample(vwSession, logger, args[0], wfDefFileName);
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

    // private methods

    /**
     * Creates and initializes a workflow step
     *
     * @param mapDef the map that will contain the new step
     * @param stepName the step name
     * @param userName the name of the step participant
     * @return an initialized VWStepDefintion object
     * @since eProcess 4.1
     */
    private VWStepDefinition addStep(VWMapDefinition mapDef, String stepName, String userName)
    {
        VWStepDefinition    newStepDef = null;
        int                 nStepId = -1;
        VWParticipant[]     participants = null;

        try
        {
            // create step as destination step for new route
            newStepDef = mapDef.createStep(stepName);
            nStepId = newStepDef.getStepId();

            // create parameters
            newStepDef.createParameter( "Field1_Integer", VWModeType.MODE_TYPE_IN, "99", VWFieldType.FIELD_TYPE_INT, false );
            newStepDef.createParameter( "Field2_String", VWModeType.MODE_TYPE_OUT, "Field2_String", VWFieldType.FIELD_TYPE_STRING, true );

            // assign the queue name
            newStepDef.setQueueName("Inbox");

            // assign a participant to the step
            participants = new VWParticipant[1];
            participants[0] = new VWParticipant();
            participants[0].setParticipantName("\"" + userName + "\"");
            newStepDef.setParticipants(participants);

            // set a step deadline
            newStepDef.setDeadline(1000);

            // set a step reminder
            newStepDef.setReminder(500);

            // set the step description
            newStepDef.setDescription("This is the description for " + stepName + ".");

            //Set map location (for display in Designer)
            newStepDef.setLocation(new java.awt.Point(nStepId * 100, 150));

            //Write out the successful step-creation status
            m_logger.log("Creation and initialization of: " + stepName + " is complete.");
        }
        catch (Exception ex)
        {
            if (m_logger != null)
                m_logger.log(ex);
            else
                ex.printStackTrace();
        }

        return newStepDef;
   }

    /**
     * Validates the given workflow
     *
     * @param workflowDef the workflow definition to validate
     * @param vwSession a VWSession object
     * @return true if the workflow definition is valid. false otherwise.
     * @since eProcess 4.1
     */
    private boolean validate(VWWorkflowDefinition workflowDef, VWSession vwSession)
    {
        VWValidationError[]     validationErrors = null;
        boolean                 bSuccess = false;

        try
        {
            // validate the workflow
            validationErrors = workflowDef.validate(vwSession, false);
            if (validationErrors != null)
            {
                m_logger.log("\nThe following validation errors occured: ", validationErrors);
            }
            else
            {
                m_logger.log("\nValidation was successful.\n");
                bSuccess = true;
            }
        }
        catch (Exception ex)
        {
            if (m_logger != null)
                m_logger.log(ex);
            else
                ex.printStackTrace();
        }

        return bSuccess;
    }
}
