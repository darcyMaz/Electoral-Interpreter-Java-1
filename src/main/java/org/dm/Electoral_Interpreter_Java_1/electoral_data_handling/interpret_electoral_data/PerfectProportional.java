package org.dm.Electoral_Interpreter_Java_1.electoral_data_handling.interpret_electoral_data;

import java.util.HashMap;
import java.util.TreeMap;

import org.dm.Electoral_Interpreter_Java_1.electoral_data_handling.collect_electoral_data.*;

/**
 * Returns the result of a perfectly proportional electoral system in CSV format.
 * @author 15148
 *
 */
public class PerfectProportional implements Electoral_System 
{

	@Override
	public String execute(Canada canada) 
	{
		String toReturn = "";
		
		HashMap<String,Integer> party_to_vote = new HashMap<>();
		
		for ( Province province : canada.getProvinces() )
		{
			for ( Riding riding : province.getRidings() )
			{
				for ( Candidate candidate : riding.getCandidates() )
				{
					// what should this one be... lets make it my epic political system based on points and not seats!
					// actually, i'll start by making a perfectly proportional system first!
					
					party_to_vote.putIfAbsent( candidate.getParty(), 0 );
					party_to_vote.put(candidate.getParty(), party_to_vote.get(candidate.getParty()) + candidate.getVotes());
					
				}
			}
		}
		
		HashMap<String,Integer> party_to_seats = new HashMap<>();
		TreeMap<Double, String> extra_seats = new TreeMap<>();
		
		int seats_given = 0;
		
		for ( String party : party_to_vote.keySet() )
		{
			double percentage = ( (double) party_to_vote.get(party) )  /  ( (double) canada.getTotalVotesCast() );
			double seats = (  (  percentage ) ) * 338.0;
			int seatsInt = (int) seats;
			
			seats_given += seatsInt;
			party_to_seats.put(party, seatsInt);
			extra_seats.put(seats - ( (double) seatsInt), party);
		}
		
		for ( Double remainder : extra_seats.descendingKeySet() )
		{
			if (seats_given == 338) break;
			party_to_seats.put( extra_seats.get(remainder) , party_to_seats.get( extra_seats.get(remainder) ) + 1);
			seats_given++;
		}
		
		for ( String party : party_to_vote.keySet() )
		{
			toReturn += party + " : " + party_to_seats.get(party) + ",";
		}
		
		return toReturn.substring( 0, toReturn.length()-1);
	}

}
