package com.example.seat_jpa.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import org.springframework.data.domain.PageRequest;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * @author 橙鼠鼠
 */
@Data
@ApiModel("基本分页参数")
public abstract class BasePageAbleParam {
    @ApiParam(value = "页大小",defaultValue = "10")
    @NotNull
    @DecimalMin("1")
    int pageSize;
    @ApiParam(value = "当前页",defaultValue = "1")
    @NotNull
    @DecimalMin("1")
    int current;

    public PageRequest toPageRequest(){
        return PageRequest.of(this.current-1,this.pageSize);
    }
}
