package jdbc.dao.postgre_dao.agregation;

import Entities.Post;
import jdbc.connection.ConnectionPool;
import jdbc.dao.core.agregation.Agregator;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class PostgreAgregator implements Agregator<Post>{

    // TODO: 22.10.2016 test agregation

    private static final int DEFAULT_PORION_SIZE = 10;
    private PriorityQueue<PostAggrQueue> sources;
    private ConnectionPool pool;
    private int nextIndex = 0;
    private int outOfSourceSize;
    private int readed;

    public PostgreAgregator(List<Integer> pageIds, ConnectionPool pool){
        sources = new PriorityQueue<>();
        for (int i : pageIds){
            PostAggrQueue q = new PostAggrQueue(i, DEFAULT_PORION_SIZE, pool);
            if (q.peek() != null)
                sources.add(q);
        }
    }

    private Post provideNextPost(){
        PostAggrQueue queue = sources.poll();
        if (queue == null)
            return null;

        Post post = queue.poll();
        if (queue.peek() != null)
            sources.add(queue);
        else outOfSourceSize += queue.readed();

        return post;
    }

    @Override
    public List<Post> getNext(int count) {
        LinkedList<Post> ret = new LinkedList<>();
        for (int i = 0; i < count; i++)
        {
            Post next = provideNextPost();
            if (next == null) break;

            ret.add(next);
        }
        readed+=ret.size();
        return ret;
    }

    @Override
    public int size() {
        int inSource = 0;
        for (PostAggrQueue queue : sources)
            inSource += queue.size();

        return inSource + outOfSourceSize;
    }

    @Override
    public int readed() {
        return readed;
    }

}
