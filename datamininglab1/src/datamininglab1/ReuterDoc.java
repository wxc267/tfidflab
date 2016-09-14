package datamininglab1;

import java.util.ArrayList;
import java.util.List;

public class ReuterDoc {
	public String title;
	public List<String> places;
	public List<String> topics;
	public String body;
	public ReuterDoc()
	{
		places=new ArrayList<String>();
		topics=new ArrayList<String>();
	}
}
