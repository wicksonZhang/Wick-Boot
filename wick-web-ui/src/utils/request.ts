import axios, {InternalAxiosRequestConfig, AxiosResponse} from "axios";
import {useUserStoreHook} from "@/store/modules/user";

// 创建 axios 实例
const service = axios.create({
  baseURL: import.meta.env.VITE_APP_BASE_API,
  timeout: 50000,
  headers: {"Content-Type": "application/json;charset=utf-8"},
});

// 请求拦截器
service.interceptors.request.use(
  (config: InternalAxiosRequestConfig) => {
    const accessToken = localStorage.getItem("accessToken");
    if (accessToken) {
      config.headers.Authorization = accessToken;
    }
    return config;
  },
  (error: any) => {
    return Promise.reject(error);
  }
);

// 响应拦截器
service.interceptors.response.use(
  (response: AxiosResponse) => {
    // 响应数据为二进制流处理(Excel导出)
    if (response.data instanceof ArrayBuffer) {
      return response;
    }
    const {code, msg} = response.data;
    if (code === 0) {
      return response.data;
    }
    // token 过期,重新登录
    if (code === 401) {
      ElMessageBox.confirm("当前页面已失效，请重新登录", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      }).then(() => {
        const userStore = useUserStoreHook();
        userStore.resetToken().then(() => {
          location.reload();
        });
      });
    }
    ElMessage.error(msg || "系统出错");
    return Promise.reject(new Error(msg || "Error"));
  },
  (error: any) => {
    ElMessage.error("系统出错");
    return Promise.reject(error.message || "Error");
  }
);

// 导出 axios 实例
export default service;
