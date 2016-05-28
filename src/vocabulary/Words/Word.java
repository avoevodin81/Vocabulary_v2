package vocabulary.Words;

/**
 * Created by Test on 09.05.2016.
 */
public class Word {
    int id;
    String eng;
    String rus;
    boolean isNew;

    public Word(int id, String eng, String rus, boolean isNew) {
        this.id = id;
        this.eng = eng;
        this.rus = rus;
        this.isNew = isNew;
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

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", eng='" + eng + '\'' +
                ", rus='" + rus + '\'' +
                ", isNew=" + isNew +
                '}';
    }
}
