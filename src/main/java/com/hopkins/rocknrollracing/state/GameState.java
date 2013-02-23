/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

import com.hopkins.rocknrollracing.state.race.RaceResult;

/**
 *
 * @author ian
 */
public class GameState {
    public Difficulty GameDifficulty;
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
        GameDifficulty = GameDifficulty.Rookie;
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
    
    public UpgradeState getRivalUpgradeState() {
        int parts = 0;
        int charges = 0;
        if (GameDifficulty == Difficulty.Rookie) {
            parts = 0;
            charges = 1;
        }
        if (GameDifficulty == Difficulty.Veteran) {
            parts = 1;
            charges = 3;
        }
        if (GameDifficulty == Difficulty.Warrior) {
            parts = 2;
            charges = 5;
        }
        if (Division == Division.A) {
            parts += 1;
            charges += 2;
        }
        UpgradeState us = new UpgradeState();
        us.setLevels(UpgradeType.Charges, charges);
        us.setLevels(UpgradeType.Parts, parts);
        return us;
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
