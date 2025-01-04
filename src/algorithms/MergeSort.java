package algorithms;

import controllers.CountingPanel;
import controllers.SortingAlgorithm;
import controllers.SortingPanel;
import controllers.SortingVisualizer;

import java.awt.*;
import java.util.Arrays;

public class MergeSort implements SortingAlgorithm {
    private final int[] array;
    private final SortingPanel panel;
    private volatile boolean isSorting = true;
    private final SortingVisualizer sv;
    private final Color[] colors;
    private final Color orange = new Color(236,143,28);
    private final Color lightGreen = new Color(154, 168, 113, 226);
    private final Color green = new Color(58,204,60);
    private final Color blue = new Color(20,30,70);

    public MergeSort(int[] array, SortingPanel panel, CountingPanel countingPanel, SortingVisualizer sv){
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
        new Thread(() -> mergeSort(array, 0, array.length - 1)).start();
    }

    private void mergeSort(int[] array, int left, int right) {
        if(left >= right) return;
        if (isSorting) {
            int mid = (left + right) / 2;
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);
            merge(array, left, mid, right);

            for (int i = left; i <= right && isSorting; i++) {
                colors[i] = green; // Mark as sorted in this section
                panel.setColors(colors);
                panel.repaint();
                try {Thread.sleep(10);}
                catch (InterruptedException e){ Thread.currentThread().interrupt();}
            }
        }
    }

    private void merge(int[] array, int left, int mid, int right) {
        int t1 = left;
        int t2 = mid + 1;
        int[] temp = new int[right - left + 1];
        int t = 0;

        while (t1 <= mid && t2 <= right && isSorting) {
            colors[t1] = orange; // Highlight left element being compared
            colors[t2] = orange; // Highlight right element being compared
            panel.setColors(colors);
            panel.repaint();
            delay();

            if (array[t1] <= array[t2]) {
                temp[t++] = array[t1++];
            } else {
                temp[t++] = array[t2++];
            }

            if(t1 > left && isSorting) colors[t1 - 1] = lightGreen; // Reset color of left element after comparison
            if(t2 > mid+1 && isSorting) colors[t2 - 1] = lightGreen; // Reset color of right element after comparison
        }

        while (t1 <= mid && isSorting) {
            temp[t++] = array[t1++];
            colors[t1-1] = lightGreen; // Highlight remaining left elements to be merged
            panel.setColors(colors);
            panel.repaint();
            delay();
        }

        while (t2 <= right && isSorting) {
            temp[t++] = array[t2++];
            colors[t2-1] = lightGreen; // Highlight remaining right elements to be merged
            panel.setColors(colors);
            panel.repaint();
            delay();
        }

        // Copy sorted temp array back into original array
        for(int i=left; i<=right; i++){
            array[i] = temp[i-left];
        }
    }

    private void delay() {
        try {
            Thread.sleep(sv.speedSelector());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
