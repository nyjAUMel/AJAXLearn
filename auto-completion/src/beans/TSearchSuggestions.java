package beans;


public class TSearchSuggestions {

  private long id;
  private String keyword;
  private String category;

  public TSearchSuggestions() {
  }

  public TSearchSuggestions(long id, String keyword, String category) {
    this.id = id;
    this.keyword = keyword;
    this.category = category;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }


  public String getKeyword() {
    return keyword;
  }

  public void setKeyword(String keyword) {
    this.keyword = keyword;
  }


  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

}
