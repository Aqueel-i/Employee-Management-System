package lk.bitproject.item.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.bitproject.item.entity.Item;

public interface ItemDao extends JpaRepository<Item, Integer> {

}
