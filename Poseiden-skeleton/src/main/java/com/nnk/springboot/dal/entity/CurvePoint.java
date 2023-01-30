package com.nnk.springboot.dal.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Getter
@Setter
@Entity
@Table(name = "curvepoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "curve_id")
    @NotNull(message = "Curve id is mandatory")
    private Integer curveId;

    @Column(name = "as_of_date")
    private Timestamp asOfDate;

    @Column
    @NotNull(message = "Term id is mandatory")
    private Double term;

    @Column
    @NotNull(message = "Value id is mandatory")
    private Double value;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    public CurvePoint (Integer curveIdIn,
                       Double termIn,
                       Double valueIn) {
        this.setCurveId(curveIdIn);
        this.setTerm(termIn);
        this.setValue(valueIn);
    }

    public CurvePoint() {

    }
}
