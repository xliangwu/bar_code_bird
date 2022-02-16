<template>
  <div class="app-container">
    <el-row :gutter="20" :style="{ textAlign: 'left', padding: '6px' }">
      <el-col :span="2">
        <el-button type="primary" icon="el-icon-edit" @click="openNewDialog">新增</el-button>
      </el-col>
      <el-col v-show="false" :span="10">
        <el-input v-model="keyword" placeholder="请输入内容" class="input-with-select" @keyup.enter.native="handleSearch">
          <el-select slot="prepend" v-model="keywordOption" placeholder="请选择">
            <el-option label="机器编号" value="1" />
          </el-select>
          <el-button slot="append" icon="el-icon-search" @click="handleSearch" />
        </el-input>
      </el-col>
    </el-row>
    <el-row :gutter="20">
      <el-col :span="24">
        <el-table v-loading="listLoading" :data="list" element-loading-text="Loading" border fit highlight-current-row>
          <el-table-column label="机器编号" prop="machineName" />
          <el-table-column label="分类" prop="machineCategory" />
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

    <el-dialog title="新增机器" :visible.sync="newDialogVisible" width="30%">
      <div>
        <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="100px">
          <el-form-item label="机器编号" prop="machineName">
            <el-input v-model="ruleForm.machineName" />
          </el-form-item>
          <el-form-item label="机器分类" prop="machineCategory">
            <el-input v-model="ruleForm.machineCategory" />
          </el-form-item>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="newDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveNewRecord('ruleForm')">确 定</el-button>
      </span>
    </el-dialog>

    <el-dialog title="修改" :visible.sync="editDialogVisible" width="30%">
      <div>
        <el-form ref="ruleForm" :model="ruleForm" :rules="rules" label-width="100px">
          <el-form-item label="机器编号" prop="machineName">
            <el-input v-model="ruleForm.machineName" />
          </el-form-item>
          <el-form-item label="机器分类" prop="machineCategory">
            <el-input v-model="ruleForm.machineCategory" />
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
  deleteMachine,
  saveMachine,
  updateMachine
} from '@/api/machine'

export default {
  data() {
    return {
      list: [],
      listLoading: true,
      total: 0,
      pageIndex: 0,
      pageSize: 10,
      keyword: '',
      keywordOption: '1',
      newDialogVisible: false,
      editDialogVisible: false,
      ruleForm: {
        machineName: '',
        machineCategory: '',
        id: -1
      },
      rules: {
        machineName: [
          { required: true, message: '请输入机器编号', trigger: 'blur' },
          {
            min: 3,
            max: 24,
            message: '长度在 3 到 24 个字符',
            trigger: 'blur'
          }
        ],
        machineCategory: [
          { required: true, message: '请输入机器分类', trigger: 'blur' },
          {
            min: 3,
            max: 24,
            message: '长度在 3 到 24 个字符',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      this.listLoading = true
      var param = { page: this.pageIndex, pageSize: this.pageSize }
      getList(param).then((response) => {
        this.list = response.data.records
        this.listLoading = false
        this.total = response.data.total
      })
    },

    pageSizeChange(val) {
      this.pageSize = val
      console.log('pageSize:' + this.pageSize)
      this.fetchData()
    },

    pageChange(val) {
      this.pageIndex = val
      console.log('pageIndex:' + this.pageIndex)
      this.fetchData()
    },

    handleDelete(item) {
      if (this.list.length === 1) {
        this.pageIndex = 1
      }

      var id = item.id
      deleteMachine(id).then((response) => {
        this.fetchData()
      })
    },

    handleSearch() {
      console.log('search')
    },

    openNewDialog() {
      this.newDialogVisible = true
    },

    saveNewRecord(formName) {
      this.newDialogVisible = false
      var record = {
        machineName: this.ruleForm.machineName,
        machineCategory: this.ruleForm.machineCategory
      }

      this.$refs[formName].validate((valid) => {
        if (valid) {
          saveMachine(record).then((response) => {
            this.fetchData()
          })
        } else {
          this.newDialogVisible = true
          return false
        }
      })
    },

    updateRecord(formName) {
      this.editDialogVisible = false
      var record = {
        machineName: this.ruleForm.machineName,
        machineCategory: this.ruleForm.machineCategory,
        id: this.ruleForm.id
      }

      this.$refs[formName].validate((valid) => {
        if (valid) {
          updateMachine(record).then((response) => {
            this.fetchData()
          })
        } else {
          this.editDialogVisible = true
          return false
        }
      })
    },

    openEditDialog(item) {
      this.editDialogVisible = true
      this.ruleForm.machineName = item.machineName
      this.ruleForm.machineCategory = item.machineCategory
      this.ruleForm.id = item.id
    },

    handleClose(done) {
      this.$confirm('确认关闭？')
        .then((_) => {
          done()
        })
        .catch((_) => {})
    }
  }
}
</script>

<style>
.el-select .el-input {
  width: 130px;
}
.input-with-select .el-input-group__prepend {
  background-color: #fff;
}
</style>
