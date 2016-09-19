package datamininglab1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainClass {
	
	private static void printResult(WordCounter wordCounter,List<ReuterDoc> reuterList) throws IOException
	{
		FileWriter countWriter=new FileWriter("./result/FeatureVector(Count).html");
		String begin="<html><head></head><body><table border=\"1\">\n";
		String end="</table></body></html>\n";
		
		String firstColumnFormat="<tr><td>%s</td>\n";
		String format="<td>%s</td>\n";
		String lastColumnFormat="<td>%s</td></tr>\n";
		List<Map<String,Integer>> countList=wordCounter.getRawFeatureVectors();
		List<String> wordsList=wordCounter.getTopWordsList();
		countWriter.write(begin);
		for(int i=-1; i<wordsList.size();i++)
		{
			if(i==-1)
			{
				countWriter.write(String.format(firstColumnFormat,"tite/words"));
			}
			else if(i==wordsList.size()-1)
			{
				countWriter.write(String.format(lastColumnFormat, wordsList.get(i)));
			}		
			else
			{
				countWriter.write(String.format(format, wordsList.get(i)));
			}
		}
		
		for(int i=0;i<reuterList.size();i++)
		{
			Map<String,Integer> count=countList.get(i);
			countWriter.write(String.format(firstColumnFormat,reuterList.get(i).title));
			for(int j=0;j<wordsList.size();j++)
			{
				String word=wordsList.get(j);
				int countNumber=0;
				if(count.containsKey(word))
				{
					countNumber=count.get(word);
				}
				 if(j==wordsList.size()-1)
				{
					countWriter.write(String.format(lastColumnFormat, countNumber));
				}		
				else
				{
					countWriter.write(String.format(format, countNumber));
				}
							
			}
			
		}
		countWriter.write(end);
		countWriter.close();
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
