/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.views;

import com.hopkins.rocknrollracing.inject.Inject;
import com.hopkins.rocknrollracing.state.HasFace;
import com.hopkins.rocknrollracing.state.PodiumPlace;
import com.hopkins.rocknrollracing.state.race.RaceResult;
import com.hopkins.rocknrollracing.utils.StringUtils;
import com.hopkins.rocknrollracing.views.elements.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author ihopkins
 */
public class PodiumView extends AppView {
    
    public static final int SLICE_HEIGHT = 75;
    public static final String SLICE_FORMAT = 
            "%s\n" +
            "%s\n\n" +
            "Points:  %s\n" +
            "Prize:  $%s";
                    
    public static final int GRAY_WIDTH = 126;
    public static final int GRAY_HEIGHT = 55;
    
    public static final int DURATION = 800;
    
    public static final int[] FROM = new int[] {-Screen.WIDTH, Screen.WIDTH, -Screen.WIDTH};
    
    @Inject
    protected FaceElement faceElement;
    @Inject
    protected TrophyElement trophyElement;
    @Inject
    protected FontBasicElement fontElement;
    @Inject
    protected PanelElement panelElement;
    
    protected long originTicks = 0;
    protected BufferedImage podiumImage;
    public RaceResult raceResult;

    @Override
    protected void loadView() throws Exception {
    }
    
    protected void generatePodiumImage() {
        podiumImage = new BufferedImage(Screen.WIDTH, Screen.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics g = podiumImage.createGraphics();
        
        panelElement.renderRedPanel(g, 0, 0, Screen.WIDTH, Screen.HEIGHT, 2);
        for(int i = 0; i < 3; i++) {
            renderSlice(g, 0, i, raceResult.Positions[i]);
        }
        g.dispose();
    }

    @Override
    public void render(Graphics g, long ticks) {
        if (originTicks == 0) {
            generatePodiumImage();
        }
        
        g.setColor(Color.BLACK);
        g.clearRect(0, 0, Screen.WIDTH, Screen.HEIGHT);
        
        
        int dx1, dx2, dy1, dy2, sx1, sx2, sy1, sy2;
        for(int i = 0; i < 3; i++) {
            dx1 = getX(i, ticks);
            dx2 = dx1 + Screen.WIDTH;
            dy1 = i * SLICE_HEIGHT;
            dy2 = dy1 + SLICE_HEIGHT;
            
            sx1 = 0;
            sx2 = Screen.WIDTH;
            sy1 = i * SLICE_HEIGHT;
            sy2 = sy1 + SLICE_HEIGHT;
            
            g.drawImage(podiumImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
        }
    }
    
    protected int getX(int place, double ticks) {
        switch (place) {
            case 0:
                if (ticks < 100) {
                    return (int) (Screen.WIDTH * (ticks / 100) - Screen.WIDTH);
                } else {
                    return 0;
                }
            case 1:
                if (ticks < 200) {
                    return Screen.WIDTH;
                } else if (ticks < 300) {
                    return (int) (Screen.WIDTH - (ticks - 200) / 100 * Screen.WIDTH);
                } else {
                    return 0;
                }
            case 2:
                if (ticks < 400) {
                    return 0 - Screen.WIDTH;
                } else if (ticks < 500) {
                    return (int) (Screen.WIDTH * (ticks - 400) / 100 - Screen.WIDTH);
                } else {
                    return 0;
                }
            default:
                return 0;
        }
    }
    
    protected void renderSlice(Graphics g, int x, int place, HasFace face) {
        int dx, y;
        PodiumPlace podiumPlace = PodiumPlace.All[place];
        
        y = place * SLICE_HEIGHT;

        // Draw the face
        dx = x + 8;
        if (place == 1) {
            dx = x + 186;
        }
        faceElement.render(g, dx, y + 2, face);
        
        // Draw the gray panel
        dx = x + 76;
        if (place == 1) {
            dx = x + 52;
        }
        panelElement.renderGrayPanel(g, dx, y + 10, GRAY_WIDTH, GRAY_HEIGHT);
        
        
        String text = String.format(SLICE_FORMAT,
                face.getName(), 
                "",
                "" + podiumPlace.getPoints(), 
                StringUtils.formatNumber(podiumPlace.getPrizeMoney())
                );
        
        fontElement.renderText(g, dx + 4, y + 17, text);
        
        dx = x + 220;
        if (place == 1) {
            dx = x + 12;
        }
        trophyElement.render(g, dx, y+11, place);
    }
    
    
}
