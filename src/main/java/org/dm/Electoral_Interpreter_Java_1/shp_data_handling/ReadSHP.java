package org.dm.Electoral_Interpreter_Java_1.shp_data_handling;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.geotools.data.DataStore;
import org.geotools.data.DataStoreFinder;
import org.geotools.data.FeatureSource;
import org.geotools.feature.FeatureCollection;
import org.geotools.feature.FeatureIterator;
import org.opengis.feature.Feature;
import org.opengis.feature.GeometryAttribute;
import org.opengis.feature.Property;

public class ReadSHP 
{
	private static boolean debug = false;
	
	
	public static void main(String[] args)
	{
		execute();
		
		//String prop_one = "MULTIPOLYGON (((7150029.354285714 885072.3457142857, 7150009.368571429 885071.58, 7150008.985714286 885081.5714285715)))";
		//prop_one = prop_one.substring(16,prop_one.length()-3);
		//System.out.println(prop_one);
		
	}
	
	// https://stackoverflow.com/questions/2044876/does-anyone-know-of-a-library-in-java-that-can-parse-esri-shapefiles
	@SuppressWarnings("rawtypes")
	public static HashMap<Integer, HashMap<String,Polling_Division_SHP> > execute()
	{
		File file = new File("C:\\Users\\15148\\OneDrive\\Desktop\\Programming\\Personal Programming\\Java Stuff\\Electoral-Interpreter-Java-1\\src\\resources\\SHP Format\\PD_CA_2021_EN.shp");
		HashMap<Integer, HashMap<String,Polling_Division_SHP> > toReturn = new HashMap<>();
		
		try {
		  Map<String, String> connect = new HashMap<>();
		  connect.put("url", file.toURI().toString());

		  DataStore dataStore = DataStoreFinder.getDataStore(connect);
		  String[] typeNames = dataStore.getTypeNames();
		  String typeName = typeNames[0];

		  System.out.println("Reading content " + typeName);

		  FeatureSource featureSource = dataStore.getFeatureSource(typeName);
		  FeatureCollection collection = featureSource.getFeatures();
		  FeatureIterator iterator = collection.features();  // second one probably

		  try {
		    while (iterator.hasNext()) {
		      Feature feature = iterator.next();  // fifth one only when paired with the second one on FeautureIterator
		      @SuppressWarnings("unused")
		      GeometryAttribute sourceGeometry = feature.getDefaultGeometryProperty();
		      
		      
		      for ( Property property : feature.getProperties() )
		      {  
		    	System.out.print( property.getValue() + ", ");
		      }
		      
		      
		      Iterator<Property> properties = feature.getProperties().iterator();
	    	  
	    	  // Description of each property.getValue() of which there are 10
		      
		      // 1) The set of coordinates which make up the polygon. ex. "MULTIPOLYGON ((( x y, x y, x y, ..., x y)))"
		      // 2) Unique national polling district number. ex. "1"
		      // 3) Unique national polling district number Suffix. Usually 0 representing nothing but can be some letter.
		      // 4) Polling district type. Can be either N, S, or M (I think, I can check the guidebook)
		      // 5) Federal Riding Number. ex. "35020"
		      // 6) Advanced Polling Number. ex. 609
		      // 7) Polling District Number (specific to the riding).
		      // 8) Shape Length (String but make it a Double)
		      // 9) Shape Area (see 8)
		      // 10) Polling District Number (specific to riding) Suffix. ex. "83-A" would be 83A 
		      
		      // Ok better idea that actually isn't factually incorrect
		      // So each line has all of this stuff, so I can take 10 and split by "-" and if 10[1].equals("0") then you get it
		      // then make a hashmap of that to ... ok wait
		      
		      // HashMap<Riding, HashMap<PD_Num,Other Info> >
		      
		  // new Stack<>(); //
	    	  Stack< LinkedList<Coordinate> > coordinates = coordStringToList( properties.next().getValue().toString() );
	    	  
	    	  if (debug) 
	    	  { 
	    		  System.out.println();
	    		  for ( Property property : feature.getProperties() )
			      {  
			    	System.out.print( property.getValue() + ", ");
			      }
	    		  
	    		  break;
	    	  }
	    	  
	    	  String national_pd_num = properties.next().getValue().toString();
	    	  national_pd_num += properties.next().getValue().toString();
	    	  String pd_type = properties.next().getValue().toString();
	    	  
	    	  // There is an error here.
	    	  Integer riding_number = Integer.parseInt( properties.next().getValue().toString() );
	    	  
	    	  String advanced_poll = properties.next().getValue().toString();
	    	  properties.next().getValue().toString();
	    	  double length = Double.parseDouble(properties.next().getValue().toString());
	    	  double area = Double.parseDouble(properties.next().getValue().toString());
	    	  
	    	  String[] poll_array = properties.next().getValue().toString().split("-");
	    	  String poll = poll_array[0];
	    	  
	    	  // There is an error here.
	    	  if (!poll_array[1].equals("0")) poll += poll_array[1];
	    	  
	    	  Polling_Division_SHP current_pd_shp = new Polling_Division_SHP
		    		  (coordinates, length, area, poll, advanced_poll, pd_type, national_pd_num);
	    	  
	    	  
	    	  toReturn.putIfAbsent(riding_number, new HashMap<>());
	    	  toReturn.get(riding_number).put(poll, current_pd_shp);
	    	  
	    	  
		      //System.out.println();
		      
		      
		    }
		    
		    
		  } 
		  finally 
		  {
		    iterator.close();
		  }

		} 
		catch (Throwable e) { e.printStackTrace(); }
		
		return toReturn;
	}
	
	/**
	 * Helper function which turns a String representation of a list of coordinates into a list of Coordinates.
	 * @param prop_one is the first Property in the Feature and represents a collection of coordinates. The format of this String is as follows: "( 0.0 0.0, ..., 0.0 0.0), ( 1.0 1.0, ..., 1.0 1.0)"
	 * @return the lists of Coordinates.
	 */
	private static Stack<LinkedList<Coordinate>> coordStringToList(String prop_one)
	{
		/*
		if (prop_one.substring(0, 23).equals("MULTIPOLYGON (((7034560.82")) 
		{
			System.out.println("Start of debug:");
			System.out.println(prop_one);
			System.out.println(prop_one.substring(15,prop_one.length()-2));
			System.out.println("End of debug.");
		}
		*/
		//System.out.println("Start of debug:");
		//System.out.println(prop_one);
		//System.out.println(prop_one.substring(15,prop_one.length()-2));
		//System.out.println("End of debug.");
		
		
		/**
		 * The inner lists are LinkedLists because their specific order matters.
		 * To be precise, these LinkedLists are looped polygons whose start and end are the same coordinates.
		 */
		Stack< LinkedList<Coordinate> > lists = new Stack<>();
		
		prop_one = prop_one.substring(16,prop_one.length()-2);
		
		// I want to go thru each char and pull out substrings from bracket to bracket.
		// To be precise, each polygon is in the format "( 0.0 0.0, ..., 0.0 0.0), ( 1.0 1.0, ..., 1.0 1.0)" and each one will...
		// ... be represented by their own String in a moment.
		Stack<String> polygons = new Stack<>();
		
		String current_string = "";
		for (int prop_one_index=0;prop_one_index<prop_one.length();prop_one_index++)
		{
			// When the char is ')' it means that a polygon has closed. Polygons are kept in this format "( lat long, lat long, ...)"
			if (prop_one.charAt(prop_one_index)==')') 
			{
				polygons.push(current_string);
				current_string = "";
				
				// I'll explain why this while loop is here but it might be hard to understand.
				// The format of the collection of coordinates is as follows: "( 0.0 0.0, ..., 0.0 0.0), ( 1.0 1.0, ..., 1.0 1.0)"
				// Each bracketed section represents a polygon. Notice that between each polygon is a comma and a space.
				// The code must understand whether the ')' in question has a comma and space after it, or whether it is the final ')'.
				// So, this while loop goes until either another '(' is found (indicating that there is another polygon)...
				// ... or until the ')' in question is not the last char in the String.
						
				while ( prop_one.charAt(prop_one_index)!='(' && prop_one_index<prop_one.length()-1)
				{
					System.out.println(prop_one_index + " " + prop_one.charAt(prop_one_index));
					prop_one_index++;
				}
				
				continue;
			} 
			
			current_string += prop_one.charAt(prop_one_index);
			
			
		}
		
		while ( !polygons.isEmpty() )
		{
			
			LinkedList<Coordinate> list = new LinkedList<>();
			
			String[] coord_strs = polygons.pop().split(",");
			
			for ( String coord_str : coord_strs )
			{
				
				coord_str = coord_str.strip();
				String[] lat_and_long = coord_str.split(" ");
				
				// DEBUG IF STATEMENT
				if (lat_and_long.length == 1) 
				{
					for ( String coord_str_temp : coord_strs )
						System.out.print( coord_str_temp + "---" );
				}
				
				
				// DEBUG TRY-CATCH
				Coordinate current_coord;
				try 
				{
					current_coord = new Coordinate(Double.parseDouble(lat_and_long[0]),Double.parseDouble(lat_and_long[1]));
				}
				catch (Exception e)
				{
					debug = true;
					e.printStackTrace();
					System.out.println("\n" + lat_and_long[0] /* + " " + lat_and_long[1]*/);
					current_coord = new Coordinate(-1.0,-1.0);
					//System.out.println("----- " + prop_one +" -----");
					//System.out.println();
				}
				
				list.add(current_coord);
			}
			
			lists.add(list);
			
		}
		
		return lists;
	}
	
	public static void pause(long timeInMilliSeconds) 
	{

	    long timestamp = System.currentTimeMillis();


	    do {

	    } while (System.currentTimeMillis() < timestamp + timeInMilliSeconds);

	}
}


// so let's take some notes down here.
// i have this class right here which can read thru all of the shp file
// sure, so what? I want all this data implemented into Polling_Divisions.
// Ok and? how? So the best way to do this is to uuuuh uuuh uhh so basically take the class here, get a fuck ton of polling divisions
// and just make some mechanism which puts them in?
// So execute2021() does a bunch of stuff and at the end I call ReadSHP.main() and it reads the SHP, gives a bunch of PDs and then
// when execute2021() has a list of PDs I then have mechanisms in O(1) time which can look at PD's data, find it's respective Riding.Polling_Station and then put it in.
// you've done it again.

// I can preorganize them here. so like... well think about it. HashMap< String, HashMap<String,PD> >
//											where String 1 is Riding number and String 2 is ... wait i have things wrong

// Ok so nice big but nothingburger problem. I understood this thing wrong! As it turns out, PDs and PSs are the same!
// Numbskull alert!
// So anyway, I am going to get rid of the PD class buuuut I still kinda want them maybe so I can get SHP data into PSs.
// I want to also rename PS to PD.


// OK READ THIS BEFORE MORE CODING.
// Interesting stuff. Right now I am trying to fix coordsToString() or whatever it's called.
// It's going good but there is one set of coords where (( happens and )) happens. What? Fixable.
// There's more errors and that's ok. Fix em after.