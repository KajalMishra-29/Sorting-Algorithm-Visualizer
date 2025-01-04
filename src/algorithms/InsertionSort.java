package algorithms;

import controllers.SortingVisualizer;
import controllers.SortingPanel;
import controllers.SortingAlgorithm;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.Arrays;

public class InsertionSort implements SortingAlgorithm {

    private final int[] array;
    private final SortingPanel panel;
    private volatile boolean isSorting = true;
    private final SortingVisualizer sv;
    private final Color[] colors;
    private final Color orange = new Color(236,143,28);
    private final Color green = new Color(58, 204, 90);
    private final Color lightGreen = new Color(154, 168, 113, 226);
    private final Color blue = new Color(20,30,70);

    public InsertionSort(int[] array, SortingPanel panel, SortingVisualizer sv){
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
            for(int i=0; i<array.length && isSorting; i++){

                if(!isSorting) break;

                int j = i;
                while(j>0 && array[j-1] > array[j] && isSorting){
                    // highlight the bars being compared
                    colors[j] = orange;
                    colors[j-1] = orange;
                    panel.setColors(colors);
                    panel.repaint();

                    // delay
                    delay();

                    // swap a[j-1] & a[j]
                    int temp = array[j-1];
                    array[j-1] = array[j];
                    array[j]= temp;
                    j--;

                    // reset colors to lightgreen after swap
                    if(isSorting){
                        colors[j] = lightGreen;
                        colors[j+1] = lightGreen;
                        panel.setColors(colors);
                        panel.repaint();
                        delay();
                    }
                }
                // color the sorted elements
                if(isSorting){
                    for(int k=0; k<=i; k++){
                        colors[k] = green;
                    }
                    panel.setColors((colors));
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
