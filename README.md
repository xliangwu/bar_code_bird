# bar_code_bird

条形码系统，定制化条形码.

# 功能介绍

用户可以自定制要打印的格式内容，然后批量生产打印内容。

# 语法

> |类型|格式|参数|

- $DATE$yyyy-MM-dd$selectedDate$
- $PARAM$$MACHINE_CODE$
- $TEXT$$MACHINE_CODE$
- $QR_CODE$$$

# 技术栈

- SpringBoot 2.6.2
- Mybatis
- vue3+

# zpl
- [ZPL指令](http://www.chongshang.com.cn/manual/zebra_Simsun_font.shtml)
- [斑马GT820条码打印机直接打印汉字的方法](http://www.zebra-zh.com/supports/Zebra-GT820-ZPL.html)

# 模板

1. 支持订单号
```html
<table style="padding-left:58px;padding-right:4px;font-size: 10px;margin:auto;f2-font-size:12px;f1-font-size:14px;padding-top:8px">
    <thead>
    <tr>
        <td style="text-align: center;height:18px">供应商</td>
        <td colspan="2" style="text-align: center;font-size:18px;p2-font-size:14px">上海福助工业有限公司</td>
    </tr>
    </thead>
    <tbody>
    <tr>
        <td style="text-align: center">品  名</td>
        <td colspan="2" style="text-align: left;padding-left:4px;width:300px;p1-header-font-size:40px;p2-header-font-size:20px;font-weight: bold">$PARAM$%s::%s$productCode,productName$</td>
    </tr>
    <tr>
        <td style="text-align: center;width:130px;height:18px">订单号</td>
        <td style="text-align: center;width:200px">$PARAM$$orderCode$</td>
        <td rowspan="5" style="text-align: center;padding-left:16px;">
            $QR_CODE$%s/%s/%s%s%02d-fz/%s/%s$orderCode,sapCode,serialNumber,shortDate,index,selectedDate,capacity$
        </td>
    </tr>
	<tr>
        <td style="text-align: center;width:130px;height:18px">生产日期</td>
        <td style="text-align: center;width:200px">$DATE$yyyy年MM月dd日$selectedDate$</td>
    </tr>
    <tr>
        <td style="text-align: center;height:18px">数  量</td>
        <td style="text-align: center">$PARAM$$capacity$</td>
    </tr>
    <tr>
        <td style="text-align: center;height:18px">批  号</td>
        <td style="text-align: center">$PARAM$%s%s%02d$machineCode,shortDate,index$</td>
    </tr>
    <tr>
        <td style="text-align: center;padding-left:4px;padding-right:4px;height:18px">CODE代码</td>
        <td style="text-align: center;font-size:38px;p2-font-size:26px;padding-left:2px;padding-right:2px;font-weight: bold">
            $PARAM$$sapCode$
        </td>
    </tr>
    </tbody>
</table>
```

