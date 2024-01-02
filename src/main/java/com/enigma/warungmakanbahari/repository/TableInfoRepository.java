package com.enigma.warungmakanbahari.repository;

import com.enigma.warungmakanbahari.entity.TableInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TableInfoRepository extends JpaRepository<TableInfo, String> {
}
