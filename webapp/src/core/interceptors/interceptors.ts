import { message } from "ant-design-vue";
import { getToken } from "@/utils/env";
import { AxiosInstance } from "axios";

export default (service: AxiosInstance) => {
  // 添加请求拦截器
  service.interceptors.request.use(
    function(config) {
      config.headers = {
        "Cache-Control": "no-cache",
        Pragma: "no-cache"
      };

      const token = getToken();
      if (token) config.headers["x-Auth-Token"] = token;

      // 在发送请求之前做些什么
      return config;
    },
    function(error) {
      // 对请求错误做些什么
      message.error("请求出错");
      return Promise.reject(error);
    }
  );

  // 添加响应拦截器
  service.interceptors.response.use(
    function(response) {
      if (response.status === 403) {
        location.href = "/403";
        return;
      }
      // 对响应数据做点什么
      if (!response.data.success) {
        // 当code为5500时, 代表导入标签模板文件上传失败,单独处理
        if (response.data.code !== 5500) {
          if (response.data.code > 5000 || response.data.code === 999) {
            message.error(response.data.message);
          } else {
            message.error("网络发生错误, 请稍后再试");
          }
        }
        return Promise.reject(response);
      }
      return response.data;
    },
    function(error) {
      // 对响应错误做点什么
      message.error("网络发生错误, 请稍后再试");
      return Promise.reject(error);
    }
  );
};
