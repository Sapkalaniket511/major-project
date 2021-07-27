package com.netspam.model.lsa;

import java.io.File;
import java.io.IOException;

public class Demo {
	public static void main(String args[]){
		String str1="This hotel is nice";
		String str2="This hotel is low quality";
		/*try {
			LsaSimilarityMeasure lsa = new LsaSimilarityMeasure(new File("E:/Shilpa/ProjectGuru/Spam/SSpace/a.txt"));
			double d=lsa.getSimilarity(str1, str2);
			System.out.println(d);
		} catch (IOException | SimilarityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		TextSimilarityMeasure measure = new WordNGramJaccardMeasure(3);    // Use word trigrams
		str1=str1.replace(".", "");
		str2=str2.replace(".", "");
		String[] tokens1 = str1.split(" ");//"This is a short example text .".split(" ");   
		String[] tokens2 = str2.split(" ");//"A short example text could look like that .".split(" ");

		double score;
		try {
			score = measure.getSimilarity(tokens1, tokens2);
			System.out.println("Similarity: " + score);
		} catch (SimilarityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		
	}
}
