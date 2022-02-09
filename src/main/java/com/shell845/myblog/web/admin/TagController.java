package com.shell845.myblog.web.admin;

import com.shell845.myblog.po.Tag;
import com.shell845.myblog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.ASC)
                                    Pageable pageable, Model model) {
        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";
    }

    // page of add new tag
    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/input-tag";
    }

    // get existing tags
    @Transactional
    @GetMapping(value = "/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        Tag tag = tagService.getTag(id);
        System.out.println("----------------editInput-------" + tag.toString());
        model.addAttribute("tag", tag);
        return "admin/input-tag";
    }

    // add new tag
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getTagName());

        if (tag1 != null) {
            result.rejectValue("tagName","nameError","Tag already exists");
        }
        if (result.hasErrors()) {
            return "admin/input-tag";
        }

        Tag t = tagService.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "Add fail");
        } else {
            attributes.addFlashAttribute("message", "Add complete");
        }
        return "redirect:/admin/tags";
    }

    // edit existing tag
    @Transactional
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getTagName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","Tag already exists");
        }
        if (result.hasErrors()) {
            return "admin/input-tag";
        }
        Tag t = tagService.updateTag(id, tag);
        if (t == null ) {
            attributes.addFlashAttribute("message", "Update fail");
        } else {
            attributes.addFlashAttribute("message", "Update success");
        }
        return "redirect:/admin/tags";
    }

    // delete tag
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "Deleted");
        return "redirect:/admin/tags";
    }
}
