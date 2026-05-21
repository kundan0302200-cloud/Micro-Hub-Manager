package mth.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long sid;
	@Column
	private String sname;
	@Column
	private String srollnum;
	
	public long getSid() {
		return sid;
	}
	public void setSid(long sid) {
		this.sid = sid;
	}
	public String getSname() {
		return sname;
	}
	public void setSname(String sname) {
		this.sname = sname;
	}
	public String getSrollnum() {
		return srollnum;
	}
	public void setSrollnum(String srollnum) {
		this.srollnum = srollnum;
	}
	@Override
	public String toString() {
		return "Student [sid=" + sid + ", sname=" + sname + ", srollnum=" + srollnum + "]";
	}
	
	
}
