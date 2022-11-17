package com.ivant.cms.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.ivant.cms.entity.baseobjects.CompanyBaseObject;

@Entity(name="TruecareTestimonial")
@Table(name="truecare_testimonials")
public class TruecareTestimonial extends CompanyBaseObject{
	
	private String name, email;
	private String testimonials;
	private int ratings;
	
	@Basic
	@Column(name = "testi_name", length = 100, nullable = true)
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	@Basic
	@Column(name = "email", length = 100, nullable = true)
	public String getEmail(){
		return email;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	@Basic
	@Column(name = "testimonial", length = 1000, nullable = true)
	public String getTestimonial(){
		return testimonials;
	}
	
	public void setTestimonial(String testimonials){
		this.testimonials = testimonials;
	}
	
	@Basic
	@Column(name = "testi_rating", length = 5, nullable = true)
	public int getRating(){
		return ratings;
	}
	
	public void setRating(int ratings){
		this.ratings = ratings;
	}
}
