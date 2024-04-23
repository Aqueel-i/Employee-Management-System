package lk.bitproject.item.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import lk.bitproject.item.dao.ItemDao;
import lk.bitproject.item.entity.Item;

@RestController
public class ItemController {

    @Autowired
    private ItemDao dao;

    @RequestMapping(value = "/item")

    public ModelAndView itemUI() {
        ModelAndView itemview = new ModelAndView(); // for return ui

        itemview.setViewName("item.html");
        return itemview;
    }

    @GetMapping(value = "/item/alldata", produces = "application/json")
    public List<Item> allItemData() {
        return dao.findAll(); // dao variable eka use krl find all function eken data krgnnwa
    }

}
