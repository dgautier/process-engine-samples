//  ------------------------------------------------------------
//  $Revision:   1.4  $
//  $Date:   09 May 2007 15:48:48  $
//  $Workfile:   SessionHelper.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

/**
* This sample class illustrates how to make use of the VWSession object.
*
* @since eProcess 4.1
*/
public class SessionHelper extends Object
{
    // declare variables
    private String      m_userName = null;
    private String      m_password = null;
    private String      m_routerPath = null;
    private Logger      m_logger = null;
    private VWSession   m_vwSession = null;
    
    /**
     * Constructor - performs initialization
     *
     * @param user a string containing the user name
     * @param pw a string containing the password
     * @param router a string containing the router path
     * @param logger a Logger object.
     * @since eProcess 4.1
     */
	public SessionHelper(String user, String pw, String router, Logger logger)
	{
	    m_userName = user;
	    m_password = pw;
	    m_routerPath = router;
	    m_logger = logger;
	}

    /**
     * Creates a logon session
     *
     * @return a VWSession
     * @since IWWS.1.0
     */
    public VWSession logon()
    {
        try
        {
            // instead of using the user information that was passed in,
            // we could display a logon dialog and allow the user to enter
            // the necessary information.
            
            
			// Establish a logon session.
			
			m_vwSession = new VWSession(m_userName, m_password, m_routerPath);
		}
		catch (Exception ex)
		{
		    if (m_logger != null)
		        m_logger.log(ex);
            else
			    ex.printStackTrace();
		}        
		
        return m_vwSession;
    }
    
    /**
     * Logs off the session
     *
     * @since IWWS.1.0
     */
    public void logoff()
    {
        try
        {
			// logoff the session
			
			if (m_vwSession != null)
			    m_vwSession.logoff();
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
		    m_vwSession = null;
		}
    }
 	
 }
