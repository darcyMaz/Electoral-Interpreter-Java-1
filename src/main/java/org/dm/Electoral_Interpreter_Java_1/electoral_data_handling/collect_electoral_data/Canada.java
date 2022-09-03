package org.dm.Electoral_Interpreter_Java_1.electoral_data_handling.collect_electoral_data ;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * A class representing the electoral data for some Canadian election determined at run-time.
 * At the moment it can only read the data of the most recent election which was in 2021.
 * That object holds 13 Objects of type Province representing the ten provinces and 3 territories.
 * 
 * Note: What I should do is make a Canada interface or abstract class which is extended by Canada2021, Canada2019, etc.
 * 
 * This class statically holds a single Object of its own type.
 * In order to access it one must invoke the static execute(folder_path) function which returns the Canada object.
 * 
 * @author 15148 - Darcy Mazloum
 * 
 */
public class Canada
{
	// HOW TO DO THE THING WITH THIS CANADA CLASS IS INHERITED BY OTHERS:
	// this object is uninitialized... ok wait do a thing that goes this.super() or like ... uuuh... ok idk actually but ill figure it out.
	
	//ok think... so this one should still be an abstract class so i can have abstract methods... or maybe i don't and the original ones are kind of just bland and empty
	public final static Canada canada = new Canada();
	
	// 10 : NL, 11 : PEI, 12 : NS, 13 : NB, 24 : QC, 35 : ON, 46 : MB, 47 : SK, 48 : AB, 59 : BC
	private TreeMap<Integer,Province> provinces;
	private int totalVotesCast;
	
	//private static boolean debug_bool = false;
	
	private Canada()
	{
		provinces = new TreeMap<>();
		totalVotesCast = 0;
		
		provinces.put( 10, new Province("Newfoundland and Labrador") );
		provinces.put( 12, new Province("Nova Scotia") );
		provinces.put( 11, new Province("Prince Edward Islands") );
		provinces.put( 13, new Province("New Brunswick") );
		provinces.put( 24, new Province("Quebec") );
		provinces.put( 35, new Province("Ontario") );
		provinces.put( 46, new Province("Manitoba") );
		provinces.put( 47, new Province("Saskatchewan") );
		provinces.put( 48, new Province("Alberta") );
		provinces.put( 59, new Province("British Columbia") );
		provinces.put( 60, new Province("Yukon") );
		provinces.put( 61, new Province("Northwest Territories") );
		provinces.put( 62, new Province("Nunavut") );	
	}
	
	/**
	 * This function takes as input a string which represents the location of the folder holding election results from 2021.
	 * This function will return an object of type Canada with corresponding electoral data inside of it and accesible via various functions.
	 * 
	 * Note: when adding electors to some polling station, you DO need to worry about merging. 
	 * ex. 500 in Avalon has 71, 512 merges into it with 101. Meaning 500 doesn't include that 101 in the total electors but it DOES include it with the votes cast for diff candidates.
	 * The conclusion to this note is to say if (sc.nextLine.split[spot with merge number]!="") then polling_stations[spot w merge num].electors += electors_from_the_current_poll_station		
	 * 
	 * @param folder_str is the path to folder of CSV files.
	 */
	public static Canada execute2021(String folder_str)
	{
	
		// All right so how am I gonna get SHP data from shp_data_handling.ReadSHP
		// I can only know that once I actually make ReadSHP return things
		// So in ReadSHP i have to uuuh uh uh uuuh make a HashMap that goes...
		
		File folder = new File( folder_str );
		File[] listOfFiles = folder.listFiles();
		
		for (File file : listOfFiles) 
		{
			Scanner sc;
			try 
			{
				Riding current_riding;
				int number_of_candidates_in_riding = 1;
				
				ArrayList<Candidate> candidate_arraylist = new ArrayList<Candidate>();
				
				String[] info;
				
				boolean mergeBool = false; // indicates whether the first polling station (later the second one) merges into different polling station.
				HashMap<String,mergeNode> merge_map = new HashMap<>(); 
				
				Polling_Station current_polling_station;
				String first_poll_num = "";
				String first_poll_name = "";
				int first_electors = 0;
				
				sc = new Scanner(file);
				sc.nextLine(); // This line of code represents getting and ignoring the header.
				
				
				// /-----First Line-----\ //

				info = getNeccesaryInfoFromLine(sc.nextLine());
				
					// If the first line merges, save its number of electors for later mapped from the number of the polling station it merges to.
				if (!info[4].equals(""))
				{
					merge_map.put(info[4], new mergeNode( Integer.parseInt(info[5]), info[2])  );
					mergeBool=true;
				}
				else
				{
					candidate_arraylist.add( new Candidate(info[8], info[6], info[7], info[9], Integer.parseInt(info[10])) );
				}
				current_riding = new Riding(info[1], Integer.parseInt(info[0])); // initialize the riding using info from line 1.
				first_poll_num = info[2];
				first_poll_name = info[3];
				// \-----First Line-----/ //
				
				
				// /-----First Polling Station-----\ //
					// This while loop deals specifically with the first polling station excluding the first line.
					// It reads lines and collects their info until it reads a line which indicates that it's reading data from the next polling station.
					// If the first polling station merges into another, the data is skipped over.
				while (true)
				{	
					info = getNeccesaryInfoFromLine(sc.nextLine());
					
					if (!info[2].equals(first_poll_num))
					{
						if (!mergeBool) 
						{
							// At each of three lines which create Polling_Stations, call the already interpreted SHP data and add it to this new PS.
							current_riding.put( new Polling_Station(first_poll_name,first_poll_num,first_electors,getCandidateList(candidate_arraylist)) );
						}
						mergeBool = false;
						if (!info[4].equals("")) // If the second polling station does merge (if the value denoting where this polling station merges does exist)
						{
							mergeBool = true;
							merge_map.put(info[4], new mergeNode(Integer.parseInt(info[5]), info[2]) );
							break;
						}
						candidate_arraylist.clear();
						candidate_arraylist.add( new Candidate(info[8], info[6], info[7], info[9], Integer.parseInt(info[10])) );
						break;
					}
					number_of_candidates_in_riding++;
					if (mergeBool) continue;
					candidate_arraylist.add( new Candidate(info[8], info[6], info[7], info[9], Integer.parseInt(info[10])) ); 
				}	
				// \-----First Polling Station-----/ //
				
				
				
				// /----- Second Polling Station -----\ //
					// This for loops deals with the second polling station.
				for (int i=0;i<number_of_candidates_in_riding-1;i++)
				{
					if (mergeBool) {sc.nextLine(); continue;}
					
					info = getNeccesaryInfoFromLine(sc.nextLine());
					
					candidate_arraylist.add( new Candidate(info[8], info[6], info[7], info[9], Integer.parseInt(info[10])) ); 
				}
				
				if (!mergeBool) // If this polling station is not merged into another...
				{
					current_polling_station = new Polling_Station(info[3],info[2],Integer.parseInt(info[5]),getCandidateList(candidate_arraylist));
					if ( merge_map.containsKey(info[2]) ) // If the first polling station merges into this one.
					{ 
						current_polling_station.addElectors( merge_map.get(info[2]).getElectors() ,  merge_map.get(info[2]).getPoll() ); 
						merge_map.remove(info[2]);
					}
					current_riding.put(current_polling_station);
				}
				candidate_arraylist.clear();
				// \----- Second Polling Station -----/ //
				
				
				// /----- All Other Polling Station in Current File -----\ //
					// This while loop deals with the rest of the file.
					// i.e. all other polling stations.
				while (sc.hasNextLine())
				{
					mergeBool = false;
					
					info = getNeccesaryInfoFromLine(sc.nextLine());
					
					if (!info[4].equals("")) 
					{
						mergeBool = true;
						merge_map.put(info[4], new mergeNode(Integer.parseInt(info[5]), info[2]) );
					}
					if (!mergeBool) candidate_arraylist.add( new Candidate(info[8], info[6], info[7], info[9], Integer.parseInt(info[10])) );
					
					for (int i=0;i<number_of_candidates_in_riding-1;i++)
					{
						if (mergeBool) {sc.nextLine();continue;}
						
						info = getNeccesaryInfoFromLine(sc.nextLine());
						
						candidate_arraylist.add( new Candidate(info[8], info[6], info[7], info[9], Integer.parseInt(info[10])) ); 
					}
					
					if (!mergeBool) 
					{
						current_polling_station = new Polling_Station(info[3],info[2],Integer.parseInt(info[5]),getCandidateList(candidate_arraylist));
						if ( merge_map.containsKey(info[0]) ) current_polling_station.addElectors(Integer.parseInt(info[5]),info[0]); // If some polling station merges into this one.
						current_riding.put(current_polling_station);
					}
					
					candidate_arraylist.clear();
					
				}
				// \----- All Other Polling Station in Current File/Riding -----/ //
				
				sc.close();
				
				// Add the riding to the province and increment the total number of votes cast.
				canada.provinces.get(current_riding.getNumber()/1000).putRiding(current_riding);
				canada.totalVotesCast += current_riding.getTotalVotesCast();
				
			}
			catch (FileNotFoundException e) 
			{
				System.out.println("collect_electoral_data/Canada.execute2021 tried to open a file. It didn't work.");
				e.printStackTrace();
			}	
			
		}
		return canada;
	}	
	
	/**
	 * Returns all the Province Objects for a given election.
	 * @return a Collection of the Provinces.
	 */
	public Collection<Province> getProvinces() 
	{
		return provinces.values();
	}
	
	/**
	 * Gets a Province object using its two digit integer representation or using a five digit Riding number.
	 * @return a Province.
	 */
	public Province getProvince(int number)
	{	
		
		if (number > 9 && number < 100) 
		{ 
			if (provinces.containsKey(number)) 
				return provinces.get(number);
		}
		if (number > 9999 && number < 100000) 
		{
			if (provinces.containsKey(number/1000)) 
				return provinces.get(number/1000);
		}

		System.err.println("The Canada.getProvince(int number) non-static method was used but the number input was invalid.\n"
				+ "\tFor that reason, the NullProvince was returned in place of a useful Province object.\n"
				+ "The number inputted was: " + number + ".");		
		
		return Province.getNullProvince();
	}
	
	/**
	 * Gets the totalVotesCast in all of Canada for a given election.
	 * @return an int representing the total votes cast.
	 */
	public int getTotalVotesCast() {
		return totalVotesCast;
	}

	/**
	 * Turns an ArrayList<Candidate> into an array of Candidates.
	 * Helper method made for execute2021.
	 * 
	 * @param candidate_num number of candidates.
	 * @param cand_arraylist 
	 * @return
	 */
	private static Candidate[] getCandidateList(ArrayList<Candidate> cand_arraylist)
	{
		Candidate[] toReturn = new Candidate[cand_arraylist.size()];
		
		for (int i=0;i<cand_arraylist.size();i++)
		{
			toReturn[i] = cand_arraylist.get(i);
		}
		
		return toReturn;
	}
	
	
	/**
	 * This function takes as input a line from a pollresults_resultatsbureau file and returns a String array with all
	 * data that is actually needed. Moreover, it fixes the style of the information so it removes
	 * certain quotation marks and white spaces.
	 * 
	 * From input line:
	 * District Number 0 - District Name English 1 - District Name French 2 - Polling Station Number 3 - Polling Station Name 4 - Void (Y/N) 5 
	 * No Poll Held (Y/N) 6 - Merge With 7 - Rejected Ballots 8 - Electors 9 - Family 10 - Mid 11 - First 12
	 * Party English 13 - Party French 14 - Incumbent (Y/N) 15 - Elected (Y/N) 16 - Votes in This Poll 17
	 * 
	 * @return a String array with the following contents in order:  
	 * District Number 0 - District Name 1 - Polling Station Number 2 - Polling Station Name 3 - Merged Polling Station 4 - Electors 5
	 * - Last Name 6 - Middle Name 7 - First Name 8 - Party 9 - Votes 10.
	 * 
	 */
	private static String[] getNeccesaryInfoFromLine(String line_input)
	{
		int important_data_points = 11;
		
		String[] toReturn = new String[important_data_points];
		
		String line_commas = removeBadCommas(line_input);
		String[] line = line_commas.split(",");
		
		toReturn[0] = line[0].strip();
		toReturn[1] = removeDoubleQuotes(line[1]).strip();
		toReturn[2] = removeDoubleQuotes(line[3]).strip();
		toReturn[3] = removeDoubleQuotes(line[4]).strip();
		toReturn[4] = removeDoubleQuotes(line[7]).strip();
		toReturn[5] = line[9].strip();
		toReturn[6] = removeDoubleQuotes(line[10]).strip();
		toReturn[7] = removeDoubleQuotes(line[11]).strip();
		toReturn[8] = removeDoubleQuotes(line[12]).strip();
		toReturn[9] = removeDoubleQuotes(line[13]).strip();
		toReturn[10] = line[17].strip();
		
		return toReturn;
	}
	

	/**
	 * Removes double quotes from a String if the String has that character at the beginning and end.
	 * If it does not have quotes at both the beginning or the end than it returns the original String.
	 * @return a String without double quotes.
	 */
	private static String removeDoubleQuotes(String pString)
	{
		if (pString.charAt(0)==34 && pString.charAt(pString.length()-1)==34) 
			pString = pString.substring(1, pString.length()-1);
		return pString;
	}
	
	
	/**
	 * This method removes commas which lie inside of double quotes. To be specific, it removes those commas which are not meant to be delimiters.
	 * @param line is the raw line from the CSV file.
	 * @return the line without bad commas.
	 */
	private static String removeBadCommas(String line)
	{
		boolean remove = false;
		String toReturn = "";
		
		for (int index=0;index<line.length();index++)
		{
			char current_char = line.charAt(index);
			
			if (current_char==34) 
			{
				if (remove) remove = false;
				else remove = true;
			}
			if (current_char==',')
			{
				if (remove) continue;
			}
			toReturn += current_char;
		}
		return toReturn;
	}
	
	private static class mergeNode
	{
		int electors;
		String poll_station_to_merge;
		
		mergeNode(int pElectors, String pPoll)
		{
			electors = pElectors;
			poll_station_to_merge = pPoll;
		}
		
		public int getElectors()
		{
			return electors;
		}
		public String getPoll()
		{
			return poll_station_to_merge;
		}
	}
	
	public String toXML()
	{
		String toReturn = "<canada>\n";
		
		for ( Province province : provinces.values() )
		{
			toReturn += province.toXML() + "\n";
		}
		
		return toReturn + "</canada>";
	}
	
	@Override
	public String toString()
	{
		String toReturn = "";
		
		Collection<Province> collection = provinces.values();
		
		for ( Province province : collection )
		{
			toReturn += province.toString() + "\n";
		}
		
		
		// SO we're gonna make this into an xml String... later!
		// This function will not be XML, it will just be normal printing out.
		
		return toReturn;
	}
	
	
	
}

