package journals;

import java.util.ArrayList;
import java.util.List;


public class Paper {
	private String paperTitle;
	private List<Author> authorlist = new ArrayList<>();
	public Paper(String paperTitle) {
		super();
		this.paperTitle = paperTitle;
	}
	public String getPaperTitle() {
		return paperTitle;
	}
	public void setPaperTitle(String paperTitle) {
		this.paperTitle = paperTitle;
	}
	public List<Author> getAuthorlist() {
		return authorlist;
	}
	public void setAuthorlist(List<Author> authorlist) {
		this.authorlist = authorlist;
	}
	
}
