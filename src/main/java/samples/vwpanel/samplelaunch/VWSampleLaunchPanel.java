//  ------------------------------------------------------------
//  VCS INFO:
//
//  $Revision:   1.3  $
//  $Date:   Oct 10 2001 16:02:44  $
//  $Workfile:   VWSampleLaunchPanel.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-1999 FileNET Corp.
//  ------------------------------------------------------------

package samples.vwpanel.samplelaunch;

import java.awt.*;
import javax.swing.*;

import filenet.vw.toolkit.runtime.step.beans.*;
import filenet.vw.toolkit.utils.*;

import samples.vwpanel.samplelaunch.images.*;

/**
 * This class contains the UI controls.
 * @version 1, 0
 * @since IWWS1.00
 */
public class VWSampleLaunchPanel extends filenet.vw.toolkit.runtime.step.beans.VWLaunchPanel
{
    /**
     * Initialize the control
     *
     * @since IWWS1.00
     */
	public VWSampleLaunchPanel()
	{
		// This code is automatically generated by Visual Cafe when you add
		// components to the visual environment. It instantiates and initializes
		// the components. To modify the code, only use code syntax that matches
		// what Visual Cafe can generate, or Visual Cafe may be unable to back
		// parse your Java file into its visual environment.
		//{{INIT_CONTROLS
		setLayout(null);
		setSize(765,437);
		vwOperationLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		vwOperationLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		vwOperationLabel.setText("vwlabel");
		vwOperationLabel.setAutoscrolls(true);
		vwOperationLabel.setParameterName("F_OperationName");
		add(vwOperationLabel);
		vwOperationLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		vwOperationLabel.setBounds(168,12,408,24);
		iconLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		iconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		add(iconLabel);
		iconLabel.setBounds(516,12,216,84);
		subjectLabel.setText("Subject:");
		add(subjectLabel);
		subjectLabel.setBounds(24,48,60,24);
		vwSubjectTextField.setParameterName("F_Subject");
		add(vwSubjectTextField);
		vwSubjectTextField.setBounds(96,48,396,24);
		instructionsLabel.setText("Instructions:");
		add(instructionsLabel);
		instructionsLabel.setBounds(24,96,84,24);
		vwInstructionsTextArea.setEditable(false);
		vwInstructionsTextArea.setRows(3);
		vwInstructionsTextArea.setParameterName("F_StepDescription");
		add(vwInstructionsTextArea);
		vwInstructionsTextArea.setBounds(24,120,720,48);
		add(vwTabbedPane);
		vwTabbedPane.setBounds(24,192,720,168);
        vwTabbedPane.setShowGeneralInfo(false);
		vwCompleteButton.setText("Launch");
		vwCompleteButton.setParameterName("F_Complete");
		add(vwCompleteButton);
		vwCompleteButton.setBounds(228,384,96,36);
		vwCancelButton.setText("Cancel");
		vwCancelButton.setParameterName("F_Cancel");
		add(vwCancelButton);
		vwCancelButton.setBounds(336,384,96,36);
		vwHelpButton.setText("Help...");
		vwHelpButton.setParameterName("F_Help");
		add(vwHelpButton);
		vwHelpButton.setBounds(444,384,96,36);
		//}}

		// set the icon
		if (iconLabel != null)
		{
		    iconLabel.setText(null);
		    iconLabel.setIcon(VWImageLoader.createImageIcon("icon.gif"));
		}
	}

    // protected methods

    /**
     * Add additional operations to perform during the cancel process.
     * This method is called after doAbort().
     *
     * @since IWWS1.00
     */
    protected void performExtraCancelOperations()
    {
    }

    /**
     * Add additional operations to perform during the complete process.
     * This method is called before doDispatch().
     *
     * @since IWWS1.00
     */
    protected void performExtraCompleteOperations()
    {
    }

    /**
     * Add additional operations to perform during the load process
     * This method is called before the controls are created.
     *
     * @since IWWS1.00
     */
    protected void performExtraLoadOperations()
    {
    }

    /**
     * Add additional operations to perform during the reassign process
     * This method is called after doReassign().
     *
     * @since IWWS1.00
     */
    protected void performExtraReassignOperations()
    {
    }

    /**
     * Add additional operations to perform during the save process
     * This method is called after doSave().
     *
     * @since IWWS1.00
     */
    protected void performExtraSaveOperations()
    {
    }

	//{{DECLARE_CONTROLS
	filenet.vw.toolkit.runtime.step.beans.VWLabel vwOperationLabel = new filenet.vw.toolkit.runtime.step.beans.VWLabel();
	javax.swing.JLabel iconLabel = new javax.swing.JLabel();
	javax.swing.JLabel subjectLabel = new javax.swing.JLabel();
	filenet.vw.toolkit.runtime.step.beans.VWTextField vwSubjectTextField = new filenet.vw.toolkit.runtime.step.beans.VWTextField();
	javax.swing.JLabel instructionsLabel = new javax.swing.JLabel();
	filenet.vw.toolkit.runtime.step.beans.VWTextArea vwInstructionsTextArea = new filenet.vw.toolkit.runtime.step.beans.VWTextArea();
	filenet.vw.toolkit.runtime.step.beans.VWTabbedPane vwTabbedPane = new filenet.vw.toolkit.runtime.step.beans.VWTabbedPane();
	filenet.vw.toolkit.runtime.step.beans.VWButton vwCompleteButton = new filenet.vw.toolkit.runtime.step.beans.VWButton();
	filenet.vw.toolkit.runtime.step.beans.VWButton vwCancelButton = new filenet.vw.toolkit.runtime.step.beans.VWButton();
	filenet.vw.toolkit.runtime.step.beans.VWButton vwHelpButton = new filenet.vw.toolkit.runtime.step.beans.VWButton();
	//}}
}