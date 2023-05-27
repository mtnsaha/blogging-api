package com.blog.bloggingapi.controller;

import com.blog.bloggingapi.config.AppConstant;
import com.blog.bloggingapi.payload.ApiResponse;
import com.blog.bloggingapi.payload.PostDto;
import com.blog.bloggingapi.payload.PostResponse;
import com.blog.bloggingapi.service.FileService;
import com.blog.bloggingapi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
   private FileService fileService;
    @Value("${project.image}")
    private String path;

//create
    @PostMapping("/user/{userId}/category/{categoryId}/posts")
    public ResponseEntity<PostDto> createPost
            (@RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){

        PostDto createPost = this.postService.createPost(postDto,userId,categoryId);

return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);


    }

    //getByUser
    @GetMapping("user/{userId}/posts")
    public  ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId){

        List<PostDto> postsDto = this.postService.getPostByUser(userId);

        return new ResponseEntity<List<PostDto>>(postsDto,HttpStatus.OK);

    }

    //getByCategory
    @GetMapping("category/{categoryID}/posts")
    public  ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryID){

        List<PostDto> postsDto = this.postService.getPostByCategory(categoryID);

        return new ResponseEntity<List<PostDto>>(postsDto,HttpStatus.OK);

    }

    //getAllPost

   /* @GetMapping("/posts")
    public  ResponseEntity<List<PostDto>> getAllPost(
            @RequestParam(value = "pageNumber",defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5", required = false) Integer pageSize
    ){

        List<PostDto> posts = this.postService.getAllPost(pageNumber,pageSize);

        return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
    }*/
   @GetMapping("/posts")
   public  ResponseEntity<PostResponse> getAllPost(
           @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER, required = false) Integer pageNumber,
           @RequestParam(value = "pageSize",defaultValue = AppConstant.PAGE_SIZE, required = false) Integer pageSize,
           @RequestParam (value = "sortBy",defaultValue = AppConstant.POST_ID,required = false) String sortBy,
           @RequestParam (value = "sortDir",defaultValue = AppConstant.SORT_DIR,required = false) String sortDir
   ){

       PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize,sortBy,sortDir);

       return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
   }

    //getpOstById

    @GetMapping("/posts/{postId}")
    public  ResponseEntity<PostDto> getAllPostById(@PathVariable Integer postId){

        PostDto post = this.postService.getPostById(postId);

        return new ResponseEntity<PostDto>(post,HttpStatus.OK);
    }
//deletePost
    @DeleteMapping("/posts/{postID}")
    public ApiResponse deletePost(@PathVariable Integer postID){

this.postService.deletePost(postID);
return new ApiResponse("post successfully deleted",true);
    }

 //updatePost

    @PutMapping("/posts/{postID}")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postID){

        PostDto updatePost = this.postService.updatePost(postDto, postID);

        return  new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
    }

    //Searching

    @GetMapping("/post/search/{keyWord}")
    public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keyWord){

        List<PostDto> result = this.postService.getSearchPost(keyWord);

        return new ResponseEntity<List<PostDto>>(result,HttpStatus.OK);


    }

    //post image upload

   @PostMapping("/post/image/upload/{postId}")
    public  ResponseEntity<PostDto> uploadImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Integer postId ) throws IOException {

        PostDto postDto = this.postService.getPostById(postId);
        String fileName = this.fileService.uploadImage(path, image);
        postDto.setImageName(fileName);
        PostDto updatedPost = this.postService.updatePost(postDto,postId);
        return  new ResponseEntity<PostDto>(updatedPost,HttpStatus.OK);

    }


}
