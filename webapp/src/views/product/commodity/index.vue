<template>
  <div class="app-container">
    <el-row :gutter="20" :style="{ textAlign: 'left', padding: '12px' }">
      <el-col :span="2">
        <el-button type="primary" icon="el-icon-download" round @click="downloadTemplate">下载模板</el-button>
      </el-col>
      <el-col :span="2.5">
        <el-button type="success" icon="el-icon-download" round @click="exportData">下载全部数据</el-button>
      </el-col>
      <el-col :span="2">
        <el-button type="danger" icon="el-icon-upload" round @click="openUploadDialog">数据上传</el-button>
      </el-col>
      <el-col v-show="false" :span="10">
        <el-input v-model="keyword" placeholder="请输入内容" class="input-with-select" @keyup.enter.native="handleSearch">
          <el-select slot="prepend" v-model="keywordOption" placeholder="请选择">
            <el-option label="模板名称" value="1" />
          </el-select>
          <el-button slot="append" icon="el-icon-search" @click="handleSearch" />
        </el-input>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="24">
        <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" border fit highlight-current-row>
          <el-table-column label="接单卡号" prop="col1" />
          <el-table-column label="SAP代码" prop="col2" />
          <el-table-column label="品名码" prop="col3" />
          <el-table-column label="产品名" prop="col4" width="400" />
          <el-table-column label="规格" prop="col5" />
          <el-table-column label="数量" prop="col6" />
          <el-table-column label="纸箱编号" prop="col7" />
           <el-table-column label="最新更新地址" prop="col8" />
          <el-table-column align="center" prop="createdTime" label="最后更新时间" width="240">
            <template slot-scope="scope">
              <i class="el-icon-time" />
              <span :style="{ paddingLeft: '4px' }">{{
                scope.row.createdTime
              }}</span>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
    <el-row v-if="total>0" :gutter="20" :style="{ textAlign: 'right', padding: '6px' }">
      <el-col :span="24">
        <el-pagination background layout="prev,sizes,pager, next" :total="total" :page-size="pageSize" :current-page="pageIndex" @size-change="pageSizeChange" @current-change="pageChange" />
      </el-col>
    </el-row>

    <el-dialog title="上传数据" :visible.sync="newDialogVisible" width="35%" :close-on-click-modal="false">
      <div :style="{textAlign:'center'}">
        <el-upload class="upload-demo" drag action="" :http-request="uploadFile" :show-file-list="false">
          <i class="el-icon-upload" />
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div slot="tip" class="el-upload__tip">只能上传.xlsx/.xls文件，且不超过5M</div>
        </el-upload>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getList,
  downloadTemplate,
  uploadTemplate,
  exportData,
} from "@/api/commodity";
import { Message } from "element-ui";

export default {
  data() {
    return {
      list: [],
      listLoading: true,
      total: 0,
      pageIndex: 0,
      pageSize: 10,
      keyword: "",
      keywordOption: "1",
      newDialogVisible: false,
    };
  },
  created() {
    this.fetchData();
  },
  methods: {
    fetchData() {
      this.listLoading = true;
      var param = { page: this.pageIndex, pageSize: this.pageSize };
      getList(param).then((response) => {
        this.list = response.data.records;
        this.listLoading = false;
        this.total = response.data.total;
      });
    },

    pageSizeChange(val) {
      this.pageSize = val;
      console.log("pageSize:" + this.pageSize);
      this.fetchData();
    },

    pageChange(val) {
      this.pageIndex = val;
      console.log("pageIndex:" + this.pageIndex);
      this.fetchData();
    },

    handleSearch() {
      console.log("search");
    },

    downloadTemplate() {
      downloadTemplate("s1").then((response) => {
        if (!response) {
          return;
        }
        var fileName = "模板";
        const blob = new Blob([response.data]);
        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          navigator.msSaveBlob(blob, fileName);
        } else {
          const binaryData = [];
          binaryData.push(blob);
          const u = window.URL.createObjectURL(new Blob(binaryData));
          const aLink = document.createElement("a");
          aLink.style.display = "none";
          aLink.href = u;
          aLink.setAttribute("download", fileName + ".xlsx");
          document.body.appendChild(aLink);
          aLink.click();
          document.body.removeChild(aLink);
          window.URL.revokeObjectURL(u);
        }
      });
    },

    exportData() {
      exportData("s1").then((response) => {
        if (!response) {
          return;
        }
        var fileName = "产品信息表";
        const blob = new Blob([response.data]);
        if (window.navigator && window.navigator.msSaveOrOpenBlob) {
          navigator.msSaveBlob(blob, fileName);
        } else {
          const binaryData = [];
          binaryData.push(blob);
          const u = window.URL.createObjectURL(new Blob(binaryData));
          const aLink = document.createElement("a");
          aLink.style.display = "none";
          aLink.href = u;
          aLink.setAttribute("download", fileName + ".xlsx");
          document.body.appendChild(aLink);
          aLink.click();
          document.body.removeChild(aLink);
          window.URL.revokeObjectURL(u);
        }
      });
    },

    openUploadDialog() {
      this.newDialogVisible = true;
    },

    uploadFile(param) {
      const file = param.file;
      // hardcode sourceType = s1; customerId = 1
      uploadTemplate(file, "s1", 1).then((response) => {
        if (response.status !== 200 || response.data.code !== 0) {
          Message({
            message: "上传数据失败",
            type: "error",
            center: true,
            duration: 3 * 1000,
          });
        } else {
          Message({
            message: "上传数据成功",
            type: "success",
            center: true,
            duration: 3 * 1000,
          });
          this.newDialogVisible = false;
          this.fetchData();
        }
      });
    },
  },
};
</script>

<style>
.el-select .el-input {
  width: 130px;
}
.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
</style>
