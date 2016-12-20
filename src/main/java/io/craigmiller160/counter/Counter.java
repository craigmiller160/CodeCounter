package io.craigmiller160.counter;

import javax.swing.*;
import java.util.Arrays;
import java.util.Optional;

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
        Thread.setDefaultUncaughtExceptionHandler((t,e) -> handleError("Uncaught Exception!", e));
        SwingUtilities.invokeLater(Counter::new);
    }

    private static void enableNimbusLAF(){
        try {
            Optional<UIManager.LookAndFeelInfo> info = Arrays.stream(UIManager.getInstalledLookAndFeels())
                    .filter((laf) -> "Nimbus".equals(laf.getName()))
                    .findFirst();
            if(info.isPresent()){
                UIManager.setLookAndFeel(info.get().getClassName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static CounterUI view;
    private CounterController controller;
    private CounterModel model;

    public Counter(){
        System.out.println("Starting Code Counter");
        enableNimbusLAF();
        init();
    }

    private void init(){
        this.model = new CounterModel();
        view = new CounterUI();
        this.controller = new CounterController(model);
        view.addListener(controller);
    }

    public static void handleError(String message, Throwable ex){
        System.err.println(message);
        if(ex != null){
            ex.printStackTrace();
        }

        if(SwingUtilities.isEventDispatchThread()){
            showErrorDialog(message, ex);
        }
        else{
            SwingUtilities.invokeLater(() -> showErrorDialog(message, ex));
        }
    }

    private static void showErrorDialog(String message, Throwable ex){
        if(ex != null){
            message = message + "[" + ex.getMessage() + "]";
        }
        if(view != null){
            JOptionPane.showMessageDialog(view.getWindow(), message, "Error", JOptionPane.ERROR_MESSAGE);
        }
        else{
            JOptionPane.showMessageDialog(null, message, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

}
