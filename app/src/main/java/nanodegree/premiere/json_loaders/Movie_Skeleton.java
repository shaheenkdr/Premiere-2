package nanodegree.premiere.json_loaders;


import java.util.List;

public class Movie_Skeleton
{


        int page;

        List<Post> results;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }


        public List<Post> getPosts() {
            return results;
        }

        public void setPosts(List<Post> result) {
           this.results = result;
        }

}
