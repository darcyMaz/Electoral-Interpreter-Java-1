package org.dm.Electoral_Interpreter_Java_1.shp_data_handling;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

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
	// THIS IS IT BUDDY HAHAHAHA!!!!
	// https://stackoverflow.com/questions/2044876/does-anyone-know-of-a-library-in-java-that-can-parse-esri-shapefiles
	@SuppressWarnings("rawtypes")
	public static void main(String[] args)
	{
		File file = new File("C:\\Users\\15148\\OneDrive\\Desktop\\SHP Format\\PD_CA_2021_EN.shp");

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
		      
		      boolean first = true;
		      for ( Property prop : feature.getProperties() )
		      {
		    	  if (first) {first=false;continue;}
		    	  System.out.print( prop + " +++ " + prop.getValue() + "----------");
		      }
		      
		      System.out.println();
		    }
		  } finally {
		    iterator.close();
		  }

		} catch (Throwable e) {}
	}
}
