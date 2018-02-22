import java.util.*;

public class randGen
{
	public void thing(int thing)
	{
		System.out.println(thing);
	}

	public static void main(String[] args) 
	{
		final int MAX= 10;
	   final int MIN = 100;
		Random rand = new Random();
		int num = rand.nextInt(Math.abs(MAX - MIN)) + MIN;
		System.out.println(num +" is the random number");
		System.out.println(Math.random() * 10+" is the number using math");	
	}
}


