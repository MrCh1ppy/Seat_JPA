package com.example.seat_jpa.entity.po;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.Contract;

import javax.persistence.*;
import java.util.List;
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
@ApiModel("区域")
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ApiModelProperty("区域名")
    private String name;
    @ApiModelProperty("区域描述")
    private String description;
    @OneToOne(targetEntity = User.class)
    @ApiModelProperty("管理员名称")
    private User admin;
    @OneToMany(mappedBy = "zone",
            targetEntity = Seat.class
    )
    @ToString.Exclude
    private List<Seat> setList;
    @Version
    private Long version;

    @Contract(pure = true)
    public Zone(String name, String description, User admin) {
        this.name = name;
        this.description = description;
        this.admin = admin;
    }

    @Contract(value = "null -> false", pure = true)
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Zone zone = (Zone) o;
        return id != null && Objects.equals(id, zone.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Setter
    public static class Builder{
        private String name;
        private String description;
        private User admin;

        public Zone build(){
            return new Zone(name, description, admin);
        }
    }
}
