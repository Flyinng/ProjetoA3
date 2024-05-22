import java.util.Arrays;
import java.util.List;

public class Ordenacao {
	    private List<Long> numeros;

	    public Ordenacao(List<Long> numeros) {
	        this.numeros = numeros;
	    }

	    public void ordenar() {
	        for (int i = 1; i < this.numeros.size(); ++i) {
	            Long key = this.numeros.get(i);
	            int j = i - 1;

	            while (j >= 0 && this.numeros.get(j) > key) {
	                this.numeros.set(j + 1, this.numeros.get(j));
	                j = j - 1;
	            }
	            this.numeros.set(j + 1, key);
	        }
	    }

	    public void imprimirNumeros() {
	        System.out.println(this.numeros);
	    }

	    public static void main(String[] args) {
	        List<Long> numeros = Arrays.asList(73647824497L, 85825632001L, 40003221080L, 47471692717L,
	        37981948331L, 42742901483L, 32588131514L, 79891270953L, 82711322759L, 53843643354L,
	        56578438959L, 18583421480L, 86885961020L, 38530407616L, 37633749007L, 36635396677L,
	        6463044326L, 7331720807L, 12688456460L, 19221868491L, 94606703968L, 82285964781L,
	        29104883623L, 71405723418L, 71177943171L, 48477606629L, 49113980562L, 89208935251L,
	        78004912424L, 57112330978L, 30256511085L, 66404591872L, 57410008303L, 73027888199L,
	        45917262202L, 39792329713L, 91020711773L, 50901263593L, 70653916786L, 79304718675L,
	        36327919388L, 56966011671L, 54570794058L, 37025827428L, 11556616905L, 84159661423L,
	        87241350266L, 98613121148L, 81818384603L, 8557437039L, 40614511205L, 96991634306L,
	        37139525020L, 69656808394L, 01734251213L, 86908668723L, 84085455879L, 93725232071L,
	        64384604496L, 78416501645L, 82836686537L, 83966249655L, 46748092582L, 72273210040L,
	        4180652804L, 9101064895L, 16662696548L, 99429440332L, 67470588199L, 95555255523L,
	        41503194129L, 96480953054L, 30423737298L, 69929232826L, 4134865194L, 94299203176L,
	        61851888763L, 30321337404L, 58416714850L, 54239289917L, 52351323019L, 11843421667L,
	        91356485291L, 18229000994L, 612877954L, 33364384171L, 16897889593L, 86784846755L,
	        96089710724L, 48825281127L, 98490188618L, 25481041171L, 64975709348L, 35592531362L
	        );

	        Ordenacao ordenacao = new Ordenacao(numeros);
	        ordenacao.ordenar();
	        ordenacao.imprimirNumeros();
	    }

}
