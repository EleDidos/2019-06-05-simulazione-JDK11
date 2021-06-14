package it.polito.tdp.crimes.db;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.crimes.model.Distretto;
import it.polito.tdp.crimes.model.Event;



public class EventsDao {
	
	public void loadAllVertici(Map <Integer, Distretto> idMap){
		String sql = "SELECT DISTINCT district_id "
				+ "FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
		
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Distretto d = new Distretto (res.getInt("district_id"));
				idMap.put(res.getInt("district_id"), d);
			}
			
			conn.close();
			return;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return ;
		}
	}
	
	public void loadEvents(Distretto d, Integer year){
		String sql = "SELECT * "
				+ "FROM events "
				+ "WHERE district_id=? and YEAR(reported_date)=?" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			st.setInt(1, d.getID());
			st.setInt(2, year);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				
					Event e = new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic"));
					d.addEvent(e);
				
			}
			
			conn.close();
			return  ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return  ;
		}
	}
	
	
	
	

}
