package jdbc.dao.postgre_dao.agregation;

import Entities.Page;
import Entities.Post;
import jdbc.connection.ConnectionPool;
import jdbc.dao.core.agregation.AgregatorQueue;
import jdbc.dao.postgre_dao.PostgrePageDao;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.Optional;


/**
 * Implementation of <code>AgreratorOueue</code> interface for <code>Post</code> objects.
 * Implements AgregatorQueue, Comparable interfaces with generic parameter <code>Page</code>.
 * Can be compared with other <code>PostAggrQueue</code> by comparing <code>time</code> fields
 * of both queues head elements
 */

@Log4j2
class PostAggrQueue implements AgregatorQueue<Post>, Comparable {

    // TODO: 22.10.2016 test queue
    private int size;
    private ConnectionPool pool;

    private int pageId;
    private String pageNickname;
    private String pageFirstName;
    private String pageSecondName;

    private int lastLoaded;
    private int lastTried;
    private int portionSize;
    private LinkedList<Post> loadedSet;
    private int removed;

    PostAggrQueue(int pageId, int portionSize, ConnectionPool conPool){
        lastLoaded = Integer.MIN_VALUE;
        this.portionSize = portionSize;
        this.pool = conPool;

        Optional<Page> optPage = new PostgrePageDao(conPool).get(pageId);
        if (optPage.isPresent()) {
            Page p = optPage.get();
            pageNickname = p.getNickname();
            pageFirstName = p.getFirstName();
            pageSecondName = p.getSecondName();
            this.pageId = p.getId();
            lastLoaded = p.getLastPostId() + 1;
            lastTried = lastLoaded;
        }
    }

    private Post buildNext(ResultSet set) throws SQLException {
        val builder = Post.builder();

        builder.nickname(pageNickname);
        builder.firstName(pageFirstName);
        builder.secondName(pageSecondName);
        builder.pageId(pageId);
        builder.time(set.getTime("post_time").toInstant());
        builder.content(set.getString("content"));
        builder.nextId(set.getInt("prev_post_id"));

        lastLoaded = set.getInt("post_id");
        builder.id(lastLoaded);

        return builder.build();
    }

    private boolean canLoad() {
        return (lastTried > 0);
    }

    private void getFromDbIfEmpty(){
        if (loadedSet == null || loadedSet.size() == 0)
            get();
    }


    private void get(){
        boolean loaded = false;
        loadedSet = new LinkedList<>();
        while (canLoad() && !loaded){
            loaded = getCycle();
        }
    }

    private boolean getCycle(){
        lastTried -= portionSize;
        try(Connection con = pool.get();
            Statement statement = con.createStatement()) {
            ResultSet set = statement.executeQuery(
                    "SELECT post_id, prev_post_id, post_time, content from web_app.posts WHERE " +
                            "page_id = " + pageId + " AND " +
                            "(post_id < " + lastLoaded + " AND post_id >= " + lastTried + ") " +
                            "ORDER BY post_time DESC");

            while(set.next()) {
                loadedSet.add(buildNext(set));
            }
            if (loadedSet.size() == 0) {
                portionSize *= 2;
                return false;
            }
            else return true;

        } catch (SQLException e) {
            log.warn("error while loading post to agregationQueue");
        }
        return false;
    }

    void reset(){
        lastTried = lastLoaded;
        loadedSet = null;
    }

    @Override
    public Post poll() {
        getFromDbIfEmpty();
        if (loadedSet.peek() != null) removed++;
        return loadedSet.poll();
    }

    @Override
    public Post peek() {
        getFromDbIfEmpty();
        return loadedSet.peek();
    }


    private void updateSize(){
        try (Connection con = pool.get();
             Statement statement = con.createStatement()) {
            ResultSet set = statement.executeQuery(
                    "SELECT COUNT(*) AS cnt FROM web_app.posts WHERE page_id = " + pageId);

            set.next();
            size = set.getInt("cnt");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int size() {
        updateSize();
        return size;
    }


    @Override
    public int readed() {
        return removed;
    }


    @Override
    public int compareTo(Object o) {
        if (this.peek() == null)
            return -1;

        PostAggrQueue that = (PostAggrQueue) o;

        if (that.peek() == null)
            return 1;

        return this.peek().getTime().compareTo(that.peek().getTime());
    }
}
