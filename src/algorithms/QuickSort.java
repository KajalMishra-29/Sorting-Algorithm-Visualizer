package algorithms;

import controllers.CountingPanel;
import controllers.SortingAlgorithm;
import controllers.SortingPanel;
import controllers.SortingVisualizer;

import java.awt.*;
import java.util.Arrays;

public class QuickSort implements SortingAlgorithm {
    private final int[] array;
    private final SortingPanel panel;
    private volatile boolean isSorting = true;
    private final SortingVisualizer sv;
    private final Color[] colors;
    private final Color red = new Color(255,0,0);
    private final Color yellow = new Color(228, 222, 26);
    private final Color orange = new Color(236,143,28);
    private final Color green = new Color(58,204,60);
    private final Color blue = new Color(20,30,70);

    public QuickSort(int[] array, SortingPanel panel, CountingPanel countingPanel, SortingVisualizer sv){
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
            quickSort(array, 0, array.length-1);
        }).start();
    }

    public void quickSort(int[] array, int low, int high){
        if(low >= high) return;

        if(isSorting){
            int pivotIdx = partition(array, low, high);

            // mark elements as sorted after partition
            if(isSorting){
                colors[pivotIdx] = green;
                panel.setColors(colors);
                panel.repaint();
                delay();
            }

            quickSort(array, low, pivotIdx-1);
            quickSort(array,pivotIdx+1, high);

            // Update colors to indicate that all elements in the range [low, high] are sorted
            for (int i = low; i <= high &&isSorting; i++) {
                colors[i] = green; // Color for sorted bars
            }
            panel.setColors(colors);
            panel.repaint();
        }
    }

    public int partition(int[] array, int low, int high){
        int pivot = array[low];
        int i = low;
        int j = high;

        // highlight the pivot
        if(isSorting){
            colors[low] = red;
            panel.setColors(colors);
            panel.repaint();
            delay();
        }

        while(i < j && isSorting){ // stop when j crosses i
            //move i till element greater than pivot
            while(i < high && array[i] <= pivot && isSorting){
                // highlight elements being compared
                if(colors[i] != red){
                    colors[i] = yellow;
                    panel.setColors(colors);
                    panel.repaint();
                    delay();

                    colors[i] = blue; // reset to unsorted blue
                    panel.setColors(colors);
                    panel.repaint();
                    delay();
                }
                i++;
            }
            if(isSorting){
                colors[i] = yellow;
                panel.setColors(colors);
                panel.repaint();
                delay();
            }

            // move j till element lesser than pivot is found
            while(j > low && array[j] > pivot && isSorting){
                // highlight elements being compared
                colors[j] = orange;
                panel.setColors(colors);
                panel.repaint();
                delay();
                // reset
                colors[j] = blue;
                panel.setColors(colors);
                panel.repaint();
                delay();
                j--;
            }
            if(isSorting){
                colors[j] = orange;
                panel.setColors(colors);
                panel.repaint();
                delay();
            }

            // swap a[i] & a[j] if i and j have not crosses
            if(i < j && isSorting){
                int temp = array[i];
                array[i] = array[j];
                array[j] = temp;

                // reset colors
                colors[i] = blue;
                colors[j] = blue;
                panel.setColors(colors);
                panel.repaint();
            }
        }
        // swap pivot to its correct position
        int temp = array[low];
        array[low] = array[j];
        array[j] = temp;

        // reset the pivot color and mark it as sorted
        if(isSorting){
            colors[low] = blue;
            colors[j] = green;
            panel.setColors(colors);
            panel.repaint();
            delay();
        }
        return j;
    }

    public void delay(){
        try{
            Thread.sleep(sv.speedSelector());
        }catch (InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
