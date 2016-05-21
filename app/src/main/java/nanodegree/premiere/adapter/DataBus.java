package nanodegree.premiere.adapter;

/**
 * Created by oblivion on 5/20/2016.
 */

public class DataBus
{
    private String movieName,release_date,poster_link,synopsis,trailer;
    private double vote_avg;
    private int ID;

    public int getID()
    {
        return ID;
    }

    public void setID(int ID)
    {
        this.ID = ID;
    }

    public String getMovieName()
    {
        return movieName;
    }

    public void setMovieName(String movie)
    {
        this.movieName = movie;
    }

    public String getRelease_date()
    {
        return release_date;
    }

    public void setRelease_date(String release)
    {
        this.release_date = release;
    }

    public String getPoster_link()
    {
        return poster_link;
    }

    public void setPoster_link(String poster)
    {
        this.poster_link = poster;
    }

    public String getSynopsis()
    {
        return synopsis;
    }

    public void setSynopsis(String syn)
    {
        this.synopsis = syn;
    }



    public double getVote_avg()
    {
        return vote_avg;
    }

    public void setVote_avg(double vote)
    {
        this.vote_avg = vote;
    }
}
