package de.uni_koeln.info.webapp.controller;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.uni_koeln.spinfo.textengineering.ir.lucene.Searcher;
import de.uni_koeln.spinfo.textengineering.ir.model.IRDocument;

@Controller
@RequestMapping(value = "index/")
public class IndexController {

	
	@Autowired
	private Searcher searcher;

	@RequestMapping(value = "size", method = RequestMethod.GET, produces = "text/plain")
	public @ResponseBody String indexSize() {
		return String.valueOf(searcher.indexSize());
	}

	/**
	 * 
	 * @param searchPhrase
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	@RequestMapping(value = "search/{searchPhrase}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<IRDocument> search(@PathVariable("searchPhrase") String searchPhrase)
			throws IOException, ParseException {
		return searcher.search(searchPhrase, 10);
	}

	@RequestMapping(value = "search/{field}/{searchPhrase}", method = RequestMethod.GET, produces = "application/json")
	public @ResponseBody List<IRDocument> searchField(@PathVariable("field") String field,
			@PathVariable("searchPhrase") String searchPhrase) throws IOException, ParseException {
		return searcher.search(searchPhrase, field, 10);
	}

}
