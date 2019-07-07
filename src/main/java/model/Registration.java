package model;

import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author sayed
 */
@Entity
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String studentID;
    private int sectionID;

    public Registration() {
    }

    public Registration(String studentID, int sectionID) {
        this.studentID = studentID;
        this.sectionID = sectionID;
    }

    public Registration(int id, String studentID, int sectionID) {
        this.id = id;
        this.studentID = studentID;
        this.sectionID = sectionID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public int getSectionID() {
        return sectionID;
    }

    public void setSectionID(int sectionID) {
        this.sectionID = sectionID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Registration other = (Registration) obj;
        if (!Objects.equals(this.studentID, other.studentID)) {
            return false;
        }
        if (this.sectionID != other.sectionID) {
            return false;
        }
        return true;
    }

}
