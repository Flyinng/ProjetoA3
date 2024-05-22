public class InsertionSort {
	            void sort(int arr[]) {
	                int n = arr.length;
	                for (int i = 1; i < n; ++i) {
	                    int key = arr[i];
	                    int j = i - 1;

	                    // Move os elementos de arr[0..i-1], que são
	                    // maiores que key, para uma posição à frente
	                    // de sua posição atual
	                    while (j >= 0 && arr[j] > key) {
	                        arr[j + 1] = arr[j];
	                        j = j - 1;
	                    }
	                    arr[j + 1] = key;
	                }
	            }

	            // Uma função utilitária para imprimir array de tamanho n
	            static void printArray(int arr[]) {
	                int n = arr.length;
	                for (int i = 0; i < n; ++i)
	                    System.out.print(arr[i] + " ");

	                System.out.println();
	            }

	            // Método principal
	            public static void main(String args[]) {
	                int arr[] = {12, 11, 13, 5, 6};

	                InsertionSort ob = new InsertionSort();
	                ob.sort(arr);

	                printArray(arr);
	            }

}
