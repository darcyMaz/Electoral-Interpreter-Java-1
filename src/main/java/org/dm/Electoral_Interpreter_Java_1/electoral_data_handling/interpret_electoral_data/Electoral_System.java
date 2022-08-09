package org.dm.Electoral_Interpreter_Java_1.electoral_data_handling.interpret_electoral_data;

import org.dm.Electoral_Interpreter_Java_1.electoral_data_handling.collect_electoral_data.Canada;

/**
 * Interface representing a function object capable of returning the String representing the results of an election in CSV format as so:
 * 
 * "Party 1 - Seat Number, Party 2 - Seat Number, ... \n Other Info, ..."
 * 
 * @author Darcy Mazloum - 15148
 *
 */
public interface Electoral_System 
{
	
	/**
	 * Returns the String upon execution of an Electoral_System object. This string is in the file of a CSV file
	 * and contains the party with their seat count and other relevant information.
	 * 
	 * @return a String which can be printed onto a CSV file.
	 */
	public String execute(Canada canada);

}
