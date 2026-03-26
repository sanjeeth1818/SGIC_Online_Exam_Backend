package com.sgic.exam.repository;

import com.sgic.exam.model.Submission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByTestId(Long testId);

    Optional<Submission> findByExamCode(String examCode);

    List<Submission> findByStudentId(Long studentId);

    List<Submission> findBySubmittedAtGreaterThan(java.time.LocalDateTime start);

    @org.springframework.data.jpa.repository.Query("SELECT COUNT(DISTINCT LOWER(TRIM(s.studentEmail))) FROM Submission s WHERE s.submittedAt > :start")
    long countDistinctStudentsSince(@org.springframework.data.repository.query.Param("start") java.time.LocalDateTime start);

    @org.springframework.data.jpa.repository.Modifying
    @org.springframework.data.jpa.repository.Query("UPDATE Submission s SET s.studentEmail = (SELECT st.email FROM Student st WHERE st.id = s.studentId) WHERE s.studentEmail IS NULL AND s.studentId IS NOT NULL")
    void backfillMissingEmails();

    List<Submission> findBySubmittedAtBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);

    @org.springframework.data.jpa.repository.Query("SELECT MIN(YEAR(s.submittedAt)), MAX(YEAR(s.submittedAt)) FROM Submission s WHERE s.submittedAt IS NOT NULL")
    List<Object[]> findMinMaxSubmissionYear();
}
