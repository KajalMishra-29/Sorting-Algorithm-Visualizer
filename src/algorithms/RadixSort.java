package algorithms;

import controllers.CountingPanel;
import controllers.SortingAlgorithm;
import controllers.SortingPanel;
import controllers.SortingVisualizer;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class RadixSort implements SortingAlgorithm {
    private final int[] array;
    private final SortingPanel panel;
    private volatile boolean isSorting = true;
    private final SortingVisualizer sv;
    private final Color[] colors;
    private final Color orange = new Color(236,143,28);
    private final Color green1 = new Color(154, 168, 113, 226);
    private final Color green2 = new Color(119, 225, 124, 255);
    private final Color green = new Color(60, 202, 90);
    private final Color blue = new Color(20,30,70);

    public RadixSort(int[] array, SortingPanel panel, CountingPanel countingPanel, SortingVisualizer sv){
        this.array = array;
        this.panel = panel;
        this.colors = new Color[array.length];
        this.sv = sv;
        Arrays.fill(colors, blue);
        panel.setColors(colors);

        sv.removeCountingPanel();
    }

    @Override
    public void  stopSorting(){
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
            int place = 1;
            while(max/place > 0 && isSorting){
                countingSort(array, place);

                // update color for sorted half sorted
                if(isSorting){
                    if(place == 1) Arrays.fill(colors, green1);
                    if(place == 10) Arrays.fill(colors, green2);
                }
                panel.setColors(colors);
                panel.repaint();
                delay();

                place *= 10;
            }
            // color final sorted array
            if(isSorting){
                Arrays.fill(colors, green);
                panel.setColors(colors);
            }
            panel.repaint();
        }).start();
    }
    public void countingSort(int[] array, int place){
        int[] count = new int[10];
        int[] output = new int[array.length];
        // count occurrences of each digit
        for (int i=0; i<array.length && isSorting; i++) {
            int digit = (array[i] / place) % 10;
            count[digit]++;

            // highlight the current element being placed
            colors[i] = orange; // current element being processed
            panel.setColors(colors);
            panel.repaint();
            delay();

            // reset color
            if(place == 1 && isSorting) colors[i] = blue;
            else if(place == 10 && isSorting) colors[i] = green1;
            else if(place == 100 && isSorting) colors[i] = green2;

            panel.setColors(colors);
            panel.repaint();
            delay();
        }
        // accumulate count
        for(int i=1; i<count.length && isSorting; i++){
            count[i] += count[i-1];
        }
        // build the output array
        for (int i=array.length-1; i>=0 && isSorting; i--) {
            int digit = (array[i] / place) % 10;
            output[count[digit] - 1] = array[i];
            count[digit]--;
        }
        System.arraycopy(output,0,array,0,array.length);
    }

    public void delay(){
        try{
            Thread.sleep(sv.speedSelector());
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
