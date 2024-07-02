package com.wick.boot.module.system.model.dto.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wick.boot.module.system.enums.GenderTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * 后台管理-用户信息
 *
 * @author ZhangZiHeng
 * @date 2024-03-28
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SystemUserDTO {

    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id", example = "1")
    private Long id;

    /**
     * 用户账号
     */
    @ApiModelProperty(value = "用户账号", example = "admin")
    private String username;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称", example = "Wickson")
    private String nickname;

    /**
     * 手机号码
     */
    @ApiModelProperty(value = "手机号码", example = "13312345678")
    private String mobile;

    /**
     * 用户性别
     */
    @ApiModelProperty(value = "用户性别", example = "1")
    private Integer gender;

    /**
     * 用户性别
     */
    @ApiModelProperty(value = "用户性别", example = "男")
    private String genderLabel;

    /**
     * 头像地址
     */
    @ApiModelProperty(value = "头像地址", example = "https://s2.loli.net/2022/04/07/gw1L2Z5sPtS8GIl.gif")
    private String avatar;

    /**
     * 帐号状态（0正常 1停用）
     */
    @ApiModelProperty(value = "帐号状态", example = "0")
    private Integer status;

    /**
     * 部门Id
     */
    @ApiModelProperty(value = "部门名称", example = "1")
    private Long deptId;

    /**
     * 部门名称
     */
    @ApiModelProperty(value = "部门名称", example = "Nexus")
    private String deptName;

    /**
     * 角色id集合
     */
    @ApiModelProperty(value = "角色ID集合", example = "1,2")
    private List<Long> roleIds;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间", example = "2024-04-06 22:11:44")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    public String getGenderLabel() {
        return GenderTypeEnum.valueOf(this.gender);
    }
}
