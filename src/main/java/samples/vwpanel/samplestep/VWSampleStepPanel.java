//  ------------------------------------------------------------
//  VCS INFO:
//
//  $Revision:   1.3  $
//  $Date:   Oct 10 2001 15:55:06  $
//  $Workfile:   VWSampleStepPanel.java  $
//  ------------------------------------------------------------
//  All Rights Reserved.  Copyright (c) 1998-1999 FileNET Corp.
//  ------------------------------------------------------------

package samples.vwpanel.samplestep;

import java.awt.*;
import javax.swing.*;

import filenet.vw.toolkit.runtime.step.beans.*;
import filenet.vw.toolkit.utils.*;

import samples.vwpanel.samplestep.images.*;

/**
 * This class contains the UI controls.
 * @version 1, 0
 * @since IWWS1.00
 */
public class VWSampleStepPanel extends filenet.vw.toolkit.runtime.step.beans.VWPanel
{
    /**
     * Initialize the control
     *
     * @since IWWS1.00
     */
	public VWSampleStepPanel()
	{
		// This code is automatically generated by Visual Cafe when you add
		// components to the visual environment. It instantiates and initializes
		// the components. To modify the code, only use code syntax that matches
		// what Visual Cafe can generate, or Visual Cafe may be unable to back
		// parse your Java file into its visual environment.
		//{{INIT_CONTROLS
		setLayout(null);
		setSize(765,500);
		vwOperationLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		vwOperationLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		vwOperationLabel.setText("vwlabel");
		vwOperationLabel.setAutoscrolls(true);
		vwOperationLabel.setParameterName("F_OperationName");
		add(vwOperationLabel);
		vwOperationLabel.setFont(new Font("Dialog", Font.BOLD, 18));
		vwOperationLabel.setBounds(168,12,408,24);
		subjectLabel.setText("Subject:");
		add(subjectLabel);
		subjectLabel.setBounds(24,48,84,24);
		vwSubjectTextField.setParameterName("F_Subject");
		add(vwSubjectTextField);
		vwSubjectTextField.setBounds(108,48,384,24);
		iconLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
		iconLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
		add(iconLabel);
		iconLabel.setBounds(516,48,216,108);
		receivedLabel.setText("Received on:");
		add(receivedLabel);
		receivedLabel.setBounds(24,84,84,24);
		vwReceivedLabel.setText("vwlabel");
		vwReceivedLabel.setParameterName("F_DateReceived");
		add(vwReceivedLabel);
		vwReceivedLabel.setBounds(108,84,384,24);
		deadlineLabel.setText("Deadline:");
		add(deadlineLabel);
		deadlineLabel.setBounds(24,120,84,24);
		vwDeadlineLabel.setText("vwlabel");
		vwDeadlineLabel.setParameterName("F_DeadLine");
		add(vwDeadlineLabel);
		vwDeadlineLabel.setBounds(108,120,384,24);
		instructionsLabel.setText("Instructions:");
		add(instructionsLabel);
		instructionsLabel.setBounds(24,156,84,24);
		vwInstructionsTextArea.setEditable(false);
		vwInstructionsTextArea.setRows(3);
		vwInstructionsTextArea.setParameterName("F_StepDescription");
		add(vwInstructionsTextArea);
		vwInstructionsTextArea.setBounds(24,180,720,48);
		add(vwTabbedPane);
		vwTabbedPane.setBounds(24,252,720,168);
        vwTabbedPane.setShowGeneralInfo(false);
		vwCompleteButton.setText("Complete");
		vwCompleteButton.setParameterName("F_Complete");
		add(vwCompleteButton);
		vwCompleteButton.setBounds(120,444,96,36);
		vwCancelButton.setText("Cancel");
		vwCancelButton.setParameterName("F_Cancel");
		add(vwCancelButton);
		vwCancelButton.setBounds(228,444,96,36);
		vwSaveButton.setText("Save");
		vwSaveButton.setParameterName("F_Save");
		add(vwSaveButton);
		vwSaveButton.setBounds(336,444,96,36);
		vwReassignButton.setText("Reassign...");
		vwReassignButton.setParameterName("F_Reassign");
		add(vwReassignButton);
		vwReassignButton.setBounds(444,444,96,36);
		vwHelpButton.setText("Help...");
		vwHelpButton.setParameterName("F_Help");
		add(vwHelpButton);
		vwHelpButton.setBounds(552,444,96,36);
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
	javax.swing.JLabel subjectLabel = new javax.swing.JLabel();
	filenet.vw.toolkit.runtime.step.beans.VWTextField vwSubjectTextField = new filenet.vw.toolkit.runtime.step.beans.VWTextField();
	javax.swing.JLabel iconLabel = new javax.swing.JLabel();
	javax.swing.JLabel receivedLabel = new javax.swing.JLabel();
	filenet.vw.toolkit.runtime.step.beans.VWLabel vwReceivedLabel = new filenet.vw.toolkit.runtime.step.beans.VWLabel();
	javax.swing.JLabel deadlineLabel = new javax.swing.JLabel();
	filenet.vw.toolkit.runtime.step.beans.VWLabel vwDeadlineLabel = new filenet.vw.toolkit.runtime.step.beans.VWLabel();
	javax.swing.JLabel instructionsLabel = new javax.swing.JLabel();
	filenet.vw.toolkit.runtime.step.beans.VWTextArea vwInstructionsTextArea = new filenet.vw.toolkit.runtime.step.beans.VWTextArea();
	filenet.vw.toolkit.runtime.step.beans.VWTabbedPane vwTabbedPane = new filenet.vw.toolkit.runtime.step.beans.VWTabbedPane();
	filenet.vw.toolkit.runtime.step.beans.VWButton vwCompleteButton = new filenet.vw.toolkit.runtime.step.beans.VWButton();
	filenet.vw.toolkit.runtime.step.beans.VWButton vwCancelButton = new filenet.vw.toolkit.runtime.step.beans.VWButton();
	filenet.vw.toolkit.runtime.step.beans.VWButton vwSaveButton = new filenet.vw.toolkit.runtime.step.beans.VWButton();
	filenet.vw.toolkit.runtime.step.beans.VWButton vwReassignButton = new filenet.vw.toolkit.runtime.step.beans.VWButton();
	filenet.vw.toolkit.runtime.step.beans.VWButton vwHelpButton = new filenet.vw.toolkit.runtime.step.beans.VWButton();
	//}}
}
