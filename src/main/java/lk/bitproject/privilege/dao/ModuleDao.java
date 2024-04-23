package lk.bitproject.privilege.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.bitproject.privilege.entity.Module;

public interface ModuleDao extends JpaRepository<Module, Integer> {

}
