import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;
import java.math.BigDecimal;

public class BinPacking {
    
    public static void main(String args[]) {
        System.out.println("Algoritmo  de Aproximación para el Bin Packing Problem.\n");

        System.out.print("Introduce el número de items: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        System.out.println("Digita "+n+" items pulsando enter: ");
        ArrayList<BigDecimal> items = new ArrayList<BigDecimal>();
        for(int i = 0; i < n; i++) {
            BigDecimal item, one = new BigDecimal(1);
            do {
                item = sc.nextBigDecimal();
                if(item.compareTo(one) > 0)
                    System.out.println("¡Ingresa un entero menor igual a 1!");
            } while(item.compareTo(one) > 0);
            items.add(item);
        }

        // Llamamos al método binPacking con items, cantidad y capacidad.
        binPacking(items, n, new BigDecimal(1.0));

        // Cerramos el Scanner.
        sc.close();
    }

    /**
     * Método para el Bin Packing Problem.
     * @param items Arreglo con los items del ejemplar.
     * @param n Cantidad de elementos del ejemplar.
     * @param capacity Capacidad de cada bin.
     */
    public static void binPacking(ArrayList<BigDecimal> items, int n, BigDecimal capacity) {
        // Cantidad de bins que se van a necesitar.
        int bins = 0;

        System.out.println("\nItems originales: " + Arrays.toString(items.toArray()));
        // Ordenamos los items en orden noincremental.
        quickSort(items, 0, items.size()-1);
        System.out.println("Items ordenados:  " + Arrays.toString(items.toArray()));

        // A lo más tendremos n bins.
        ArrayList<BigDecimal> binValues = new ArrayList<BigDecimal>();

        // Iniciamos la capacidad de cada binValue:
        for(int i = 0; i < n; i++)
            binValues.add(capacity);
        System.out.println("Bins iniciales:   " + Arrays.toString(binValues.toArray())+"\n");

        System.out.println("»»»»»»»»»»»»»»»»»»»»»»»»ITERACIONES««««««««««««««««««««««««");

        // Asignamos cada item a un bin.
        for(int i = 0; i < n; i++)
            // Le asignamos un bin en caso de caber.
            for(int j = 0; j < binValues.size(); j++) {
                BigDecimal substract = sub(binValues.get(j),items.get(i));
                BigDecimal zero = new BigDecimal(0);
                if(substract.compareTo(zero) >= 0) {
                    System.out.println("Capacidad del bin "+j+" antes:   "+binValues.get(j));
                    binValues.set(j, substract);
                    System.out.println("Capacidad del bin "+j+" después: "+binValues.get(j));
                    System.out.println("-------------------------------------------------");
                    break;
                }
            }

        for(int i = 0; i < binValues.size(); i++)
            if(binValues.get(i) != capacity)
                bins++;

        //Quitamos los binValues que no usamos.
        binValues.removeAll(new ArrayList<BigDecimal>() {{add(new BigDecimal(1.0));}});
        // Imprimimos la capacidad sobrante de cada bin.
        System.out.print("\nCapacidad sobrante de cada bin: " + Arrays.toString(binValues.toArray()));

        BigDecimal one = new BigDecimal(1);        
        for(int i = 0; i < binValues.size(); i++)
            binValues.set(i, sub(one, binValues.get(i)));

        // Imprimimos la capacidad ocupada de cada bin.        
        System.out.println("\nCapacidad ocupada de cada bin:  " + Arrays.toString(binValues.toArray()));
        // Cantidad total de bins utilizados.
        System.out.println("Número de bins requeridos: "+bins);
    }

    /**
     * Método que resta dos BigDecimal.
     * @param b1 Primer BigDecimal.
     * @param b2 Segundo BigDecimal.
     * @return BigDecimal, resultado de b1 menos b2
     */
    public static BigDecimal sub(BigDecimal b1, BigDecimal b2) {
        return b1.subtract(b2);
    }
    
    /**
     * Método que ordena de manera noincremental basado en el algoritmo QuickSort.
     * @param arr El arreglo a ordenar.
     * @param low Índice más pequeño para operar.
     * @param high Índice más grande para operar.
     */
    public static void quickSort(ArrayList<BigDecimal> arr, int low, int high) {
		if(arr.size() == 0)
			return;
		if(low >= high)
			return;
		// Escogemos el pivote.
		int middle = low+(high-low)/2;
		BigDecimal pivot = arr.get(middle);
		// Hacemos left < pivot y right > pivot.
		int i = low, j = high;
		while(i <= j) {
			while(arr.get(i).compareTo(pivot) > 0) {
				i++;
			}
			while(arr.get(j).compareTo(pivot) < 0) {
				j--;
			}
			if(i <= j) {
                BigDecimal temp = arr.get(i);
                arr.set(i, arr.get(j));
				arr.set(j, temp);
				i++;
				j--;
			}
		}
		// Ordenamos recursivamente las dos subpartes.
		if(low < j)
			quickSort(arr, low, j);
		if(high > i)
			quickSort(arr, i, high);
	}
}