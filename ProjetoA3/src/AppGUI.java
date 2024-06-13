import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AppGUI extends JFrame {

    private JTextArea textArea;
    private JTextField searchField;
    private JLabel searchResultLabel;
    private String nomeArquivo = "ProjetoA3//src//files//NumerosOrdenarArquivo.txt";

    public AppGUI() {
        setTitle("Ordenar Números");
        setSize(600, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        textArea = new JTextArea();
        textArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 5));

        JButton reloadButton = new JButton("Recarregar Lista");
        reloadButton.addActionListener(e -> updateTextArea(lerArquivo(nomeArquivo)));
        buttonPanel.add(reloadButton);

        JButton insertionSortButton = new JButton("Insertion Sort");
        insertionSortButton.addActionListener(new SortButtonListener("insertion"));
        buttonPanel.add(insertionSortButton);

        JButton selectionSortButton = new JButton("Selection Sort");
        selectionSortButton.addActionListener(new SortButtonListener("selection"));
        buttonPanel.add(selectionSortButton);

        JButton mergeSortButton = new JButton("Merge Sort");
        mergeSortButton.addActionListener(new SortButtonListener("merge"));
        buttonPanel.add(mergeSortButton);

        JButton quickSortButton = new JButton("Quick Sort");
        quickSortButton.addActionListener(new SortButtonListener("quick"));
        buttonPanel.add(quickSortButton);

        JButton heapSortButton = new JButton("Heap Sort");
        heapSortButton.addActionListener(new SortButtonListener("heap"));
        buttonPanel.add(heapSortButton);

        add(buttonPanel, BorderLayout.SOUTH);

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new FlowLayout());

        searchField = new JTextField(10);
        JButton searchButton = new JButton("Pesquisar");
        searchResultLabel = new JLabel("");

        searchButton.addActionListener(new SearchButtonListener());

        searchPanel.add(new JLabel("Pesquisar:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(searchResultLabel);

        add(searchPanel, BorderLayout.NORTH);

        // Load the initial list of numbers
        updateTextArea(lerArquivo(nomeArquivo));
    }

    private List<String> lerArquivo(String nomeArquivo) {
        List<String> numeros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            int count = 0;
            while ((linha = br.readLine()) != null && count < 100) {
                numeros.add(linha);
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numeros;
    }

    private void updateTextArea(List<String> numeros) {
        StringBuilder sb = new StringBuilder();
        for (String numero : numeros) {
            sb.append(numero).append("\n");
        }
        textArea.setText(sb.toString());
    }

    private class SortButtonListener implements ActionListener {
        private String sortType;

        public SortButtonListener(String sortType) {
            this.sortType = sortType;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> numeros = lerArquivo(nomeArquivo);
            switch (sortType) {
                case "insertion":
                    Ordenar.insertionSort(numeros);
                    break;
                case "selection":
                    Ordenar.selectionSort(numeros);
                    break;
                case "merge":
                    Ordenar.mergeSort(numeros);
                    break;
                case "quick":
                    Ordenar.quickSort(numeros);
                    break;
                case "heap":
                    Ordenar.heapSort(numeros);
                    break;
            }
            updateTextArea(numeros);
        }
    }

    private class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String chave = searchField.getText();
            List<String> numeros = lerArquivo(nomeArquivo);
            int resultado = PesquisaBinaria.pesquisaBinaria(numeros, chave);
            if (resultado != -1) {
                searchResultLabel.setText("Número encontrado na posição: " + resultado);
            } else {
                searchResultLabel.setText("Número não encontrado.");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppGUI app = new AppGUI();
            app.setVisible(true);
        });
    }
}
