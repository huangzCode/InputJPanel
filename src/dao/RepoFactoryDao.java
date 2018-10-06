package dao;

import com.j256.ormlite.dao.Dao;

import dao.PinYin;

public class RepoFactoryDao {

	public static final Dao<PinYin, String> pinyin_dao = RepoFactory.createDAO(PinYin.class);

}
