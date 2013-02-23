/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing;

import com.hopkins.rocknrollracing.state.race.Coord;
import com.hopkins.rocknrollracing.state.race.Vector3D;
import com.hopkins.rocknrollracing.state.race.World;
import com.hopkins.rocknrollracing.views.Screen;
import org.junit.*;
import static org.junit.Assert.*;

/**
 *
 * @author ian
 */
public class WorldTests {

    protected Vector3D cameraPos;
    protected Coord center;

    @Before
    public void setUp() {
        cameraPos = new Vector3D(3.0f, 3.0f, 0.0f);
        
        center = new Coord(Screen.WIDTH / 2, Screen.HEIGHT / 2);
    }

    @Test
    public void testScreenCentre() {
        // The camera position should be the centre of the screen
        Coord actual = World.toScreenPosition(cameraPos, cameraPos);

        coordEquals(center, actual);
    }

    @Test
    public void testPoints() {
        float[][] positions = new float[][]{
            new float[]{0, 0},
            new float[]{4, 1},
            new float[]{1, 3},
            new float[]{4, 5}
        };
        int[][] expected = new int[][]{
            new int[]{center.X, center.Y - 48},
            new int[]{center.X + 96, center.Y - 8},
            new int[]{center.X - 64, center.Y - 16},
            new int[]{center.X - 32, center.Y + 24}
        };

        for (int i = 0; i < positions.length; i++) {
            Vector3D itemPos = new Vector3D();
            itemPos.set(positions[i][0], positions[i][1], 0);
            Coord actual = World.toScreenPosition(itemPos, cameraPos);
            Coord exc = new Coord(expected[i][0], expected[i][1]);

            coordEquals(exc, actual);
        }
    }
    
    @Test
    public void testMapTranslate() {
        int[][] mapCoords = new int[][] {
            new int[] {1, 1},
            new int[] {3, 4},
            new int[] {5, 2},
            new int[] {7, 7}
        };
        float[][] worldPos = new float[][] {
            new float[] {12f, 12f},
            new float[] {24f, 30f},
            new float[] {36f, 18f},
            new float[] {48f, 48f}
        };
        
        for(int i = 0; i < mapCoords.length; i++) {
            Coord c = new Coord(mapCoords[i][0], mapCoords[i][1]);
            Vector3D expected = new Vector3D(worldPos[i][0], worldPos[i][1], 0f);
            Vector3D actual = World.fromMapPosition(c);
            
            vector3DEquals(expected, actual);
        }
                
        
    }
    
    protected void vector3DEquals(Vector3D expected, Vector3D actual) {
        assertEquals(expected.toString(), actual.toString());
    }
    
    protected void coordEquals(Coord a, Coord b) {
        assertEquals(a.toString(), b.toString());
    }
    
    
}
