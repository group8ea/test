package edu.miu.appointmentsystem.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="category_id")
    private Integer id;

    @NotEmpty(message = "{String.empty}")
    private String title;

    @NotEmpty(message = "{String.empty}")
    private Integer duration;

    @OneToMany(mappedBy = "category")
    @JsonIgnore
    private Collection<Appointment> appointments;
}
