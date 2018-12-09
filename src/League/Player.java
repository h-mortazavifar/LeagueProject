package League;

public class Player {
    private String name;
    private String lastName;
    private Post post;
    private int playerNo;

    public void setPost(Post post) {
        this.post = post;
    }

    private enum Post {
        front, defender, halfBack, goalKeeper;
    }

    public Player(String name, String lastName, String post) {
        setPlayerNo();
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

    public void setPost(String post) {
        this.post = Post.valueOf(post);
    }
}
