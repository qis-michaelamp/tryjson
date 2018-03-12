package com.example.lenovo.tryjson.pojo;

import com.google.gson.annotations.SerializedName;

public class PatientItem{

	@SerializedName("birthday")
	private String birthday;

	@SerializedName("gender")
	private String gender;

	@SerializedName("name")
	private String name;

	@SerializedName("mrn")
	private String mrn;

	@SerializedName("id")
	private int id;

	public void setBirthday(String birthday){
		this.birthday = birthday;
	}

	public String getBirthday(){
		return birthday;
	}

	public void setGender(String gender){
		this.gender = gender;
	}

	public String getGender(){
		return gender;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setMrn(String mrn){
		this.mrn = mrn;
	}

	public String getMrn(){
		return mrn;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	@Override
 	public String toString(){
		return 
			"PatientItem{" + 
			"birthday = '" + birthday + '\'' + 
			",gender = '" + gender + '\'' + 
			",name = '" + name + '\'' + 
			",mrn = '" + mrn + '\'' + 
			",id = '" + id + '\'' + 
			"}";
		}
}