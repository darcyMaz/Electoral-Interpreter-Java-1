package org.dm.Electoral_Interpreter_Java_1.electoral_data_handling.collect_electoral_data ;

import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * I want to remove this main class from this package eventually.
 * @author 15148
 *
 */
public class Main 
{

	public static void main(String[] args) 
	{
		// might be better to do Canada.execute(folder,year,month). I don't think I will though.
		// Canada election_object = Canada.execute2021("src\\resources\\pollresults_debug");
		// System.out.println( election_object.getProvince(24) );
		
		//System.out.println(election_object);
		
		String a = "aaa";
		List<String> b = new ArrayList<>();
		
		b.add(a);
		a = "zzz";
		
		System.out.println( b.get(0) );
		
		
		/*
		File file = new File("src\\resources\\pollresults_debug\\pollresults_resultatsbureau35094.csv");
		
		Scanner sc_old;
		
		try {
			sc_old = new Scanner(file);
			//file_new.createNewFile();
			
			//FileWriter write = new FileWriter("src\\collect_electoral_data\\pollresults_debug\\pollresults_resultatsbureau48021_VCP.csv");
			//write.write(sc_old.nextLine() + "\n");
			
			int total = 0;
			
			while (sc_old.hasNextLine())
			{
				String line =  sc_old.nextLine();
				String[] a = line.split(",");
				
				//System.out.println(a[13] + " : " + a[17]);
				if ((a[13].substring(1, a[13].length()-1)).equals("National Citizens Alliance")) 
				{
					//System.out.println(a[7]);
					if ((a[7].substring(1,a[7].length()-1)).equals("")) {total += Integer.parseInt(a[17]); System.out.println(a[17]);}
					//write.write(line + "\n");
				}
			}
			
			System.out.println(total);
			sc_old.close();
			//write.close();
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		
		// 13, 17
		
		//System.out.println(new File("src\\collect_electoral_data\\pollresults\\pollresults_resultatsbureau48021.csv").getAbsolutePath());
		
		//System.out.println( sc1.nextLine() );
		
		
		
		/*
		while (true)
		{
			System.out.println("1 for toPrint. 2 for FPPResult(). Other button breaks.");
			Integer a = Integer.parseInt(sc1.nextLine());
			
			if (a==1)
				System.out.println(election_object);
			else if (a==2)
				
				;
			else break;
			
		}
		*/
		
		

		/*
		ArrayList<Candidate> a = new ArrayList<>();
		
		a.add(new Candidate("darcy","mazloum","","ndp",12));
		a.add(new Candidate("calvin","mazloum","","liberal",89));
		//a.add(new Candidate("calvin","mazloum","","liberal",321));
		//a.add(new Candidate());
		//a.add(new Candidate());
		
		Iterator<Candidate> b = a.iterator();
		
		while (b.hasNext())
		{
			Candidate c = b.next();
			c.addVotes(12);
			Candidate d = new Candidate("calvin","mazloum","","liberal",89);
			if (c.equals(d)) System.out.println("yup");
			System.out.println(c + " - " + c.hashCode() + " - " + Objects.hash(c.getName(),c.getParty()));
		}
		

		//System.out.println(a.get(0));
		*/
	}

}
