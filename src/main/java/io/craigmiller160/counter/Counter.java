package io.craigmiller160.counter;

import javax.swing.*;

/**
 * Created by craig on 12/16/16.
 */
public class Counter {

    /*
     * TODO add these items
     * - Better logging
     * - Error handling with UI indicators
     * - Progress bar while processing
     * - Output report dialog
     */

    public static void main(String[] args){
        SwingUtilities.invokeLater(Counter::new);
    }

    private static void enableNimbusLAF(){
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private CounterUI view;
    private CounterController controller;
    private CounterModel model;

    public Counter(){
        enableNimbusLAF();
        init();
    }

    private void init(){
        this.model = new CounterModel();
        this.view = new CounterUI();
        this.controller = new CounterController(model);
        this.view.addListener(controller);
    }

}
