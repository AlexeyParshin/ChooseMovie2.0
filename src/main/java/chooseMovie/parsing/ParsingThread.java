package chooseMovie.parsing;

import org.apache.commons.lang3.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dip17_000 on 02.07.2017.
 */
public class ParsingThread extends Thread {

  private List<Movie> movies = new ArrayList<>();
  private String url;

  public ParsingThread(String url) {
    this.url = url;
    start();
  }

  private void parsePage() {
    ArrayList<Movie> movies = new ArrayList<>();
    Document doc = null;
    try {
      doc = Jsoup.connect(url).get();
    } catch (IOException exc) {
      System.out.println(exc);
    }
    Elements infoElements = doc.getElementsByAttributeValue("class", "info");

    infoElements.forEach(infoElement -> {
      Element jsserpmetrikaElement = infoElement.child(0).child(0);
      String name = StringEscapeUtils.unescapeHtml4(jsserpmetrikaElement.text());
      String URL = jsserpmetrikaElement.attr("href");
      String imgURL = jsserpmetrikaElement.attr("data-id");
      String rate = infoElement.child(1).text().substring(0, 5);

      movies.add(new Movie(name, URL, imgURL, rate));
    });

    this.movies = movies;
  }

  public List<Movie> getMovies() {
    return movies;
  }

  @Override
  public void run() {
    parsePage();
  }
}
