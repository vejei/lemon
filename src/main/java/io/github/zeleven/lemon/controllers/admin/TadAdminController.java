package io.github.zeleven.lemon.controllers.admin;

import io.github.zeleven.lemon.entities.Tag;
import io.github.zeleven.lemon.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin/tag")
public class TadAdminController {
    @Autowired
    private TagService tagService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@RequestParam(name = "name") String name) {
        Tag tag = new Tag();
        tag.setName(name);
        tagService.saveTag(tag);
        return "redirect:/admin/tag";
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public String update(@RequestParam(name = "id") Integer id,
                         @RequestParam(name = "name") String name) {
        Tag tag = tagService.findOne(id);
        tag.setName(name);
        tagService.saveTag(tag);
        return "redirect:/admin/tag";
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public String delete(@RequestParam(name = "tid") Integer id) {
        tagService.deleteTag(id);
        return "redirect:/admin/tag";
    }
}
