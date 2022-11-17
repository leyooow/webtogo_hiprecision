package com.ivant.cms.wrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class TagCloudGenerator {
	private String string = new String();
	private TreeMap<String, Integer> countMap;
	private ArrayList<Map.Entry<String,Integer>> tagCloud;
	private Integer minPx = 12;
	private Integer maxPx = 72;
	public String getString() {
		return string;
	}


	public void setString(String string) {
		this.string = string;
	}


	public TreeMap<String, Integer> getCountMap() {
		return countMap;
	}


	public void setCountMap(TreeMap<String, Integer> countMap) {
		this.countMap = countMap;
	}


	public ArrayList<Map.Entry<String, Integer>> getTagCloud() {
		return tagCloud;
	}


	public void setTagCloud(ArrayList<Map.Entry<String, Integer>> tagCloud) {
		this.tagCloud = tagCloud;
	}


	public Integer getMinPx() {
		return minPx;
	}


	public void setMinPx(Integer minPx) {
		this.minPx = minPx;
	}


	public Integer getMaxPx() {
		return maxPx;
	}


	public void setMaxPx(Integer maxPx) {
		this.maxPx = maxPx;
	}


	public Integer getMaxOutput() {
		return maxOutput;
	}


	public void setMaxOutput(Integer maxOutput) {
		this.maxOutput = maxOutput;
	}


	public ArrayList<String> getStopwords() {
		return stopwords;
	}


	public void setStopwords(ArrayList<String> stopwords) {
		this.stopwords = stopwords;
	}


	private Integer maxOutput = 0;
	

	private ArrayList<String> stopwords = new ArrayList<String>(Arrays.asList(new String [] { "a", "about", "above", "accordingly", "after",
  "again", "against", "ah", "all", "also", "although", "always", "am", "among", "amongst", "an",
  "and", "any", "anymore", "anyone", "are", "as", "at", "away", "be", "been",
  "begin", "beginning", "beginnings", "begins", "begone", "begun", "being",
  "below", "between", "but", "by", "ca", "can", "cannot", "come", "could",
  "did", "do", "doing", "during", "each", "either", "else", "end", "et",
  "etc", "even", "ever", "far", "ff", "following", "for", "from", "further", "furthermore",
  "get", "go", "goes", "going", "got", "had", "has", "have", "he", "her",
  "hers", "herself", "him", "himself", "his", "how", "i", "if", "in", "into",
  "is", "it", "its", "itself", "last", "lastly", "less", "many", "may", "me",
  "might", "more", "must", "my", "myself", "near", "nearly", "never", "new",
  "next", "no", "not", "now", "o", "of", "off", "often", "oh", "on", "only",
  "or", "other", "otherwise", "our", "ourselves", "out", "over", "perhaps",
  "put", "puts", "quite", "s", "said", "saw", "say", "see", "seen", "shall",
  "she", "should", "since", "so", "some", "such", "t", "than", "that", "the",
  "their", "them", "themselves", "then", "there", "therefore", "these", "they",
  "this", "those", "though", "throughout", "thus", "to", "too",
  "toward", "unless", "until", "up", "upon", "us", "ve", "very", "was", "we",
  "were", "what", "whatever", "when", "where", "which", "while", "who",
  "whom", "whomever", "whose", "why", "with", "within", "without", "would",
  "yes", "your", "yours", "yourself", "yourselves" }));


	public TagCloudGenerator(String string, Integer maxOutput, Integer maxPx, Integer minPx) {
		super();
		this.maxOutput = maxOutput;
		this.maxPx = maxPx;
		this.minPx = minPx;
		this.string = string;
	}


	public TagCloudGenerator(String string) {
		super();
		this.string = string;
	}


	public TagCloudGenerator( String string ,Integer maxPx, Integer minPx) {
		super();
		this.maxPx = maxPx;
		this.minPx = minPx;
		this.string = string;
	}
	
	
	public ArrayList<Map.Entry<String,Integer>> returnTagCloud() {
		if(string.isEmpty()) return null;
		
		string = string.replaceAll("\\W"," ");
		string = string.toLowerCase();
		String [] wordArray = string.split("\\s+");
		countMap = new TreeMap<String, Integer>();
	    for(String word: wordArray) {
	    	if(!(stopwords.contains(word) || word.length() < 3)) {
	    		if(countMap.containsKey(word))
	    			countMap.put(word, countMap.get(word)+1);
	    		else 
	    			countMap.put(word, 1);
	    	}
	    }
	            
	       tagCloud = new ArrayList<Map.Entry<String,Integer>>(countMap.entrySet());
	        Collections.sort(tagCloud, new Comparator<Map.Entry<String,Integer>>() {
	            public int compare(Map.Entry<String,Integer> e1, Map.Entry<String,Integer> e2) {
	                Integer i1 = e1.getValue();
	                Integer i2 = e2.getValue();
	                return i2.compareTo(i1);
	            }
	        });
	        
	        int max = tagCloud.get(0).getValue();
	        //System.out.println(max);
	        for(Map.Entry<String,Integer> e : tagCloud) {
	            e.setValue((int) ((this.maxPx-this.minPx)*(e.getValue()/(float)max)+this.minPx));
	        }
	        
	        if(this.maxOutput > 0 && this.tagCloud.size() > this.maxOutput) {
	        	tagCloud = new ArrayList<Map.Entry<String,Integer>>(tagCloud.subList(0, this.maxOutput));
	        }
	        
	        return tagCloud;
	}
	
	
}
