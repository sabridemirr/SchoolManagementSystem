/*
 * Gives the application Spring Data database operations for students.
 *
 * Student is the entity being managed.
 * Integer is the data type of the Student primary key.
 */

package com.schoolmanagement.repository;

import com.schoolmanagement.model.Student; // The entity managed by this repository.
import org.springframework.data.jpa.repository.JpaRepository; // Provides ready-made CRUD
import java.util.Optional; // Safely represents either a found Student or no result.

public interface StudentJpaRepository extends JpaRepository<Student, Integer> {

    Optional<Student> findByNameIgnoreCase(String name);  // Searches for a student by name without considering uppercase differences.
}