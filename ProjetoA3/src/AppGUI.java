import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
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
    private int numLinhas;

    public AppGUI(int numLinhas) {
        this.numLinhas = numLinhas;
        
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

        JPanel searchPanel = new JPanel(new BorderLayout());

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Pesquisar");
        searchResultLabel = new JLabel("");

        searchButton.addActionListener(new SearchButtonListener(scrollPane));
        leftPanel.add(searchField);
        leftPanel.add(searchButton);

        searchPanel.add(leftPanel, BorderLayout.WEST);
        searchPanel.add(searchResultLabel, BorderLayout.CENTER);

        // Load the icon and resize it for the save button
        ImageIcon disketteIcon = new ImageIcon("ProjetoA3//src//icons//diskette.png");
        Image img = disketteIcon.getImage();
        Image resizedImg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        ImageIcon resizedDisketteIcon = new ImageIcon(resizedImg);

        // Adding the save button with resized diskette icon to the right
        JButton saveButton = new JButton(resizedDisketteIcon);
        searchPanel.add(saveButton, BorderLayout.EAST);

        add(searchPanel, BorderLayout.NORTH);

        // Load the initial list of numbers
        updateTextArea(formatarNumeros(lerArquivo(nomeArquivo)));
    }

    private List<String> lerArquivo(String nomeArquivo) {
        List<String> numeros = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            int count = 0;
            while ((linha = br.readLine()) != null && count < numLinhas) {
                numeros.add(linha);
                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return numeros;
    }

    public List<String> formatarNumeros(List<String> numeros) {
        List<String> numerosFormatados = new ArrayList<>();
        int count = 1;
        for (String numero : numeros) {
            numerosFormatados.add(count + "° " + numero);
            count++;
        }
        return numerosFormatados;
    }

    private void updateTextArea(List<String> numeros) {
        textArea.setText(""); // Limpa o texto anterior
        StringBuilder sb = new StringBuilder();
        for (String numero : numeros) {
            sb.append(numero).append("\n");
        }
        textArea.setText(sb.toString());
    }

    private void highlightAndScrollToLine(JScrollPane scrollPane, int lineIndex) {
        try {
            int startOffset = textArea.getLineStartOffset(lineIndex);
            int endOffset = textArea.getLineEndOffset(lineIndex);

            // Highlight the line
            textArea.getHighlighter().addHighlight(startOffset, endOffset, new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN));

            // Scroll to the line
            Rectangle2D viewRect = textArea.modelToView2D(startOffset);
            if (viewRect != null) {
                scrollPane.getViewport().scrollRectToVisible(viewRect.getBounds());
            }

            // Ensure the text area is updated
            textArea.setCaretPosition(startOffset);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
            updateTextArea(formatarNumeros(numeros));
        }
    }

    private class SearchButtonListener implements ActionListener {
        private JScrollPane scrollPane;

        public SearchButtonListener(JScrollPane scrollPane) {
            this.scrollPane = scrollPane;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String chave = searchField.getText();
            List<String> numeros = lerArquivo(nomeArquivo);
            int resultado = PesquisaBinaria.pesquisaBinaria(numeros, chave);
            if (resultado != -1) {
                // Update the TextArea with formatted numbers
                List<String> numerosFormatados = formatarNumeros(numeros);
                updateTextArea(numerosFormatados);

                // Highlight and scroll to the found line
                highlightAndScrollToLine(scrollPane, resultado);
                searchResultLabel.setText("Número encontrado.");
            } else {
                searchResultLabel.setText("Número não encontrado.");
            }
        }
    }
}
