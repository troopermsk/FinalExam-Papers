package journals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Journal {
	private String name;
	private double impactFactor;
	private Set<String> papertitles = new HashSet<>();
	private Map<String, Paper> jpapers = new HashMap<>();
	public Journal(String name, double impactFactor) {
		super();
		this.name = name;
		this.impactFactor = impactFactor;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getImpactFactor() {
		return impactFactor;
	}
	public void setImpactFactor(double impactFactor) {
		this.impactFactor = impactFactor;
	}
	public Set<String> getSet() {
		return papertitles;
	}
	public Map<String, Paper> getMap() {
		return jpapers;
	}
	public Integer getnum() {
		return papertitles.size();
	}
}
