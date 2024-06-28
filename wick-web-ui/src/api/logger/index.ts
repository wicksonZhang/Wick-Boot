import request from "@/utils/request";
import {AxiosPromise} from "axios";
import {LoginLogPageVO} from "@/api/logger/type";

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
