package org.dm.Electoral_Interpreter_Java_1.electoral_data_handling.collect_electoral_data ;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.dm.Electoral_Interpreter_Java_1.shp_data_handling.Coordinate;

/**
 * A Polling_Station is a class which represents one of the real-world subdivisions of electoral ridings.
 * 
 * @author 15148
 *
 */
public class Polling_Station
{
	private ArrayList<Candidate> candidates;
	private String name;
	private String number;
	private int totalElectors;
	private int totalVotesCast;

	/**
	 * Set of coordinates representing this physical area.
	 */
	private List<Coordinate> coordinates; 
	
	/**
	 * The unique national polling division number including its suffix if it has one.
	 */
	private String nat_poll_num;
	
	/**
	 * There are three types of polling districts: . This character determines it.
	 */
	private char poll_type;
	
	/**
	 * Advanced polling number which corresponds to this polling division.
	 */
	private String advanced_poll;
	
	/**
	 * Length of the shape of this polling division.
	 */
	private double length;
	
	/**
	 * Area of this polling division.
	 */
	private double area;
	
	/**
	 * List of Polling Station numbers which have merged into this one.
	 */
	private List<String> merges;
	
	Polling_Station(String pName, String pNumber, int pTotalElectors ,Candidate ... pCandidates)
	{
		name = pName;
		number = pNumber;
		totalElectors = pTotalElectors;
		totalVotesCast = 0;
		candidates = new ArrayList<>();
		merges = new ArrayList<>();
		addList(pCandidates);
	}
	
	void addList(Candidate[] candidate_list)
	{
		for (Candidate candidate : candidate_list)
		{
			candidates.add(candidate);
			totalVotesCast = getTotalVotesCast() + candidate.getVotes();
		}
	}
	
	/**
	 * Gets the name of the Polling_Station.
	 * @return The name of the Polling_Station as a String.
	 */
	public String getName() 
	{
		return name;
	}
	
	/**
	 * Gets the Polling_Station number.
	 * 
	 * @return The Polling_Station number as a String.
	 */
	public String getNumber() 
	{
		return number;
	}
	
	/**
	 * Gets the total number of electors in a Polling_Station.
	 * @return the total number of electors as an integer.
	 */
	public int getTotalElectors()
	{
		return totalElectors;
	}

	/**
	 * Gets the total number of votes cast in a Polling_Station.
	 * @return the total number of votes cast as an integer.
	 */
	public int getTotalVotesCast() 
	{
		return totalVotesCast;
	}

	/**
	 * Updates the total number of electors.
	 * This method exists because a CSV which represents some Candidate-Polling_Station which has merged into another will have
	 * 0 for the candidate vote count but will say the number of electors in it.
	 * 
	 * @param electors
	 */
	void addElectors(int electors, String merged_poll)
	{
		totalElectors += electors;
		merges.add(merged_poll);
	}
	
	/**
	 * This method returns Candidates from this polling station. 
	 * 
	 * @return a collection of Candidates.
	 */
	public Collection<Candidate> getCandidates()
	{
		return candidates;
	}
	
	/**
	 * This method checks if a Polling_Station contains a specific Candidate.
	 * 
	 * @param name of the Candidate as a String.
	 * @param party of the Candidate as a String.
	 * @return boolean which says if the Polling_Station contains a specific Candidate.
	 */
	boolean contains(String name, String party)
	{
		for ( Candidate candidate : candidates )
		{
			if ( Objects.hash(name,party) == candidate.hashCode() ) return true;
		}
		
		return false;
	}
	
	public String toXML()
	{
		String toReturn = "<polling_station>\n"
				+ "<name>"
				+ getName()
				+ "</name>\n"
				
				+ "<number>"
				+ getNumber()
				+ "</number>\n"
				
				+ "<electors>"
				+ getTotalElectors()
				+ "</electors>\n"
				
				;
		
		for ( Candidate candidate : candidates )
		{
			toReturn += candidate.toXML() + "\n";
		}
		
		return toReturn += "</polling_station>";
	}
	
	@Override
	public String toString()
	{
		String toReturn = "Polling Station Name: " + getName() + "," + "Polling Station Number: " + getNumber() + "," + "Total Electors: " + getTotalElectors() + "\n";
		
		for ( Candidate candidate : candidates )
		{
			toReturn += "\t" + candidate.toString() + "\n";
		}
		
		return toReturn;
	}

}
