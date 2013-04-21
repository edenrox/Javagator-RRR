/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.PlayerState;
import com.hopkins.rocknrollracing.state.NPC;
import com.hopkins.rocknrollracing.state.Upgrade;
import com.hopkins.rocknrollracing.state.UpgradePosition;
import com.hopkins.rocknrollracing.state.UpgradeType;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import com.hopkins.rocknrollracing.utils.StringUtils;
import com.hopkins.rocknrollracing.views.elements.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ihopkins
 */
public class UpgradeView extends AppView {
    
    public static final String EXIT_TEXT = "\n\nReturn to Menu";
    public static final String EXIT_SPRITE_PATH = "images/upgrades/exit.png";
    public static final String CANT_AFFORD_TEXT =
            "Player 1       \n\n" +
            "Gordo:    \n\n" +
            "'Sorry, but you\n" +
            "ain't got enough\n" +
            "cash.'";
    public static final String HOVER_TEXT = 
            "Money: $%s\n\n" +
            "%s\n\n";
    public static final String UPGRADE_TEXT = 
            "Upgrade:\n" +
            "%s\n" +
            "$%s\n";
    public static final String AMMO_TEXT = 
            "Ammunition:\n" +
            "%s\n" +
            "$%s\n";
    
    public static final UpgradePosition EXIT_POSITION = new UpgradePosition(19, 89, UpgradeType.Armor);
    
    @Inject
    protected BigCarElement bigCarElement;
    
    @Inject
    protected UpgradeElement upgradeElement;
    
    @Inject
    protected PanelElement panelElement;
    
    @Inject
    protected FaceElement faceElement;
    
    @Inject
    protected FontBasicElement fontElement;
    
    protected BufferedImage exitImage;
    
    public PlayerState playerState;
    public int cursor;

    @Override
    protected void loadView() throws Exception {
        exitImage = ImageUtils.loadSprite(EXIT_SPRITE_PATH);
    }

    @Override
    public void render(Graphics g, long ticks) {
        int cp = cursor;
        if (ticks % 30 > 15) {
            cp = -1;
        }
        
        // Figure out the upgrade positions
        UpgradePosition[] positions = UpgradePosition.All[playerState.Model.ordinal()];
        
        // Render the backgrounds
        panelElement.renderRedPanel(g, 0, 0, Screen.WIDTH, Screen.HEIGHT, 3);
        panelElement.renderBlackInlayRed(g, 16, 87, 226, 122, 1);
        panelElement.renderGrayPanel(g, 101, 13, 142, 63);
        
        // Render the car
        bigCarElement.render(g, 18, 90, playerState.Model, playerState.Color);
        
        // Render gordo's face
        faceElement.render(g, 25, 16, NPC.Gordo);
        
        // Render the text
        String text = "";
        if (cursor == 0) {
            text = EXIT_TEXT;
        } else {
            UpgradeType type = positions[cursor - 1].getType();
            boolean isAmmo = playerState.isAmmoUpgrade(type);
            Upgrade current = playerState.getCurrentUpgrade(type);
            
            text = String.format(HOVER_TEXT,
                    StringUtils.formatNumber(playerState.Money),
                    current.getName()
                    );
            if (playerState.canUpgrade(type)) {
                if (isAmmo) {
                    text += String.format(AMMO_TEXT,
                            current.getSingle(),
                            StringUtils.formatNumber(current.getPrice()));
                } else {
                    Upgrade upgrade = playerState.getNextUpgrade(type);
                    text += String.format(UPGRADE_TEXT,
                            upgrade.getName(),
                            StringUtils.formatNumber(upgrade.getPrice()));
                }
            }
        }
        fontElement.renderText(g, 104, 16, text);
        
        // Render the exit button
        renderExit(g, EXIT_POSITION.getX(), EXIT_POSITION.getY(), (cp == 0));
        
        // Render the upgrades
        
        for(int i = 0; i < positions.length; i++) {
            UpgradePosition pos = positions[i];
            UpgradeType type = pos.getType();
            int offset = getSpriteOffset(type);
            
            upgradeElement.renderUpgradeIcon(g, pos.getX(), pos.getY(), type, offset, (cp == i+1));
            if (type.isAmmo()) {
                upgradeElement.renderLevels(g, pos.getX(), pos.getY(), getCharges(type));
            }
        }
    }
    
    protected int getCharges(UpgradeType type) {
        return playerState.Upgrades.getCharges(type);
    }
    
    protected int getSpriteOffset(UpgradeType type) {
        switch(type) {
            case Armor:
            case Engine:
            case Tires:
            case Shocks:
                return playerState.Upgrades.getLevel(type);
            case Weapon:
                return playerState.Model.getWeapon().ordinal();
            case Drop:
                return playerState.Model.getDrop().ordinal();
            case Boost:
                return playerState.Model.getBoost().ordinal();
            default:
                return -1;
        }
    }
    
    protected void renderExit(Graphics g, int x, int y, boolean isHighlighted) {
        g.drawImage(exitImage, x, y, null);
        
        if (isHighlighted) {
            g.setColor(MenuColors.Yellow);
            g.drawRect(x, y, 32, 16);
        }
    }
    
    
    
}
