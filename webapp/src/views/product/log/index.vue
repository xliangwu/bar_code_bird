<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="24">
        <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" border fit highlight-current-row>
          <el-table-column label="日志类型" prop="loggedEvent" width="180" />
          <el-table-column label="日志信息" prop="loggedEntity" />
          <el-table-column align="center" prop="loggedTime" label="最后更新时间" width="240">
            <template slot-scope="scope">
              <i class="el-icon-time" />
              <span :style="{ paddingLeft: '4px' }">{{
                scope.row.loggedTime
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

  </div>
</template>

<script>
import { getList } from "@/api/log";

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
