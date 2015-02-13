//  ------------------------------------------------------------
//  $Revision:   1.4  $
//  $Date:   09 May 2007 15:48:50  $
//  $Workfile:   SystemStepHelper.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

/**
* This demo class illustrates how to make use of the VWSession,
* VWCompoundStepDefinition, VWStepDefinition, and VWValidationError objects.
*
*
* @since eProcess 4.1
* @see      Logger
*/

public class SystemStepHelper
{
    // decare variables
    private VWSession   m_vwSession = null;
    private Logger      m_logger = null;

    /**
     * Constructor - performs initialization.
     *
     * @param session a VWSession object.
     * @param logger the current Logger object.
     */
    public SystemStepHelper(VWSession session, Logger logger)
    {
        m_vwSession = session;
        m_logger = logger;
    }
    
    /**
    * Creates and initializes a workflow system step.
    *
    * @param mapDef the map that will contain the new step.
    * @param stepName the step name.
    * @return an initialized VWStepDefintion object.
    * @since eProcess 4.1
    */
    public VWCompoundStepDefinition addCompoundStep(VWMapDefinition mapDef, String stepName)
    {
      VWCompoundStepDefinition    newStepDef = null;
      int                 nStepId = -1;
      
      try
      {
        // create step as destination step for new route
        newStepDef = mapDef.createCompoundStep(stepName);
        nStepId = newStepDef.getStepId();
        
        // set the step description
        newStepDef.setDescription("This is the description for system step" + nStepId + ".");
        
        //Set map location (for display in Designer)
        newStepDef.setLocation(new java.awt.Point(nStepId * 100, 150));
        
        //Write out the successful step-creation status
        m_logger.log("Creation and initialization of system step: " + nStepId + " is complete.");
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
    * Creates and initializes a workflow step.
    *
    * @param mapDef the map that will contain the new step.
    * @param stepName the step name.
    * @param queueName the queue to assign the step to.
    * @param userName the name of the step participant.
    * @return an initialized VWStepDefintion object.
    * @since eProcess 4.1
    */
    public VWStepDefinition addStep(VWMapDefinition mapDef, String stepName, String queueName, String userName)
    {
      VWStepDefinition    newStepDef = null;
      int                 nStepId = -1;
      
      try
      {
        // create step as destination step for new route
        newStepDef = mapDef.createStep(stepName);
        nStepId = newStepDef.getStepId();
        
        // create parameters
        newStepDef.createParameter( "Field1_Integer", VWModeType.MODE_TYPE_IN, "99", VWFieldType.FIELD_TYPE_INT, false );
        newStepDef.createParameter( "Field2_String", VWModeType.MODE_TYPE_OUT, "Field2_String", VWFieldType.FIELD_TYPE_STRING, true );
        
        // assign the queue name
        newStepDef.setQueueName(queueName);
        
        if (userName != null && userName.length() > 0)
        {
            // assign a participant to the step
            VWParticipant[] participants = new VWParticipant[1];
            participants[0] = new VWParticipant();
            participants[0].setParticipantName("\"" + userName + "\"");
            newStepDef.setParticipants(participants);
        }
        
        // set the step description
        newStepDef.setDescription("This is the description for step" + nStepId + ".");
        
        //Set map location (for display in Designer)
        newStepDef.setLocation(new java.awt.Point(nStepId * 100, 150));
        
        //Write out the successful step-creation status
        m_logger.log("Creation and initialization of step: " + nStepId + " is complete.");
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
     * Validates the given workflow.
     *
     * @param workflowDef the workflow definition to validate.
     * @param vwSession a VWSession object.
     * @return true if the workflow definition is valid. false otherwise.
     * @since eProcess 4.1
     */
    public boolean validate( VWWorkflowDefinition workflowDef, VWSession vwSession)
    {
        VWValidationError[]     validationErrors = null;
        boolean bSuccess = false;
        
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