(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-6addf38c"],{"94d1":function(e,t,o){},b209:function(e,t,o){"use strict";o.r(t);var l=function(){var e=this,t=e.$createElement,o=e._self._c||t;return o("div",{staticClass:"app-container"},[o("el-row",{style:{textAlign:"left",padding:"6px"},attrs:{gutter:20}},[o("el-col",{attrs:{span:2}},[o("el-button",{attrs:{type:"primary",icon:"el-icon-edit"},on:{click:e.openNewDialog}},[e._v("新增")])],1),o("el-col",{directives:[{name:"show",rawName:"v-show",value:!1,expression:"false"}],attrs:{span:10}},[o("el-input",{staticClass:"input-with-select",attrs:{placeholder:"请输入内容"},nativeOn:{keyup:function(t){return!t.type.indexOf("key")&&e._k(t.keyCode,"enter",13,t.key,"Enter")?null:e.handleSearch(t)}},model:{value:e.keyword,callback:function(t){e.keyword=t},expression:"keyword"}},[o("el-select",{attrs:{slot:"prepend",placeholder:"请选择"},slot:"prepend",model:{value:e.keywordOption,callback:function(t){e.keywordOption=t},expression:"keywordOption"}},[o("el-option",{attrs:{label:"模板名称",value:"1"}})],1),o("el-button",{attrs:{slot:"append",icon:"el-icon-search"},on:{click:e.handleSearch},slot:"append"})],1)],1)],1),o("el-row",{attrs:{gutter:20}},[o("el-col",{attrs:{span:24}},[o("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"}],attrs:{data:e.list,"element-loading-text":"Loading",border:"",fit:"","highlight-current-row":""}},[o("el-table-column",{attrs:{label:"模板名称",prop:"title"}}),o("el-table-column",{attrs:{label:"模板类型",prop:"type"},scopedSlots:e._u([{key:"default",fn:function(t){return[o("span",{style:{paddingLeft:"4px"}},[e._v(e._s(e.options_labels[t.row.type]))])]}}])}),o("el-table-column",{attrs:{label:"模板样式",prop:"content"}},[[o("span",[e._v("暂不支持预览")])]],2),o("el-table-column",{attrs:{label:"Code字体大小(2x2)",prop:"p1CodeFontSize"}}),o("el-table-column",{attrs:{label:"Code字体大小(2x3)",prop:"p2CodeFontSize"}}),o("el-table-column",{attrs:{align:"center",prop:"createdTime",label:"最后更新时间",width:"240"},scopedSlots:e._u([{key:"default",fn:function(t){return[o("i",{staticClass:"el-icon-time"}),o("span",{style:{paddingLeft:"4px"}},[e._v(e._s(t.row.createdTime))])]}}])}),o("el-table-column",{attrs:{fixed:"right",label:"操作",width:"100"},scopedSlots:e._u([{key:"default",fn:function(t){return[o("el-button",{attrs:{type:"text",size:"small"},on:{click:function(o){return e.openEditDialog(t.row)}}},[e._v("编辑")]),o("el-button",{attrs:{type:"text",size:"small"},on:{click:function(o){return e.handleDelete(t.row)}}},[e._v("删除")])]}}])})],1)],1)],1),e.total>0?o("el-row",{style:{textAlign:"right",padding:"6px"},attrs:{gutter:20}},[o("el-col",{attrs:{span:24}},[o("el-pagination",{attrs:{background:"",layout:"prev,sizes,pager, next",total:e.total,"page-size":e.pageSize,"current-page":e.pageIndex},on:{"size-change":e.pageSizeChange,"current-change":e.pageChange}})],1)],1):e._e(),o("el-dialog",{attrs:{title:"新增模板",visible:e.newDialogVisible,width:"35%","close-on-click-modal":!1},on:{"update:visible":function(t){e.newDialogVisible=t}}},[o("div",[o("el-form",{ref:"ruleForm",attrs:{model:e.ruleForm,rules:e.rules,"label-width":"150px"}},[o("el-form-item",{attrs:{label:"模板名称",prop:"title"}},[o("el-input",{model:{value:e.ruleForm.title,callback:function(t){e.$set(e.ruleForm,"title",t)},expression:"ruleForm.title"}})],1),o("el-form-item",{attrs:{label:"模板类型",prop:"type"}},[o("el-select",{attrs:{placeholder:"选择模板类型","value-key":"id"},model:{value:e.ruleForm.type,callback:function(t){e.$set(e.ruleForm,"type",t)},expression:"ruleForm.type"}},e._l(e.options,(function(e){return o("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1),o("el-form-item",{attrs:{label:"Code字体大小(2x2)",prop:"p1CodeFontSize"}},[o("el-input",{attrs:{type:"input"},model:{value:e.ruleForm.p1CodeFontSize,callback:function(t){e.$set(e.ruleForm,"p1CodeFontSize",t)},expression:"ruleForm.p1CodeFontSize"}})],1),o("el-form-item",{attrs:{label:"Code字体大小(2x3)",prop:"p2CodeFontSize"}},[o("el-input",{attrs:{type:"input"},model:{value:e.ruleForm.p2CodeFontSize,callback:function(t){e.$set(e.ruleForm,"p2CodeFontSize",t)},expression:"ruleForm.p2CodeFontSize"}})],1),o("el-form-item",{attrs:{label:"模板样式",prop:"content"}},[o("el-input",{attrs:{type:"textarea",rows:4},model:{value:e.ruleForm.content,callback:function(t){e.$set(e.ruleForm,"content",t)},expression:"ruleForm.content"}})],1)],1)],1),o("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[o("el-button",{on:{click:function(t){e.newDialogVisible=!1}}},[e._v("取 消")]),o("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.saveNewRecord("ruleForm")}}},[e._v("确 定")])],1)]),o("el-dialog",{attrs:{title:"修改模板",visible:e.editDialogVisible,width:"35%","close-on-click-modal":!1},on:{"update:visible":function(t){e.editDialogVisible=t}}},[o("div",[o("el-form",{ref:"ruleForm",attrs:{model:e.ruleForm,rules:e.rules,"label-width":"150px"}},[o("el-form-item",{attrs:{label:"模板名称",prop:"title"}},[o("el-input",{model:{value:e.ruleForm.title,callback:function(t){e.$set(e.ruleForm,"title",t)},expression:"ruleForm.title"}})],1),o("el-form-item",{attrs:{label:"模板类型",prop:"type"}},[o("el-select",{attrs:{placeholder:"选择模板类型","value-key":"id"},model:{value:e.ruleForm.type,callback:function(t){e.$set(e.ruleForm,"type",t)},expression:"ruleForm.type"}},e._l(e.options,(function(e){return o("el-option",{key:e.value,attrs:{label:e.label,value:e.value}})})),1)],1),o("el-form-item",{attrs:{label:"Code字体大小(2x2)",prop:"p1CodeFontSize"}},[o("el-input",{attrs:{type:"input"},model:{value:e.ruleForm.p1CodeFontSize,callback:function(t){e.$set(e.ruleForm,"p1CodeFontSize",t)},expression:"ruleForm.p1CodeFontSize"}})],1),o("el-form-item",{attrs:{label:"Code字体大小(2x3)",prop:"p2CodeFontSize"}},[o("el-input",{attrs:{type:"input"},model:{value:e.ruleForm.p2CodeFontSize,callback:function(t){e.$set(e.ruleForm,"p2CodeFontSize",t)},expression:"ruleForm.p2CodeFontSize"}})],1),o("el-form-item",{attrs:{label:"模板样式",prop:"content"}},[o("el-input",{attrs:{type:"textarea",rows:4},model:{value:e.ruleForm.content,callback:function(t){e.$set(e.ruleForm,"content",t)},expression:"ruleForm.content"}})],1)],1)],1),o("span",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[o("el-button",{on:{click:function(t){e.editDialogVisible=!1}}},[e._v("取 消")]),o("el-button",{attrs:{type:"primary"},on:{click:function(t){return e.updateRecord("ruleForm")}}},[e._v("确 定")])],1)])],1)},i=[],n=o("ade3"),r=o("b775");function a(e){return Object(r["a"])({url:"/v1/template",method:"get",params:e})}function s(e){return Object(r["a"])({url:"/v1/template/"+e,method:"delete"})}function u(e){return Object(r["a"])({url:"/v1/template/save",method:"post",headers:{"Content-Type":"application/json"},data:e})}function p(e){return Object(r["a"])({url:"/v1/template/update",method:"post",headers:{"Content-Type":"application/json;charset=UTF-8"},data:e})}var c={data:function(){return{list:[],listLoading:!0,total:0,pageIndex:0,pageSize:10,keyword:"",keywordOption:"1",newDialogVisible:!1,editDialogVisible:!1,ruleForm:{title:"",content:"",type:"",p1CodeFontSize:16,p2CodeFontSize:15,id:-1},options:[{value:"common",label:"普通打印机"},{value:"zebra",label:"斑马打印机"}],options_labels:{common:"普通打印机",zebra:"斑马打印机"},rules:Object(n["a"])({title:[{required:!0,message:"请输入模板名称",trigger:"blur"},{min:3,max:24,message:"长度在 3 到 24 个字符",trigger:"blur"}],content:[{required:!0,message:"请输入模板内容",trigger:"blur"}],p1CodeFontSize:[{required:!0,message:"请输入字体大小",trigger:"blur"}],p2CodeFontSize:[{required:!0,message:"请输入字体大小",trigger:"blur"}]},"content",[{required:!0,message:"请输入模板内容",trigger:"blur"},{min:1,max:8192,message:"长度在 1 到 8192 个字符",trigger:"blur"}])}},created:function(){this.fetchData()},methods:{fetchData:function(){var e=this;this.listLoading=!0;var t={page:this.pageIndex,pageSize:this.pageSize};a(t).then((function(t){e.list=t.data.records,e.listLoading=!1,e.total=t.data.total}))},pageSizeChange:function(e){this.pageSize=e,console.log("pageSize:"+this.pageSize),this.fetchData()},pageChange:function(e){this.pageIndex=e,console.log("pageIndex:"+this.pageIndex),this.fetchData()},handleDelete:function(e){var t=this;1===this.list.length&&(this.pageIndex=1);var o=e.id;s(o).then((function(e){t.fetchData()}))},handleSearch:function(){console.log("search")},openNewDialog:function(){this.newDialogVisible=!0,this.ruleForm.title=null,this.ruleForm.content=null,this.p2CodeFontSize=12,this.p1CodeFontSize=12},saveNewRecord:function(e){var t=this;this.newDialogVisible=!1;var o={title:this.ruleForm.title,content:this.ruleForm.content,type:this.ruleForm.type,p2CodeFontSize:this.ruleForm.p2CodeFontSize,p1CodeFontSize:this.ruleForm.p1CodeFontSize};this.$refs[e].validate((function(e){if(!e)return t.newDialogVisible=!0,!1;u(o).then((function(e){t.fetchData()}))}))},updateRecord:function(e){var t=this;this.editDialogVisible=!1;var o={title:this.ruleForm.title,content:this.ruleForm.content,type:this.ruleForm.type,p2CodeFontSize:this.ruleForm.p2CodeFontSize,p1CodeFontSize:this.ruleForm.p1CodeFontSize,id:this.ruleForm.id};this.$refs[e].validate((function(e){if(!e)return t.editDialogVisible=!0,!1;p(o).then((function(e){t.fetchData()}))}))},openEditDialog:function(e){this.editDialogVisible=!0,this.ruleForm.title=e.title,this.ruleForm.type=e.type,this.ruleForm.content=e.content,this.ruleForm.id=e.id,this.ruleForm.p1CodeFontSize=e.p1CodeFontSize,this.ruleForm.p2CodeFontSize=e.p2CodeFontSize},handleClose:function(e){this.$confirm("确认关闭？").then((function(t){e()})).catch((function(e){}))}}},d=c,m=(o("cf94"),o("2877")),f=Object(m["a"])(d,l,i,!1,null,null,null);t["default"]=f.exports},cf94:function(e,t,o){"use strict";o("94d1")}}]);