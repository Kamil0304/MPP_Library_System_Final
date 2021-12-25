package model;

public class Author extends Person {
    
	private String bio;
	private String credential;

	public String getBio() {
		return this.bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getCredential() {
		return this.credential;
	}

	public void setCredential(String credential) {
		this.credential = credential;
	}

}