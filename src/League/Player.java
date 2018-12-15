package League;

public class Player {
    private int goals;
    private String name;
    private String lastName;
    private Post post;
    private int playerNo;

//    public void setPost(Post post) {
//        this.post = post;
//    }

    public enum Post {
        front("حمله"), defender("دفاع"), halfBack("هافبک"), goalKeeper("دروازه بان");
        private String persianPost;

        Post(String persianPost) {
            this.persianPost = persianPost;
        }

        public String getPersianPost() {
            return persianPost;
        }
    }

    public Player(String name, String lastName, String post) {
        setPlayerNo();
//        setPost(post);
        this.name = name;
        this.lastName = lastName;
        this.post = getPost();
    }

    public int getPlayerNo() {
        return playerNo;
    }

    public void setPlayerNo() {
        this.playerNo += 1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals += goals;
    }
}
