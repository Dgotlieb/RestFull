package main;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User {
	private String name;
	private String password;
	private String email;
	
	public User(){}
	
	public User(String name, String password, String email) {
		super();
		this.name = name;
		this.password = password;
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public boolean equals(Object o) {
		if(o instanceof User)
		return name.equals(((User)o).getName().equals(name)&&((User)o).getPassword().equals(password));
		throw new ClassCastException();
	}
	@Override
	public String toString() {
		return "Item: "+name+" "+password+ " "+email;
	}
	
}