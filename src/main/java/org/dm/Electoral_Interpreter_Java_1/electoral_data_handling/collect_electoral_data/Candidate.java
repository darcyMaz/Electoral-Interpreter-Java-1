package org.dm.Electoral_Interpreter_Java_1.electoral_data_handling.collect_electoral_data ;

import java.util.Objects;

/**
 * This class represents a Candidate in a Canadian election.
 * @author 15148
 *
 */
public class Candidate implements Cloneable
{

	/**
	 * Name of the Candidate.
	 */
	private String first_name;
	
	/**
	 * Middle name of the Candidate.
	 */
	private String middle_name;
	
	/**
	 * Last name of the Candidate.
	 */
	private String last_name;
	
	/**
	 * The party of the Candidate.
	 */
	private String party;
	
	/**
	 * Total votes obtained for the Candidate.
	 */
	private int aVotes;
	
	private static Candidate nullCandidate = new Candidate("NULL_fname","NULL_lname","NULL_mname","NULL_party", 0);
	
	/**
	 * Candidate constructor.
	 * @param fname is the first name.
	 * @param lname is the last name.
	 * @param mname is the middle name.
	 * @param pParty is the party.
	 * @param pVotes is the total votes received by this candidate.
	 */
	public Candidate(String fname, String lname, String mname,String pParty, int pVotes)
	{
		first_name = fname;
		middle_name = mname;
		last_name = lname;
		
		party = pParty;
		
		aVotes = 0;
		addVotes(pVotes);
		
	}

	/**
	 * Returns the name of this Candidate as a String.
	 * @return
	 */
	public String getName() 
	{
		if (middle_name.equals("")) return first_name + " " + last_name;
		return first_name + " " + middle_name + " " + last_name;
	}

	/**
	 * Returns the party affiliation as a String of this Candidate.
	 * @return String representing the party.
	 */
	public String getParty() 
	{
		return party;
	}

	/**
	 * Returns number of votes that some Candidate received.
	 * @return int representing the votes.
	 */
	public int getVotes() 
	{
		return aVotes;
	}
	
	/**
	 * Adds total votes to a Candidate object.
	 * Only accessible within this package because I don't want anyone else able to addVotes to a Candidate!
	 * 
	 * @param pVotes is the votes being added as an int.
	 */
	void addVotes(int pVotes)
	{
		aVotes += pVotes;
	}
	
	/**
	 * Function which returns the nullCandidate object.
	 * The null-object design pattern can be done better when it is a class extending Candidate. Do that later.
	 * 
	 * @return Candidate object as the nullCandidate
	 */
	public static Candidate nullCandidate()
	{
		return nullCandidate;
	}
	
	/**
	 * Clones a Candidate object.
	 * 
	 * @return a copy of this Candidate instance.
	 */
	@Override
	public Candidate clone()
	{
		try 
		{
			return (Candidate) super.clone();
		} 
		catch (CloneNotSupportedException e) 
		{
			e.printStackTrace();
			return nullCandidate();
		}
	}
	
	public String toXML()
	{
		return "<candidate>"
				
				+ "<name>"
				+ getName()
				+ "</name>"
				
				+ "<party>"
				+ getParty()
				+ "</party>"
				
				+ "<votes>"
				+ getVotes()
				+ "</votes>"
				
				+ "</candidate>";
	}
	
	/**
	 * Returns a String which gives the name, party, and number of votes this Candidate received.
	 */
	@Override
	public String toString()
	{
		return getName() + "," + getParty() + "," + getVotes();
	}

	/**
	 * Returns a hash code based on the built-in Objects.hash(name,party).
	 */
	@Override
	public int hashCode()
	{
		return Objects.hash(getName(),getParty());
	}	
	
	/**
	 * Equality based on the overridden hashCode() function where the result of that function depends on the built in Objects.hash(name, party).
	 */
	@Override
	public boolean equals(Object o1)
	{
		return this.hashCode() == o1.hashCode();
	}
	
	
}
