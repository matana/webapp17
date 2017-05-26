package de.uni_koeln.info.webapp.config;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.lucene.classification.KNearestNeighborClassifier;
import org.apache.lucene.classification.SimpleNaiveBayesClassifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.uni_koeln.spinfo.textengineering.ir.classifier.ClassifierStrategy;
import de.uni_koeln.spinfo.textengineering.ir.classifier.LuceneAdapter;
import de.uni_koeln.spinfo.textengineering.ir.classifier.TextClassifier;
import de.uni_koeln.spinfo.textengineering.ir.lucene.Indexer;
import de.uni_koeln.spinfo.textengineering.ir.lucene.Searcher;
import de.uni_koeln.spinfo.textengineering.ir.model.Corpus;
import de.uni_koeln.spinfo.textengineering.ir.model.IRDocument;
import de.uni_koeln.spinfo.textengineering.ir.model.newspaper.NewsCorpus;

@Configuration
public class IRConfig {
	
	private static final String indexDir = "luceneIndex";
	private static final String textDir = "texte/";
	
	/**
	 * filterQuery is used to filter training documents
	 */
	private static final String filterQuery = "spiegel";
	
	@Bean
	public Searcher searcher() throws IOException {
		Indexer indexer = new Indexer(indexDir);
		indexer.index(corpus());
		indexer.close();
		return new Searcher(indexDir);
	}
	
	@Bean
	public Corpus corpus() {
		return new NewsCorpus(textDir);
	}

	@Bean(name = "nb")
	public TextClassifier naiveBayesClassifier() throws IOException {
		Set<IRDocument> trainingSet = new HashSet<IRDocument>(corpus().getDocuments());
		ClassifierStrategy classifier = new LuceneAdapter(new SimpleNaiveBayesClassifier(), indexDir, filterQuery);
		return new TextClassifier(classifier, trainingSet);
	}
	
	@Bean(name = "knn")
	public TextClassifier kNearestNeighborClassifier() throws IOException {
		Set<IRDocument> trainingSet = new HashSet<IRDocument>(corpus().getDocuments());
		ClassifierStrategy classifier = new LuceneAdapter(new KNearestNeighborClassifier(1), indexDir, filterQuery);
		return new TextClassifier(classifier, trainingSet);
	}

}
