package com.moayo.moayoeats.frontend.domain.post;

import com.moayo.moayoeats.backend.domain.post.controller.PostController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1")
public class PostFrontController {

    private final PostController postController;

    @GetMapping("/test/createpost")
    public String createPostPage(){
        return "domain/post/createpost";
    }
}
