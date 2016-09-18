package datamininglab1;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class FileParser {
	
	public static List<ReuterDoc> parse(String fileName)
	{
		List<ReuterDoc> reuterList=new ArrayList<ReuterDoc>();
		Document doc;
		try {
			
			doc = Jsoup.parse(new File(fileName), "UTF-8");
			Elements reuters=doc.select("REUTERS");
			int size=reuters.size();
			for(int i=0;i<size;i++)
			{
				Element reuter=reuters.get(i);
				ReuterDoc reuterDoc=new ReuterDoc();	
				
				Element title=reuter.select("TITLE").first();
				Element topics=reuter.select("TOPICS").first();
				Element places=reuter.select("PLACES").first();
				Element text=reuter.select("TEXT").first();
				List<String> topicList=new ArrayList<String>();
				List<String> placesList=new ArrayList<String>();
				for(int j=0;j<topics.childNodeSize();j++)
				{
					Element topic=topics.child(j);
					topicList.add(topic.text());
				}
				for(int k=0;k<places.childNodeSize();k++)
				{
					Element place=places.child(k);
					placesList.add(place.text());
				}
				if(title!=null){
					reuterDoc.title=title.text();
				}else
				{
					reuterDoc.title="Document "+i;
				}
				reuterDoc.topics=topicList;
				reuterDoc.places=placesList;
				reuterDoc.body=text.text();
				reuterList.add(reuterDoc);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        return reuterList;
	}

}
