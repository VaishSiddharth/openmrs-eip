package org.cicr.sync.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "concept")
@AttributeOverrides(
        {
                @AttributeOverride(name = "id", column = @Column(name = "concept_id"))
        }
)
public class ConceptEty extends OpenMrsEty {

    @NotNull
    private int datatypeId;

    @NotNull
    private int classId;

    @NotNull
    private int creator;

    @NotNull
    private String dateCreated;

    public ConceptEty() {}
}
