
package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author sayed
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"sectionID"})
@Entity
public class Section {
    @Id
    private int sectionID;
    private int sectionNumber;
    private int semesterNumber;
    private int seatLimit;
    private String code;
    private String initial;
    
}
