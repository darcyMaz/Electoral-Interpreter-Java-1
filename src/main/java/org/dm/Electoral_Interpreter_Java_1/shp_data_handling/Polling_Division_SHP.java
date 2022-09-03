package org.dm.Electoral_Interpreter_Java_1.shp_data_handling;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

/**
 * This class holds certain Polling_Division info which will come from the SHP files and later added to
 * actual Polling_Division representations.
 * @author 15148 - Darcy Mazloum
 *
 */
public class Polling_Division_SHP 
{
	/**
	 * Set of polygons representing the multi-polygon physical area.
	 */
	private Stack< LinkedList<Coordinate> > polygons; 
	
	/**
	 * The unique national polling division number including its suffix if it has one.
	 */
	private String nat_poll_num;

	/**
	 * There are three types of polling districts: . This character determines it.
	 */
	private String poll_type;
	
	/**
	 * Advanced polling number which corresponds to this polling division.
	 */
	private String advanced_poll;
	
	/**
	 * The number of this polling division including suffix. This number is unique to the riding but not unique nationally.
	 */
	private String poll_num;
	
	/**
	 * Length of the shape of this polling division.
	 */
	private double length;
	
	/**
	 * Area of this polling division.
	 */
	private double area;
	
	Polling_Division_SHP( Stack< LinkedList<Coordinate>> pPolygons, double pArea, double pLength, String pPoll_num, String pAdvanced_pol, String pPoll_type, String pNat_pol_num )
	{
		polygons = new Stack<>();
		
		while ( !pPolygons.isEmpty() )
		{
			LinkedList<Coordinate> polygon = pPolygons.pop();
			LinkedList<Coordinate> polygonToAdd = new LinkedList<>();
			
			for ( Coordinate coord : polygon )
			{
				polygonToAdd.add(coord);
			}
			
			polygons.add(polygonToAdd);
		}
		
		area = pArea;
		length = pLength;
		poll_num = pPoll_num;
		advanced_poll = pAdvanced_pol;
		poll_type = pPoll_type;
		nat_poll_num = pNat_pol_num;
	}

	public String getNat_poll_num() 
	{
		return nat_poll_num;
	}

	public String getPoll_type() 
	{
		return poll_type;
	}

	public String getAdvanced_poll() 
	{
		return advanced_poll;
	}

	public String getPoll_num() 
	{
		return poll_num;
	}

	public double getLength() 
	{
		return length;
	}

	public double getArea() 
	{
		return area;
	}
	
	public Iterator< LinkedList<Coordinate> > getPolygons()
	{
		return polygons.iterator();
	}
}
