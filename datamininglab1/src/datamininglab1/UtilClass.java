package datamininglab1;



public class UtilClass {
	public static String GetThreeDigitsNumber(int number)
	{
		String result="000";
		if(number<10&&number>=0)
		{
			result="00"+String.valueOf(number);
		}
		else if(number>=10&&number<100)
		{
			result="0"+String.valueOf(number);
		}
		else
		{
			result=String.valueOf(number);
		}
		return result;
	}

}
