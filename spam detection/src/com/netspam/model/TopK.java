package com.netspam.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import com.netspam.bean.Product;



public class TopK {

	public static double[] topKLargest(double[] input, int k) {
		PriorityQueue<Double> minheap = new PriorityQueue<Double>(k, new Comparator<Double>() {
			@Override
			public int compare(Double o1, Double o2) {
				if(o1 < o2) {
					return 1;
				} else if(o1 > o2) {
					return -1;
				} else {
					return 0;
				}
			}
		});

		for (double i : input) {
			minheap.add(i);
		}

		double[] out = new double[k];
		for (int i = 0; i < out.length; i++) {
			out[i] = minheap.poll();
		}
		return out;
	}
	public static ArrayList<Product> topKLargest(ArrayList<Product> hdList, int k) {
		Collections.sort(hdList, new TopKProducts());
		ArrayList<Product> topkList = new ArrayList<Product>();
		for(int i=0;i<k;i++)
			topkList.add(hdList.get(i));
		return topkList;			
	}

	/*public static int[] topKLargestBounded(int[] input, int k) {
		// to get top k largest we need to have minheap. The BPQ internally has
		// a minheap which discards the top element if current element is greater.
		// NOTE: It just gives the topk element. order is not guaranteed.
		BoundedPriorityQueue<Integer> bq = new BoundedPriorityQueue<Integer>(k);
		
		for (int i : input) {
			bq.add(i);
		}

		int[] out = new int[k];
		for (int i = 0; i < out.length; i++) {
			out[i] = bq.poll();
		}
		return out;
		
	}*/
	
	public static void main(String[] args) {
		double[] out = topKLargest(new double[] { 10, 23, 5, 1, 7,12,23, 8, 4, 5, 7, 12,
				523 }, 5);
		System.out.print("Largest: ");
		for (double o : out) {
			System.out.print(o + " ");
		}
		
		/*out = topKLargestBounded(new int[] { 10, 23, 5, 1, 7,12,24, 8, 4, 5, 7, 12,
				523 }, 3);
		System.out.println();
		System.out.print("Largest: ");
		for (int o : out) {
			System.out.print(o + " ");
		}*/
	}
}
