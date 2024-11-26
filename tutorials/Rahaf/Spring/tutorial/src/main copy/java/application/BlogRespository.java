package application;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRespository extends JpaRepository<Blog, Integer>{
    List<Blog> findBlogs (String text, String textAgain);
    List<Blog> findAll();
    Blog findOne(int id);
    Blog save(Blog b);
    boolean delete(int id);
    //List<Blog> findByTitle(String title);
}
