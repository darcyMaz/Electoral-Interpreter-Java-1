package org.dm.Electoral_Interpreter_Java_1.electoral_data_handling.collect_electoral_data;

import java.util.Collection;
//import java.util.HashMap;
import java.util.TreeMap;

/**
 * A class which represents a province or territory and holds its electoral data. 
 * @author 15148 - Darcy Mazloum
 *
 */
public class Province
{
	/**
	 * Ridings held in a tree map as to keep them in order by Riding Number.
	 */
	private TreeMap<Integer, Riding> ridings;
	private String name;
	private int totalVotesCast;
	private static Province nullProvince = new Province("NULL_PROVINCE") {};
	
	/**
	 *  The default constructor is intentionally limited to the package.
	 */
	Province() { ridings = new TreeMap<>(); totalVotesCast = 0; }
	Province(String pName, Riding ...pRidings) 
	{
		name = pName;
		ridings = new TreeMap<>();
		totalVotesCast = 0;
		
		putRidingList(pRidings);
	}

	/**
	 * Put a riding into the HashMap ridings.
	 * @param pRiding
	 */
	void putRiding(Riding pRiding)
	{
		ridings.put(pRiding.getNumber(), pRiding);
		totalVotesCast += pRiding.getTotalVotesCast();
	}
	
	/**
	 * Put a list of ridings into the HashMap ridings.
	 * @param list
	 */
	void putRidingList(Riding[] list)
	{
		for (Riding riding : list)
		{
			putRiding(riding);
		}
	}
	
	/**
	 * Returns the name of the province.
	 * 
	 * @return the name as a String.
	 */
	public String getName()
	{
		return name;
	}
	
	/**
	 * Returns the total votes cast in this province.
	 * @return total votes cast as an int.
	 */
	public int getTotalVotesCast() 
	{
		return totalVotesCast;
	}
	/**
	 * This function returns all of the ridings from a province. From what I can tell it shouldn't be possible to change them once gotten.
	 * 
	 * @return all of the ridings as an iterator.
	 */
	public Collection<Riding> getRidings()
	{
		return ridings.values();
	}
	
	/**
	 * Get the null province.
	 * @return the nullProvince Province.
	 */
	static Province getNullProvince() 
	{
		return nullProvince;
	}
	
	public String toXML()
	{
		String toReturn = "<province>\n";
		
		for ( Riding riding : ridings.values() )
		{
			toReturn += riding.toXML() + "\n";
		}
		
		return toReturn + "</province>";
	}
	
	@Override
	/**
	 * Returns a string with the name of the province other important information (that info will be added soon).
	 */
	public String toString()
	{
		String toReturn = name + ":\n";
		
		Collection<Riding> riding_collection = ridings.values();
		
		for ( Riding riding : riding_collection )
		{
			toReturn += "\t" + riding.toString() + "\n";
		}
		
		
		return toReturn;
	}
	
}
