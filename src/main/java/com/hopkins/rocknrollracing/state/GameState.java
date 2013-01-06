/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

/**
 *
 * @author ian
 */
public class GameState {
    public Difficulty Difficulty;
    public PlayerState Player1;
    public PlayerState Player2;
    public int NumPlayers;
    public GameMode Mode;
    public Enemy Rival;
    public Division Division;
    public int RaceNumber;
    public SoundState Sound;
    public RaceResult LastRaceResult;
    
    public GameState() {
        Difficulty = Difficulty.Rookie;
        Player1 = new PlayerState();
        NumPlayers = 1;
        Mode = GameMode.Career;
        Rival = Rival.Viper;
        Division = Division.B;
        RaceNumber = 1;
        Sound = new SoundState();
    }
    
    public HasFace getCharacter(int index) {
        if ((NumPlayers == 1) && (index > 0)) {
            index++;
        }
        switch (index) {
            case 0:
                return Player1.Hero;
            case 1:
                return Player2.Hero;
            case 2:
                return Rival;
            case 3:
                return Enemy.Rip;
            case 4:
                return Enemy.Shred;
        }
        return null;
    }
    
    
    public boolean canAdvancePlanet() {
        if (Player1.Points >= Rival.getPointsRequired()) {
            return true;
        } else {
            return false;
        }
    }
    
    public void advancePlanet() {
        if (Division == Division.B) {
            Division = Division.A;
        } else {
            int ordinal = Rival.ordinal();
            Rival = Enemy.All[ordinal+1];
            Division = Division.B;
        }
        Player1.Points = 0;
    }
}
