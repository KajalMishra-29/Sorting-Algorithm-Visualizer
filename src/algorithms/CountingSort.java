package algorithms;

import controllers.CountingPanel;
import controllers.SortingAlgorithm;
import controllers.SortingPanel;
import controllers.SortingVisualizer;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Random;

public class CountingSort implements SortingAlgorithm {
    private final int[] array;
    private final SortingPanel panel;
    private final SortingVisualizer sv;
    private volatile boolean isSorting = true;
    private final CountingPanel countingPanel;
    private final Color[] colors;
    private final Color red = new Color(255, 69, 69);
    private final Color blue = new Color(20,30,70);
    private final Color color1 = new Color(143, 151, 121);
    private final Color color2 = new Color(135, 169, 107);
    private final Color color3 = new Color(80, 200, 120);
    private final Color color4 = new Color(34, 139, 34);
    private final Color color5 = new Color(79, 121, 66);
    private final Color color6 = new Color(134, 200, 53);
    private final Color color7 = new Color(73, 121, 107);
    private final Color color8 = new Color(0, 73, 83);

    public CountingSort(int[] array, SortingPanel sortingPanel, CountingPanel countingPanel, SortingVisualizer sv){
        this.array = array;
        this.panel = sortingPanel;
        this.sv = sv;
        this.countingPanel = countingPanel;
        colors = new Color[array.length];

        // modify array values for counting sort
        int[] randomValues = {100,200,250,300,350,400,450,500};
        Random ran = new Random();
        for(int i=0; i<array.length; i++){
            int randIdx = ran.nextInt(randomValues.length); // 0 to randomValues.length-1
            array[i] = randomValues[randIdx];
        }

        countingPanel.resetCounts(); // reset
        sv.addCountingPanel(); // add counting panel

        Arrays.fill(colors,blue);
        panel.setColors(colors);
    }

    @Override
    public void stopSorting() {
        isSorting = false;
        Thread.currentThread().interrupt();
        Arrays.fill(colors, blue);
        panel.setColors(colors);
        panel.repaint();
    }

    @Override
    public void sort() {

        new Thread(()->{
            int max = Arrays.stream(array).max().getAsInt();
            int[] count = new int[max+1];
            // count frequency
            for (int i=0; i<array.length && isSorting; i++) {
                count[array[i]]++;
                countingPanel.incrementCount(array[i]);
                delay();

                colors[i] = red; // mark current bar
                panel.setColors(colors);
                panel.repaint();
                delay();

                // assign color based on values
                switch (array[i]){
                    case 100 -> colors[i] = color1;
                    case 200 -> colors[i] = color2;
                    case 250 -> colors[i] = color3;
                    case 300 -> colors[i] = color4;
                    case 350 -> colors[i] = color5;
                    case 400 -> colors[i] = color6;
                    case 450 -> colors[i] = color7;
                    case 500 -> colors[i] = color8;
                    default -> colors[i] = blue;
                }
                panel.setColors(colors);
                panel.repaint();
                delay();
            }
            // sort array
            int idx = 0;
            for(int i=0; i<count.length && isSorting; i++){
                while(count[i] != 0){
                    array[idx] = i;
                    count[i]--;
                    // decrement count
                    countingPanel.decrementCount(i);

                    // update color of each sorted position
                    colors[idx] = red;
                    panel.setColors(colors);
                    panel.repaint();
                    delay();

                    // restore original color based on sorted value
                    switch(array[idx]){
                        case 100 -> colors[idx] = color1;
                        case 200 -> colors[idx] = color2;
                        case 250 -> colors[idx] = color3;
                        case 300 -> colors[idx] = color4;
                        case 350 -> colors[idx] = color5;
                        case 400 -> colors[idx] = color6;
                        case 450 -> colors[idx] = color7;
                        case 500 -> colors[idx] = color8;
                        default -> colors[idx] = blue;
                    }
                    idx++;
                }
            }
            if(isSorting) panel.setColors(colors);
            panel.repaint();
        }).start();
    }

    public void delay(){
        try{
            Thread.sleep(sv.speedSelector());
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
