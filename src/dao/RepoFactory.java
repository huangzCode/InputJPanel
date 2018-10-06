package dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.UUID;

import com.j256.ormlite.dao.Dao;  
import com.j256.ormlite.dao.DaoManager;  
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;


public class RepoFactory 
{
	static ConnectionSource connectionSource;
	static String databaseUrl = "jdbc:sqlite:pinyin.db";
	
	static SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static
	{
		try 
		{
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		 try 
		 {
			connectionSource = new JdbcConnectionSource(databaseUrl);
		 } 
		 catch (SQLException e) 
		 {
			e.printStackTrace();
		 }
	}
	public static  <T, TEntity, TKey> Dao<TEntity,TKey> createDAO(java.lang.Class<TEntity> cls)
	{
		try 
		{
			return DaoManager.createDao(connectionSource, cls);
			
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}
