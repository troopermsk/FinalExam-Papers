package journals;

import java.util.*;
import java.util.stream.Collectors;

public class Journals {
	private Map<String, Journal> jmap = new HashMap<>();
	private Set<Double> impactfactors = new TreeSet<>();
	private SortedMap<Double, List<String>> impactmap = new TreeMap<>();
	private Map<String, Author> autmap = new HashMap<>();
	private SortedMap<String, Integer> numPapers = new TreeMap<>();
	private SortedMap<String, Integer> autPapers = new TreeMap<>();
	//R1 
	/**
	 * inserts a new journal with name and impact factor. 
	 * 
	 * @param name	name of the journal
	 * @param impactFactor relative impact factor
	 * @return  the number of characters of the name
	 * @throws JException if the journal (represented by its name) already exists
	 */
	public int addJournal (String name, double impactFactor) throws JException {
		if(jmap.containsKey(name))
			throw new JException("Journal has already been defined");
		jmap.put(name, new Journal(name, impactFactor));
		impactfactors.add(impactFactor);
		return name.length();
	}

	/**
	 * retrieves the impact factor of the journal indicated by the name
	 * 
	 * @param name the journal name
	 * @return the journal IF
	 * @throws JException if the journal does not exist
	 */
	public double getImpactFactor (String name) throws JException {
		if(!jmap.containsKey(name))
			throw new JException("Journal with name: "+name+" is not defined.");
		return jmap.get(name).getImpactFactor();
	}

	/**
	 * groups journal names by increasing impact factors. 
	 * Journal names are listed in alphabetical order
	 * 
	 * @return the map of IF to journal
	 */
	public SortedMap<Double, List<String>> groupJournalsByImpactFactor () {
		List<String> sorted;
		for(Double d : impactfactors) {
			impactmap.put(d, new ArrayList<>());
		}
		for(Double d : impactfactors) {
			sorted = new ArrayList<>();
			for(String s : jmap.keySet()) {
				if(jmap.get(s).getImpactFactor() == d)
					sorted.add(s);
			}
			sorted = sorted.stream().sorted().collect(Collectors.toList());
			impactmap.get(d).addAll(sorted);
		}
		
		return impactmap;
	}

	//R2
	/**
	 * adds authors. 
	 * Duplicated authors are ignored.
	 * 
	 * @param authorNames names of authors to be added
	 * @return the number of authors entered so far
	 */
	public int registerAuthors (String... authorNames) {
		for(String s : authorNames) {
			if(!autmap.containsKey(s))
				autmap.put(s, new Author(s));
		}
		return autmap.size();
	}
	
	/**
	 * adds a paper to a journal. 
	 * The journal is indicated by its name; 
	 * the paper has a title that must be unique in the specified journal,
	 * the paper can have one or more authors.
	 * 
	 * @param journalName
	 * @param paperTitle
	 * @param authorNames
	 * @return the journal name followed by ":" and the paper title.
	 * @throws JException if the journal does not exist or the title is not unique within the journal or not all authors have been registered
	 */
	public String addPaper (String journalName, String paperTitle, String... authorNames) throws JException {
		if(!jmap.containsKey(journalName))
			throw new JException("Journal does not exist");
		if(jmap.get(journalName).getSet().contains(paperTitle)) 
			throw new JException("The journal already has the defined paper title");
		jmap.get(journalName).getMap().put(paperTitle, new Paper(paperTitle));
		jmap.get(journalName).getSet().add(paperTitle);
		for(String s : authorNames) {
			if(!autmap.containsKey(s))
				throw new JException("Author has not been defined");
			Author e = new Author(s);
			autmap.get(s).setCount(1);
			autmap.get(s).setImpact(jmap.get(journalName).getImpactFactor());
			jmap.get(journalName).getMap().get(paperTitle).getAuthorlist().add(e);
		}
		return journalName+":"+paperTitle;
	}
	
	/**
	 * gives the number of papers for each journal. 
	 * Journals are sorted alphabetically. 
	 * Journals without papers are ignored.
	 * 
	 * @return the map journal to count of papers
	 */
	public SortedMap<String, Integer> giveNumberOfPapersByJournal () { //serve toMap
		for(String s : jmap.keySet()) {
			if(jmap.get(s).getSet().size() > 0)
				numPapers.put(s, jmap.get(s).getSet().size());
		}
		return numPapers;
	}
	
	//R3
	/**
	 * gives the impact factor for the author indicated.
	 * The impact factor of an author is obtained by adding 
	 * the impact factors of his/her papers. 
	 * The impact factor of a paper is equal to that of the 
	 * journal containing the paper.
	 * If the author has no papers the result is 0.0.
	 *
	 * @param authorName
	 * @return author impact factor
	 * @throws JException if the author has not been registered
	 */
	public double getAuthorImpactFactor (String authorName) throws JException {
		if(!autmap.containsKey(authorName))
			throw new JException("Author doesn't exist");
		return autmap.get(authorName).getImpact();
	}
	
	/**
	 * groups authors (in alphabetical order) by increasing impact factors.
	 * Authors without papers are ignored.
	 * 
	 * @return the map IF to author list
	 */
	public SortedMap<Double, List<String>> getImpactFactorsByAuthors () {
		return autmap.values().stream().filter(e->e.getImpact()>0).collect(Collectors.groupingBy(Author::getImpact,()-> new TreeMap<>(), Collectors.mapping(Author::getAutname, Collectors.toList())));
	}
	
	
	//R4 
	/**
	 * gives the number of papers by author; 
	 * authors are sorted alphabetically. 
	 * Authors without papers are ignored.
	 * 
	 * @return
	 */
	public SortedMap<String, Integer> getNumberOfPapersByAuthor() {
		autmap.values().stream().filter(e->e.getCount()>0).sorted(Comparator.comparing(Author::getAutname)).forEach(e->autPapers.put(e.getAutname(), e.getCount()));
		return autPapers;
	}
	
	/**
	 * gives the name of the journal having the largest number of papers.
	 * If the largest number of papers is common to two or more journals 
	 * the result is the name of the journal which is the first in 
	 * alphabetical order.
	 * 
	 * @return journal with more papers
	 */
	public String getJournalWithTheLargestNumberOfPapers() {
		Journal j = jmap.values().stream().max(Comparator.comparing(Journal::getnum).thenComparing(Journal::getName, Collections.reverseOrder())).orElse(null);
		return j.getName()+":"+j.getnum();
	}

}

