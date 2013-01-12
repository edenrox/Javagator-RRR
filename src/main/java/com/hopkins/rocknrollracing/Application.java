/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing;

import com.hopkins.rocknrollracing.controllers.AppController;
import com.hopkins.rocknrollracing.state.CarModel;
import com.hopkins.rocknrollracing.state.ControllerState;
import com.hopkins.rocknrollracing.state.GameState;
import com.hopkins.rocknrollracing.views.Screen;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author ian
 */
public class Application implements Runnable {
    public static final long NS_PER_MS = 1000000;
    public static final long MS_PER_SECOND = 1000;
    public static final long DESIRED_FPS = 60;
    public static final long DESIRED_PERIOD_MS = MS_PER_SECOND / DESIRED_FPS;
    public static final String INITIAL_CONTROLLER = "Race";
    public static final String TITLE = "Javagator Rock 'n Roll Racing";
    
    
    protected long startTimeNano;
    protected GameState gameState;
    protected boolean running;
    protected AppController controller;
    protected BufferedImage buffer;
    protected Graphics bufferGraphics;
    protected JFrame frame;
    protected JPanel panel;
    protected ControllerState controllerState;
    protected long ticker;
    
    public Application() {
        gameState = new GameState();
        gameState.Player1.Model = CarModel.AirBlade;
        initBuffer();
        dispatch(INITIAL_CONTROLLER);
    }
    
    protected void initWindow() {
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(Screen.WIDTH * 2, Screen.HEIGHT * 2));
        panel.setIgnoreRepaint(true);
        
        frame = new JFrame();
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setTitle(TITLE);
        
        controllerState = new ControllerState();
        controllerState.loadConfig("config/keys.properties");
        frame.addKeyListener(controllerState);
    }
    
    protected final void initBuffer() {
        buffer = new BufferedImage(Screen.WIDTH, Screen.HEIGHT, BufferedImage.TYPE_INT_ARGB);
    }
    
    public final void dispatch(String controllerName) {
        ticker = 0;
        controller = instantiateController(controllerName);
        controller.load(gameState);
    }
    
    protected AppController instantiateController(String controllerName) {
        String className = String.format("com.hopkins.rocknrollracing.controllers.%sController", controllerName);
        try {
            Class<?> clazz = Class.forName(className);
            return (AppController) clazz.getConstructor().newInstance();
        } catch (Exception ex) {
            throw new RuntimeException(String.format(
                    "Unknown controller type: %s %s",
                    controllerName, className),
                    ex);
        }
    }
    
    public void run() {
        running = true;
        initWindow();
        
        startTimeNano = System.nanoTime();
        while (running) {
            update();
            render();
            paint();
            sleep();
        }
    }
    
    protected void sleep() {
        // the timer
        long timeNS = System.nanoTime();
        long deltaNS = timeNS - startTimeNano;
        startTimeNano = timeNS;
        
        // sleeping
        try {
            Thread.sleep(Math.max(5, DESIRED_PERIOD_MS - deltaNS/NS_PER_MS));
        } catch (InterruptedException ex) {
            
        }
    }
    
    protected void update() {
        String dispatchTo = controller.getDispatchTo();
        if (dispatchTo != null) {
            dispatch(dispatchTo);
        }
        controller.update(controllerState, ticker);
        controllerState.afterUpdate();
        ticker++;
    }
    protected void render() {
        Graphics g = buffer.getGraphics();
        controller.getView().render(g, ticker);
        g.dispose();
    }
    protected void paint() {
        Graphics g = panel.getGraphics();
        g.drawImage(buffer, 0, 0, panel.getWidth(), panel.getHeight(), frame);
        g.dispose();
        
        Toolkit.getDefaultToolkit().sync();
    }
    
    public static void main(String[] args) {
        Application app = new Application();
        app.run();
    }
}
