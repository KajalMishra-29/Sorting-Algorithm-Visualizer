package controllers;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class SortingPanel extends JPanel {
    private static final int Default_array_length = 50;
    private static final int GAP = 2;
    private int[] array;
    private Color[] colors;

    public SortingPanel(){
        generateNewArray(Default_array_length);
        // initialize colors array
        colors = new Color[array.length];
        Arrays.fill(colors, new Color(20, 30, 70));
    }

    public int[] getArray(){
        return array;
    }

    public void setColors(Color[] colors){
        this.colors = colors;
        repaint();
    }

    public void generateNewArray(int n){
        if(n < 20) n = 20;
        if(n > 120) n = 120;
        Random random = new Random();
        array = new int[n];
        for(int i=0; i<array.length; i++){
            array[i] = random.nextInt(500) + 30;
        }
        // Reset colors after generating new array
        colors = new Color[array.length];
        Arrays.fill(colors, new Color(20,30,70));
        repaint();
    }

    @Override
    // repaint() invokes paintComponent
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (array == null) return;
        int width = getWidth();
        int height = getHeight();
        int totalGapWidth = (array.length - 1) * GAP;
        int barWidth = (width - totalGapWidth) / array.length;

        if(array.length > 70){ // greater than 70 bars show only color
            for(int i=0; i<array.length; i++){
                int barHeight = array[i];
                g.setColor(colors[i]);
                g.fillRect(i*(barWidth+GAP), height-barHeight, barWidth, barHeight);
            }
        }else{ // if less than 70 bars -> show text
            FontMetrics fm = g.getFontMetrics(); // For text measurements
            for(int i=0; i<array.length; i++){
                int barHeight = array[i];
                int x = i*(barWidth+GAP);
                int y = height - barHeight;

                // set color for the bars
                g.setColor(colors[i]);
                g.fillRect(x,y,barWidth,barHeight);

                // prepare text details
                String text = String.valueOf(array[i]);
                int textWidth = fm.stringWidth(text);
                int textHeight = fm.getAscent();

                // calculate position for vertical test
                int textX = x + (barWidth/2);
                int textY = y + (barHeight/2);

                // rotate graphics for vertical text
                Graphics2D g2d = (Graphics2D) g;
                g2d.rotate(-Math.PI/2, textX, textY);

                // draw text on top of bar
                g.setColor(Color.WHITE);
                g.drawString(text, textX-(textWidth/2), textY + (textHeight / 2));

                // reset rotation
                g2d.rotate(Math.PI/2 , textX, textY);
            }
        }
    }
}
