//  ------------------------------------------------------------
//  $Revision:   1.4  $
//  $Date:   09 May 2007 15:48:48  $
//  $Workfile:   LogViewer.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-2007 FileNet Corp.
//  ------------------------------------------------------------

package samples.api;

import filenet.vw.api.*;

import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

/**
* This is a demonstration sample class that shows how to use the FileNET API
* and Java Swing components to build a log viewing tool.
*
* @since eProcess 4.1
*/
class LogViewer extends JFrame implements ActionListener, MenuListener
{
    // declare variables
    private JDesktopPane dpane;
    private boolean isLoggedIn;
    private boolean isLogSelected;
    private String routerName = "hq_eprocess/vwrouter";
    private String logName;
    private JMenuItem selRouterItem;
    private JMenuItem logonItem;
    private JMenuItem logoffItem;
    private JMenuItem selLogItem;
    private JMenuItem createIdxItem;
    private JMenuItem queryLogItem;
    private JMenuItem listExposedFieldsItem;
    private VWSession vwSession;
    private VWLog vw_Log;

   /**
    * Constructor - creates DesktopPane and Menubar.
    */
    public LogViewer()
    {
        isLoggedIn = false;
        isLogSelected = false;

        /////////////////
        addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e) {System.exit(0);}
            });

        setTitle("Panagon Log Viewer");
        setSize(800,600);
        /////////////////


        dpane = new JDesktopPane();
        setContentPane(dpane);

        JMenuBar mbar = new JMenuBar();
        this.setJMenuBar(mbar);

        // The System Menu
        JMenu sysMenu = new JMenu("System");
        sysMenu.addMenuListener(this);

        selRouterItem = new JMenuItem("Select system/router...");
        selRouterItem.setActionCommand("SelectRouter");
        selRouterItem.addActionListener(this);
        sysMenu.add(selRouterItem);

        logonItem = new JMenuItem("Logon...");
        logonItem.setActionCommand("Logon");
        logonItem.addActionListener(this);
        sysMenu.add(logonItem);

        logoffItem = new JMenuItem("Logoff");
        logoffItem.setActionCommand("Logoff");
        logoffItem.addActionListener(this);
        sysMenu.add(logoffItem);
        logonItem.setEnabled(true);

        mbar.add(sysMenu);

        // The Log Menu
        JMenu logMenu = new JMenu("Log");
        logMenu.addMenuListener(this);

        selLogItem = new JMenuItem("Select Log...");
        selLogItem.setActionCommand("SelectLog");
        selLogItem.addActionListener(this);
        logMenu.add(selLogItem);

        logMenu.addSeparator();

        createIdxItem = new JMenuItem("Create new log index...");
        createIdxItem.setActionCommand("CreateIdxLog");
        createIdxItem.addActionListener(this);
        logMenu.add(createIdxItem);
        createIdxItem.setEnabled(true);

        queryLogItem = new JMenuItem("Query Log...");
        queryLogItem.setActionCommand("QueryLog");
        queryLogItem.addActionListener(this);
        logMenu.add(queryLogItem);
        queryLogItem.setEnabled(true);


        listExposedFieldsItem = new JMenuItem("List Exposed Log Fields...");
        listExposedFieldsItem.setActionCommand("ListExposedFields");
        listExposedFieldsItem.addActionListener(this);
        logMenu.add(listExposedFieldsItem);
        listExposedFieldsItem.setEnabled(true);

        mbar.add(logMenu);

    }
    
    /**
     * 
     * @param args
     */
    public static void main(String[] args)
    {
        LogViewer bframe = new LogViewer();
        bframe.show();
    }

    /**
     * Destructor - cleans up class upon deallocation
     * ensuring sessions are logged off from server.
     */
    public void destroy()
    {
        // make sure we log off of our session before exiting
        if (isLoggedIn){
            logoff();
        }
    }

    /**
     * Logs the session off.
     */
    public void logoff()
    {
        try
        {
            vwSession.logoff();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    //--------------------------------------
    // ActionListener methods
    //--------------------------------------

    /**
     * Control interface implementation method.
     *
     * @param event the event which occured
     */
    public void actionPerformed(final java.awt.event.ActionEvent event)
    {
        String cmd = event.getActionCommand();
        // The select router menu command allows the user to choose the system and router to which to connect.
        // Note that input is in the form of:
        // <server name>:<port number>/<router instance name>systemname/routername,
        // which is the input format that VWSession() uses.

        if (cmd.equals("SelectRouter"))
        {
            String v = (String)JOptionPane.showInternalInputDialog(dpane, "Enter the router name you wish to use", "Router Selection", JOptionPane.QUESTION_MESSAGE, null, null, routerName);

            if (v != null)
            {
                routerName = v;
            }
        }

        // To create a new session with an eProcess server, it is necessary to know the target system
        // name and router name, a logon name and a logon password
        if (cmd.equals("Logon"))
        {
            isLoggedIn = false;
            isLogSelected = false;
            Object[] obj = new Object[4];
            obj[0] = new JLabel("Username");
            obj[1] = new JTextField();
            obj[2] = new JLabel("Password");
            obj[3] = new JPasswordField();

            // collect logon information
            int rtn = JOptionPane.showInternalOptionDialog(dpane, obj, "Login", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Login", "Cancel"}, "Login");
            if (rtn == 0)
            {
                String uname = ((JTextField)obj[1]).getText();
                char[] p = ((JPasswordField)obj[3]).getPassword();
                String pword = new String(p);
                // Here we attempt to logon to the eProcess server
                try
                {
                    vwSession = new VWSession(uname, pword, routerName);
                    isLoggedIn = true;
                    JOptionPane.showInternalMessageDialog(dpane, "You are logged into " + routerName);
                }
                catch (VWException vwe)
                {
                    vwe.printStackTrace();
                    // A common cause of logon failure is an incorrect username/password combination
                    // vwe.getLocalizedMessage can provide FileNET controlled, localized, useful
                    // error text for a variety of exceptions, including a bad password or username
                    JOptionPane.showInternalMessageDialog(dpane, vwe.getLocalizedMessage());
                }
            }

        }

        // It is important to log off of a VWSession in order to free resources on the eProcess server
        if (cmd.equals("Logoff"))
        {
            logoff();
            JOptionPane.showInternalMessageDialog(dpane, "You have been successfully logged off");
            isLoggedIn = false;
            isLogSelected = false;
        }

        // An eProcess server or server group may have several logs associated with it.
        // By default, the system has at least one event log.

        if (cmd.equals("SelectLog"))
        {
            isLogSelected = false;
            try
            {
                String[] elogNames = vwSession.fetchEventLogNames();
                logName = (String)JOptionPane.showInternalInputDialog(dpane, "Please select a log from the following list", "Log Selection", JOptionPane.PLAIN_MESSAGE, null, elogNames, elogNames[0]);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            if (logName != null)
            {
                try
                {
                    vw_Log = vwSession.fetchEventLog(logName);
                    isLogSelected = true;
                    JOptionPane.showInternalMessageDialog(dpane, "Opened " + logName);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    JOptionPane.showInternalMessageDialog(dpane, "Unable to open " + logName);
                    isLogSelected = false;
                }
            }
        }

        if (cmd.equals("QueryLog"))
        {
            try
            {
                // Attempt to get the log definition, which contains useful information about the log,
                // including the indexes for the log.
                VWLogDefinition vw_LogDef = vw_Log.fetchLogDefinition();

                // Retrieve the list of defined indexes for the log
                VWIndexDefinition[] vw_Indexes = vw_LogDef.getIndexes();

                // Create a dialog of indexes in the log for the user to choose from
                Object[] componentList = new Object[2];

                JLabel ListBoxLabel = new JLabel("Select the query index to use");
                componentList[0] = ListBoxLabel;
                Object listComponent;
                if (vw_Indexes.length > 20)
                {
                    listComponent = new JList(vw_Indexes);
                    JScrollPane sPane = new JScrollPane((JList)listComponent);
                    componentList[1] = sPane;
                }
                else
                {
                    listComponent = new JComboBox(vw_Indexes);
                    ((JComboBox)listComponent).setEditable(false);
                    componentList[1] = listComponent;
                }
                JOptionPane.showInternalOptionDialog(dpane, componentList, "Index Selection", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, null, null);
                int xix = 0;
                if (vw_Indexes.length > 20)
                  xix = ((JList)listComponent).getSelectedIndex();
                else
                  xix = ((JComboBox)listComponent).getSelectedIndex();

                VWIndexDefinition vw_Idx = vw_Indexes[xix];

                VWLogQuery vwq = null;
                int rtn = JOptionPane.showInternalOptionDialog(dpane, "Choose a query method", "Query method", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, new Object[] {"Basic Query", "Index-Based Query", "Cancel"}, "Cancel");
                if (rtn == 2) return; // cancellation

                if (rtn == 1)
                {
                    // Search the list of fields in the log for those which are used in the selected index, and derive their VWExposedFieldDefinitions
                    String[] idxFieldNames = vw_Idx.getFieldNames();
                    Arrays.sort(idxFieldNames);

                    VWExposedFieldDefinition[] vw_EFDef = vw_LogDef.getFields();
                    VWExposedFieldDefinition[] fieldDefs = new VWExposedFieldDefinition[idxFieldNames.length];

                    for (int i=0;i<vw_EFDef.length;i++)
                    {
                        int x = Arrays.binarySearch(idxFieldNames, vw_EFDef[i].getName());
                        if (x >= 0){
                            fieldDefs[x] = vw_EFDef[i];
                        }
                    }

                    JPanel panel = new JPanel();
                    panel.setBorder(new EtchedBorder());
                    Box box = Box.createVerticalBox();
                    panel.add(box);
                    IndexFieldPanel[] iPanel = new IndexFieldPanel[fieldDefs.length];

                    // Use the information stored in the VWExposedFieldPanelDefinitions to determine the data type of each field, for display to the user
                    for (int i=0;i<fieldDefs.length;i++)
                    {
                        iPanel[i] = new IndexFieldPanel(fieldDefs[i].getName(), VWFieldType.getLocalizedString(fieldDefs[i].getFieldType()), fieldDefs[i].getFieldType());
                        box.add(iPanel[i]);
                    }

                    Box footer = Box.createHorizontalBox();
                    footer.add(new JLabel("Please note: All fields must be filled for this type of query"));
                    box.add(footer);
                    if (JOptionPane.showInternalOptionDialog(dpane, panel, "Query", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Query", "Cancel"}, "Query") != 0) return;

                    vw_Log.setBufferSize(20);
                    Object[] min = new Object[iPanel.length];
                    Object[] max = new Object[iPanel.length];
                    for (int i=0;i<iPanel.length;i++)
                    {
                        min[i] = iPanel[i].getMinVal();
                        max[i] = iPanel[i].getMaxVal();
                    }

                    vwq = vw_Log.startQuery(vw_Idx.getName(), min, max, 0, null, null);
                }


                if (rtn == 0)
                {
                    vwq = vw_Log.startQuery(vw_Idx.getName(), null, null, 0, null, null);
                }
                ////////////////////////////////////////////////////////////////////////
                ////////////////////////////////////////////////////////////////////////
                VWLogElement le = null;
                ArrayList eventTypes = new ArrayList();
                ArrayList timeStamps = new ArrayList();
                ArrayList seqNumber = new ArrayList();

                while (vwq.hasNext())
                {
                    le = vwq.next();
                    eventTypes.add(VWLoggingOptionType.getLocalizedString(le.getEventType()));
                    timeStamps.add(le.getTimeStamp());
                    seqNumber.add(String.valueOf(le.getSequenceNumber()));
                }
                if (le != null)
                {
                    Object[] e1 = eventTypes.toArray();
                    Object[] e2 = timeStamps.toArray();
                    Object[] e3 = seqNumber.toArray();

                    Object[][] data = new Object[e1.length][3];
                    for (int i=0;i<e1.length;i++)
                    {
                        data[i][0] = e1[i];
                        data[i][1] = e3[i];
                        data[i][2] = e2[i];
                    }

                    JPanel jp = new JPanel();
                    jp.setBorder(new EtchedBorder());
                    JTable jt = new JTable(data, new String[] {"Event Type", "Sequence Number","Time"});
                    JScrollPane jsp = new JScrollPane(jt);
                    jp.add(jsp);
                    JOptionPane.showInternalOptionDialog(dpane, jsp, "Results", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Okay"}, "Okay");
                }
                else
                    JOptionPane.showInternalMessageDialog(dpane, "No matching elements in log " + vw_Log.getName());
            }
            catch (VWException vwe)
            {
                vwe.printStackTrace();
                JOptionPane.showInternalMessageDialog(dpane, vwe.getLocalizedMessage());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        // The contents of an event log can be searched like a 
        // database using predefined or user-created
        // indexes. Indexes are made up of log field names, which can be
        // loaded with minimum and maximum values when queried. An index
        // can be further refined with a filter and/or a SQL search string
        // when used as part of a query.

        if (cmd.equals("CreateIdxLog"))
        {
            try
            {
               // Instantiate VWSystemConfiguration and VWLogDefinition classes.  These
               // objects will handle index information:

                VWSystemConfiguration sysConfig = vwSession.fetchSystemConfiguration();
                VWLogDefinition vw_LogDef = vw_Log.fetchLogDefinition();

               // get the exposed fields available in this log.

                VWExposedFieldDefinition[] vw_EFDef = vw_LogDef.getFields();

                String[] EFDefNames = new String[vw_EFDef.length];

                for (int i=0;i<vw_EFDef.length;i++)
                {
                    EFDefNames[i] = vw_EFDef[i].getName();
                }
                // Get fields and an index name

                NewIdxPanel nip = new NewIdxPanel(EFDefNames);
            if (JOptionPane.showInternalOptionDialog(dpane, nip, "Create new Log Index Definition", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Create", "Cancel"}, "Cancel") == 0)
                {
                    int[] idx = nip.getSelectedIndexes();

                // Load a fields array with the selected indices

                    String[] fields = new String[idx.length];
                    for (int i=0;i<idx.length;i++)
                    {
                        fields[i] = EFDefNames[idx[i]];
                    }

                    String name = nip.getIdxName();
                    System.out.println("Index named " + name); // echo the index name

                    // use the name and fields arrays to create the index in the log definition

                    vw_LogDef.createIndexDefinition(name, fields);
                    sysConfig.updateLogDefinition(vw_LogDef);  // Be sure to add the changes into the log
                                                               // configuration.
                    String[] err = sysConfig.commit();         // Changes must be committed.
                    if (err != null)
                    {
                        for (int i=0;i<fields.length;i++)
                        {
                            System.out.println("Err " + String.valueOf(i) + " " + err[i]);
                        }
                    }
                    else
                    {
                        JOptionPane.showInternalMessageDialog(dpane, "Sucessfully created index " + name + " in log " + vw_LogDef.getName());
                    }
                }
            }
            catch (VWException vwe)
            {
                vwe.printStackTrace();
                JOptionPane.showInternalMessageDialog(dpane, vwe.getLocalizedMessage());
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        if (cmd.equals("ListExposedFields"))
        {
            try
            {
                // get a list of all exposed fields in the currently selected log

                VWLogDefinition vw_LogDef = vw_Log.fetchLogDefinition();
                VWExposedFieldDefinition[] vw_EFDef = vw_LogDef.getFields();
                String[] EFDefNames = new String[vw_EFDef.length];
                for (int i=0;i<vw_EFDef.length;i++)
                {
                    EFDefNames[i] = vw_EFDef[i].getName();
                }
                JOptionPane.showInternalInputDialog(dpane, "Exposed Fields in Log "+logName, "Exposed Field List", JOptionPane.PLAIN_MESSAGE, null, EFDefNames, EFDefNames[0]);
            }
            catch (VWException vwe)
            {
                vwe.printStackTrace();
                JOptionPane.showInternalMessageDialog(dpane, vwe.getLocalizedMessage());
            }
            catch (Exception e)
            {
                e.printStackTrace();
                //JOptionPane.showInternalMessageDialog(dpane, "Unable to retrieve indices");
            }
        }
    }
    
    //--------------------------------------
    // MenuListener methods
    //--------------------------------------
    
    /**
     * 
     * @param p1
     */
    public void menuCanceled(final javax.swing.event.MenuEvent p1)
    {
    }

    /**
     * 
     * @param p1
     */
    public void menuSelected(final javax.swing.event.MenuEvent p1)
    {
        // make sure that the appropriate menu controls are activated or deactivated
        // right before the menu is drawn
        this.selRouterItem.setEnabled(!isLoggedIn);
        this.logonItem.setEnabled(!isLoggedIn);
        this.logoffItem.setEnabled(isLoggedIn);
        this.selLogItem.setEnabled(isLoggedIn);
        this.createIdxItem.setEnabled(isLoggedIn && isLogSelected);
        this.queryLogItem.setEnabled(isLoggedIn && isLogSelected);
        this.listExposedFieldsItem.setEnabled(isLoggedIn && isLogSelected);
    }

    /**
     * 
     * @param p1
     */
    public void menuDeselected(final javax.swing.event.MenuEvent p1)
    {
    }
}

////////////////////////////////////////////////////
// class IndexFieldPanel extends JPanel
//
// used to generate query field parameter lines in
// the log query dialog
////////////////////////////////////////////////////
class IndexFieldPanel extends JPanel
{
    private JLabel minLabel;
    private JLabel maxLabel;
    private JTextField[] minValField = new JTextField[6];
    private JTextField[] maxValField = new JTextField[6];
    private int fieldType;
    
    /**
     * Constructor
     * 
     * @param fname
     * @param ftype
     * @param type
     */
    public IndexFieldPanel(String fname, String ftype, int type)
    {
        fieldType = type;
        Box b = Box.createVerticalBox();
        this.add(b);
        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();

        b.add(p1);
        b.add(p2);

        p1.add(new FixedLabel(fname, new Dimension(100, 14)));
        p2.add(new FixedLabel(ftype, new Dimension(100, 14)));

        minLabel = new FixedLabel("minimum", new Dimension(60, 14));
        p1.add(minLabel);
        maxLabel = new FixedLabel("maximum", new Dimension(60, 14));
        p2.add(maxLabel);

        switch (fieldType)
        {
            case VWFieldType.FIELD_TYPE_TIME:
            minValField[0] = new JTextField(2);
            p1.add(minValField[0]);
            maxValField[0] = new JTextField(2);
            p2.add(maxValField[0]);
            p1.add(new FixedLabel("/", new Dimension(5, 14)));
            p2.add(new FixedLabel("/", new Dimension(5, 14)));

            minValField[1] = new JTextField(2);
            p1.add(minValField[1]);
            maxValField[1] = new JTextField(2);
            p2.add(maxValField[1]);
            p1.add(new FixedLabel("/", new Dimension(5, 14)));
            p2.add(new FixedLabel("/", new Dimension(5, 14)));

            minValField[2] = new JTextField(2);
            p1.add(minValField[2]);
            maxValField[2] = new JTextField(2);
            p2.add(maxValField[2]);
            p1.add(new FixedLabel("  ", new Dimension(6, 14)));
            p2.add(new FixedLabel("  ", new Dimension(6, 14)));

            minValField[3] = new JTextField(2);
            p1.add(minValField[3]);
            maxValField[3] = new JTextField(2);
            p2.add(maxValField[3]);
            p1.add(new FixedLabel(":", new Dimension(3, 14)));
            p2.add(new FixedLabel(":", new Dimension(3, 14)));

            minValField[4] = new JTextField(2);
            p1.add(minValField[4]);
            maxValField[4] = new JTextField(2);
            p2.add(maxValField[4]);
            minValField[5] = new JTextField(2);
            p1.add(minValField[5]);
            maxValField[5] = new JTextField(2);
            p2.add(maxValField[5]);
            p1.add(new FixedLabel("AM/PM", new Dimension(40, 14)));
            p2.add(new FixedLabel("AM/PM", new Dimension(40, 14)));

            break;

            default:
            minValField[0] = new JTextField(22);
            p1.add(minValField[0]);
            maxValField[0] = new JTextField(22);
            p2.add(maxValField[0]);
            break;
        }
    }
    
    /**
     * 
     * @return the minimum value
     */
    public Object getMinVal()
    {
        switch (fieldType)
        {
            case VWFieldType.FIELD_TYPE_BOOLEAN:
            return Boolean.valueOf(minValField[0].getText());
            case VWFieldType.FIELD_TYPE_FLOAT:
            return Double.valueOf(minValField[0].getText());
            case VWFieldType.FIELD_TYPE_INT:
            return Integer.valueOf(minValField[0].getText());
            case VWFieldType.FIELD_TYPE_STRING:
            return minValField[0].getText();
            case VWFieldType.FIELD_TYPE_TIME:
            DateFormat fmt = DateFormat.getDateInstance(DateFormat.SHORT);
            try
            {
            Date dt = fmt.parse(
            Integer.parseInt(minValField[1].getText())+"/"+ // mm
            Integer.parseInt(minValField[2].getText())+"/"+ // dd
            Integer.parseInt(minValField[0].getText())+" "+ // yy
            Integer.parseInt(minValField[3].getText())+":"+
            Integer.parseInt(minValField[4].getText())+":"+
            minValField[5].getText());
            return dt;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 
     * @return the maximum value
     */
    public Object getMaxVal()
    {
        switch (fieldType)
        {
            case VWFieldType.FIELD_TYPE_BOOLEAN:
            return Boolean.valueOf(maxValField[0].getText());
            case VWFieldType.FIELD_TYPE_FLOAT:
            return Double.valueOf(maxValField[0].getText());
            case VWFieldType.FIELD_TYPE_INT:
            return Integer.valueOf(maxValField[0].getText());
            case VWFieldType.FIELD_TYPE_STRING:
            return maxValField[0].getText();
            case VWFieldType.FIELD_TYPE_TIME:
            DateFormat fmt = DateFormat.getDateInstance(DateFormat.SHORT);
            
            try
            {
                Date dt = fmt.parse(
                Integer.parseInt(maxValField[1].getText())+"/"+ // mm
                Integer.parseInt(maxValField[2].getText())+"/"+ // dd
                Integer.parseInt(maxValField[0].getText())+" "+ // yy
                Integer.parseInt(maxValField[3].getText())+":"+
                Integer.parseInt(maxValField[4].getText())+":"+
                maxValField[5].getText());
                return dt;
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        
        return null;
    }

}

////////////////////////////////////////////////////
// class FixedLabel extends JLabel
//
// implements fixed length text labels
////////////////////////////////////////////////////
class FixedLabel extends JLabel
{
    /**
     * 
     * @param name
     * @param d
     */
    public FixedLabel(String name, Dimension d)
    {
        super(name);
        setPreferredSize(d);
    }
    
    /**
     * 
     * @return the preferred size
     */
    public Dimension getMinimumSize()
    {
        return getPreferredSize();
    }
    
    /**
     * 
     * @return the maximum size
     */
    public Dimension getMaximumSize()
    {
        return getPreferredSize();
    }
}

////////////////////////////////////////////////////
// class NewIdxPanel extends JPanel
//
// Implements new log index dialog panel
////////////////////////////////////////////////////
class NewIdxPanel extends JPanel
{
    private JTextField idxName = new JTextField(20);
    private JList fieldList;

    /**
     * Constructor
     * 
     * @param data
     */
    public NewIdxPanel(Object[] data)
    {
        this.setBorder(new EtchedBorder());
        Box b = Box.createVerticalBox();
        this.add(b);
        Box p1 = Box.createHorizontalBox();
        b.add(p1);

        p1.add(new FixedLabel("Index name", new Dimension(80, 14)));
        p1.add(idxName);
        b.add(Box.createRigidArea(new Dimension(0, 10)));
        b.add(new FixedLabel("Select Exposed Fields to include", new Dimension(200, 18)));
        fieldList = new JList(data);
        b.add( new JScrollPane(fieldList));
    }

    /**
     * 
     * @return teh selected indexes
     */
    public int[] getSelectedIndexes()
    {
        return fieldList.getSelectedIndices();
    }
    
    /**
     * 
     * @return the index name
     */
    public String getIdxName()
    {
        return idxName.getText();
    }

}