package com.sgic.exam.repository;

import com.sgic.exam.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
    boolean existsByName(String name);

    java.util.List<Test> findAllByIsDeletedFalseOrIsDeletedIsNull();

    java.util.List<Test> findTop3ByOrderByIdDesc();

    @org.springframework.data.jpa.repository.Query("SELECT t.name, g.examDate FROM Test t JOIN t.studentGroups g WHERE g.examDate IS NOT NULL")
    java.util.List<Object[]> findTestNamesAndExamDates();

    java.util.List<Test> findByStatusNot(String status);
}
