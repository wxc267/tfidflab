package datamininglab1;

import java.util.List;
import java.util.Map;

public class MainClass {
	
	public static void main(String[] args)
	{
		String fileName="./data/reut2-000.sgm";
		List<ReuterDoc> reuterList=FileParser.parse(fileName);
		WordCounter word=new WordCounter(reuterList);
		List<Map<String,Double>> result=word.getTFIDFValue();
		
		System.out.println(result.get(0).toString());
	}
}
