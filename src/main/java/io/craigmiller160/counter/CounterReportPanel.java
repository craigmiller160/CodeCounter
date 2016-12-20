package io.craigmiller160.counter;

import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import java.io.File;

import static io.craigmiller160.counter.CounterController.SAVE_ACTION;

/**
 * Created by craigmiller on 12/20/16.
 */
public class CounterReportPanel extends AbstractView {

    private JPanel panel;
    private JLabel reportLabel;
    private JButton saveButton;

    public CounterReportPanel(){
        init();
    }

    public JPanel getPanel(){
        return panel;
    }

    private void init(){
        panel.setLayout(new MigLayout());

        reportLabel = new JLabel();

        saveButton = new JButton("Save");
        saveButton.setToolTipText("Save the report to a file");
        saveButton.addActionListener((e) -> saveReport());

        panel.add(reportLabel, "wrap");
        panel.add(saveButton, "");
    }

    public void setReport(String report){
        this.reportLabel.setText(report);
    }

    private void saveReport(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);

        int result = fileChooser.showSaveDialog(panel.getTopLevelAncestor());
        if(result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            fireActionEvent(SAVE_ACTION, new Object[] {file, reportLabel.getText()});
        }
    }

}
