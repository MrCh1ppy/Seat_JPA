package com.example.seat_jpa.entity.po;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.Hibernate;
import org.jetbrains.annotations.Contract;
import org.springframework.data.annotation.Version;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@ApiModel("用户")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @ApiModelProperty("用户名")
    private String userName;
    @ApiModelProperty("密码")
    @JsonIgnore
    private String password;
    @ApiModelProperty("是否为管理员")
    private Boolean isAdmin;
    @Version
    @JsonIgnore
    private Long version;

    @Contract(pure = true)
    public User(Integer id, String userName, String password, Boolean isAdmin) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
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
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Setter
    public static class Builder {
        private String username;
        private String password;
        private Boolean isAdmin;

        public User build(){
            return new User(null,username,password,isAdmin);
        }
    }
}
