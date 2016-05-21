package nanodegree.premiere.json_loaders;


import java.util.List;

public class Post
{



    String poster_path;
    Boolean adult;
    String overview;
    String release_date;
    int[] genre_ids = new int[3];
    int id;
    String original_title;
    String original_language;
    String title;
    String backdrop_path;
    double popularity;
    int vote_count;
    Boolean video;
    double vote_average;

    public String getPoster_path()
    {
        return poster_path;
    }

    public void setPoster_path(String poster_path)
    {
        this.poster_path=poster_path;
    }

    public Boolean getAdult()
    {
        return adult;
    }

    public void setAdult(Boolean adult)
    {
        this.adult = adult;
    }

    public String getOverview()
    {
        return overview;
    }

    public void setOverview(String overview)
    {
        this.overview = overview;
    }

    public String getRelease_date()
    {
        return release_date;
    }

    public void setRelease_date(String release_date)
    {
        this.release_date = release_date;
    }

    public int[] getGenre_ids()
    {
        return genre_ids;
    }

    public void setGenre_ids(int[] genre_ids)
    {
        this.genre_ids[0] = genre_ids[0];

        this.genre_ids[1] = genre_ids[1];

        this.genre_ids[2] = genre_ids[2];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getOriginal_title()
    {
        return original_title;
    }

    public void setOriginal_title(String original_title)
    {
        this.original_title = original_title;
    }

    public String getOriginal_language()
    {
        return original_language;
    }

    public void setOriginal_language(String original_language)
    {
        this.original_language = original_language;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }


    public Double getPopularity() {
        return popularity;
    }

    public void setTitle(Double popularity) {
        this.popularity = popularity;
    }


    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public Boolean getVideo()
    {
        return video;
    }

    public void setVideo(Boolean video)
    {
        this.video = video;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }



}
