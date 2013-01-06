/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hopkins.rocknrollracing.state;

/**
 *
 * @author ian
 */
public class CarColor extends NamedModel {
    
    public static final CarColor Black = new CarColor("Black", 0xff787878, 0xff505050, 0xff282828, 0xff202020, 0xff101010);
    public static final CarColor Blue = new CarColor("Blue",  0xff98C8F8, 0xff6888D8, 0xff4050C0, 0xff2020A0, 0xff2020A0);
    public static final CarColor Red = new CarColor("Red", 0xffF898C0, 0xffD83858, 0xffB00000, 0xff800000, 0xff580000);
    public static final CarColor Green = new CarColor("Green",  0xff30C828, 0xff10A810, 0xff008000, 0xff005800, 0xff002800);
    public static final CarColor Yellow = new CarColor("Yellow",  0xffF8F8F8, 0xffF0F078, 0xffE8E800, 0xffC8A000, 0xffA86000);
    public static final CarColor Purple = new CarColor("Purple",  0xff903898, 0xff801870, 0xff680048, 0xff480028, 0xff480028);
    public static final CarColor Gray = new CarColor("Gray",  0xffB8B8B8, 0xff909890, 0xff707070, 0xff485048, 0xff282828);
    public static final CarColor Orange = new CarColor("Orange", 0xffD0B800, 0xffC08000, 0xffB85000, 0xff803000, 0xff481800);
    
    public static final CarColor[] All = new CarColor[] {
        Black, Blue, Red, Green, Yellow, Purple, Gray, Orange
    };
            
    protected int[] colors;
    
    public int[] getColors() {
        return colors;
    }
    
    public CarColor(String name, int color1, int color2, int color3, int color4, int color5) {
      super(name);
      this.colors = new int[] {color1, color2, color3, color4, color5};
    }
}
