import java.util.Arrays;
import java.util.ArrayList;
import java.util.Scanner;

public class SubsetSum {
    
    public static void main(String args[]) {
        System.out.println("Algoritmo  de Aproximación para el Subset Sum Problem.\n");

        Scanner sc = new Scanner(System.in);

        System.out.print("Introduce el número de items: ");
        int n = sc.nextInt();

        System.out.println("Digita "+n+" items pulsando enter: ");
        ArrayList<Integer> items = new ArrayList<Integer>();
        for(int i = 0; i < n; i++)
            items.add(sc.nextInt());

        System.out.print("Introduce el valor del objetivo t: ");
        int t = sc.nextInt();

        System.out.print("Introduce el valor del factor ε: ");
        double epsilon = sc.nextDouble();

        int val = approxSubsetSum(items, t, epsilon);
        System.out.println("El algoritmo regresa: " + val);

        // Cerramos el Scanner.
        sc.close();
    }

    /**
     * Método que combina dos listas, quita sus elementos repetidos, y los ordena de manera incremental.
     * @param arr1 Primer arreglo a ordenar.
     * @param arr2 Segundo arreglo a ordenar.
     */
    public static ArrayList<Integer> mergeList(ArrayList<Integer> arr1, ArrayList<Integer> arr2){
        System.out.println("»»»»»»»»»»»»»»»»»»»»»»»»»MERGE-LIST«««««««««««««««««««««««««");
        System.out.println("Items originales de arr1: " + Arrays.toString(arr1.toArray()));
        System.out.println("Items originales de arr2: " + Arrays.toString(arr2.toArray()));

        ArrayList<Integer> arr = new ArrayList<Integer>();

        // Primero agregamos todos los elementos en arr.
        arr.addAll(arr1);
        arr.addAll(arr2);
        System.out.println("Items de combinar ambas listas: " + Arrays.toString(arr.toArray()));

        // Quitamos los elementos repetidos.
        Object[] st = arr.toArray();
        for (Object s : st) {
            if(arr.indexOf(s) != arr.lastIndexOf(s)) {
                arr.remove(arr.lastIndexOf(s));
            }
        }
        System.out.println("Items sin repetidos: " + Arrays.toString(arr.toArray()));

        // Ordenamos de manera incremental.
        quickSort(arr, 0, arr.size()-1);
        return arr;
    }

    /**
     * Método que trimea. :v
     * @param arr Arreglo a trimear.
     * @param delta Factor delta a considerar del método.
     */
    public static ArrayList<Integer> trim(ArrayList<Integer> arr, double delta) {
        System.out.println("»»»»»»»»»»»»»»»»»»»»»»»»»»»»TRIM««««««««««««««««««««««««««««");
        // Sea m la longitud de arr.
        int m = arr.size();
        ArrayList<Integer> arr2 = new ArrayList<Integer>();
        arr2.add(arr.get(0));
        int last = arr.get(0);
        int y_i;
        for(int i=1; i <= m; i++) {
            y_i = arr.get(i-1);
            if(y_i > last*(1+delta)) {
                arr2.add(y_i);
                last = y_i;
            }
        }
        return arr2;
    }

    /**
     * Crea t arreglos de enteros.
     * @param t numero de arreglos a crear.
     * @return Lista de arreglos.
     */
    public static ArrayList<Integer>[] createLists(int t) {
        ArrayList<Integer> list[] = new ArrayList[t];
        for(int i = 0; i < t; i++) {
            list[i] = new ArrayList<Integer>();
        }
        return list;
    }

    /**
     * Método que le suma una cierta cantidad a cada elemento de un arreglo.
     * @param arr Arreglo inicial.
     * @param t Cantidad a sumar.
     */
    public static ArrayList<Integer> sum(ArrayList<Integer> arr, int t) {
        ArrayList<Integer> arr2 = new ArrayList<Integer>();
        for(int i = 0; i < arr.size(); i++)
            arr2.add(arr.get(i)+t);
        return arr2;
    }

    /**
     * Método que elimina todos los elementos de un arreglo que sean mayores al parámetro especificado.
     * @param arr El arreglo de donde vamos a quitar elementos.
     * @param t Objetivo t del algoritmo.
     * @return Arreglo sin entradas mayores a t.
     */
    public static ArrayList<Integer> quitaMayores(ArrayList<Integer> arr, int t) {
        System.out.println("»»»»»»»»»»»»»»»»»»»»»»»»QUITA-MAYORES««««««««««««««««««««««««");
        ArrayList<Integer> arr2 = new ArrayList<Integer>();
        for(int i = 0; i < arr.size(); i++)
            if(arr.get(i) <= t)
                arr2.add(arr.get(i));
        return arr2;
    }

    /**
     * Método que aplica el algoritmo de aproximación para el Subset Problem.
     * @param S Conjunto de enteros positivos.
     * @param t Entero positivo.
     * @param epsilon Factor ε a considerar en el algoritmo de aproximación.
     * @return El valor calculado en el algoritmo.
     */
    public static int approxSubsetSum(ArrayList<Integer> S, int t, double epsilon) {
        int n = S.size();
        ArrayList<Integer> L_0 = new ArrayList<Integer>();
        L_0.add(0);
        ArrayList<Integer> L[] = createLists(n+1);
        L[0] = L_0;
        for(int i = 1; i <= n; i++) {
            System.out.println();
            L[i] = mergeList(L[i-1], sum(L[i-1], S.get(i-1)));
            System.out.println("Lista "+i+" mezclada:    "+Arrays.toString(L[i].toArray()));
            L[i] = trim(L[i], epsilon/(2*n));
            System.out.println("Lista "+i+" trimeada:    "+Arrays.toString(L[i].toArray()));
            L[i] = quitaMayores(L[i], t);
            System.out.println("Lista "+i+" sin mayores: "+Arrays.toString(L[i].toArray())+"\n");
        }
        return L[n].get(L[n].size()-1);
    }

    /**
     * Método que ordena de manera incremental basado en el algoritmo QuickSort.
     * @param arr El arreglo a ordenar.
     * @param low Índice más pequeño para operar.
     * @param high Índice más grande para operar.
     */
    public static void quickSort(ArrayList<Integer> arr, int low, int high) {
		if(arr.size() == 0 || low >= high)
			return;
		// Escogemos el pivote.
		int middle = low+(high-low)/2;
		int pivot = arr.get(middle);
		// Hacemos left < pivot y right > pivot.
		int i = low, j = high;
		while(i <= j) {
			while(arr.get(i).compareTo(pivot) < 0) {
				i++;
			}
			while(arr.get(j).compareTo(pivot) > 0) {
				j--;
			}
			if(i <= j) {
                int temp = arr.get(i);
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