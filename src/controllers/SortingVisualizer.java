package controllers;

import algorithms.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SortingVisualizer extends JFrame implements ActionListener {

    private final SortingPanel sortingPanel;
    private final JButton startSorting;
    private final JButton generateNewArr;
    private final JComboBox<String> algorithmSelector, speedSelector;
    private final JTextField arrayLength;
    private SortingAlgorithm currentSortingAlgo;
    private final CountingPanel countingPanel;

    public SortingVisualizer(){
        setTitle("Sorting Algorithm Visualizer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1150,760);
        setLayout(null);
        setLocation(200,60);

        // Obtain the graphics device to set full screen
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

        JLabel l1 = new JLabel("Enter bars : ");
        l1.setFont(new Font("Courier New", Font.BOLD, 19));
        l1.setBounds(60,650,150,30);
        add(l1);

        arrayLength = new JTextField("50");
        arrayLength.setFont(new Font("Courier New", Font.BOLD, 17));
        arrayLength.setBounds(200,650,50,30);
        add(arrayLength);

        sortingPanel = new SortingPanel();
        sortingPanel.setBounds(20,70,1100,550);
        add(sortingPanel);

        startSorting = new JButton("Start Sorting");
        startSorting.setFont(new Font("Courier New", Font.BOLD, 15));
        startSorting.setBounds(300, 650,150,30);
        startSorting.setFocusPainted(false);
        startSorting.addActionListener(this);
        add(startSorting);

        generateNewArr = new JButton("Generate New Array");
        generateNewArr.setFont(new Font("Courier New", Font.BOLD, 15));
        generateNewArr.setBounds(470, 650,200,30);
        generateNewArr.setFocusPainted(false);
        generateNewArr.addActionListener(this);
        add(generateNewArr);

        algorithmSelector = new JComboBox<>(new String[]
                {"Bubble Sort","Insertion Sort","Selection Sort", "Counting Sort",
                        "Merge Sort","Quick Sort",
                        "Radix Sort"
                });
        algorithmSelector.setBounds(690,650,200,30);
        algorithmSelector.setFont(new Font("Courier New",Font.BOLD,15));
        add(algorithmSelector);

        speedSelector = new JComboBox<>(new String[]{
                "Very Slow", "Slow","Normal","Fast","Very Fast"
        });
        speedSelector.setSelectedIndex(2);
        speedSelector.setBounds(910,650,130,30);
        speedSelector.setFont(new Font("Courier New",Font.BOLD,15));
        add(speedSelector);

        // initialize counting panel
        countingPanel = new CountingPanel();

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == generateNewArr){
            if(currentSortingAlgo != null){
                currentSortingAlgo.stopSorting();
            }
            int num = Integer.parseInt(arrayLength.getText());
            sortingPanel.generateNewArray(num);
        }
        if(ae.getSource() == startSorting){
            if(currentSortingAlgo != null){
                currentSortingAlgo.stopSorting();
            }
            startSorting();
        }
    }

    private void startSorting(){
        sortingPanel.generateNewArray(Integer.parseInt(arrayLength.getText()));
        String algorithm = (String)algorithmSelector.getSelectedItem();

        assert algorithm != null;
        if(algorithm.equals("Bubble Sort")){
            currentSortingAlgo = new BubbleSort(sortingPanel.getArray(), sortingPanel, this); // dynamically retrieve the updated speed value from controllers.SortingVisualizer in algorithms.BubbleSort you can pass a reference to the controllers.SortingVisualizer instance to the algorithms.BubbleSort class
        }else if(algorithm.equals("Insertion Sort")){
            currentSortingAlgo = new InsertionSort(sortingPanel.getArray(), sortingPanel, this);
        }else if(algorithm.equals("Selection Sort")){
            currentSortingAlgo = new SelectionSort(sortingPanel.getArray(), sortingPanel, this);
        }else if(algorithm.equals("Counting Sort")){
            currentSortingAlgo = new CountingSort(sortingPanel.getArray(), sortingPanel, countingPanel, this);
        }else if(algorithm.equals("Radix Sort")){
            currentSortingAlgo = new RadixSort(sortingPanel.getArray(), sortingPanel, countingPanel, this);
        }else if(algorithm.equals("Merge Sort")){
            currentSortingAlgo = new MergeSort(sortingPanel.getArray(), sortingPanel, countingPanel, this);
        }else if(algorithm.equals("Quick Sort")){
            currentSortingAlgo = new QuickSort(sortingPanel.getArray(), sortingPanel, countingPanel, this);
        }

        // sorting function
        if(currentSortingAlgo != null){
            currentSortingAlgo.sort();
        }
    }

    public int speedSelector(){
        String speed = (String) speedSelector.getSelectedItem();
        int speedVal = switch (speed) {
            case "Normal" -> 100;
            case "Slow" -> 160;
            case "Very Slow" -> 220;
            case "Fast" -> 70;
            case "Very Fast" -> 40;
            default -> 90;
        };
        return speedVal;
    }

    public void addCountingPanel(){
        countingPanel.setBounds(50,0,1100,100);
        add(countingPanel);
        countingPanel.setVisible(true);
        revalidate(); // refresh the frame to show new component
        repaint(); // repaint the frame
    }
    public void removeCountingPanel(){
        remove(countingPanel);
        countingPanel.setVisible(false);
        revalidate();
        repaint();
    }
    public static void main(String[] args) {
        new SortingVisualizer();
    }
}
