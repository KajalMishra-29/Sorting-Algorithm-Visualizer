package algorithms;

import controllers.SortingAlgorithm;
import controllers.SortingPanel;
import controllers.SortingVisualizer;

import java.awt.*;
import java.util.Arrays;

public class SelectionSort implements SortingAlgorithm {
    private final int[] array;
    private final SortingPanel panel;
    private volatile boolean isSorting = true;
    private final SortingVisualizer sv;
    private final Color[] colors;
    private final Color orange = new Color(236,143,28);
    private final Color green = new Color(58, 204, 90);
    private final Color blue = new Color(20,30,70);
    private final Color red = new Color(184, 45, 45);  // For highlighting the current minimum

    public SelectionSort(int[] array, SortingPanel panel, SortingVisualizer sv){
        this.array = array;
        this.panel = panel;
        this.colors = new Color[array.length];
        this.sv = sv;
        Arrays.fill(colors, blue);
        panel.setColors(colors);

        sv.removeCountingPanel();
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
            for(int i=0; i<array.length && isSorting; i++) {

                // highlight the current index
                if(isSorting){
                    colors[i] = orange;
                    panel.setColors(colors);
                    panel.repaint();
                    delay();
                }

                int min = Integer.MAX_VALUE;
                int minIdx = i;
                colors[minIdx] = red;
                for (int j = i + 1; j < array.length && isSorting; j++) {
                    // highlight the bar being compared
                    colors[j] = orange;
                    panel.setColors(colors);
                    panel.repaint();
                    delay();

                    if (array[j] < min) {
                        // reset previous min colur
                        colors[minIdx] = blue;
                        minIdx = j; // update new min
                        min = array[j];
                        colors[minIdx] = red; // highlight the new min
                    }else{
                        colors[j] = blue; // Reset color if it's not the new minimum
                    }
                    panel.setColors(colors);
                    panel.repaint();
                    delay();
                }
                // swap minIdx & i
                int temp = array[i];
                array[i] = array[minIdx];
                array[minIdx] = temp;

                // reset colors : set sorted elemnts to green and reset compared elements
                if(isSorting){
                    colors[minIdx] = blue;
                    colors[i] = green;
                    panel.setColors(colors);
                    panel.repaint();
                    delay();
                }
            }
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

