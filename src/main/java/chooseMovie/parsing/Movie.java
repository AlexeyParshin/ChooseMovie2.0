package chooseMovie.parsing;

/**
 * Created by dip17_000 on 02.07.2017.
 */
public class Movie {

  private String name;
  private String URL;
  private String imgURL;
  private String rate;

  public Movie(String name, String URL, String imgURL, String rate) {
    this.name = name;
    this.URL = URL;
    this.rate = rate;
    this.imgURL = "https://st.kp.yandex.net/images/film_iphone/iphone360_" + imgURL + ".jpg";
  }

  public String getRate() {
    return rate;
  }

  public void setRate(String rate) {
    this.rate = rate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getURL() {
    return URL;
  }

  public void setURL(String URL) {
    this.URL = URL;
  }

  public String getImgURL() {
    return imgURL;
  }

  public void setImgURL(String imgURL) {
    this.imgURL = "https://st.kp.yandex.net/images/film_iphone/iphone360_" + imgURL + ".jpg";
  }

  @Override
  public String toString() {
    return name;
  }
}
