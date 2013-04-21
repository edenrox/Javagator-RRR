/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.CarColor;
import com.hopkins.rocknrollracing.state.CarModel;
import com.hopkins.rocknrollracing.state.CarState;
import com.hopkins.rocknrollracing.state.NPC;
import com.hopkins.rocknrollracing.state.PlayerState;
import com.hopkins.rocknrollracing.utils.StringUtils;
import com.hopkins.rocknrollracing.views.elements.*;
import java.awt.Graphics;

/**
 *
 * @author ian
 */
public class BuyView extends ViewWithBackground {
    
    public static final String TEXT_FORMAT = 
            "Player 1\n\n" +
            "Money: $%s\n" +
            "Item: %s\n" +
            "Cost: $%s";
    public static final String CANT_AFFORD_TEXT =
            "Player 1       \n\n" +
            "Fast Eddie:    \n\n" +
            "'Sorry, but you\n" +
            "ain't got enough\n" +
            "cash.'";
    public static final String CANT_EXIT_TEXT =
            "Player 1       \n\n" +
            "Fast Eddie:    \n\n" +
            "'You can't race\n" +
            "without wheels!'";
    public static final String SPRITE_PATH = "images/buy/place.png";
    
    public static final int NUM_PLACES = 3;
    public static final int STOP_FRAME = 21;
    
    @Inject
    protected BuyCraneElement buyCrane;
    
    @Inject
    protected FaceElement face;
    
    @Inject
    protected FontBasicElement font;
    
    @Inject
    protected PanelElement panel;
    
    @Inject
    protected CarElement carElement;
    
    @Inject
    protected BuyMenuElement menu;
    
    @Inject
    protected BuyPlaceElement place;
    
    public int modelIndex;
    public boolean isSelected;
    public boolean isComplete;
    public int colorIndex;
    public int menuIndex;
    public int modelOffset;
    public PlayerState playerState;
    
    protected boolean hasGrabbed;
    protected int cx = 85, cy = 0;
    protected int[] modelFrame;
    public static final int MAX_CY = 34;

    @Override
    protected void loadView() throws Exception {
        modelFrame = new int[] {STOP_FRAME, STOP_FRAME, STOP_FRAME};
        hasGrabbed = false;
    }
    
    

    @Override
    protected void renderBackground(Graphics g) {
        // Background
        panel.renderRedPanel(g, 0, 0, Screen.WIDTH, Screen.HEIGHT, 2);
        
        // Eddie's face
        face.render(g, 18, 9, NPC.Eddie);
        
        // Text panel
        panel.renderGrayPanel(g, 101, 14, 142, 62);
        
        // Main area
        panel.renderBlackInlayRed(g, 64, 86, 178, 122, 1);
        
        // Crane Rails
        buyCrane.renderRails(g, 65, 87);
        
        // Menu buttons panel
        panel.renderBluePanel(g, 17, 80, 40, 136, 1);
        
        // Places for the cars
        for (int i = 0; i < NUM_PLACES; i++) {
            place.render(g, 71 + i * 56, 130);
        }
        
        // Menu Buttons
        menu.render(g, 21, 87);
    }

    @Override
    protected void renderForeground(Graphics g, long ticks) {
        
        // Text
        CarModel selected = getSelected();
        String text = String.format(TEXT_FORMAT,
                StringUtils.formatNumber(playerState.Money),
                selected.getName(),
                StringUtils.formatNumber(selected.getPrice()));
        font.renderText(g, 104, 17, text);
        
        // Menu Highlight
        if (!isSelected) {
            if ((ticks % 30) > 15) {
                int x = 21;
                int y = menu.getMenuY(87, menuIndex);
                g.setColor(MenuColors.Yellow);
                g.drawRect(x, y, 32, 16);
            }
        }
        
        // calculate the frame rotation
        if (ticks % 8 == 0) {
            for(int i = 0; i < 3; i++) {
                if (modelFrame[i] != STOP_FRAME) {
                    modelFrame[i] = (modelFrame[i] + 1) % 24;
                } else if ((i == modelIndex) && (!isSelected)) {
                    modelFrame[i] = (modelFrame[i] + 1) % 24;
                }
            }
        }
        
        
        // calculate crane position and arm position
        int desiredCx = 85 + modelIndex * 56;
        BuyCraneElement.ArmPosition armPos = BuyCraneElement.ArmPosition.Closed;
        
        // animate the crane
        if (ticks % 4 == 0) {
            if ((hasGrabbed) && (cy == 0)) {
                cx += 2;
            } else if (desiredCx > cx) {
                cx += 2;
            } else if (desiredCx < cx) {
                cx -= 2;
            } else if ((isSelected) && (modelFrame[modelIndex] == STOP_FRAME)) {
                if (!hasGrabbed) {
                    cy += 2;
                } else if (cy > 0) {
                    cy -= 2;
                }
            }
        }
        if (cy == MAX_CY) {
            hasGrabbed = true;
            armPos = BuyCraneElement.ArmPosition.Gripping;
        } else if (!hasGrabbed) {
            if (cy >= MAX_CY - 4) {
                armPos = BuyCraneElement.ArmPosition.Open;
            } else if (cy >= MAX_CY - 8) {
                armPos = BuyCraneElement.ArmPosition.Gripping;
            }
        }
        if (hasGrabbed) {
            armPos = BuyCraneElement.ArmPosition.Gripping;
            if (cx > Screen.WIDTH) {
                isComplete = true;
            }
        }
        
        g.clipRect(65, 87, 176, 120);
        buyCrane.renderArmBehind(g, cx, 87, cy, armPos);
        
        // Cars
        CarState cs = new CarState();
        cs.color = CarColor.All[colorIndex];
        for (int i = 0; i < NUM_PLACES; i++) {
            cs.y = 149;
            cs.frame = modelFrame[i];
            cs.x = 73 + i * 56;
            if ((i == modelIndex) && (hasGrabbed)) {
                cs.x += (cx - desiredCx);
                cs.y -= (MAX_CY - cy);
            }
            cs.model = getModel(i);
            carElement.render(g, cs);
        }
        
        // Buy crane
        buyCrane.renderCrane(g, cx, 87, cy, armPos);
        g.setClip(null);
        
    }
    
    public CarModel getSelected() {
        return getModel(modelIndex);
    }
    
    public CarModel getModel(int index) {
        return CarModel.All[NUM_PLACES - 1 - index + modelOffset];
    }
    
    
    
    
    
    
}
