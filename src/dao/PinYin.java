package dao;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@XmlRootElement
@DatabaseTable(tableName = "pinyin")
public class PinYin {
	
	@DatabaseField(id = true)
	@JsonProperty()
	public int ID;
	
	@DatabaseField()
	@JsonProperty()
	public String hanzi;		
	
	@DatabaseField()
	@JsonProperty()
	public String pinyin;
	
	
	public int getId() {
		return ID;
	}
	public void setId(int id) {
		this.ID = id;
	}
	public String getHanzi() {
		return hanzi;
	}
	public void setHanzi(String hanzi) {
		this.hanzi = hanzi;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	@Override
	public String toString() {
		return "PinYin [id=" + ID + ", hanzi=" + hanzi + ", pinyin=" + pinyin + "]";
	}
	
	
}
