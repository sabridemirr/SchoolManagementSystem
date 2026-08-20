package com.schoolmanagement.repository;

import com.schoolmanagement.model.Parent;

import java.util.ArrayList;

public class ParentRepository {

    // ==================== PARENT STORAGE ====================
    private final ArrayList<Parent> parents = new ArrayList<>(); // The repository owns and stores the parent objects.
// This creates the repository's internal parent list

    // ==================== ADD PARENT ====================
    public void add(Parent parent) {
        parents.add(parent); // Adds the provided Parent object to storage.
    }

    // ==================== GET ALL PARENTS ====================
    public ArrayList<Parent> findAll() {
        return new ArrayList<>(parents); // Returns a copy so outside code cannot directly damage the stored list. This way the repos list structure is protected
    }

    // ==================== FIND PARENT BY ID ==================
    public Parent findById(int parentId) {
        for (Parent parent : parents) { // Checks every stored parent.
            if (parent.getParentId() == parentId) { // Compares the existing ID with the requested ID.
                return parent; // Immediately returns the matching Parent object.
            }
        }
        return null; // No Parent had the requested ID.
    }

    // ==================== FIND PARENT BY NAME ====================
    public Parent findByName(String name) {
        for (Parent parent : parents) { // Checks every stored parent.
            if (parent.getName().equalsIgnoreCase(name)) { // Ignores uppercase and lowercase differences.
                return parent; // Returns the first parent with the matching name.
            }
        }
        return null; // No parent had the requested name.
    }

    // ==================== CHECK WHETHER ID EXISTS ====================
    public boolean existsById(int parentId) {
        return findById(parentId) != null; // Returns true when findById finds a parent.
    }

    // ==================== DELETE PARENT ====================
    public boolean delete(Parent parent) {
        return parents.remove(parent); // Removes the parent and reports whether removal succeeded.
    }

    // ==================== CHECK WHETHER REPOSITORY IS EMPTY ====================
    public boolean isEmpty() {
        return parents.isEmpty(); // Returns true when no parents are stored.
    }
}
