package com.noob.blog.web.admin;

import com.noob.blog.po.Tag;
import com.noob.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    // 标签页跳转
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 3, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                       Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    // 新增页面跳转
    @GetMapping("/tags/input")
    public String tagInput(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    // 新增功能
    @PostMapping("/tags")
    public String tagPost(@Valid Tag tag,
                          BindingResult result,
                          RedirectAttributes attributes) {
        Tag test = tagService.getTagByName(tag.getName());
        if (test != null)
            result.rejectValue("name", "nameError", "不能添加重复标签");

        if (result.hasErrors())
            return "admin/tags-input";

        Tag t = tagService.saveTag(tag);
        if (t == null)
            attributes.addFlashAttribute("message", "新增失败");
        else
            attributes.addFlashAttribute("message", "新增成功");

        return "redirect:/admin/tags";
    }

    // 编辑跳转
    @GetMapping("tags/{id}/input")
    public String tagEditEntry(@PathVariable Long id,
                               Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    // 编辑功能
    @PostMapping("tags/{id}")
    public String tagEdit(@Valid Tag tag,
                          @PathVariable Long id,
                          BindingResult result,
                          RedirectAttributes attributes) {
        Tag test = tagService.getTagByName(tag.getName());
        if (test != null)
            result.rejectValue("name", "nameError", "不能添加重复标签");

        if (result.hasErrors())
            return "admin/tags-input";

        Tag t = tagService.updateTag(id, tag);
        if (t == null)
            attributes.addFlashAttribute("message", "修改失败");
        else
            attributes.addFlashAttribute("message", "修改成功");

        return "redirect:/admin/tags";
    }

    // 标签删除
    @GetMapping("tags/{id}/delete")
    public String tagDelete(@PathVariable Long id,
                            RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/tags";
    }
}
