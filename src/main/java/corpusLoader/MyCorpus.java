/**
 * 
 */
package corpusLoader;

import java.io.BufferedWriter;
import java.io.FileInputStream;   
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonStreamParser;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;




public class MyCorpus{

	private String filelocation_review;
	private String filelocation_business;
	private String filelocation_pos;
	private List<String> restaurants = new ArrayList<String>();
	private Set<String> allTerms = new HashSet<String>();
	
	
	public MyCorpus(String filelocation_review, String filelocation_business, String filelocation_pos) {
		this.filelocation_review = filelocation_review;
		this.filelocation_business = filelocation_business;
		this.filelocation_pos = filelocation_pos;
		
	}
	

	public List<String> business_identifier() throws FileNotFoundException, UnsupportedEncodingException {
	   //int counter = 0;	
	   InputStream is_b = new FileInputStream(filelocation_business);
	   Reader r_b = new InputStreamReader(is_b, "UTF-8");
	   Gson gson_b = new GsonBuilder().create();
	   JsonStreamParser p = new JsonStreamParser(r_b);
	   while (p.hasNext()) {
	      JsonElement e = p.next();
	      if (e.isJsonObject()) {
	          business_identifier identifier = gson_b.fromJson(e, business_identifier.class);     
	          boolean isRestaurant = identifier.contains_key("RestaurantsPriceRange2");
	          if (isRestaurant == true) {
	        	  restaurants.add(identifier.get_id());
	          } 
	          //if (counter == 10) {
	            // break;  
	          //}
	          //counter += 1;
	          
	      }
	   }
	   return restaurants;

	}
		
	
	public void review_loader() throws FileNotFoundException, UnsupportedEncodingException {
		int counter = 0;
	    String pattern ="[\\p{Punct}&&[^@',&]]";
		Properties props = new Properties();
	    // set the list of annotators to run
	    props.setProperty("annotators", "tokenize,ssplit,pos,lemma");
	    // set a property for an annotator, in this case the coref annotator is being
	    // build pipeline
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	    
		MaxentTagger tagger = new MaxentTagger(filelocation_pos);
		InputStream is_r = new FileInputStream(filelocation_review);
		Reader r_r = new InputStreamReader(is_r, "UTF-8");
		Gson gson_r = new GsonBuilder().create();
		JsonStreamParser p = new JsonStreamParser(r_r);
		while (p.hasNext()) {
			counter += 1;
			JsonElement e = p.next();
			if (e.isJsonObject()) {
				Review review = gson_r.fromJson(e, Review.class);
				if (restaurants.contains(review.get_id())) {
					HashSet<String> review_terms = review.stanford_pipeline_tagger(pipeline, pattern);
					allTerms.addAll(review_terms);
					System.out.println("size:" + allTerms.size() + "reviews processed: " + counter);

					// review.get_sentences();
					// review.pos_tagger(tagger);
					// ArrayList<String> terms_in_review = review.get_adj_verb_noun();
					// allTerms.addAll(terms_in_review);
					// System.out.println(allTerms.size());
					// System.out.println(sentences_list);
					// System.out.println(verbs);					
				}
				}
			}
		}
	
	
	public void review_loader_own() throws FileNotFoundException, UnsupportedEncodingException {
		String[] needed_tags = {"VB","VBD", "VBG", "VBN","VBP","VBZ","VH","VHD","VHG","VHN","VHP","VHZ","VV","VVD","VVG","VVN","VVP","VVZ","JJ","JJR","JJS","NN","NNS","NP","NPS" };
		ArrayList<String> needed_tags_l = new ArrayList<String>(Arrays.asList(needed_tags));
		int counter = 0;
		MaxentTagger tagger = new MaxentTagger(filelocation_pos);
		InputStream is_r = new FileInputStream(filelocation_review);
		Reader r_r = new InputStreamReader(is_r, "UTF-8");
		Gson gson_r = new GsonBuilder().create();
		JsonStreamParser p = new JsonStreamParser(r_r);
		while (p.hasNext()) {
			counter += 1;
			JsonElement e = p.next();
			if (e.isJsonObject()) {
				Review review = gson_r.fromJson(e, Review.class);
				if (restaurants.contains(review.get_id())) {
					review.get_pos_tags(tagger);
					HashSet<String> terms_in_review = review.get_adj_noun_verb_new(needed_tags_l);
					allTerms.addAll(terms_in_review);
					System.out.println("size:" + allTerms.size() + "reviews processed: " + counter);
					// System.out.println(verbs);					
				}
				}

			}
		}
	
	public void write_to_file() throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter("E:\\OutputTerms\\Output_stanford.txt"));
		Iterator<String> it = allTerms.iterator(); // why capital "M"?
		while(it.hasNext()) {
		    out.write(it.next());
		    out.newLine();
		}
		out.close();

	}
		
	
	public static void main(String args[]) throws IOException {
		// WHEN YOU RUN THE FILE CHANGE THE 3 FILELOCATIONS OF THE MYCORPUS CLASS!
		MyCorpus yelp_dataset = new MyCorpus("E:\\review.json", "E:\\business.json", "C:\\Users\\Ruben\\git\\Heracles\\stanford-postagger-2018-10-16\\models\\english-left3words-distsim.tagger");
		List<String> restaurants = yelp_dataset.business_identifier();
		// for(int i =0; i < rest.size(); i++) {
		//	  if (rest.lastIndexOf(rest.get(i)) != i)  {
		// 	     System.out.println(rest.get(i)+" is duplicated");
	    //      }
		//   }
		yelp_dataset.review_loader();
		yelp_dataset.write_to_file();
		System.out.println(yelp_dataset.allTerms);
			}
	
}
