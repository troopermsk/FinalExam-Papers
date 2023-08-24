package journals;

public class Author {
	private String autname;
	private double impact;
	private int countPapers;
	public Author(String autname) {
		super();
		this.autname = autname;
	}

	public String getAutname() {
		return autname;
	}

	public void setAutname(String autname) {
		this.autname = autname;
	}
	public void setImpact(double imp){
		this.impact = this.impact + imp;
	}
	public double getImpact() {
		return impact;
	}
	public Integer getCount() {
		return countPapers;
	}
	public void setCount(int c) {
		this.countPapers = this.countPapers + c;
	}
}
