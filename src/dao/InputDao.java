package dao;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;


import dao.PinYin;

public class InputDao {

	public static List<PinYin> findData(String input){
		//ormlite查询
		PinYin pinYin = new PinYin();
		pinYin.setPinyin(input);
		List<PinYin> queryForMatching = null;
		try {
			queryForMatching = RepoFactoryDao.pinyin_dao.queryForMatching(pinYin);
			System.out.println("HZ----InputDao"+queryForMatching);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		System.out.println("HZ----InputDao"+queryForMatching);
		return queryForMatching;
	}
}
