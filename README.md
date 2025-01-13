# Sorting-Algorithm-Visualizer
This is an interactive Java application that demonstrate various sorting algorithms through graphical interface. It is built using Java, Swing and AWT, this tool offers a clear, step-by-step visualization of how different sorting methods operate, making it an excellent resource for learning and teaching.
<br><br>

## FEATURES  
1. Visualization of multiple sorting algorithms including :
   <ul>     
      <li>Bubble Sort</li>           
      <li>Insertion Sort</li>          
      <li>Selection Sort</li>            
      <li>Counting Sort</li>     
      <li>Merge Sort</li>         
      <li>Quick Sort</li>              
      <li>Radix Sort</li>
   </ul>
2. Real-time graphical representation of sorting process.
3. Adjustable sorting speed.
4. Highlighting of active elements during the sorting process with different colors.             
<br><br>

## TECHNOLOGIES USED      
JAVA : core language used for implementing sorting algorithms ans visualizations.            
SWING and AWT : used for creating the GUI           
<br><br>

## FILE STRUCTURE 
-> CONTROLLERS   
<ul>
   <li>SortingVisualizer.java  :-  Main class to run the sorting visualizer</li>
   <li>SortingPanel.java  :-  panel to visualize sorting process</li>
   <li>CountingPanel.java  :-  Panel to show frequency of elements (used in counting sort)</li>
   <li>SortingAlgorithm.java  :-  Interface for sorting algorithms</li>
</ul>

-> ALGORITHMS
<ul>
   <li>BubbleSort.java</li>
   <li>InsertionSort.java</li>
   <li>SelectionSort.java</li>
   <li>CountingSort.java</li>
   <li>MergeSort.java</li>
   <li>QuickSort.java</li>
   <li>RadixSort.java</li>
</ul>
<br><br>

## HOW TO RUN

### Via command line      
1. clone the project repository to your local machine                 
   ```bash
   git clone https://github.com/KajalMishra-29/Sorting-Algorithm-Visualizer.git
   ```
   
3. navigate to project directory             
   ```bash
   cd Sorting-Algorithm-Visualizer
   ```

4. compile the code
   ```bash
   javac -d bin src/controllers/*.java src/algorithms/*.java
   ```
   
6. run the SortingVisualizer class to launch the application                         
   ```bash
   java -cp bin controllers.SortingVisualizer
   ```

### Run from an IDE 
1. Import the project into your IDE        
2. Run the SortingVisualizer.java class directly
<br><br>


## SCREENSHORTS

![image](https://github.com/user-attachments/assets/e7597c2e-33a5-4c88-9a64-13189d9cef57)

![image](https://github.com/user-attachments/assets/7d5f5e0c-8d39-4bad-a104-17093fb07c84)

![image](https://github.com/user-attachments/assets/2ca667b9-1c40-43a7-9855-c595dbf69cac)

![image](https://github.com/user-attachments/assets/1e86e9fc-4150-41c7-b4da-3cb27d93767b)







   



          
















