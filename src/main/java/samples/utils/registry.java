/**********************************************************************
 *  IBM Confidential
 *  
 *  OCO Source Materials
 *  
 *  5724-R76, 5724-R87
 *  
 *  © Copyright IBM Corporation 1998, 2009
 *  The source code for this program is not published or otherwise 
 *  divested of its trade secrets, irrespective of what has been 
 *  deposited with the U.S. Copyright Office.
 **********************************************************************/

package samples.utils;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * 
 *
 */
public class registry
{
	public registry()
	{
	}


	static void usage()
	{
		System.out.println("View and/or remove entries from the RMI registry.");
		System.out.println("");
		System.out.println("Usage:  registry <options>");
		System.out.println("");
		System.out.println("     /v [remote system] - view contents of rmiregistry");
		System.out.println("     /r <object name>   - remove <object name> from rmiregistry");
		System.out.println("     /R                 - remove all objects from rmiregistry");

		System.exit(1);
	}


	public static void main(String args[])
	{
		if (args.length < 1 || (args.length > 0 && args[0].compareTo("?") == 0))
			usage();

		try
		{
			if (args[0].charAt(1) == 'v')
			{
				Registry reg = null;

				if (args.length > 1)
					reg = LocateRegistry.getRegistry(args[1]);
				else
					reg = LocateRegistry.getRegistry();

				String[] list = reg.list();

				for (int i=0; i<list.length; ++i)
					System.out.println(list[i]);
			}
			else if (args[0].charAt(1) == 'r')
			{
				Registry reg = LocateRegistry.getRegistry();

				System.out.println("Removing..." + args[1]);
				reg.unbind(args[1]);
			}
			else if (args[0].charAt(1) == 'R')
			{
				Registry reg = LocateRegistry.getRegistry();

				String[] list = reg.list();

				for (int i=0; i<list.length; ++i)
				{
					System.out.println("Removing..." + list[i]);
					reg.unbind(list[i]);
				}
			}
			else
			{
				usage();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		System.exit( 1 );
	}
}
