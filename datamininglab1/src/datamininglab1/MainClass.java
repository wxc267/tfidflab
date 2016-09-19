package datamininglab1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainClass {
	private static String printLabelList(List<String> topic, List<String> location)
	{
		String result="";
		for(int i=0;i<topic.size();i++)
		{
			result+=topic.get(i)+",";
		}
		for(int i=0;i<location.size();i++)
		{
			result+=location.get(i);
			if(i!=location.size()-1)
			{
				result+=",";
			}
		}
		return result;
	}
	private static void printResult(WordCounter wordCounter,List<ReuterDoc> reuterList) throws IOException
	{
		FileWriter countWriter=new FileWriter("./result/FeatureVector(Count).html");
		FileWriter tfidfWriter=new FileWriter("./result/FeatureVector(TFIDFValue).html");
		String begin="<html><head></head><body><table border=\"1\">\n";
		String end="</table></body></html>\n";
		String firstColumnFormat="<tr><td>%s</td>\n";
		String format="<td>%s</td>\n";
		String lastColumnFormat="<td>%s</td></tr>\n";
		List<Map<String,Integer>> countList=wordCounter.getRawFeatureVectors();
		List<Map<String,Double>> tfidfList=wordCounter.getTFIDFValue();
		List<String> wordsList=wordCounter.getTopWordsList();
		countWriter.write(begin);
		tfidfWriter.write(begin);
		for(int i=-1; i<wordsList.size();i++)
		{
			if(i==-1)
			{
				countWriter.write(String.format(firstColumnFormat,"tite/words"));
				countWriter.write(String.format(format, "label(topic,location)"));
				tfidfWriter.write(String.format(firstColumnFormat,"tite/words"));
				tfidfWriter.write(String.format(format, "label(topic,location)"));
			}
			else if(i==wordsList.size()-1)
			{
				countWriter.write(String.format(lastColumnFormat, wordsList.get(i)));
				tfidfWriter.write(String.format(lastColumnFormat, wordsList.get(i)));
			}		
			else
			{
				countWriter.write(String.format(format, wordsList.get(i)));
				tfidfWriter.write(String.format(format, wordsList.get(i)));
			}
		}
		
		for(int i=0;i<reuterList.size();i++)
		{
			Map<String,Integer> count=countList.get(i);
			Map<String,Double> tfidf=tfidfList.get(i);
			countWriter.write(String.format(firstColumnFormat,reuterList.get(i).title));
			countWriter.write(String.format(format, printLabelList(reuterList.get(i).topics,reuterList.get(i).places)));
			tfidfWriter.write(String.format(firstColumnFormat,reuterList.get(i).title));
			tfidfWriter.write(String.format(format, printLabelList(reuterList.get(i).topics,reuterList.get(i).places)));
			for(int j=0;j<wordsList.size();j++)
			{
				
				String word=wordsList.get(j);
				int countNumber=0;
				double tfidfValue=0;
				if(count.containsKey(word))
				{
					countNumber=count.get(word);
				}
				if(tfidf.containsKey(word))
				{
					tfidfValue=tfidf.get(word);
				}
				 if(j==wordsList.size()-1)
				{
					countWriter.write(String.format(lastColumnFormat, countNumber));
					tfidfWriter.write(String.format(lastColumnFormat, tfidfValue));
				}		
				else
				{
					countWriter.write(String.format(format, countNumber));
					tfidfWriter.write(String.format(format,tfidfValue));
				}
							
			}
			
		}
		countWriter.write(end);
		tfidfWriter.write(end);
		countWriter.close();
		tfidfWriter.close();
	}
	
	public static void main(String[] args) throws IOException
	{
		List<ReuterDoc> reuterList=new ArrayList<ReuterDoc>();
		for(int i=0;i<22;i++){
			String fileName="./data/reut2-"+UtilClass.GetThreeDigitsNumber(i)+".sgm";
			List<ReuterDoc> reuters=FileParser.parse(fileName);
			reuterList.addAll(reuters);
		}
		WordCounter word=new WordCounter(reuterList);
		printResult(word,reuterList);	
	}
}
