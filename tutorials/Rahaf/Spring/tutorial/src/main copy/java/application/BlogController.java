package application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Service("BlogRepository")
public class BlogController {

    /*@RequestMapping("/")
    public String index() {
        return "Congratulations from BlogController.java";
    }*/

    @Autowired
    BlogRespository blogRepo ;

    @RequestMapping("/")
    public String Hello()
    {
        return "hello !!";
    }

   // BlogMockedData bmd = BlogMockedData.getInstance();

    @GetMapping("/blog")
    public List<Blog> index()
    {
        return blogRepo.findAll();
        //return bmd.fetchBlogs();
    }

    @GetMapping("/blog/{id}")
    public Blog show(@PathVariable String id)
    {
        int blogId = Integer.parseInt(id);
        //return bmd.getBlogById(blogId);
        return blogRepo.findOne(blogId);
    }

    @PostMapping("/blog/search")
    public List<Blog> search(@RequestBody Map<String, String> body)
    {
        String searchTerm = body.get("text");
       //return bmd.searchBlogs(searchTerm);
        return blogRepo.findBlogs(searchTerm, searchTerm);
    }

    @PostMapping("/blog")
    public Blog create(@RequestBody Map<String, String> body)
    {
       // int id = Integer.parseInt(body.get("id"));
        String title = body.get("title");
        String content = body.get("content");
        //return bmd.createBlog(id, title, content);
        return blogRepo.save(new Blog(title,content));
    }

    @PutMapping("/blog/{id}")
    public Blog update(@PathVariable String bId, @RequestBody Map<String, String> body)
    {
        int id = Integer.parseInt(bId);
        Blog blog = blogRepo.findOne(id);
        blog.setTitle(body.get("title"));
        blog.setContent(body.get("content"));
        //return bmd.UpdateBlog(id, title, content);
        return blogRepo.save(blog);
    }

    @DeleteMapping("/blog/delete/{id}")
    public Boolean delete(@PathVariable String id)
    {
        int blogId = Integer.parseInt(id);
        //return bmd.delete(blogId);
        return blogRepo.delete(blogId);
    }

}
