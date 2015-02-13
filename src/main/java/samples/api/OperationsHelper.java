//  ------------------------------------------------------------
//  $Revision:   1.5  $
//  $Date:   09 May 2007 15:48:48  $
//  $Workfile:   OperationsHelper.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

/**
* This demo class illustrates how to make use of the VWSession,
* VWQueueDefinition, VWOperationDefinition, VWQueue,
* and VWParameterDefinition objects.
*
* @since eProcess 4.1
* @see      Logger
*/
public class OperationsHelper
{
  // decare variables
  private VWSession   m_vwSession = null;
  private Logger      m_logger = null;

  
 /**
  * Constructor - performs initialization.
  *
  * @param vwSession a VWSession object.
  * @param logger the current Logger object.
  */
  public OperationsHelper(VWSession vwSession, Logger logger)
  {
    m_vwSession = vwSession;
    m_logger = logger;
  }

 /**
  * Retrieves the definition of an operation.
  *
  * @param operationName The name of the operation to retrieve.
  * @param theQueue The queue where the operation resides.
  * @return The VWOperationDefinition corresponding to the operation.
  */
  public VWOperationDefinition getOperationDefinition(String operationName, VWQueue theQueue)
  {
    VWQueueDefinition vwQueueDef = null;
    VWOperationDefinition vwOpDef = null;
    
    try
    {
    // we need to extricate the operation definition from the queue definition,
    // which can be accessed from the actual queue object
      vwQueueDef = theQueue.fetchQueueDefinition();
      vwOpDef = vwQueueDef.getOperation(operationName);
    }
    catch (Exception ex)
    {
      if (m_logger != null)
        m_logger.log(ex);
      else
        ex.printStackTrace();
    }
    return vwOpDef;
  }

 /**
  * Prints information about a VWOperationDefinition object.
  *
  * @param vwOpDef the VWOperationDefinition to print.
  */
  public void printOperationDetails(VWOperationDefinition vwOpDef)
  {
    try
    {
      m_logger.log("\tName: " + vwOpDef.getName());
      m_logger.log("\t\tDescription: " + vwOpDef.getDescription());
      printOperationParameters(vwOpDef);
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
  * Prints parameter information about a VWOperationDefinition object.
  *
  * @param vwOpDef the VWOperationDefinition to print.
  */
  public void printOperationParameters(VWOperationDefinition vwOpDef)
  {
    VWParameterDefinition[] vwPD = null;
    
    try
    {
      vwPD = vwOpDef.getParameterDefinitions();
      m_logger.log("\t\tName\t\tData Type\tValue");
      for (int i=0;i<vwPD.length;i++)
      {
              m_logger.log("\t\t" + vwPD[i].getName() + "\t\t" + VWFieldType.getLocalizedString(vwPD[i].getDataType()) + "\t" + vwPD[i].getValue());
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
}