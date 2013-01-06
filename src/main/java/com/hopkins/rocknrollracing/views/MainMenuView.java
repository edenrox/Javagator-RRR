/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views;

import com.hopkins.rocknrollracing.state.Difficulty;
import com.hopkins.rocknrollracing.utils.ImageUtils;
import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.utils.StringUtils;
import com.hopkins.rocknrollracing.views.elements.FontIntroElement;
import com.hopkins.rocknrollracing.views.elements.TitleElement;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ian
 */
public class MainMenuView extends AppView {
    public static final String SPRITE_PATH = "images/menu/cursor.png";
    public static final String INITIAL_MENU = "New Game\n\nPassword\n\nVS.Mode";
    public static final String NUM_PLAYERS_MENU = "1 Player\n\n2 Player";
    
    @Inject
    protected FontIntroElement fontElement;
    
    @Inject
    protected TitleElement titleElement;
    
    protected String difficultyMenu;
    protected BufferedImage cursor;
    protected String[] allMenus;
    
    protected int menuIndex;
    protected int cursorPosition;
    
    public int getMenuIndex() { return menuIndex; }
    public void setMenuIndex(int value) { menuIndex = value; }
    public int getCursorPosition() { return cursorPosition; }
    public void setCursorPosition(int value) { cursorPosition = value; }
    
    @Override
    protected void loadView() throws Exception {
        String[] difficulties = new String[] {
            Difficulty.Rookie.getName(),
            Difficulty.Veteran.getName(),
            Difficulty.Warrior.getName()
        };
        difficultyMenu = StringUtils.join(difficulties, "\n\n");
        allMenus = new String[] {INITIAL_MENU, NUM_PLAYERS_MENU, difficultyMenu};
        
        cursor = ImageUtils.loadSprite(SPRITE_PATH);
    }

    @Override
    public void render(Graphics g, long ticks) {
        clear(g, Color.black);
        
        titleElement.render(g, 36, 16);
        
        fontElement.renderText(g, 50, 105, allMenus[menuIndex]);
        
        
        if (ticks % 60 < 30) {
            int y = 103 + cursorPosition * FontIntroElement.HEIGHT * 2;
            g.drawImage(cursor, 20, y, null);
        }
    }
    
    
    
}
