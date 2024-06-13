# Projeto Organizador de Números

Este projeto é uma aplicação Java para ordenação e pesquisa de números a partir de um arquivo de texto, utilizando diferentes algoritmos de ordenação e uma pesquisa binária. A interface gráfica foi desenvolvida usando o framework Swing.

## Funcionalidades

1. **Leitura de Números**: O programa lê os números de um arquivo de texto especificado e exibe uma lista formatada na interface.
   
2. **Ordenação**: Os números podem ser ordenados utilizando os seguintes métodos de ordenação:
   - **Insertion Sort**
   - **Selection Sort**
   - **Merge Sort**
   - **Quick Sort**
   - **Heap Sort**
   
   Cada método de ordenação é acionado por botões na interface gráfica.

3. **Pesquisa Binária**: Permite buscar um número específico na lista ordenada utilizando o método de pesquisa binária.

4. **Conexão com Banco de Dados**: É possível salvar os números ordenados em um banco de dados MySQL e limpar os dados do banco quando necessário.

## Como Funcionam os Métodos de Ordenação

### Insertion Sort

O **Insertion Sort** percorre a lista, inserindo cada elemento na posição correta, de acordo com a ordem crescente.

```java
import java.util.List;
import java.util.Collections;

public class InsertionSort {
    public static void insertionSort(List<String> numeros) {
        for (int i = 1; i < numeros.size(); i++) {
            String key = numeros.get(i);
            int j = i - 1;
            while (j >= 0 && numeros.get(j).compareTo(key) > 0) {
                numeros.set(j + 1, numeros.get(j));
                j = j - 1;
            }
            numeros.set(j + 1, key);
        }
    }
}
```

### Selection Sort

O **Selection Sort** encontra o menor elemento restante e o move para sua posição correta.

```java
import java.util.List;
import java.util.Collections;

public class SelectionSort {
    public static void selectionSort(List<String> numeros) {
        for (int i = 0; i < numeros.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < numeros.size(); j++) {
                if (numeros.get(j).compareTo(numeros.get(minIndex)) < 0) {
                    minIndex = j;
                }
            }
            Collections.swap(numeros, i, minIndex);
        }
    }
}
```

### Merge Sort

O **Merge Sort** divide a lista em duas metades, ordena cada metade recursivamente e depois mescla as duas metades ordenadas.

```java
import java.util.List;
import java.util.Collections;

public class MergeSort {
    public static void mergeSort(List<String> numeros) {
        if (numeros.size() > 1) {
            int mid = numeros.size() / 2;
            List<String> left = numeros.subList(0, mid);
            List<String> right = numeros.subList(mid, numeros.size());

            mergeSort(left);
            mergeSort(right);

            merge(numeros, left, right);
        }
    }

    private static void merge(List<String> numeros, List<String> left, List<String> right) {
        int leftIndex = 0, rightIndex = 0, mainIndex = 0;

        while (leftIndex < left.size() && rightIndex < right.size()) {
            if (left.get(leftIndex).compareTo(right.get(rightIndex)) < 0) {
                numeros.set(mainIndex++, left.get(leftIndex++));
            } else {
                numeros.set(mainIndex++, right.get(rightIndex++));
            }
        }

        while (leftIndex < left.size()) {
            numeros.set(mainIndex++, left.get(leftIndex++));
        }

        while (rightIndex < right.size()) {
            numeros.set(mainIndex++, right.get(rightIndex++));
        }
    }
}
```

### Quick Sort

O **Quick Sort** escolhe um elemento como pivô e particiona a lista ao redor do pivô, movendo os menores à esquerda e os maiores à direita.

```java
import java.util.List;
import java.util.Collections;

public class QuickSort {
    public static void quickSort(List<String> numeros) {
        quickSort(numeros, 0, numeros.size() - 1);
    }

    private static void quickSort(List<String> numeros, int low, int high) {
        if (low < high) {
            int pi = partition(numeros, low, high);
            quickSort(numeros, low, pi - 1);
            quickSort(numeros, pi + 1, high);
        }
    }

    private static int partition(List<String> numeros, int low, int high) {
        String pivot = numeros.get(high);
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (numeros.get(j).compareTo(pivot) <= 0) {
                i++;
                Collections.swap(numeros, i, j);
            }
        }
        Collections.swap(numeros, i + 1, high);
        return i + 1;
    }
}
```

### Heap Sort

O **Heap Sort** transforma a lista em uma estrutura de heap, onde o maior elemento é colocado na raiz, e depois move o maior elemento para a posição correta.

```java
import java.util.List;
import java.util.Collections;

public class HeapSort {
    public static void heapSort(List<String> numeros) {
        int n = numeros.size();

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(numeros, n, i);
        }

        for (int i = n - 1; i > 0; i--) {
            Collections.swap(numeros, 0, i);
            heapify(numeros, i, 0);
        }
    }

    private static void heapify(List<String> numeros, int n, int i) {
        int largest = i;
        int left = 2 * i + 1;
        int right = 2 * i + 2;

        if (left < n && numeros.get(left).compareTo(numeros.get(largest)) > 0) {
            largest = left;
        }

        if (right < n && numeros.get(right).compareTo(numeros.get(largest)) > 0) {
            largest = right;
        }

        if (largest != i) {
            Collections.swap(numeros, i, largest);
            heapify(numeros, n, largest);
        }
    }
}
```

## Método de Pesquisa Binária

A **Pesquisa Binária** é um método eficiente para buscar um elemento em uma lista ordenada. Ela compara o elemento chave com o elemento no meio da lista e decide continuar a busca na metade esquerda ou direita da lista, reduzindo pela metade o espaço de busca a cada iteração.

```java
import java.util.List;
import java.util.Collections;

public class PesquisaBinaria {

    public static int pesquisaBinaria(List<String> numeros, String chave) {
        long chaveLong = Long.parseLong(chave); 
        
        Collections.sort(numeros);

        return buscaBinariaRecursiva(numeros, 0, numeros.size() - 1, chaveLong);
    }

    private static int buscaBinariaRecursiva(List<String> numeros, int inicio, int fim, long chave) {
        if (inicio > fim) {
            return -1; 
        }

        int meio = inicio + (fim - inicio) / 2;
        long valorMeio = Long.parseLong(numeros.get(meio)); 

        if (valorMeio == chave) {
            return meio; 
        }

        if (valorMeio < chave) {
            return buscaBinariaRecursiva(numeros, meio + 1, fim, chave);
        } else {
            return buscaBinariaRecursiva(numeros, inicio, meio - 1, chave);
        }
    }
}
```

## Requisitos

- Java Development Kit (JDK)
- MySQL Server
- IDE Java (Eclipse, IntelliJ IDEA, etc.)

## Execução

Para executar o projeto:
1. Importe o projeto em sua IDE Java (Eclipse, NetBeans, VSCode e etc).
2. Certifique-se de ter configurado o MySQL.
3. Após isso crie o Schema em sua query.
```Sql
# Comando SQL
CREATE DATABASE IF NOT EXISTS dbnumeros
USE dbnumeros
```
4. Crie a Tabela números em seu novo schema.
```Sql
# Comando SQL
CREATE TABLE IF NOT EXISTS numeros (id INT AUTO_INCREMENT PRIMARY KEY,numero VARCHAR(100) NOT NULL)
``` 
5. Execute a classe `App` para iniciar a aplicação.

---

*Projeto desenvolvido na aula de Programação e Soluções Computacionais do curso Entra21 na Unisociesc - SC.* 📜
