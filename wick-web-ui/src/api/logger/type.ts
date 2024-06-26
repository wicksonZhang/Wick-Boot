/**
 * 登录日志查询对象类型
 */
export interface LoginLogQuery extends PageQuery {
  /**
   * 用户名称
   */
  username?: string;
  /**
   * 用户ip
   */
  userIp?: string;
  /**
   * 登录日期
   */
  createTime?: Date[];
}

/**
 * 登录日志分页对象
 */
export interface LoginLogPageVO {
  /**
   * 日志编号
   */
  id: number;
  /**
   * 操作类型
   */
  logType: number;
  /**
   * 链路追踪id
   */
  traceId: number;
  /**
   * 用户id
   */
  userId: number;
  /**
   *用户类型
   */
  userType: number;
  /**
   * 用户名称
   */
  username: string;
  /**
   * 登陆结果
   */
  result: number;
  /**
   * 登录地址
   */
  userIp: string;
  /**
   * 浏览器
   */
  userAgent: string;
  /**
   * 登录日期
   */
  createTime: Date
}
