package gr.ntua.ece.softeng.entities;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import gr.ntua.ece.softeng.entities.Parent;

enum state { OPEN, IN_PROGRESS, FINISHED }

@Entity
public class Event {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long Id;
	private String eventname;
	private String description;
	private String Area;
	private String StreetName;
	private Integer StreetNumber;
	private Integer AgeGroup;
	private Integer capacity;
	private Integer price;
	private String category;
	private state state;	
	
	
	@ManyToOne
	@JsonIgnore
	private Providers provider;
	
	
	@ManyToMany(mappedBy = "events")
	@JsonIgnore
	private Set<Parent> parents;
	
	
	public Set<Parent> getParents() {
		return parents;
	}

	public void setParents(Set<Parent> parents) {
		this.parents = parents;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	
	public Providers getProvider() {
		return provider;
	}

	public void setProvider(Providers provider) {
		this.provider = provider;
	}


	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public state getState() {
		return state;
	}

	public void setState(state state) {
		this.state = state;
	}
	
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getEventname() {
		return eventname;
	}

	public void setEventname(String eventname) {
		this.eventname = eventname;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getArea() {
		return Area;
	}

	public void setArea(String area) {
		Area = area;
	}

	public String getStreetName() {
		return StreetName;
	}

	public void setStreetName(String streetName) {
		StreetName = streetName;
	}

	public Integer getStreetNumber() {
		return StreetNumber;
	}

	public void setStreetNumber(Integer streetNumber) {
		StreetNumber = streetNumber;
	}

	public Integer getAgeGroup() {
		return AgeGroup;
	}

	public void setAgeGroup(Integer ageGroup) {
		AgeGroup = ageGroup;
	}
	

}