import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

public class BinPacking {
    
    public static void main(String args[]) {
        System.out.println("Algoritmo  de Aproximación para el Bin Packing Problem.\n");

        System.out.print("Introduce el número de items: ");
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        System.out.println("Digita "+n+" items pulsando enter: ");
        ArrayList<Double> items = new ArrayList<Double>(); //
        for(int i = 0; i < n; i++)
            items.add(sc.nextDouble());

        // Llamamos al método binPacking con items, cantidad y capacidad.
        binPacking(items, n, 1.0);

        // Cerramos el Scanner.
        sc.close();
    }

    /**
     * Método para el Bin Packing Problem.
     * @param items Arreglo con los items del ejemplar.
     * @param n Cantidad de elementos del ejemplar.
     * @param capacity Capacidad de cada bin.
     */
    public static void binPacking(ArrayList<Double> items, int n, double capacity) {
        // Cantidad de bins que se van a necesitar.
        int bins = 0;

        System.out.println("\nItems originales: " + Arrays.toString(items.toArray()));
        // Ordenamos los items en orden noincremental.
        quickSort(items, 0, items.size()-1);
        System.out.println("Items ordenados:  " + Arrays.toString(items.toArray()));

        // A lo más tendremos n bins.
        ArrayList<Double> binValues = new ArrayList<Double>();

        // Iniciamos la capacidad de cada binValue:
        for(int i = 0; i < n; i++)
            binValues.add(capacity);
        System.out.println("Bins iniciales:   " + Arrays.toString(binValues.toArray())+"\n");

        System.out.println("»»»»»»»»»»»»»»»»»»»»»»»»ITERACIONES««««««««««««««««««««««««");

        // Asignamos cada item a un bin.
        for(int i = 0; i < n; i++)
            // Le asignamos un bin en caso de caber.
            for(int j = 0; j < binValues.size(); j++) {
                if(binValues.get(j)-items.get(i) >= 0) {
                    System.out.println("Capacidad del bin "+j+" antes:   "+binValues.get(j));
                    binValues.set(j, binValues.get(j) - items.get(i));
                    System.out.println("Capacidad del bin "+j+" después: "+binValues.get(j));
                    System.out.println("-------------------------------------------------");
                    break;
                }
            }

        for(int i = 0; i < binValues.size(); i++)
            if(binValues.get(i) != capacity)
                bins++;

        //Quitamos los binValues que no usamos.
        binValues.removeAll(new ArrayList<Double>() {{add(1.0);}});

        System.out.println("\nBins finales: " + Arrays.toString(binValues.toArray()));
        System.out.println("Número de bins requeridos: "+bins);
    }

    /**
     * Método que ordena de manera noincremental basado en el algoritmo QuickSort.
     * @param arr El arreglo a ordenar.
     * @param low Índice más pequeño para operar.
     * @param high Índice más grande para operar.
     */
    public static void quickSort(ArrayList<Double> arr, int low, int high) {
		if(arr == null || arr.size() == 0)
			return;
		if(low >= high)
			return;
		// Escogemos el pivote.
		int middle = low +(high - low) / 2;
		double pivot = arr.get(middle);
		// Hacemos left < pivot y right > pivot.
		int i = low, j = high;
		while(i <= j) {
			while(arr.get(i) > pivot) {
				i++;
			}
			while(arr.get(j) < pivot) {
				j--;
			}
			if(i <= j) {
                double temp = arr.get(i);
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