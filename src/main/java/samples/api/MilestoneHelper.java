//  ------------------------------------------------------------
//  $Revision:   1.5  $
//  $Date:   09 May 2007 15:48:48  $
//  $Workfile:   MilestoneHelper.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

/**
* This demo class illustrates how to make use of the VWSession, VWQueueElement,
* VWWorkObject, VWProcess, and VWMilestoneDefinition objects.
*
* @since eProcess 4.1
* @see      Logger
*/
public class MilestoneHelper
{
    // declare variables
    private VWSession   m_vwSession = null;
    private Logger      m_logger = null;
    
    
  /**
   * Constructor - performs initialization.
   *
   * @param vwSession a VWSession object.
   * @param logger the current Logger object.
   */
  public MilestoneHelper(VWSession vwSession, Logger logger)
  {
    m_vwSession = vwSession;
    m_logger = logger;
  }

  /**
   * Retrieves milestone definition information from a VWQueueElement.
   *
   * @param vwQueueElement the VWQueueElement to search.
   * @return An array of VWMilestoneDefinition containing the definitions of
   * all milestones in the queue.
   */
  public VWMilestoneDefinition[] getMilestoneDefinitions(VWQueueElement vwQueueElement)
  {
    VWMilestoneDefinition[] msd = null;
    try
    {
      // The work object contains the process information about the workflow.
      // We need to retrieve this to find the Milestone definitions for the
      // workflow.

        VWWorkObject workObj = vwQueueElement.fetchWorkObject(false, false);
        VWProcess proc = workObj.fetchProcess();
        msd = proc.getMilestoneDefinitions();
    }
    catch (Exception ex)
    {
      if (m_logger != null)
        m_logger.log(ex);
      else
        ex.printStackTrace();
    }
    return msd;
  }

 /**
  * Prints information about a set of milestone definitions.
  *
  * @param msd An array of VWMilestoneDefinition objects.
  */
  public void printMilestoneInfo(VWMilestoneDefinition[] msd)
  {
    try
    {
      m_logger.log("\t\tName\t\t\tMessage\tLevel");

      // Iterate through all the milestones defined for the workflow
      // and report each name, message, and level.
      for (int i=0;i<msd.length;i++)
      {
        m_logger.log("\t\t" + msd[i].getName() + "\t\t" + msd[i].getMessage() + "\t" + String.valueOf(msd[i].getLevel()));
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