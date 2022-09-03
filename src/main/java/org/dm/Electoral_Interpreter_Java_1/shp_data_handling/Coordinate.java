package org.dm.Electoral_Interpreter_Java_1.shp_data_handling;

public class Coordinate 
{
	
	final double latitude;
	final double longitude;
	
	Coordinate( double pLat, double pLong )
	{
		latitude = pLat;
		longitude = pLong;
	}
	
	double longitude()
	{
		return longitude;
	}
	
	double latitude()
	{
		return latitude;
	}
	
}
