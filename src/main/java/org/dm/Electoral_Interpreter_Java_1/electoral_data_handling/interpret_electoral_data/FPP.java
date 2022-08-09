package org.dm.Electoral_Interpreter_Java_1.electoral_data_handling.interpret_electoral_data;

import java.util.HashMap;

import org.dm.Electoral_Interpreter_Java_1.electoral_data_handling.collect_electoral_data.*;

public class FPP implements Electoral_System
{

	public FPP() {}
	
	/**
	 * Returns the results of an election in CSV format from a Canada object.
	 */
	@Override
	public String execute(Canada canada) 
	{
		
		String winning_candidates = "";
		
		HashMap<String,Integer> party_seat_count = new HashMap<>();
		
		for ( Province province : canada.getProvinces() )
		{
			for ( Riding riding : province.getRidings() )
			{
				int mostVotes = 0;
				Candidate winningCandidate = Candidate.nullCandidate();
				
				for ( Candidate candidate : riding.getCandidates() )
				{
					// first past the post increments a party seat for the winning party
					// if party exists add it else increment by 1 seat
					if (candidate.getVotes() > mostVotes) 
						{winningCandidate = candidate; mostVotes = candidate.getVotes();}
				}
			
				party_seat_count.putIfAbsent(winningCandidate.getParty(),  0);
				party_seat_count.put( winningCandidate.getParty() , party_seat_count.get(winningCandidate.getParty()) + 1);
				
				winning_candidates += winningCandidate.getName() + "," + winningCandidate.getParty() + "," + riding.getNumber() + "," + winningCandidate.getVotes() + "\n";
			}
			
		}
		
		String toReturn = "";
		
		for ( String party : party_seat_count.keySet() )
		{
			toReturn += party + " : " + party_seat_count.get(party) + ",";
		}
		if ( !toReturn.equals("") ) toReturn = toReturn.substring(0, toReturn.length()-1); // This line removes the trailing comma.
		toReturn += "\n";
		
		toReturn += "Name, Party, Riding Number, Votes\n";
		toReturn += winning_candidates;
		
		return toReturn;
	}

}
