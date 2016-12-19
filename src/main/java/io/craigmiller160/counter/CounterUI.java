package io.craigmiller160.counter;

import io.craigmiller160.counter.listener.ViewActionEvent;
import io.craigmiller160.counter.listener.ViewChangeEvent;
import io.craigmiller160.counter.listener.ViewEventListener;
import net.miginfocom.swing.MigLayout;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.*;

import static io.craigmiller160.counter.CounterController.*;

/**
 * Created by craig on 12/16/16.
 */
public class CounterUI implements DocumentListener{

    private final java.util.List<ViewEventListener> listeners = new ArrayList<>();

    private File lastLocation;

    private JFrame window;
    private JTextField pathField;
    private JButton pathChooserButton;
    private JLabel javaOption;
    private JLabel javaScriptOption;
    private JLabel sqlOption;
    private JLabel xmlOption;
    private JLabel htmlOption;
    private JLabel bashOption;
    private JLabel propsOption;
    private JLabel cssOption;
    private JLabel jarsOption;
    private JButton executeButton;

    private JCheckBox includeComments;

    public CounterUI(){
        init();
    }

    public void addListener(ViewEventListener listener){
        this.listeners.add(listener);
    }

    public void removeListener(ViewEventListener listener){
        this.listeners.remove(listener);
    }

    protected void fireChangeEvent(String key, Object value){
        listeners.forEach((l) -> l.viewEvent(new ViewChangeEvent(this, key, value)));
    }

    protected void fireActionEvent(String command){
        listeners.forEach((l) -> l.viewEvent(new ViewActionEvent(this, command)));
    }

    public JFrame getWindow(){
        return window;
    }

    private void init(){
        window = new JFrame("Code Counter");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setContentPane(new JPanel(new MigLayout()));

        ((JPanel) window.getContentPane()).setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        executeButton = new JButton("Execute");
        executeButton.setToolTipText("Execute the code counting.");
        executeButton.addActionListener((e) -> fireActionEvent(EXECUTE_ACTION));

        JLabel title = new JLabel("Code Counter");
        title.setAlignmentX(JLabel.CENTER);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("serif", Font.BOLD, 16));
        title.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));

        window.getContentPane().add(title, "dock north");
        window.getContentPane().add(buildPathPanel(), "dock north");
        window.getContentPane().add(buildConfigPanel(), "dock center");
        window.getContentPane().add(executeButton, "dock south"); //Ends up on bottom because it's called first
        window.getContentPane().add(buildOptionsPanel(), "dock south");

        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    private JPanel buildPathPanel(){
        JPanel pathPanel = new JPanel(new MigLayout());

        pathPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.GRAY, 1),
                        "Project"
                ),
                BorderFactory.createEmptyBorder(2,2,2,2)
        ));

        String pathToolTip = "The path to the directory containing the project whose lines should be counted.";

        JLabel pathLabel = new JLabel("Project Directory: ");
        pathLabel.setToolTipText(pathToolTip);
        pathField = new JTextField(10);
        pathField.getDocument().addDocumentListener(this);

        pathChooserButton = new JButton("...");
        pathChooserButton.setToolTipText("Open a file chooser to select the path to the directory containing the project whose lines should be counted");
        pathChooserButton.addActionListener((e) -> selectPath());

        pathPanel.add(pathLabel);
        pathPanel.add(pathField, "growx, pushx");
        pathPanel.add(pathChooserButton);

        return pathPanel;
    }

    private JPanel buildConfigPanel(){
        JPanel configPanel = new JPanel(new MigLayout());

        configPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.GRAY, 1),
                        "Supported Languages"
                ),
                BorderFactory.createEmptyBorder(2,2,2,2)
        ));

        javaOption = new JLabel("Java");

        javaScriptOption = new JLabel("JavaScript");

        sqlOption = new JLabel("SQL");

        xmlOption = new JLabel("XML");

        htmlOption = new JLabel("HTML");

        bashOption = new JLabel("Bash");

        propsOption = new JLabel("Properties");

        jarsOption = new JLabel("Jars");

        cssOption = new JLabel("CSS");

        configPanel.add(javaOption, "");
        configPanel.add(javaScriptOption, "");
        configPanel.add(htmlOption, "wrap");
        configPanel.add(xmlOption, "");
        configPanel.add(sqlOption, "");
        configPanel.add(bashOption, "wrap");
        configPanel.add(propsOption, "");
        configPanel.add(cssOption, "");
        configPanel.add(jarsOption, "");

        return configPanel;
    }

    private JPanel buildOptionsPanel(){
        JPanel optionsPanel = new JPanel(new MigLayout());

        optionsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(Color.GRAY, 1),
                        "Options"
                ),
                BorderFactory.createEmptyBorder(2,2,2,2)
        ));

        includeComments = new JCheckBox("Include Comments");
        includeComments.setToolTipText("Include lines that are comments in line count");
        includeComments.addItemListener((e) -> fireChangeEvent(INCLUDE_COMMENT_PROP, e.getStateChange() == ItemEvent.SELECTED));

        optionsPanel.add(includeComments);

        return optionsPanel;
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        updatePath(e);
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        updatePath(e);
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        updatePath(e);
    }

    private void updatePath(DocumentEvent event){
        try{
            String text = event.getDocument().getText(0, event.getDocument().getLength());
            pathField.setToolTipText(text);
            fireChangeEvent(PATH_PROP, text);
        }
        catch(BadLocationException ex){
            Counter.handleError("Failed to update path property.", ex);
        }
    }

    private void selectPath(){
        JFileChooser fileChooser = new JFileChooser();
        if(lastLocation != null){
            fileChooser.setCurrentDirectory(lastLocation);
        }

        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setMultiSelectionEnabled(false);

        int result = fileChooser.showOpenDialog(window);
        if(result == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            if(file != null){
                lastLocation = file;
                pathField.setText(file.getAbsolutePath());
            }
        }
    }

}
