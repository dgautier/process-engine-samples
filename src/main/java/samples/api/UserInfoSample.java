//  ------------------------------------------------------------
//  $Revision:   1.4  $
//  $Date:   09 May 2007 15:48:50  $
//  $Workfile:   UserInfoSample.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

/**
* This demonstration sample class illustrates how to make use of the VWSession, VWQueue,
* VWQueueQuery, VWQueueElement, VWWorkObject, and VWInstructionInvocation
* objects.
*
* @since eProcess 4.1
*/
public class UserInfoSample
{
    // declare variables
    VWQueue queue = null;
    VWSession session = null;

    /**
     * Constructor
     * 
     * @param user
     * @param pw
     * @param router
     * @param email_suffix
     * @since eProcess 4.1
     */
    public UserInfoSample(String user, String pw, String router, String email_suffix)
    {
        try
        {
            System.out.println ("Starting...");

            session = new VWSession(user, pw, router);

            // Create a security list object from the session.  Each
            // fetch returns 1000 elements, and a "false" second argument
            // causes the list to consist of users, only, excluding groups.

            VWSecurityList sl = session.fetchUsers(1000, false);

            while (sl.hasNext())
            {
                // Create a user information object from fetchUserInfo and
                // a security list object.

                VWUserInfo ui = session.fetchUserInfo((String)sl.next());

                // Use the user information object to print the email address
                // you are about to set--then set it with the
                // VWUserInfo.setEMailAddress(String) method.

                System.out.println(ui.getName() + email_suffix);
                ui.setEMailAddress(ui.getName() + email_suffix);

                // Combine notification flag values by performing
                // a bitwise or on each value to be included. In production,
                // this should be done prior to the while loop, for efficiency.

                int nf = VWUserInfo.NOTIFICATION_STEP_EXPIRED_DEADLINE |
                            VWUserInfo.NOTIFICATION_STEP_NEW_ASSIGNMENT |
                            VWUserInfo.NOTIFICATION_STEP_REMINDERS |
                            VWUserInfo.NOTIFICATION_TRACKER_EXPIRED_DEADLINE |
                            VWUserInfo.NOTIFICATION_TRACKER_NEW_ASSIGNMENT |
                            VWUserInfo.NOTIFICATION_TRACKER_WORKFLOW_EXCEPTION;

                ui.setNotificationFlags(nf);
                ui.save();
            }

            System.out.println("done");

            session.logoff();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.exit( 1 );
        }
    }

    /**
     * Main method
     * 
     * @param args
     * @since eProcess 4.1
     */
    public static void main(String args[])
    {
        if (args.length < 4 || (args.length > 0 && args[0].compareTo("?") == 0))
        {
            System.out.println("Usage:  UserInfoSample username password router_URL email_suffix");
            System.exit(1);
        }

            new UserInfoSample(args[0],	// user name
                                args[1],	// password
                                args[2],	// router URL
                                args[3]);	// email_suffix
    }
    
}
