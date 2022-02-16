import { Vue } from "vue-property-decorator";

const isDev = process.env.NODE_ENV === "development";
const devBaseUrl = "http://172.19.75.49:18090";

/**
 依据环境变量获取相应得baseUrl
 */
export const getBaseUrl = () => (isDev ? devBaseUrl : "");

/**
 依据环境变量获取token
 */
export const getToken = () =>
  isDev
    ? Vue.prototype.xToken || process.env.VUE_APP_TOKEN
    : Vue.prototype.xToken;
