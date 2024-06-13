import java.util.List;

public class PesquisaBinaria {

    public static int pesquisaBinaria(List<String> numeros, String chave) {
        long chaveLong = Long.parseLong(chave); // Convertendo a chave para long
        return buscaBinariaRecursiva(numeros, 0, numeros.size() - 1, chaveLong);
    }

    private static int buscaBinariaRecursiva(List<String> numeros, int inicio, int fim, long chave) {
        if (inicio > fim) {
            return -1; // Elemento não encontrado
        }

        int meio = inicio + (fim - inicio) / 2;
        long valorMeio = Long.parseLong(numeros.get(meio)); // Convertendo o valor do meio para long

        if (valorMeio == chave) {
            return meio; // Elemento encontrado
        }

        if (valorMeio < chave) {
            return buscaBinariaRecursiva(numeros, meio + 1, fim, chave);
        } else {
            return buscaBinariaRecursiva(numeros, inicio, meio - 1, chave);
        }
    }
}
