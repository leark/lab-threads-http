/**
 * A class that races sorting algorithms.
 * 
 * @author Joel Ross
 */
public class SortRacer {

	public static void main(String[] args) 
	{
		/** Threads */
		// Thread t1 = new Thread(new Sorting.MergeSort());
		// Thread t2 = new Thread(new Sorting.QuickSort());

		// t1.start();
		// t2.start();
		new Thread(new Sorting.MergeSort()).start();
		new Thread(new Sorting.QuickSort()).start();

	}	
}