import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {

	String main_url = "https://www.314e.com";

	HashSet<String> urls = new HashSet<String>();

	HashMap<String, Integer> words_frequency = new HashMap<String, Integer>();

	HashMap<String, Integer> word_pairs_frequency = new HashMap<String, Integer>();

	LinkedHashMap<String, Integer> sortedMap_words = new LinkedHashMap<>();

	LinkedHashMap<String, Integer> sortedMap_word_pairs = new LinkedHashMap<>();

	public static void main(String args[]) {

		Parser p = new Parser();

		p.level_order_traversal();

		p.count_words_hashset();

		p.sort_words_frequency();

		p.display_words_frequency();

		p.sort_words_pairs_frequency();

		p.display_word_pairs_frequency();

	}

	public void sort_words_frequency() {
		
		ArrayList<Integer> list = new ArrayList<>();
		
		for (Map.Entry<String, Integer> entry : words_frequency.entrySet()) {
			
			list.add(entry.getValue());
			
		}
		
		Collections.sort(list, Collections.reverseOrder());

		for (int num : list) {
			
			for (Entry<String, Integer> entry : words_frequency.entrySet()) {
				
				if (entry.getValue().equals(num)) {
					
					sortedMap_words.put(entry.getKey(), num);
					
				}
			}
		}
	}

	public void sort_words_pairs_frequency() {
		
		ArrayList<Integer> list = new ArrayList<>();
		
		for (Map.Entry<String, Integer> entry : word_pairs_frequency.entrySet()) {
			
			list.add(entry.getValue());
			
		}
		Collections.sort(list, Collections.reverseOrder());

		for (int num : list) {
			
			for (Entry<String, Integer> entry : word_pairs_frequency.entrySet()) {
				
				if (entry.getValue().equals(num)) {
					
					sortedMap_word_pairs.put(entry.getKey(), num);
					
				}
			}
		}
	}

	public void display_words_frequency() {
		
		int count = 0;

		for (Map.Entry<String, Integer> entry : sortedMap_words.entrySet()) {
			
			System.out.println("word = " + entry.getKey() + ", frequency = " + entry.getValue());
			
			count++;

			if (count == 10)
				
				break;
		}
	}

	public void display_word_pairs_frequency() {
		
		int count = 0;

		for (Map.Entry<String, Integer> entry : sortedMap_word_pairs.entrySet()) {
			
			System.out.println("word pair = " + entry.getKey() + ", frequency = " + entry.getValue());
			
			count++;

			if (count == 10)
				
				break;
		}

	}

	public void count_words_hashset() {

		for (String link : urls) {
			
			count_words(link);
			count_word_pairs(link);

		}
	}

	public void count_word_pairs(String link) {
		
		Document doc= null;
		
		try {
			
			doc = Jsoup.connect(link).get();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			System.out.println("Exception in count_word_pairs: This URL does not exist or is not valid for this problem");
			
			e.printStackTrace();
		}
		

		if (doc != null) {
			
			String text = doc.body().text();

			String[] sentences = text.split("\\.");

			for (int i = 0; i < sentences.length; i++) {

				String[] words = sentences[i].split("[\\s,;]+");

				for (int j = 0; j < words.length - 1; j++) {
					String word_pair = words[j] + "|" + words[j + 1];

					if (!word_pairs_frequency.containsKey(word_pair)) {
						
						word_pairs_frequency.put(word_pair, 1);
					}

					else {
						int val = word_pairs_frequency.get(word_pair);
						
						word_pairs_frequency.put(word_pair, ++val);
					}

				}

			}

		}
	}

	
	public void count_words(String link) {
		
		Document doc= null;
		
		try {
			
			doc = Jsoup.connect(link).get();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			System.out.println("Exception in count_words: This URL does not exist or is not valid for this problem");
			
			e.printStackTrace();
		}

		if (doc != null) {
			
			String text = doc.body().text();

			String[] words = text.split("[\\s,;.]+");

			for (int i = 0; i < words.length; i++) {
				
				if (!words_frequency.containsKey(words[i])) {
					
					words_frequency.put(words[i], 1);
				}

				else

				{
					int previous_count = words_frequency.get(words[i]);
					
					words_frequency.put(words[i], ++previous_count);
				}
			}

		}

	}

	public void level_order_traversal() {
		
		Queue<String> hyperLinks = new LinkedList<String>();

		hyperLinks.add(main_url);

		urls.add(main_url);

		int count = 0;

		while (!hyperLinks.isEmpty() && count < 4) {

			int n = hyperLinks.size();

			while (n > 0) {
				
				String url_retrieved = hyperLinks.remove();

				Document document = null;
				try {
					document = Jsoup.connect(url_retrieved).get();
				} catch (IOException e) {
					System.out.println(
							"Exception in level order traversal: This URL does not exist or is not valid for this problem");
					e.printStackTrace();
				}

				if (document != null) {
					Elements links = document.getElementsByTag("a");

					for (Element link : links) {

						if (link.attr("href").startsWith(main_url)) {
							if (!urls.contains(link.attr("href"))) {
								hyperLinks.add(link.attr("href"));
								urls.add(link.attr("href"));
							}
						}
					}

				}

				n--;
			}

			count++;
			System.out.println("URL's Upto level " + count);
			System.out.println(urls);
		}

	}
}