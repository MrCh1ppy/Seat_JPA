package com.example.seat_jpa.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.Version;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author 橙鼠鼠
 */
@Entity
@Getter
@Setter
@ToString
@Accessors(chain = true)
@NoArgsConstructor
@ApiModel("座椅")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ApiModelProperty("座椅描述")
    private String description;
    @Version
    private Long version;
    @ManyToOne
    @ApiModelProperty("所属区域")
    @JsonIgnore
    private Zone zone;
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Seat seat = (Seat) o;
        return id != null && Objects.equals(id, seat.id);
    }

    public Seat(String description, Zone zone) {
        this.description = description;
        this.zone=zone;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Data
    public static class Builder{
        private String description;
        private Zone zone;

        public Seat build(){
            return new Seat(description, zone);
        }
    }
}
