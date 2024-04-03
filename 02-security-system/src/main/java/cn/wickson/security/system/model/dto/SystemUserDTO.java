package cn.wickson.security.system.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private Long id;

    /**
     * 用户账号
     */
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 用户性别
     */
    private Integer gender;

    /**
     * 头像地址
     */
    private String avatar;

    /**
     * 帐号状态（0正常 1停用）
     */
    private Integer status;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

}
