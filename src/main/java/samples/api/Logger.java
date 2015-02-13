//  ------------------------------------------------------------
//  $Revision:   1.5  $
//  $Date:   09 May 2007 15:48:48  $
//  $Workfile:   Logger.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import java.io.*;

import filenet.vw.api.*;

/**
* This class implements a logger object.
*
* @since eProcess 4.1
* @see      PrintWriter
* @see      FileWriter
* @see      File
*/
public class Logger extends Object
{
    // declare variables
    private PrintWriter     m_fOut = null;

    /**
     * Constructor - performs initialization
     *
     * @param outputFile a string containing the output file name
     * @since eProcess 4.1
     */
    public Logger(String outputFile)
    {
            try
            {
                // make sure we have a file name
                if (outputFile == null)
                    outputFile = new String("Logger.out");

                // create the PrintWriter object
                m_fOut = new PrintWriter(new BufferedWriter(new FileWriter(new File(outputFile))), true);

                // let the user know which file we are using
                System.out.println("Writing messages to file: " + outputFile);
        }
        catch (Exception ex)
        {
            log(ex);
        }
    }

    /**
     * Displays the contents of the given step element.
     *
     * @param vwStepElement a VWStepElement object.
     * @since eProcess 4.1
     */
    public void displayStepElementInfo(VWStepElement vwStepElement)
    {
        String[]    parameterNames = null;
        Object      value = null;

        try
        {
            // do we have a step element?
            if (vwStepElement == null)
            {
                log("\t The step element is null!");
                return;
            }

                log("\t Step element:");

            // Display the parameters
            parameterNames = vwStepElement.getParameterNames();
            if (parameterNames == null)
            {
                log("\t\tNo Parameters!");
            }
            else
            {
                log("\t\tParameters:");

                // display the field names and their values
                for (int i = 0; i < parameterNames.length; i++)
                {
                    if (parameterNames[i] != null)
                    {
                        // retrieve the parameter value
                        value = vwStepElement.getParameterValue(parameterNames[i]);

                        // write the information to the log
                        log("\t\t\t" + parameterNames[i] + " = ", value);
                    }
                }
            }

            // Print Specialized Data
            log("");
            String bvalue = vwStepElement.getWorkObjectNumber();
            log("\t\t\t"+ "WorkObjectNumber" + "=" + bvalue);

            String svalue = vwStepElement.getWorkObjectName();
            log("\t\t\t"+ "WorkObjectName" + "=" + svalue);

            svalue = vwStepElement.getTag();
            log("\t\t\t"+ "Tag" + "=" + svalue);

            svalue = vwStepElement.getWorkClassName();
            log("\t\t\t"+ "WorkClassName" + "=" + svalue);

            svalue = vwStepElement.getOperationName();
            log("\t\t\t"+ "CurrentOperationName" + "=" + svalue);
        }
        catch (Exception ex)
        {
            log(ex);
        }
    }

    /**
     * Displays the contents of the given work object.
     *
     * @param vwWorkObject a VWWorkObject object.
     * @since eProcess 4.1
     */
    public void displayWorkObjectInfo(VWWorkObject vwWorkObject)
    {
        String[]    fieldNames = null;
        Object      value = null;

        try
        {
            // do we have a step element?
            if (vwWorkObject == null)
            {
                log("\t The work object is null!");
                return;
            }

            log("\tWork object: ");

            // Display the fields
            fieldNames = vwWorkObject.getFieldNames();
            if (fieldNames == null)
            {
                log("\t\tNo Fields!");
            }
            else
            {
                log("\t\tFields:");

                // display the field names and their values
                for (int i = 0; i < fieldNames.length; i++)
                 {
                    if (fieldNames[i] != null)
                     {
                        // retrieve the field value
                        value = vwWorkObject.getFieldValue(fieldNames[i]);

                        // write the information to the log
                        log("\t\t\t" + fieldNames[i] + " = ", value);
                     }
                 }
            }

            // print Specialized Data
            log("");
            String bvalue = vwWorkObject.getWorkObjectNumber();
            log("\t\t\t"+ "WorkObjectNumber" + "=" + bvalue);

            String svalue = vwWorkObject.getWorkObjectName();
            log("\t\t\t"+ "WorkObjectName" + "=" + svalue);

            svalue = vwWorkObject.getTag();
            log("\t\t\t"+ "Tag" + "=" + svalue);

            svalue = vwWorkObject.getWorkClassName();
            log("\t\t\t"+ "WorkClassName" + "=" + svalue);

            svalue = vwWorkObject.getWorkPerformerClassName();
            log("\t\t\t"+ "CurrentWorkPerformerClassName" + "=" + svalue);

            svalue = vwWorkObject.getOperationName();
            log("\t\t\t"+ "CurrentOperationName" + "=" + svalue);
        }
        catch (Exception ex)
        {
            log(ex);
        }
    }

    /**
     * Writes a stack trace to the log file (if available)
     *
     * @param ex the exception
     * @since eProcess 4.1
     */
    public void log(Exception ex)
    {
        if (m_fOut != null)
            ex.printStackTrace(m_fOut);

        ex.printStackTrace();
    }

    /**
     * Writes a line of text to the log file (if available)
     *
     * @param text a String containing the output text
     * @since eProcess 4.1
     */
    public void log(String text)
    {
        if (m_fOut != null)
            m_fOut.println(text);
        else
            System.out.println(text);
    }

    /**
     * Constructs output text and writes it to the log file (if available)
     *
     * @param text a String containing the output text
     * @param arg1 an object to output
     * @since eProcess 4.1
     */
    public void log(String text, Object arg1)
    {
        try
        {
            if (arg1 == null)
            {
                log(text + "<null>");
            }
            else if (arg1 instanceof Object[])
            {
                Object[]        args = (Object[])arg1;
                StringBuffer    buffer = new StringBuffer();

                buffer.append("{");

                // build a string using the arguments
                for (int i = 0; i < args.length; i++)
                {
                    if (i > 0)
                        buffer.append(",");

                    buffer.append(args[i]);
                }

                buffer.append("}");

                log(text, buffer.toString());
            }
            else
            {
                log(text + arg1);
            }
        }
        catch (Exception ex)
        {
            log(ex);
        }
    }

    /**
     * Writes a line of text to the log file (if available)
     * Also writes the text to stdout.
     *
     * @param text a String containing the output text
     * @since eProcess 4.1
     */
    public void logAndDisplay(String text)
    {
        if (m_fOut != null)
            m_fOut.println(text);

        System.out.println(text);
    }

    //--------------------------------------
    // private methods
    //--------------------------------------

}