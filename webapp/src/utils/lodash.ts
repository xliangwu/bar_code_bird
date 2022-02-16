import { Any } from "@/core/types/basic.type";
import { ColumnsType } from "@/core/types/rule.type";
import { columnOperate } from "@/utils/const";

// 生成uuid
export function generateUUID() {
  let d = new Date().getTime();
  if (window.performance && typeof window.performance.now === "function") {
    d += performance.now(); //use high-precision timer if available
  }
  return "xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx".replace(/[xy]/g, function(c) {
    const r = (d + Math.random() * 16) % 16 | 0;
    d = Math.floor(d / 16);
    return (c === "x" ? r : (r & 0x3) | 0x8).toString(16);
  });
}

/**
 获取url上的参数对象
 @function getUrlParams
 @return {**: ****}
 */
export function getUrlParams() {
  const { href, search } = location;
  const query: Any = {};
  if (search) {
    const paramsString = href.split("?")[1];
    const paramsArr = paramsString.split("&");
    for (let i = 0, len = paramsArr.length; i < len; i++) {
      const urlItem = paramsArr[i];
      const item = urlItem.split("=");
      query[item[0]] = item[1];
    }
  }
  return query;
}


export function processColumns(columns: ColumnsType[], isNeedOperate = true) {
  // 添加slot
  columns.forEach((item, index) => {
    item["scopedSlots"] = { customRender: "table-" + index };
    item["ellipsis"] = true;
    item.value = item.title;
    delete item.title;
    item["slots"] = { title: "customHeader-" + index };
    if (item.value === "最后更新时间") {
      item["width"] = 200;
    }
  });
  // 添加操作行, 问题: 什么时候添加操作行
  if (isNeedOperate) {
    columns.push(columnOperate);
  }
}

/*
 * fn [function] 需要节流的函数
 * delay [number] 毫秒，节流期限值， 默认200
 */
export function throttle() {
  let isTimer = true;
  return function(fn: Function, delay = 200) {
    if (isTimer) {
      isTimer = false;
      fn();
      setTimeout(() => {
        isTimer = true;
      }, delay);
    }
  };
}
/*
 * fn [function] 需要防抖的函数
 * delay [number] 毫秒，防抖期限值， 默认200
 */
export function debounce() {
  let timer: number;
  return function(fn: Function, delay = 200) {
    setTimeout(fn, delay);
    timer && clearTimeout(timer); //进入该分支语句，说明当前正在一个计时过程中，并且又触发了相同事件。所以要取消当前的计时，重新开始计时
    timer = setTimeout(fn, delay);
  };
}

/**
 将string[] =》 number[]
 */
export function stringArr2NumberArr(arr: string[]): number[] {
  return arr.map(item => Number(item));
}
