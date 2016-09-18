package datamininglab1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class StopWords {
	
	public Set<String> stopWords;
	
	public StopWords()
	{
		stopWords=new HashSet<String>();
		InitStopWords();
	}
	
	
	public  void InitStopWords() 
	{
		
	    FileReader file;
		try {
			file = new FileReader("./data/stopWords");
			BufferedReader reader=new BufferedReader(file);
			String raw_string;
			try {
				raw_string = reader.readLine();
				String[] list=raw_string.split(",");
				for(int i=0;i<list.length;i++)
				{
					stopWords.add(list[i]);
				}
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		
	}
}
