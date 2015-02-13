//  ------------------------------------------------------------
//  $Revision:   1.6  $
//  $Date:   09 May 2007 15:48:48  $
//  $Workfile:   QueueHelper.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

/**
* This demo class illustrates how to make use of the VWSession, VWQueue,
* VWQueueQuery, VWQueueElement, and VWWorkObject objects.
*
* @since eProcess 4.1
* @see      SessionHelper
* @see      Logger
*/
public class QueueHelper extends Object
{
    // declare variables
    private VWSession   m_vwSession = null;
    private Logger      m_logger = null;

    
    /**
     * Constructor - performs initialization
     *
     * @param vwSession a VWSession object.
     * @param logger a Logger object.
     * @since eProcess 4.1
     */
        public QueueHelper(VWSession vwSession, Logger logger)
        {
            m_vwSession = vwSession;
            m_logger = logger;
        }

    /**
     * Outputs the contents of the requested queue
     *
     * @param vwQueue a VWQueue object.
     * @since eProcess 4.1
     */
        public void displayQueueContents(VWQueue vwQueue)
        {
        try
        {
            // do we have a valid VWQueue object?
            if (vwQueue == null)
            {
                m_logger.log("The queue object is null!");
                return;
            }

            // display the queue name
            m_logger.log("Queue:  " + vwQueue.toString());

            // display the queue depth
            m_logger.log("Depth:  " + vwQueue.fetchCount());

            // display the queue elements
            displayQueueElements(vwQueue);

            // display the step elements
            displayStepElements(vwQueue);

            // display the work objects
            displayWorkObjects(vwQueue);
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
     * Displays the contents of the given queue element.
     *
     * @param vwQueueElement a VWQueueElement object.
     * @since eProcess 4.1
     */
        public void displayQueueElementInfo(VWQueueElement vwQueueElement)
    {
    String[]    fieldNames = null;
    Object      value = null;

    try
    {
        // do we have a queue element?
        if (vwQueueElement == null)
        {
            m_logger.log("\t The queue element is null!");
            return;
        }

        m_logger.log("\t Queue element:");

        // display the System fields
        fieldNames = vwQueueElement.getSystemDefinedFieldNames();
        if (fieldNames == null)
        {
            m_logger.log("\t\t no System Defined Fields!");
        }
        else
        {
            m_logger.log("\t\tSystem Defined Fields:");

            // iterate through each field
            for (int i = 0; i < fieldNames.length; i++)
            {
                if (fieldNames[i] != null)
                {
                    value = vwQueueElement.getFieldValue(fieldNames[i]);

                    // Display the field name and its value
                    m_logger.log("\t\t\t" + fieldNames[i] + "=" + value);
                }
            }
        }

        m_logger.log("");

        // display the User Defined fields
        fieldNames = vwQueueElement.getUserDefinedFieldNames();
        if (fieldNames == null)
        {
            m_logger.log("\t\tNo User Defined Fields!\n");
        }
        else
        {
            m_logger.log("\t\tUser Defined Fields:\n");

            // display the field names and their values
            for (int i = 0; i < fieldNames.length; i++)
            {
                if (fieldNames[i] != null)
                                        {
                    // retrieve the parameter value
                    value = vwQueueElement.getFieldValue(fieldNames[i]);

                    // write the information to the log
                    m_logger.log("\t" + fieldNames[i] + " = ", value);
                }
            }
        }

        // print Specialized Data
        m_logger.log("\t\tOther Information:\n");

        String bvalue = vwQueueElement.getWorkObjectNumber();
        m_logger.log("\t\t\t"+ "WorkObjectNumber" + "=" + bvalue);

        String svalue = vwQueueElement.getWorkObjectName();
        m_logger.log("\t\t\t"+ "WorkObjectName" + "=" + svalue);

        svalue = vwQueueElement.getTag();
        m_logger.log("\t\t\t"+ "Tag" + "=" + svalue);

        svalue = vwQueueElement.getWorkClassName();
        m_logger.log("\t\t\t"+ "WorkClassName" + "=" + svalue);

        svalue = vwQueueElement.getQueueName();
        m_logger.log("\t\t\t"+ "CurrentQueueName" + "=" + svalue);

        svalue = vwQueueElement.getOperationName();
        m_logger.log("\t\t\t"+ "CurrentOperationName" + "=" + svalue);

        int ivalue = vwQueueElement.getLockedStatus();
        if (ivalue == VWQueueElement.LOCKED_BY_USER)
            svalue = new String("Locked by user");
        else if (ivalue == VWQueueElement.LOCKED_BY_SYSTEM)
            svalue = new String("Locked by System");
        else
            svalue = new String("Locked by No one");

        m_logger.log("\t\t\t"+ "CurrentLockStatus" + "=" + svalue);

        ivalue = vwQueueElement.getLockedMachine();
        m_logger.log("\t\t\t"+ "CurrentInstructionSheetName" + "=" + ivalue);
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
     * Outputs the queue elements in the requested queue
     *
     * @param vwQueue a VWQueue object.
     * @since eProcess 4.1
     */
        public void displayQueueElements(VWQueue vwQueue)
    {
    VWQueueQuery    qQuery = null;
    VWQueueElement  vwQueueElement = null;

    try
    {
        // do we have a valid VWQueue object?
        if (vwQueue == null)
        {
            m_logger.log("The queue object is null!");
            return;
        }

        // Set the maximum number of items to be retrieved in each server fetch transaction.
        // In this case, setting the value to 25 will require less memory for each fetch
        // than the default setting (50).
        vwQueue.setBufferSize(25);

        // construct a queue query object and query for all elements.
        qQuery = vwQueue.createQuery(null, null, null, 0, null, null, VWFetchType.FETCH_TYPE_QUEUE_ELEMENT);

        // fetch the first queue element using the VWQueueQuery object
        vwQueueElement = (VWQueueElement)qQuery.next();

        // check to see if there are any queue elements
        if (vwQueueElement == null)
        {
            m_logger.log("\t Queue elements: none");
        }
        else
        {
            // iterate through the queue elements
            do
            {
                // display the queue element information
                displayQueueElementInfo(vwQueueElement);
            }
            while ((vwQueueElement = (VWQueueElement)qQuery.next()) != null);
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
     * Displays the queue names
     *
     * @since eProcess 4.1
     */
    public void displayQueueLockStatus()
    {
    String[]        queueNames = null;
    VWQueue         vwQueue = null;
    VWQueueQuery    qQuery = null;
    VWQueueElement  queueElement = null;

    try
    {
        // get the queue names
        queueNames = getQueueNames(false);
        if (queueNames != null)
        {
            m_logger.log("Found " + queueNames.length + " queues (NOT including system queues):");

            // display the queue names
            for (int i = 0; i < queueNames.length; i++)
            {
                //Get the queue object
                vwQueue = getQueue(queueNames[i]);

                // Display queue name
                m_logger.log("\nQueue:  " + vwQueue.toString());

                // Set the maximum number of items to be retrieved in each server fetch transaction.
                // In this case, setting the value to 25 will require less memory for each fetch
                // than the default setting (50).
                vwQueue.setBufferSize(25);

                // construct a queue query object and query for all locked elements.
                qQuery = vwQueue.createQuery(null, null, null, 1, "F_Locked = 1", null, 3);
                if (qQuery.hasNext())
                {
                    // display column headers for list of locked workobjects
                    m_logger.log("\t WorkObject Number\t\t\tLocked User");

                    do
                    {
                        // fetch the next queue element using the VWQueueQuery object
                        queueElement = (VWQueueElement)qQuery.next();

                        // display workobject number of each locked workobject.
                        m_logger.log("\t " + queueElement.getWorkObjectNumber() + "\t" + queueElement.getLockedUser());
                    }
                    while (qQuery.hasNext());
                }
                else
                {
                    m_logger.log("\t No locked WorkObject items in this queue.");
                }
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
     * Displays the queue names
     *
     * @since eProcess 4.1
     */
    public void displayQueueNames()
    {
    String[]    queueNames = null;

    try
    {
        // get the queue names
        queueNames = getQueueNames(true);
        if (queueNames != null)
        {
            m_logger.log("Found " + queueNames.length + " queues (including system queues):");

            // display the queue names
            for (int i = 0; i < queueNames.length; i++)
            {
                m_logger.log("\t\t" + queueNames[i]);
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
     * Outputs the step elements in the requested queue
     *
     * @param vwQueue a VWQueue object.
     * @since eProcess 4.1
     */
        public void displayStepElements(VWQueue vwQueue)
    {
    VWQueueQuery    qQuery = null;
    VWStepElement   vwStepElement = null;

    try
    {
        // do we have a valid VWQueue object?
        if (vwQueue == null)
        {
            m_logger.log("The queue object is null!");
            return;
        }

        // Set the maximum number of items to be retrieved in each server fetch transaction.
        // In this case, setting the value to 25 will require less memory for each fetch
        // than the default setting (50).
        vwQueue.setBufferSize(25);

        // construct a queue query object and query for all step elements.
        qQuery = vwQueue.createQuery(null, null, null, 0, null, null, VWFetchType.FETCH_TYPE_STEP_ELEMENT);

        // fetch the first step element using the VWQueueQuery object
        vwStepElement = (VWStepElement)qQuery.next();

        // check to see if there are any queue elements
        if (vwStepElement == null)
        {
            m_logger.log("\t Step elements: none");
        }
        else
        {
            // iterate through the step elements
            do
            {
                // display the step element information
                m_logger.displayStepElementInfo(vwStepElement);
            }
            while ((vwStepElement = (VWStepElement)qQuery.next()) != null);
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
     * Outputs the work objects in the requested queue
     *
     * @param vwQueue a VWQueue object.
     * @since eProcess 4.1
     */
        public void displayWorkObjects(VWQueue vwQueue)
    {
    VWQueueQuery    qQuery = null;
    VWWorkObject    vwWorkObject = null;

    try
    {
        // do we have a valid VWQueue object?
        if (vwQueue == null)
        {
            m_logger.log("The queue object is null!");
            return;
        }

        // Set the maximum number of items to be retrieved in each server fetch transaction.
        // In this case, setting the value to 25 will require less memory for each fetch
        // than the default setting (50).
        vwQueue.setBufferSize(25);

        // construct a queue query object and query for all objects.
        qQuery = vwQueue.createQuery(null, null, null, 0, null, null, VWFetchType.FETCH_TYPE_WORKOBJECT);

        // fetch the first work object using the VWQueueQuery object
        vwWorkObject = (VWWorkObject)qQuery.next();

        // check to see if there are any objects
        if (vwWorkObject == null)
        {
            m_logger.log("\t Work objects: none");
        }
        else
        {
            // iterate through the work objects
            do
            {
                // display the work object information
                m_logger.displayWorkObjectInfo(vwWorkObject);
            }
            while ((vwWorkObject = (VWWorkObject)qQuery.next()) != null);
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
     * Returns a VWQueue object.
     *
     * @param queueName the name of the queue to retrieve
     * @return a VWQueue object
     * @since eProcess 4.1
     */
    public VWQueue getQueue(String queueName)
    {
    VWQueue    vwQueue = null;

        try
        {
            if (m_vwSession != null)
            {
                if (queueName == null)
                {
                    m_logger.log("Invalid queue name: <null>");
                }
                else
                {
                    // Get the queue object for the queueName requested.
                    vwQueue = m_vwSession.getQueue(queueName);
                    if (vwQueue == null)
                        m_logger.log("Error retrieving queue: " + queueName + "");
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

        return vwQueue;
    }

    /**
     * Returns a String array containing the queue names.
     *
     * @param bIncludeSystem if true, system queue names are also included
     * @return a String array containing the queue names
     * @since eProcess 4.1
     */
    public String[] getQueueNames(boolean bIncludeSystem)
    {
    int         nFlags = VWSession.QUEUE_PROCESS | VWSession.QUEUE_USER_CENTRIC | VWSession.QUEUE_IGNORE_SECURITY;
    String[]    queueNames = null;

    try
    {
        if (m_vwSession != null)
            {
                if (bIncludeSystem)
                    nFlags |= VWSession.QUEUE_SYSTEM;

                // retrieve the list of available queues.
                queueNames = m_vwSession.fetchQueueNames(nFlags);

                // did we find any?
                if (queueNames == null)
                    m_logger.log("Error retrieving queue names.");
            }
        }
        catch (Exception ex)
        {
            if (m_logger != null)
                m_logger.log(ex);
            else
                ex.printStackTrace();
        }
        return queueNames;
    }

    /**
     * Returns the first step element retrieved from the requested queue
     *
     * @param vwQueue a VWQueue object.
     * @return a step element, or null if none are found.
     * @since eProcess 4.1
     */
    public VWStepElement getStepElement(VWQueue vwQueue)
    {
        VWQueueQuery    qQuery = null;
        VWStepElement   stepElement = null;
    
        try
        {
            // do we have a valid VWQueue object?
            if (vwQueue == null)
            {
                m_logger.log("The queue object is null!");
                return null;
            }
            // Set the maximum number of items to be retrieved in each server fetch transaction.
            // In this case, setting the value to 25 will require less memory for each fetch
            // than the default setting (50).
            vwQueue.setBufferSize(25);
    
            // construct a queue query object and query for all step elements.
            qQuery = vwQueue.createQuery(null, null, null, 0, null, null, VWFetchType.FETCH_TYPE_STEP_ELEMENT);
    
            // fetch the first step element using the VWQueueQuery object
            stepElement = (VWStepElement)qQuery.next();
        }
        catch (Exception ex)
        {
            if (m_logger != null)
                m_logger.log(ex);
            else
                ex.printStackTrace();
            }
        return stepElement;
    }

    //--------------------------------------
    // private methods
    //--------------------------------------

}
