package com.noob.blog.web;

import com.noob.blog.po.Comment;
import com.noob.blog.po.User;
import com.noob.blog.service.BlogService;
import com.noob.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    // 获取评论
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId,
                           Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    // 提交按钮
    @PostMapping("/comments")
    public String post(Comment comment,
                       HttpSession session) {
        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));
        // 管理员
        User user = (User) session.getAttribute("user");
        if (user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
            //comment.setNickname(user.getNickname());

        } else {
            comment.setAvatar(avatar);
        }
        comment.setAvatar(avatar);
        commentService.saveComment(comment);

        // 重定向到 comments 方法（上面）
        return "redirect:/comments/" + blogId;
    }
}
