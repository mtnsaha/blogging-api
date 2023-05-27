package com.blog.bloggingapi.service.impl;

import com.blog.bloggingapi.entities.Category;
import com.blog.bloggingapi.entities.Post;
import com.blog.bloggingapi.entities.User;
import com.blog.bloggingapi.exception.ResourceNotFoundException;
import com.blog.bloggingapi.payload.PostDto;
import com.blog.bloggingapi.payload.PostResponse;
import com.blog.bloggingapi.repository.CategoryRepository;
import com.blog.bloggingapi.repository.PostRepository;
import com.blog.bloggingapi.repository.UserRepository;
import com.blog.bloggingapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

        User user= this.userRepository.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("user","userId",userId));
        Category category= this.categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category","category id",categoryId));
        Post post = this.modelMapper.map(postDto,Post.class);
        post.setImageName(postDto.getImageName());
        post.setAddedDate(new Date());
        post.setCategory(category);
        post.setUser(user);
        Post savedPost = this.postRepository.save(post);

       return this.modelMapper.map(savedPost, PostDto.class);
       // return savedPost;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Integer postId) {

        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "post id", postId));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(postDto.getImageName());
       // post.setCategory(postDto.getCategory());

        this.postRepository.save(post);

        return this.modelMapper.map(post,PostDto.class);
    }

    @Override
    public void deletePost(Integer postId) {

        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "post id", postId));
        this.postRepository.delete(post);

    }

    @Override
    public PostResponse getAllPost(Integer pageNo, Integer pageSize, String sortBy, String sortDir) {

        //Ternary operator
        Sort sort=(sortDir.equalsIgnoreCase("asc"))? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        /*if(sortDir.equalsIgnoreCase("asc")){
            sort = Sort.by(sortBy).ascending();
        }
        else {
            sort = Sort.by(sortBy).descending();

        }*/

        //Pageable p = PageRequest.of(pageNo, pageSize, Sort.by(sortBy).descending());
        Pageable p = PageRequest.of(pageNo, pageSize, sort);

        Page<Post> pagePost = this.postRepository.findAll(p);
        List<Post> allPost = pagePost.getContent();

        List<PostDto> postDtos = allPost.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        PostResponse postResponse= new PostResponse();
        postResponse.setContent(postDtos);
        postResponse.setPageNumber(pagePost.getNumber());
        postResponse.setPageSize(pagePost.getSize());
        postResponse.setTotalElements(pagePost.getTotalElements());
        postResponse.setTotalPages(pagePost.getTotalPages());
        postResponse.setLastPage(pagePost.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostById(Integer postId) {

        Post post = this.postRepository.findById(postId).orElseThrow(() -> new ResourceNotFoundException("post", "post id", postId));


        return this.modelMapper.map(post,PostDto.class);

    }

    @Override
    public List<PostDto> getPostByCategory(Integer categoryId) {

        Category cat = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","category id",categoryId ));

        List<Post> posts = this.postRepository.findByCategory(cat);

        List<PostDto> postsDto = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postsDto;
    }

    @Override
    public List<PostDto> getPostByUser(Integer userId) {
        User user = this.userRepository.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user", "user id",userId));
        List<Post> posts = this.postRepository.findByUser(user);
        List<PostDto> postByUser = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
        return postByUser;


    }

    @Override
    public List<PostDto> getSearchPost(String keyword) {
        List<Post> posts = this.postRepository.findByTitleContaining(keyword);

        List<PostDto> postsDto = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());

        return postsDto;
    }
}
