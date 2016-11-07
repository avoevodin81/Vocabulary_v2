package vocabulary.Words;

/**
 * Created by Test on 09.05.2016.
 */
public class Word {
    int id;
    String eng;
    String rus;
    String filter;

    public Word(int id, String eng, String rus, String filter) {
        this.id = id;
        this.eng = eng;
        this.rus = rus;
        this.filter = filter;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEng() {
        return eng;
    }

    public void setEng(String eng) {
        this.eng = eng;
    }

    public String getRus() {
        return rus;
    }

    public void setRus(String rus) {
        this.rus = rus;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", eng='" + eng + '\'' +
                ", rus='" + rus + '\'' +
                ", filter=" + filter +
                '}';
    }

}
