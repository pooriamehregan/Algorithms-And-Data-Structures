import java.util.LinkedList;
import java.util.Queue;

public class Sorts {
    public static void quickSort(int A[], int min, int max)
    {
        // Quicksort av array med heltall

        int indexofpartition;

        if (max - min  > 0)
        {
            // Partisjonerer array
            indexofpartition = findPartition(A, min, max);

            // Sorterer venstre del
            quickSort(A, min, indexofpartition - 1);

            // Sorterer høyre del
            quickSort(A, indexofpartition + 1, max);
        }
    }

    private static int findPartition (int[] A, int min, int max)
    {
        int left, right;
        int temp, partitionelement;

        // Bruker *første* element til å dele opp
        partitionelement = A[min];

        left = min;
        right = max;

        // Gjør selve partisjoneringen
        while (left < right)
        {
            // Finn et element som er større enn part.elementet
            while (A[left] <= partitionelement && left < right)
                left++;

            // Finn et element som er mindre enn part.elementet
            while (A[right] > partitionelement)
                right--;

            // Bytt om de to hvis ikke ferdig
            if (left < right)
            {
                temp = A[left];
                A[left] = A[right];
                A[right] = temp;
            }
        }

        // Sett part.elementet mellom partisjoneringene
        temp = A[min];
        A[min] = A[right];
        A[right] = temp;

        // Returner indeksen til part.elementet
        return right;
    }


    public static void mergeSort (int[] A, int min, int max)
    {

        // Flettesortering av array med heltall

        if (min==max)
            return;

        int[] temp;
        int index1, left, right;
        int size = max - min + 1;
        int mid = (min + max) / 2;

        temp = new int[size];

        // Flettesorterer de to halvdelene av arrayen
        mergeSort(A, min, mid);
        mergeSort(A, mid + 1,max);

        // Kopierer array over i temp.array
        for (index1 = 0; index1 < size; index1++)
            temp[index1] = A[min + index1];

        // Fletter sammen de to sorterte halvdelene over i A
        left = 0;
        right = mid - min + 1;
        for (index1 = 0; index1 < size; index1++)
        {
            if (right <= max - min)
                if (left <= mid - min)
                    if (temp[left] > temp[right])
                        A[index1 + min] = temp[right++];
                    else
                        A[index1 + min] = temp[left++];
                else
                    A[index1 + min] = temp[right++];
            else
                A[index1 + min] = temp[left++];
        }
    }


    public static void insertionSort(int[] A)
    {
        // Innstikksortering av array med heltall

        int n = A.length;
        int key;

        for (int i = 1; i < n; i++)
        {
            // A er sortert t.o.m. indeks i-1
            key = A[i];
            int j = i;
            // Setter element nummer i på riktig plass
            // blant de i-1 første elementene
            while (j > 0 && A[j-1] > key)
            {
                A[j] = A[j-1];
                j--;
            }
            A[j] = key;
        }
    }


    public static void radixSort(int a[], int maksAntSiffer)
    {
        // Radixsortering av en array a med desimale heltall
        // maksAntSiffer: Maksimalt antall siffer i tallene

        int ti_i_m = 1; // Lagrer 10^m
        int n = a.length;

        // Oppretter 10 tomme køer
        Queue<Integer>[] Q = (Queue<Integer>[])(new Queue[10]);

        for (int i = 0; i < 10; i++)
            Q[i] = new LinkedList();

        // Sorterer på et og et siffer, fra høyre mot venstre

        for (int m = 0; m < maksAntSiffer; m++)
        {
            // Fordeler tallene i 10 køer
            for (int i = 0; i < n; i++)
            {
                int siffer = (a[i] / ti_i_m) % 10;
                Q[siffer].add(new Integer(a[i]));
            }

            // Tømmer køene og legger tallene fortløpende tilbake i a
            int j = 0;
            for (int i = 0; i < 10; i++)
                while (!Q[i].isEmpty())
                    a[j++] = (int) Q[i].remove();

            // Beregner 10^m for neste iterasjon
            ti_i_m *= 10;
        }
    }

}
