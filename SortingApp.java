
public class SortingApp {
	import javax.swing.*;
	import java.awt.*;
	import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.io.File;
	import java.util.Scanner;

	public class SortingApp extends JFrame {
	    private JButton chooseFileButton;
	    private JButton sortButton;
	    private JComboBox<String> sortMethodComboBox;
	    private JLabel executionTimeLabel;
	    private File selectedFile;
	    private long[] numbers;

	    public SortingApp() {
	        setTitle("Sorting Algorithms");
	        setSize(400, 300);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);

	        JPanel panel = new JPanel();
	        panel.setLayout(new GridLayout(4, 1));

	        sortMethodComboBox = new JComboBox<>(new String[]{"Insertion Sort", "Quick Sort", "Bogo Sort"});
	        panel.add(sortMethodComboBox);

	        chooseFileButton = new JButton("Escolher arquivo");
	        chooseFileButton.addActionListener(new ChooseFileButtonListener());
	        panel.add(chooseFileButton);

	        sortButton = new JButton("Ordenar");
	        sortButton.addActionListener(new SortButtonListener());
	        panel.add(sortButton);

	        executionTimeLabel = new JLabel("Tempo de execução: ");
	        panel.add(executionTimeLabel);

	        add(panel);
	    }

	    private class ChooseFileButtonListener implements ActionListener {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            JFileChooser fileChooser = new JFileChooser();
	            int result = fileChooser.showOpenDialog(null);
	            if (result == JFileChooser.APPROVE_OPTION) {
	                selectedFile = fileChooser.getSelectedFile();
	                try (Scanner scanner = new Scanner(selectedFile)) {
	                    StringBuilder content = new StringBuilder();
	                    while (scanner.hasNextLine()) {
	                        content.append(scanner.nextLine()).append(" ");
	                    }
	                    String[] numberStrings = content.toString().split("\\s+");
	                    numbers = new long[numberStrings.length];
	                    for (int i = 0; i < numberStrings.length; i++) {
	                        numbers[i] = Long.parseLong(numberStrings[i]);
	                    }
	                } catch (Exception ex) {
	                    JOptionPane.showMessageDialog(null, "Erro ao ler o arquivo.");
	                }
	            }
	        }
	    }

	    private class SortButtonListener implements ActionListener {
	        @Override
	        public void actionPerformed(ActionEvent e) {
	            if (numbers == null) {
	                JOptionPane.showMessageDialog(null, "Escolha um arquivo primeiro.");
	                return;
	            }

	            String selectedMethod = (String) sortMethodComboBox.getSelectedItem();
	            long startTime = System.currentTimeMillis();

	            if ("Insertion Sort".equals(selectedMethod)) {
	                insertionSort(numbers.clone());
	            } else if ("Quick Sort".equals(selectedMethod)) {
	                quickSort(numbers.clone(), 0, numbers.length - 1);
	            }

	            long endTime = System.currentTimeMillis();
	            executionTimeLabel.setText("Tempo de execução: " + (endTime - startTime) + " ms");
	        }
	    }

	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            new SortingApp().setVisible(true);
	        });
	    }

	    public static void insertionSort(long[] array) {
	        for (int i = 1; i < array.length; ++i) {
	            long key = array[i];
	            int j = i - 1;

	            while (j >= 0 && array[j] > key) {
	                array[j + 1] = array[j];
	                j = j - 1;
	            }
	            array[j + 1] = key;
	        }
	    }

	    public static void quickSort(long[] array, int low, int high) {
	        if (low < high) {
	            int pi = partition(array, low, high);
	            quickSort(array, low, pi - 1);
	            quickSort(array, pi + 1, high);
	        }
	    }

	    private static int partition(long[] array, int low, int high) {
	        long pivot = array[high];
	        int i = (low - 1);
	        for (int j = low; j < high; j++) {
	            if (array[j] < pivot) {
	                i++;
	                long temp = array[i];
	                array[i] = array[j];
	                array[j] = temp;
	            }
	        }
	        long temp = array[i + 1];
	        array[i + 1] = array[high];
	        array[high] = temp;

	        return i + 1;
	    }

	    private static boolean isSorted(long[] array) {
	        for (int i = 1; i < array.length; i++) {
	            if (array[i - 1] > array[i]) {
	                return false;
	            }
	        }
	        return true;
	    }

	    private static void shuffleArray(long[] array) {
	        for (int i = array.length - 1; i > 0; i--) {
	            int index = (int) (Math.random() * (i + 1));
	            long temp = array[index];
	            array[index] = array[i];
	            array[i] = temp;
	        }
	    }
	}
}
