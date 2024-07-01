import request from "@/utils/request";
import {AxiosPromise} from "axios";
import {LoginLogPageVO, OperateLogPageVO} from "@/api/logger/type";

/**
 * 获取登录日志分页数据
 *
 * @param queryParams
 */
export function getLoginLogPage(queryParams: PageQuery): AxiosPromise<PageResult<LoginLogPageVO[]>> {
  return request({
    url: "/api/v1/login-log/page",
    method: "get",
    params: queryParams,
  });
}

/**
 * 导出用户
 *
 * @param queryParams
 * @returns
 */
export function exportLoginLog(queryParams: PageQuery) {
  return request({
    url: "/api/v1/login-log/export",
    method: "get",
    params: queryParams,
    responseType: "arraybuffer",
  });
}

/**
 * 获取操作日志分页数据
 *
 * @param queryParams
 */
export function getOperateLogPage(queryParams: PageQuery): AxiosPromise<PageResult<OperateLogPageVO[]>> {
  return request({
    url: "/api/v1/operate-log/page",
    method: "get",
    params: queryParams,
  });
}
