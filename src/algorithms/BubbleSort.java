package algorithms;

import controllers.SortingVisualizer;
import controllers.SortingPanel;
import controllers.SortingAlgorithm;

import java.awt.Color;
import java.util.Arrays;

public class BubbleSort implements SortingAlgorithm {
    private final int[] array;
    private final SortingPanel panel;
    private volatile boolean isSorting = true;
    private final SortingVisualizer sv; // Reference to controllers.SortingVisualizer
    private final Color[] colors;
    private final Color orange = new Color(236, 143, 28);
    private final Color green = new Color(58, 204, 60);
    private final Color blue = new Color(20,30,70);


    public BubbleSort(int[] array, SortingPanel panel, SortingVisualizer sv){
        this.array = array;
        this.panel = panel;
        this.colors = new Color[array.length];
        this.sv = sv; // Assign controllers.SortingVisualizer reference
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
           boolean swapped = false;
           for(int i=0; i<array.length-1 && isSorting; i++){
               if(!isSorting){
                   break; // check if sorting should be stopped
               }
               for(int j=0; j< array.length-i-1 && isSorting; j++){
                   // highlight bars being compared
                   colors[j] = orange;
                   colors[j+1] = orange;
                   panel.setColors(colors);
                   panel.repaint();
                   delay();

                   // swap
                   if(array[j] > array[j+1]){
                       int temp = array[j];
                       array[j] = array[j+1];
                       array[j+1] = temp;
                       swapped = true;
                   }
                   // reset colors back to blue after comparison
                   colors[j] = blue;
                   colors[j+1] = blue;
                   panel.setColors(colors);
                   panel.repaint();
                   delay();
               }

               // Color sorted elements
               if(isSorting){
                   colors[array.length-i-1] = green;
                   panel.setColors(colors);
                   panel.repaint();
               }

               if(!swapped) break;

           }
           // Final pass to color all bars green
           for (int i = 0; i < array.length && isSorting; i++) {
               colors[i] = green;
           }

           panel.setColors(colors);
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
