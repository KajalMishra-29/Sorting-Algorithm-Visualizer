package controllers;

import javax.swing.*;
import java.awt.*;
import java.util.TreeMap;
import java.util.Map;

public class CountingPanel extends JPanel {
    private final TreeMap<Integer, Integer> countMap;
    private Integer currentlyUpdatedKey = null;

    public CountingPanel(){
        countMap = new TreeMap<>();
        countMap.put(100,0);
        countMap.put(200,0);
        countMap.put(250,0);
        countMap.put(300,0);
        countMap.put(350,0);
        countMap.put(400,0);
        countMap.put(450,0);
        countMap.put(500,0);

        setSize(1100, 100);
    }

    // update count
    public void incrementCount(int val){
        countMap.put(val, countMap.getOrDefault(val,0)+1);
        currentlyUpdatedKey = val;
        repaint();
    }
    public void decrementCount(int val){
        countMap.put(val, countMap.getOrDefault(val,0)-1);
        currentlyUpdatedKey = val;
        repaint();
    }

    // reset counts for each new sort
    public void resetCounts(){
        countMap.put(100,0);
        countMap.put(200,0);
        countMap.put(250,0);
        countMap.put(300,0);
        countMap.put(350,0);
        countMap.put(400,0);
        countMap.put(450,0);
        countMap.put(500,0);

        currentlyUpdatedKey = null; // clear highlight on reset
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        int xPosition = 10;
        for(Map.Entry<Integer, Integer> entry : countMap.entrySet()){
            int val = entry.getKey();
            int count = entry.getValue();

            if(currentlyUpdatedKey!= null && val == currentlyUpdatedKey){
                g.setColor(Color.RED);  // Border color
                g.drawRect(xPosition - 5, 25, 90, 30);  // Draw border around the text
            }else{
                g.setColor(Color.BLACK);
            }
            g.setFont(new Font("Raleway", Font.BOLD, 22));
            g.drawString(val + " : " + count,xPosition,50);
            xPosition += 135;
        }
    }
}
