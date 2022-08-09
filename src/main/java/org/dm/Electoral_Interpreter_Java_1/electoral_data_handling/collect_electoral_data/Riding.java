package org.dm.Electoral_Interpreter_Java_1.electoral_data_handling.collect_electoral_data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * A Riding is an Object representing the electoral data from a riding in a Canadian election.
 * It has a HashMap of polling_stations where the key is their polling number as a String.
 * 
 * You can add Polling_Stations through the constructor, by using any HashMap function, or by using the
 * two custom functions putList(Polling_Station[]) or the overloaded put(Polling_Station).
 * 
 * @author 15148 - Darcy Mazloum
 *
 */
public class Riding
{
	/**
	 * All of the Polling_Stations in some Riding in HashMap form.
	 * The key is the Polling Station number and the value is the Polling Station.
	 */
	private HashMap<String,Polling_Station> polling_stations;
	private ArrayList<Candidate> candidates;
	private int totalVotesCast;
	private String name; // Name of riding in English
	private int number;  // Number of riding (held as an int)
	
	/**
	 * Total electors represents the total electors as described in the data.
	 * However, it seems some of this data is incomplete.
	 */
	private int totalElectors = 0;
	
	
	Riding(String pName, int pNumber, Polling_Station ... polling_Stations )
	{
		name = pName;
		number = pNumber;
		totalVotesCast = 0;
		
		polling_stations = new LinkedHashMap<>( );
		
		candidates = new ArrayList<>();
		
		putList(polling_Stations);
	}
	
	/**
	 * Puts a list of polling stations to this riding.
	 * 
	 * @param list of Polling Stations being put into HashMap.
	 */
	void putList(Polling_Station[] list)
	{
		for (Polling_Station poll_station : list)
		{
			put(poll_station);
		}
	}
	
	/**
	 * Puts a Polling_Station into the HashMap but this function only takes one parameter.
	 * This function updates the list of Candidates in this Riding object.
	 * 
	 * @param polling_station is being put into HashMap.
	 */
	void put(Polling_Station polling_station)
	{
		
		polling_stations.put(polling_station.getNumber(), polling_station);
		totalVotesCast += polling_station.getTotalVotesCast();
		
		//if (polling_station.getNumber().equals("95")) 
			//System.out.print(" ")
			//;
		
		// Here I must update the Candidate arrayList. 
		// if empty then add them
		// else update
		
		// I must also update the total electors.
		totalElectors += polling_station.getTotalElectors();
		
		//<Candidate> new_Candidates = polling_station.getCandidates();
		
		// If this riding has no candidates inside of it yet, we must add them.
		if (candidates.size()==0) 
		{
			// Add all of these new_Candidates from the polling_station to the ArrayList this.candidates.
			for ( Candidate candidate : polling_station.getCandidates() )
			{
				//Candidate new_Candidate = candidate.clone();
				candidates.add( candidate.clone() );
			}
			return;
		}
		
		// the candidates already exist so we must update them.
		for ( Candidate candidate : polling_station.getCandidates() )
		{
			//Candidate new_Candidate = candidate;
			
			// Find the Candidate object which equals the new_Candidate
			// This for loop will not execute if there are no candidates in the candidates array yet.
			for (int index=0 ;index<candidates.size(); index++)
			{
				Candidate current_Candidate = candidates.get(index);
				if (candidate.equals(current_Candidate)) // ok we're gonna check real votes in excel vs what shows up here. (all before adding 95) ndp: 2070
				{
					current_Candidate.addVotes( candidate.getVotes() );
					break;
				}	
			}	
		}	
		
	}
	
	/**
	 * Gets the name of the riding.
	 * @return name.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Gets the riding number.
	 * @return number.
	 */
	public int getNumber()
	{
		return number;
	}
	
	/**
	 * Gets the number of electors in a riding.
	 * @return the number of electors.
	 */
	public int getTotalElectors() 
	{
		return totalElectors;
	}
	
	public int getTotalVotesCast() {
		return totalVotesCast;
	}

	/**
	 * This method returns Candidates from this riding. 
	 * All Candidates have all the data found in the Polling_Stations in this Riding.
	 * 
	 * @return an iterator of Candidates.
	 */
	public Collection<Candidate> getCandidates()
	{
		return candidates;
	}
	
	/**
	 * Function lets one see the Collection of polling stations in this Riding.
	 * @return a Collection of Polling_Stations
	 */
	public Collection<Polling_Station> getPollingStations()
	{
		return polling_stations.values();
	}
	
	public String toXML()
	{
		String toReturn = "<riding>\n"
				+ "<name>"
				+ getName()
				+ "</name>\n"
				
				+ "<number>"
				+ getNumber()
				+ "</number>\n"
		;
		
		for ( Polling_Station ps : polling_stations.values() )
		{
			toReturn += ps.toXML() + "\n";
		}
		
		return toReturn += "</riding>";
	}
	
	@Override
	public String toString()
	{
		String toReturn = "Riding Name: " + name + ". Riding Number: " + number + "." + "\n";
		
		//Collection<Polling_Station> polling_stations_coll = polling_stations.values();
		
		/*
		for ( Polling_Station polling_station : polling_stations_coll )
		{
			toReturn += polling_station.toString() ;//+ "\n";
		}
		*/
		
		return toReturn;
	}
	
}
