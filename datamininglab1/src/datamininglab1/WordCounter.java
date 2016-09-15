package datamininglab1;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WordCounter {
	private List<ReuterDoc> reuterList;
	private Map<String,Integer> docFreq;
	private int docTotalNumber;
	private List<Integer> docTotalWordNumber;
	private List<Map<String,Integer>> rawFeatureVectors;
	public WordCounter(List<ReuterDoc> reuterList)
	{
		this.reuterList=new ArrayList<ReuterDoc>();
		this.reuterList=reuterList;
		docFreq=new HashMap<String,Integer>();
		docTotalWordNumber=new ArrayList<Integer>();
		rawFeatureVectors=new ArrayList<Map<String,Integer>>();
		InitWordCount();
	}
	private Map<String,Integer> WordCounterEachText(String text)
	{
		Map<String,Integer> counter=new HashMap<String,Integer>();
		int totalNum=0;
		int start=0;
		int end=0;
		while(end<text.length())
		{
			//tokenize and count the word
			char ch=text.charAt(end);
			if(Character.isLetter(ch))
			{
				//if current one is character
				end++;
			}
			else if(Character.isLetter(text.charAt(start)))
			{
				/*
				if the begin of index of the string is character but the current index is non-character, then take
				the word.
				*/
				String word=text.substring(start,end);
				word=word.toLowerCase();
				totalNum++;
					if(counter.containsKey(word))
					{
						int oldCount=counter.get(word);
						counter.put(word, oldCount+1);
					}
					else
					{
						counter.put(word, 1);
						if(docFreq.containsKey(word))
						{
							int oldCount=docFreq.get(word);
							docFreq.put(word, oldCount+1);
						}
						else
						{
							docFreq.put(word, 1);
						}
					}
				start=end+1;
				end=start;
			}
			else
			{
				//if there are continuous non-letter characters.
				start++;
				end=start;
			}
		}
		docTotalWordNumber.add(totalNum);
		return counter;
	}
	private void InitWordCount()
	{
		docTotalNumber=reuterList.size();
		for(int i=0;i<docTotalNumber;i++)
		{
			String text=reuterList.get(i).body;
			Map<String,Integer> counter=WordCounterEachText(text);
			rawFeatureVectors.add(counter);
		}
	}
	private Map<String,Double> GetTopThreeWords(Map<String,Double> map)
	{
		List<Map.Entry<String,Double>> list =
	            new LinkedList<Map.Entry<String,Double>>( map.entrySet() );
	        Collections.sort( list, new Comparator<Map.Entry<String,Double>>()
	        {
	            public int compare( Map.Entry<String,Double> o1, Map.Entry<String,Double> o2 )
	            {
	                return (o1.getValue()).compareTo( o2.getValue() );
	            }
	        } );
	        Map<String,Double> result=new HashMap<String,Double>();
	        for(int i=0;i<3;i++)
	        {
	        	Map.Entry<String, Double> entry=list.get(list.size()-1-i);
	        	result.put(entry.getKey(), entry.getValue());
	        }
	        return result;
	}
	public List<Map<String,Integer>> getRawFeatureVectors()
	{
		return this.rawFeatureVectors;
	}
	public int getTotalDocNumber()
	{
		return this.docTotalNumber;
	}
	public List<Integer> getAllDocTotalWordNumber()
	{
		return this.docTotalWordNumber;
	}
	public Map<String,Integer> getDocumentFrequency()
	{
		return this.docFreq;
	}
	public List<Map<String,Double>> getTFIDFValue()
	{
		List<Map<String,Double>> tfidfList=new ArrayList<Map<String,Double>>();
		for(int i=0;i<rawFeatureVectors.size();i++){
			int totalWordNumber=docTotalWordNumber.get(i);
			Map<String,Integer> counter=rawFeatureVectors.get(i);
			Map<String,Double> tfidfDoc=new HashMap<String,Double>();
			//calculate tf value
			for(Map.Entry<String, Integer> record : counter.entrySet())
			{
				String word=record.getKey();
				int count=record.getValue();
				double tf=count*1.0/totalWordNumber;
				//calculate idf value
				int wordDocFreq=docFreq.get(word);
				double idf=Math.log(docTotalNumber*1.0/wordDocFreq);
				double tfidf=tf*idf;
				tfidfDoc.put(word, tfidf);
			}
			
			tfidfList.add(GetTopThreeWords(tfidfDoc));
		}
		return tfidfList;
	}
}
