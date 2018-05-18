package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.ArtObjectsAndCount;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	public int caloclaExhibitionComuni(ArtObject aop, ArtObject aoa) {
		String sql = "select count(*) as cnt/*sarebbe il peso*/\n" + 
				"from exhibition_objects as eo1, exhibition_objects as eo2\n" + 
				"where eo1.exhibition_id=eo2.exhibition_id\n" + 
				"and eo1.object_id=?\n" + 
				"and eo2.object_id=?";
		
		Connection conn = DBConnect.getConnection();

		try {
			
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, aop.getId());
			st.setInt(2, aoa.getId());
			
			ResultSet res = st.executeQuery();
			
			res.next();
			
			int conteggio = res.getInt("cnt");

			conn.close();
			
			return conteggio;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	
	public List<ArtObjectsAndCount> ListObjectAndCount(ArtObject aop) {
		String sql = "select eo1.object_id, count(eo2.exhibition_id) as cnt, eo2.object_id\n" + 
				"from exhibition_objects as eo1, exhibition_objects as eo2\n" + 
				"where eo1.exhibition_id=eo2.exhibition_id\n" + 
				"	and eo1.object_id=?\n" + 
				"	and eo2.object_id>eo1.object_id\n" + 
				"group by eo1.object_id, eo2.object_id";
		
		
		List<ArtObjectsAndCount> result = new ArrayList<>();

		Connection conn = DBConnect.getConnection();

		try {
			
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, aop.getId());
			
			ResultSet res = st.executeQuery();
			
			
			while(res.next()) {
				
				int objIDp = res.getInt("eo1.object_id");
				int objIDa = res.getInt("eo2.object_id");
				int count = res.getInt("cnt");
				ArtObjectsAndCount aoAndC = new ArtObjectsAndCount(objIDp, objIDa, count);
				
				result.add(aoAndC);
			}

			conn.close();
			
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
}
