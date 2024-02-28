package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
@Repository
// Stub
public class PostRepository {
    private final AtomicLong id = new AtomicLong();
    private final ConcurrentHashMap<Long, Post> postStorage = new ConcurrentHashMap<>();


    public List<Post> all() {
        return new ArrayList<>(postStorage.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(postStorage.get(id));
    }

    public Post save(Post post) {
        if (post.getId() == 0) {
            id.incrementAndGet();
            postStorage.put(id.get(), new Post(id.get(), post.getContent()));
            return postStorage.get(post.getId());
        } else if (postStorage.containsKey(post.getId())) {
            postStorage.replace(id.get(), new Post(id.get(), post.getContent()));
            return post;
        } else {
            throw new NotFoundException("Неверный идентификатор поста: " + post.getId());
        }

    }

    public void removeById(long id) {
        if (postStorage.containsKey(id)) {
            postStorage.remove(id);
        } else {
            throw new NotFoundException("Неверный идентификатор поста: " + id);
        }
    }
}

