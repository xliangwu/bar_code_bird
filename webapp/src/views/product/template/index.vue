<template>
  <div class="app-container">
    <el-row :gutter="20" :style="{ textAlign: 'left', padding: '6px' }">
      <el-col :span="2">
        <el-button type="primary" icon="el-icon-edit" @click="openNewDialog">新增</el-button>
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
          <el-table-column label="模板名称" prop="title" />
          <el-table-column label="模板类型" prop="type">
            <template slot-scope="scope">
              <span :style="{ paddingLeft: '4px' }">{{
                options_labels[scope.row.type]
              }}</span>
            </template>
          </el-table-column>
          <el-table-column label="模板样式" prop="content">
            <template>
              <span>暂不支持预览</span>
            </template>
          </el-table-column>
          <el-table-column label="Code字体大小(2x2)" prop="p1CodeFontSize" />
          <el-table-column label="Code字体大小(2x3)" prop="p2CodeFontSize" />
          <el-table-column align="center" prop="createdTime" label="最后更新时间" width="240">
            <template slot-scope="scope">
              <i class="el-icon-time" />
              <span :style="{ paddingLeft: '4px' }">{{
                scope.row.createdTime
              }}</span>
            </template>
          </el-table-column>
          <el-table-column fixed="right" label="操作" width="100">
            <template slot-scope="scope">
              <el-button type="text" size="small" @click="openEditDialog(scope.row)">编辑</el-button>
              <el-button type="text" size="small" @click="handleDelete(scope.row)">删除</el-button>
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

    <el-dialog title="新增模板" :visible.sync="newDialogVisible" width="35%" :close-on-click-modal="false">
      <div>
        <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="150px">
          <el-form-item label="模板名称" prop="title">
            <el-input v-model="ruleForm.title" />
          </el-form-item>
          <el-form-item label="模板类型" prop="type">
            <el-select v-model="ruleForm.type" placeholder="选择模板类型" value-key="id">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Code字体大小(2x2)" prop="p1CodeFontSize">
            <el-input v-model="ruleForm.p1CodeFontSize" type="input" />
          </el-form-item>
          <el-form-item label="Code字体大小(2x3)" prop="p2CodeFontSize">
            <el-input v-model="ruleForm.p2CodeFontSize" type="input" />
          </el-form-item>
          <el-form-item label="模板样式" prop="content">
            <el-input v-model="ruleForm.content" type="textarea" :rows="4" />
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="newDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveNewRecord('ruleForm')">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改模板" :visible.sync="editDialogVisible" width="35%" :close-on-click-modal="false">
      <div>
        <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="150px">
          <el-form-item label="模板名称" prop="title">
            <el-input v-model="ruleForm.title" />
          </el-form-item>
          <el-form-item label="模板类型" prop="type">
            <el-select v-model="ruleForm.type" placeholder="选择模板类型" value-key="id">
              <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="Code字体大小(2x2)" prop="p1CodeFontSize">
            <el-input v-model="ruleForm.p1CodeFontSize" type="input" />
          </el-form-item>
          <el-form-item label="Code字体大小(2x3)" prop="p2CodeFontSize">
            <el-input v-model="ruleForm.p2CodeFontSize" type="input" />
          </el-form-item>
          <el-form-item label="模板样式" prop="content">
            <el-input v-model="ruleForm.content" type="textarea" :rows="4" />
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateRecord('ruleForm')">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {
  getList,
  deleteTemplate,
  saveTemplate,
  updateTemplate,
} from "@/api/template";

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
      editDialogVisible: false,
      ruleForm: {
        title: "",
        content: "",
        type: "",
        p1CodeFontSize: 16,
        p2CodeFontSize: 15,
        id: -1,
      },
      options: [
        {
          value: "common",
          label: "普通打印机",
        },
        {
          value: "zebra",
          label: "斑马打印机",
        },
      ],
      options_labels: { common: "普通打印机", zebra: "斑马打印机" },

      rules: {
        title: [
          { required: true, message: "请输入模板名称", trigger: "blur" },
          {
            min: 3,
            max: 24,
            message: "长度在 3 到 24 个字符",
            trigger: "blur",
          },
        ],
        content: [
          { required: true, message: "请输入模板内容", trigger: "blur" },
        ],
        p1CodeFontSize: [
          { required: true, message: "请输入字体大小", trigger: "blur" },
        ],
        p2CodeFontSize: [
          { required: true, message: "请输入字体大小", trigger: "blur" },
        ],
        content: [
          { required: true, message: "请输入模板内容", trigger: "blur" },
          {
            min: 1,
            max: 8192,
            message: "长度在 1 到 8192 个字符",
            trigger: "blur",
          },
        ],
      },
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

    handleDelete(item) {
      if (this.list.length === 1) {
        this.pageIndex = 1;
      }

      var id = item.id;
      deleteTemplate(id).then((response) => {
        this.fetchData();
      });
    },

    handleSearch() {
      console.log("search");
    },

    openNewDialog() {
      this.newDialogVisible = true;
      this.ruleForm.title = null;
      this.ruleForm.content = null;
      this.p2CodeFontSize = 12;
      this.p1CodeFontSize = 12;
    },

    saveNewRecord(formName) {
      this.newDialogVisible = false;
      var record = {
        title: this.ruleForm.title,
        content: this.ruleForm.content,
        type: this.ruleForm.type,
        p2CodeFontSize: this.ruleForm.p2CodeFontSize,
        p1CodeFontSize: this.ruleForm.p1CodeFontSize,
      };

      this.$refs[formName].validate((valid) => {
        if (valid) {
          saveTemplate(record).then((response) => {
            this.fetchData();
          });
        } else {
          this.newDialogVisible = true;
          return false;
        }
      });
    },

    updateRecord(formName) {
      this.editDialogVisible = false;
      var record = {
        title: this.ruleForm.title,
        content: this.ruleForm.content,
        type: this.ruleForm.type,
        p2CodeFontSize: this.ruleForm.p2CodeFontSize,
        p1CodeFontSize: this.ruleForm.p1CodeFontSize,
        id: this.ruleForm.id,
      };

      this.$refs[formName].validate((valid) => {
        if (valid) {
          updateTemplate(record).then((response) => {
            this.fetchData();
          });
        } else {
          this.editDialogVisible = true;
          return false;
        }
      });
    },

    openEditDialog(item) {
      this.editDialogVisible = true;
      this.ruleForm.title = item.title;
      this.ruleForm.type = item.type;
      this.ruleForm.content = item.content;
      this.ruleForm.id = item.id;
      this.ruleForm.p1CodeFontSize = item.p1CodeFontSize;
      this.ruleForm.p2CodeFontSize = item.p2CodeFontSize;
    },

    handleClose(done) {
      this.$confirm("确认关闭？")
        .then((_) => {
          done();
        })
        .catch((_) => {});
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
