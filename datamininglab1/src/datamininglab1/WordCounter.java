package datamininglab1;

import java.util.ArrayList;
import java.util.HashMap;
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
	public List<Map<String,Integer>> getTFIDFValue()
	{
		List<Map<String,Integer>> tf_idf=new ArrayList<Map<String,Integer>>();
		for(int i=0;i<rawFeatureVectors.size();i++){
			Map<String,Integer> counter=rawFeatureVectors.get(i);
			Map<String,Integer> result=new HashMap<String,Integer>();
			
			//calculate tf value
		
			//calculate idf value
		}
		return tf_idf;
	}
}
