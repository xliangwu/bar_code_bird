(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-7beb84c4"],{"1a44":function(e,t,a){},d86f:function(e,t,a){"use strict";a("1a44")},e028:function(e,t,a){"use strict";a.r(t);var n=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"app-container"},[a("el-row",{attrs:{gutter:20}},[a("el-col",{attrs:{span:24}},[a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.listLoading,expression:"listLoading"}],attrs:{data:e.list,"element-loading-text":"Loading",border:"",fit:"","highlight-current-row":""}},[a("el-table-column",{attrs:{label:"日志类型",prop:"loggedEvent",width:"180"}}),a("el-table-column",{attrs:{label:"日志信息",prop:"loggedEntity"}}),a("el-table-column",{attrs:{align:"center",prop:"loggedTime",label:"最后更新时间",width:"240"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("i",{staticClass:"el-icon-time"}),a("span",{style:{paddingLeft:"4px"}},[e._v(e._s(t.row.loggedTime))])]}}])})],1)],1)],1),e.total>0?a("el-row",{style:{textAlign:"right",padding:"6px"},attrs:{gutter:20}},[a("el-col",{attrs:{span:24}},[a("el-pagination",{attrs:{background:"",layout:"prev,sizes,pager, next",total:e.total,"page-size":e.pageSize,"current-page":e.pageIndex},on:{"size-change":e.pageSizeChange,"current-change":e.pageChange}})],1)],1):e._e()],1)},i=[],l=a("b775");a("bc3a");function o(e){return Object(l["a"])({url:"/v1/log",method:"get",params:e})}var s={data:function(){return{list:[],listLoading:!0,total:0,pageIndex:0,pageSize:10,keyword:"",keywordOption:"1"}},created:function(){this.fetchData()},methods:{fetchData:function(){var e=this;this.listLoading=!0;var t={page:this.pageIndex,pageSize:this.pageSize};o(t).then((function(t){e.list=t.data.records,e.listLoading=!1,e.total=t.data.total}))},pageSizeChange:function(e){this.pageSize=e,console.log("pageSize:"+this.pageSize),this.fetchData()},pageChange:function(e){this.pageIndex=e,console.log("pageIndex:"+this.pageIndex),this.fetchData()},handleSearch:function(){console.log("search")}}},r=s,g=(a("d86f"),a("2877")),c=Object(g["a"])(r,n,i,!1,null,null,null);t["default"]=c.exports}}]);