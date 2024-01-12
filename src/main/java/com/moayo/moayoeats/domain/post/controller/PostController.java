package com.moayo.moayoeats.domain.post.controller;

import com.moayo.moayoeats.domain.post.dto.request.PostCategoryRequest;
import com.moayo.moayoeats.domain.post.dto.request.PostIdRequest;
import com.moayo.moayoeats.domain.post.dto.request.PostRequest;
import com.moayo.moayoeats.domain.post.dto.request.PostSearchRequest;
import com.moayo.moayoeats.domain.post.dto.response.BriefPostResponse;
import com.moayo.moayoeats.domain.post.dto.response.DetailedPostResponse;
import com.moayo.moayoeats.domain.post.service.PostService;
import com.moayo.moayoeats.global.dto.ApiResponse;
import com.moayo.moayoeats.global.security.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class PostController {

    private final PostService postService;

    // 글 생성하기
    @PostMapping("/posts")
    public ApiResponse<Void> createPost(@Valid @RequestBody PostRequest postReq,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        postService.createPost(postReq, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.CREATED.value(), "글을 생성했습니다.");
    }

    // 모든 글 조회하기
    @GetMapping("/posts")
    public ApiResponse<List<BriefPostResponse>> getPosts(
        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return new ApiResponse<>(HttpStatus.OK.value(), "모든 글 조회에 성공했습니다.",
            postService.getPosts(userDetails.getUser()));
    }

    //글 단독 조회, 글 상세페이지
    @GetMapping("/posts/{postId}")
    public ApiResponse<DetailedPostResponse> getPost(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @PathVariable(name = "postId") Long postId) {
        return new ApiResponse<>(HttpStatus.OK.value(), "글 상세페이지 조회에 성공했습니다.",
            postService.getPost(postId, userDetails.getUser()));
    }

    //글 카테고리별 조회
    @GetMapping("/posts/category")
    public ApiResponse<List<BriefPostResponse>> getPostsByCategory(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @Valid @RequestBody PostCategoryRequest postCategorySearchReq) {
        return new ApiResponse<>(HttpStatus.OK.value(), "글 카테고리별 조회에 성공했습니다.",
            postService.getPostsByCategory(postCategorySearchReq, userDetails.getUser()));
    }

    //글 검색하기
    @GetMapping("/posts/search")
    public ApiResponse<List<BriefPostResponse>> searchPost(
        @AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody PostSearchRequest postSearchReq) {
        return new ApiResponse<>(HttpStatus.OK.value(), "검색 결과",
            postService.searchPost(postSearchReq, userDetails.getUser()));
    }

    //글 삭제하기
    @DeleteMapping("/posts")
    public ApiResponse<Void> deletePost(@AuthenticationPrincipal UserDetailsImpl userDetails,
        @RequestBody PostIdRequest postIdReq) {
        postService.deletePost(postIdReq, userDetails.getUser());
        return new ApiResponse<>(HttpStatus.OK.value(), "글 삭제 성공했습니다.");
    }

}
