package com.netspam.model.lsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordNGramJaccardMeasure
extends TextSimilarityMeasureBase
{
protected int n;

private boolean toLowerCase;

public WordNGramJaccardMeasure()
{
    // default constructor uses trigrams and does not convert to lowerCase
    initialize(3, false);
}

public WordNGramJaccardMeasure(boolean toLowerCase)
{
    initialize(3, toLowerCase);
}

public WordNGramJaccardMeasure(int n)
{
    initialize(n, false);
}

public WordNGramJaccardMeasure(int n, boolean toLowerCase)
{
    initialize(n, toLowerCase);
}

private void initialize(int n, boolean toLowerCase)
{
    this.n = n;
    this.toLowerCase = toLowerCase;
}

@Override
public double getSimilarity(Collection<String> stringList1, Collection<String> stringList2)
    throws SimilarityException
{
    // Get n-grams
    Set<List<String>> ngrams1 = getNGrams(Arrays.asList(stringList1.toArray(new String[] {})));
    Set<List<String>> ngrams2 = getNGrams(Arrays.asList(stringList2.toArray(new String[] {})));

    double sim = getNormalizedSimilarity(ngrams1, ngrams2);

    return sim;
}

protected double getNormalizedSimilarity(Set<List<String>> suspiciousNGrams,
        Set<List<String>> originalNGrams)
{
    // Compare using the Jaccard similarity coefficient (Manning & Schütze, 1999)
    Set<List<String>> commonNGrams = new HashSet<List<String>>();
    commonNGrams.addAll(suspiciousNGrams);
    commonNGrams.retainAll(originalNGrams);

    Set<List<String>> unionNGrams = new HashSet<List<String>>();
    unionNGrams.addAll(suspiciousNGrams);
    unionNGrams.addAll(originalNGrams);

    double norm = unionNGrams.size();
    double sim = 0.0;

    if (norm > 0.0) {
        sim = commonNGrams.size() / norm;
    }

    return sim;
}

private Set<List<String>> getNGrams(List<String> stringList)
{
    Set<List<String>> ngrams = new HashSet<List<String>>();

    for (int i = 0; i < stringList.size() - (n - 1); i++) {
        // Generate n-gram at index i
        List<String> ngram = new ArrayList<String>();
        for (int k = 0; k < n; k++) {
            String token = stringList.get(i + k);
            if (toLowerCase) {
                token = token.toLowerCase();
            }
            ngram.add(token);
        }

        // Add
        ngrams.add(ngram);
    }

    return ngrams;
}

@Override
public String getName()
{
    return this.getClass().getSimpleName() + "_" + n + "grams";
}

public boolean isToLowerCase()
{
    return toLowerCase;
}

public void setToLowerCase(boolean toLowerCase)
{
    this.toLowerCase = toLowerCase;
}
}