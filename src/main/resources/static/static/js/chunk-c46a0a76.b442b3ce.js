(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-c46a0a76"],{"69f8":function(e,t,a){"use strict";a.d(t,"a",(function(){return o})),a.d(t,"b",(function(){return i})),a.d(t,"c",(function(){return n})),a.d(t,"d",(function(){return l})),a.d(t,"e",(function(){return c}));var r=a("b775");function o(e){return Object(r["a"])({url:"/v1/print/loadMetaData",method:"get",params:e})}function i(e){return Object(r["a"])({url:"/v1/print/preview",method:"post",headers:{"Content-Type":"application/json"},data:e})}function n(e){return Object(r["a"])({url:"/v1/print/print",method:"post",headers:{"Content-Type":"application/json"},data:e})}function l(e){return Object(r["a"])({url:"/v1/zplPrint/preparePrint",method:"post",headers:{"Content-Type":"application/json"},data:e})}function c(e){return Object(r["a"])({url:"/v1/zplPrint/print",method:"post",headers:{"Content-Type":"application/json"},data:e})}},"796a":function(e,t,a){"use strict";a.r(t);var r=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"app-container"},[a("el-form",{ref:"form",attrs:{model:e.form,rules:e.rules,"label-width":"120px"}},[a("el-form-item",{attrs:{label:"供应商"}},[a("el-input",{staticStyle:{width:"35%"},attrs:{disabled:""},model:{value:e.form.name,callback:function(t){e.$set(e.form,"name",t)},expression:"form.name"}})],1),a("el-form-item",{attrs:{label:"模板",prop:"templateId"}},[a("el-select",{staticStyle:{width:"25%"},attrs:{placeholder:"选择模板",filterable:!0,"value-key":"id"},on:{change:e.changeTemplate},model:{value:e.form.templateId,callback:function(t){e.$set(e.form,"templateId",t)},expression:"form.templateId"}},e._l(e.templates,(function(e){return a("el-option",{key:e.id,attrs:{label:e.title,value:e}})})),1)],1),a("el-form-item",{attrs:{label:"机器编号",prop:"machineName"}},[a("el-cascader",{staticStyle:{width:"25%"},attrs:{options:e.machines,props:{expandTrigger:"hover"}},model:{value:e.form.machineName,callback:function(t){e.$set(e.form,"machineName",t)},expression:"form.machineName"}})],1),a("el-form-item",{attrs:{label:"接单卡号",prop:"orderNo"}},[a("el-select",{attrs:{placeholder:"选择接单卡号",filterable:!0,"value-key":"id"},on:{change:e.orderOptionChange},model:{value:e.form.orderNo,callback:function(t){e.$set(e.form,"orderNo",t)},expression:"form.orderNo"}},e._l(e.commodities,(function(e){return a("el-option",{key:e.id,attrs:{label:e.col1,value:e}})})),1)],1),a("el-form-item",{attrs:{label:"品名"}},[a("el-input",{staticStyle:{width:"35%"},attrs:{disabled:""},model:{value:e.form.productCode,callback:function(t){e.$set(e.form,"productCode",t)},expression:"form.productCode"}})],1),a("el-form-item",{attrs:{label:"规格"}},[a("el-input",{staticStyle:{width:"35%"},attrs:{disabled:""},model:{value:e.form.specification,callback:function(t){e.$set(e.form,"specification",t)},expression:"form.specification"}})],1),a("el-form-item",{attrs:{label:"SAP代码"}},[a("el-input",{staticStyle:{width:"35%"},attrs:{disabled:""},model:{value:e.form.sapCode,callback:function(t){e.$set(e.form,"sapCode",t)},expression:"form.sapCode"}})],1),a("el-form-item",{attrs:{label:"日期",prop:"selectedDate"}},[a("el-col",{attrs:{span:24}},[a("el-date-picker",{staticStyle:{width:"35%"},attrs:{type:"date",format:"yyyy年MM月dd日","value-format":"yyyyMMdd",placeholder:"选择日期"},model:{value:e.form.selectedDate,callback:function(t){e.$set(e.form,"selectedDate",t)},expression:"form.selectedDate"}})],1)],1),a("el-form-item",{attrs:{label:"箱容量"}},[a("el-input",{staticStyle:{width:"35%"},model:{value:e.form.capacity,callback:function(t){e.$set(e.form,"capacity",t)},expression:"form.capacity"}},[a("el-select",{staticStyle:{width:"100px"},attrs:{slot:"prepend",placeholder:"请选择"},slot:"prepend",model:{value:e.form.capacityLabel,callback:function(t){e.$set(e.form,"capacityLabel",t)},expression:"form.capacityLabel"}},[a("el-option",{attrs:{label:"枚/卷",value:"枚/卷"}}),a("el-option",{attrs:{label:"枚/箱",value:"枚/箱"}})],1)],1)],1),a("el-form-item",{attrs:{label:"起始页数"}},[a("el-input-number",{staticStyle:{width:"150px"},attrs:{step:1,size:"medium",min:1,max:5e3},model:{value:e.form.startIndex,callback:function(t){e.$set(e.form,"startIndex",t)},expression:"form.startIndex"}})],1),a("el-form-item",{attrs:{label:"打印份数"}},[a("el-input-number",{staticStyle:{width:"150px"},attrs:{step:1,size:"medium",min:1,max:5e3},model:{value:e.form.printCount,callback:function(t){e.$set(e.form,"printCount",t)},expression:"form.printCount"}})],1),a("el-form-item",[a("el-col",{attrs:{span:2}}),a("el-col",{attrs:{span:2}},[a("el-button",{attrs:{icon:"el-icon-printer",type:"primary"},on:{click:function(t){return e.onPreview("form")}}},[e._v("打印")])],1)],1)],1),a("el-dialog",{staticStyle:{padding:"0px 8px"},attrs:{title:"打印",visible:e.previewDialogVisible,"before-close":e.beforeClosePreviewDialog,"close-on-click-modal":!1,width:"600px",center:!0},on:{"update:visible":function(t){e.previewDialogVisible=t}}},[a("span",[a("el-form",{ref:"printForm",attrs:{model:e.printForm,rules:e.rules,"label-width":"120px"}},[a("el-form-item",{attrs:{label:"斑马打印机",prop:"printer"}},[a("el-select",{attrs:{placeholder:"选择斑马打印机",filterable:!0},model:{value:e.printForm.printer,callback:function(t){e.$set(e.printForm,"printer",t)},expression:"printForm.printer"}},e._l(e.printers,(function(e){return a("el-option",{key:e,attrs:{label:e,value:e}})})),1)],1),a("el-form-item",{directives:[{name:"show",rawName:"v-show",value:e.showPreviewImg,expression:"showPreviewImg"}],attrs:{label:"缩略图"}},[a("el-image",{staticStyle:{width:"400px"},attrs:{src:e.previewImg}})],1),a("el-form-item",[a("el-row",[a("el-col",{attrs:{span:8}},[a("el-button",{on:{click:function(t){e.previewDialogVisible=!1}}},[e._v("取 消")])],1),a("el-col",{attrs:{span:3}}),a("el-col",{attrs:{span:8}},[a("el-button",{attrs:{icon:"el-icon-printer",type:"primary"},on:{click:function(t){return e.printZpl("printForm")}}},[e._v("打印")])],1)],1)],1)],1)],1)])],1)},o=[],i=a("69f8"),n=(a("5c96"),{data:function(){return{form:{name:"上海福助工业有限公司",orderNo:"",machineName:[],selectedDate:new Date,startIndex:1,printCount:1,capacity:"",productCode:"",productName:"",sapCode:"",specification:"",templateId:null,capacityLabel:"枚/卷"},printForm:{printer:null},templateHtml:"",templateContent:"",previewImg:null,showPreviewImg:!1,printers:[],rules:{machineName:[{required:!0,message:"请输入机器编号",trigger:"blur"}],orderNo:[{required:!0,message:"请选择接单卡号",trigger:"change"}],templateId:[{required:!0,message:"请选择模板",trigger:"change"}],printer:[{required:!0,message:"请选择斑马打印机",trigger:"change"}],selectedDate:[{required:!0,message:"请选择日期",trigger:"change"}]},commodities:[],machines:[],templates:[],previewDialogVisible:!1}},created:function(){this.loadMetaData()},methods:{printZpl:function(e){var t=this;this.$refs[e].validate((function(e){if(!e)return!1;var a={templateContent:t.templateContent,specification:t.form.specification,machineCode:t.form.machineName[1],selectedDate:t.form.selectedDate,startIndex:t.form.startIndex,printCount:t.form.printCount,capacity:t.form.capacity?t.form.capacity+" "+t.form.capacityLabel:"",sapCode:t.form.sapCode,productCode:t.form.productCode,productName:t.form.productName,printer:t.printForm.printer};Object(i["e"])(a).then((function(e){t.previewDialogVisible=!1,t.$message({message:"发送打印命令成功！",type:"success"})}))}))},onPreview:function(e){var t=this;this.$refs[e].validate((function(e){if(!e)return!1;var a={templateContent:t.templateContent,specification:t.form.specification,machineCode:t.form.machineName[1],selectedDate:t.form.selectedDate,startIndex:t.form.startIndex,printCount:t.form.printCount,capacity:t.form.capacity?t.form.capacity+" "+t.form.capacityLabel:"",sapCode:t.form.sapCode,productCode:t.form.productCode,productName:t.form.productName};Object(i["d"])(a).then((function(e){t.previewDialogVisible=!0,t.printers=e.data.printers,t.showPreviewImg=null!==e.data.previewImg,t.previewImg="data:image/png;base64,"+e.data.previewImg}))}))},beforeClosePreviewDialog:function(){console.log("call before close dialog"),this.previewDialogVisible=!1},changeTemplate:function(e){this.templateContent=e.content},orderOptionChange:function(e){this.form.capacity=e.col6,this.form.productName=e.col4,this.form.productCode=e.col3,this.form.sapCode=e.col2,this.form.specification=e.col5},loadMetaData:function(){var e=this;this.listLoading=!0;var t={type:"zebra"};Object(i["a"])(t).then((function(t){console.log(t.data.machines.length),e.commodities=t.data.commodities,e.machines=t.data.machines,e.templates=t.data.templates}))}}}),l=n,c=(a("8cd9"),a("2877")),s=Object(c["a"])(l,r,o,!1,null,null,null);t["default"]=s.exports},"8cd9":function(e,t,a){"use strict";a("d8ad")},d8ad:function(e,t,a){}}]);