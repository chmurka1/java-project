package code.controls;

import java.awt.*;
import java.util.function.Function;


public class FiltersOfColor {

    public static class AdjustBrightness implements Function<Color,Color>{

        int value;

        AdjustBrightness(int value){
           this.value = value;
        }
        @Override
        public Color apply(Color color) {
            int red = Math.max(Math.min(color.getRed()+value, 255),0);
            int green = Math.max(Math.min(color.getGreen()+value, 255),0);
            int blue = Math.max(Math.min(color.getBlue()+value, 255),0);
            return new Color(red,green,blue);
        }
    }
}
