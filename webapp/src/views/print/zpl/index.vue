<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-form-item label="供应商">
        <el-input v-model="form.name" style="width: 35%;" disabled />
      </el-form-item>
      <el-form-item label="模板" prop="templateId">
        <el-select v-model="form.templateId" placeholder="选择模板" :filterable="true" value-key="id" @change="changeTemplate">
          <el-option v-for="item in templates" :key="item.id" :label="item.title" :value="item" />
        </el-select>
      </el-form-item>
      <el-form-item label="机器编号" prop="machineName">
        <el-select v-model="form.machineName" placeholder="选择机器编号" value-key="id">
          <el-option v-for="item in machines" :key="item.id" :label="item.machineName" :value="item.machineName" />
        </el-select>
      </el-form-item>
      <el-form-item label="接单卡号" prop="orderNo">
        <el-select v-model="form.orderNo" placeholder="选择接单卡号" :filterable="true" value-key="id" @change="orderOptionChange">
          <el-option v-for="item in commodities" :key="item.id" :label="item.col1" :value="item" />
        </el-select>
      </el-form-item>
      <el-form-item label="品名">
        <el-input v-model="form.productCode" style="width: 35%;" disabled />
      </el-form-item>
      <el-form-item label="规格">
        <el-input v-model="form.specification" style="width: 35%;" disabled />
      </el-form-item>
      <el-form-item label="SAP代码">
        <el-input v-model="form.sapCode" style="width: 35%;" disabled />
      </el-form-item>
      <el-form-item label="日期" prop="selectedDate">
        <el-col :span="24">
          <el-date-picker v-model="form.selectedDate" type="date" format="yyyy年MM月dd日" value-format="yyyyMMdd" placeholder="选择日期" style="width: 35%;" />
        </el-col>
      </el-form-item>
      <el-form-item label="箱容量">
        <el-input v-model="form.capacity" style="width: 35%;" />
      </el-form-item>

      <el-form-item label="起始页数">
        <el-input-number v-model="form.startIndex" :step="1" size="medium" style="width:150px" :min="1" :max="5000" />
      </el-form-item>
      <el-form-item label="打印份数">
        <el-input-number v-model="form.printCount" :step="1" size="medium" style="width:150px" :min="1" :max="5000" />
      </el-form-item>

      <el-form-item>
        <el-col :span="2" />
        <el-col :span="2">
          <el-button icon="el-icon-printer" type="primary" @click="onPreview('form')">打印</el-button>
        </el-col>
      </el-form-item>
    </el-form>

    <el-dialog title="打印" :visible.sync="previewDialogVisible" :before-close="beforeClosePreviewDialog" :close-on-click-modal="false" width="600px" :center="true" style="padding:0px 8px;">
      <span>
        <el-form ref="printForm" :model="printForm" :rules="rules" label-width="120px">
          <el-form-item label="斑马打印机" prop="printer">
            <el-select v-model="printForm.printer" placeholder="选择斑马打印机" :filterable="true">
              <el-option v-for="item in printers" :key="item" :label="item" :value="item" />
            </el-select>
          </el-form-item>
          <el-form-item label="缩略图" v-show="false">
            <el-image :src="previewImg" style="width:400px"></el-image>
          </el-form-item>
          <el-form-item>
            <el-row>
              <el-col :span="8">
                <el-button @click="previewDialogVisible = false">取 消</el-button>
              </el-col>
              <el-col :span="3" />
              <el-col :span="8">
                <el-button icon="el-icon-printer" type="primary" @click="printZpl('printForm')">打印</el-button>
              </el-col>
            </el-row>

          </el-form-item>
        </el-form>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { loadMetaData, zplPreparePrint, zplPrint } from "@/api/print";
import { Message } from "element-ui";

export default {
  data() {
    return {
      form: {
        name: "上海福助工业有限公司",
        orderNo: "",
        machineName: "",
        selectedDate: new Date(),
        startIndex: 1,
        printCount: 1,
        capacity: "",
        productCode: "",
        productName: "",
        sapCode: "",
        specification: "",
        templateId: null,
      },
      printForm: {
        printer: null,
      },
      templateHtml: "",
      templateContent: "",
      previewImg: null,
      showPreviewImg: false,
      printers: [],
      rules: {
        machineName: [
          { required: true, message: "请输入机器编号", trigger: "blur" },
        ],
        orderNo: [
          { required: true, message: "请选择接单卡号", trigger: "change" },
        ],
        templateId: [
          { required: true, message: "请选择模板", trigger: "change" },
        ],
        printer: [
          { required: true, message: "请选择斑马打印机", trigger: "change" },
        ],
        selectedDate: [
          {
            required: true,
            message: "请选择日期",
            trigger: "change",
          },
        ],
      },
      commodities: [],
      machines: [],
      templates: [],
      previewDialogVisible: false,
    };
  },

  created() {
    this.loadMetaData();
  },

  methods: {
    printZpl(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var parmas = {
            templateContent: this.templateContent,
            specification: this.form.specification,
            machineCode: this.form.machineName,
            selectedDate: this.form.selectedDate,
            startIndex: this.form.startIndex,
            printCount: this.form.printCount,
            capacity: this.form.capacity,
            sapCode: this.form.sapCode,
            productCode: this.form.productCode,
            productName: this.form.productName,
            printer: this.printForm.printer,
          };

          zplPrint(parmas).then((response) => {
            this.previewDialogVisible = false;
            this.$message({
              message: "发送打印命令成功！",
              type: "success",
            });
          });
        } else {
          return false;
        }
      });
    },

    onPreview(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var parmas = {
            templateContent: this.templateContent,
            specification: this.form.specification,
            machineCode: this.form.machineName,
            selectedDate: this.form.selectedDate,
            startIndex: this.form.startIndex,
            printCount: this.form.printCount,
            capacity: this.form.capacity,
            sapCode: this.form.sapCode,
            productCode: this.form.productCode,
            productName: this.form.productName,
          };
          zplPreparePrint(parmas).then((response) => {
            this.previewDialogVisible = true;
            this.printers = response.data.printers;
            this.showPreviewImg = response.data.previewImg !== null;
            this.previewImg =
              "data:image/png;base64," + response.data.previewImg;
          });
        } else {
          return false;
        }
      });
    },

    beforeClosePreviewDialog() {
      console.log("call before close dialog");
      this.previewDialogVisible = false;
    },

    changeTemplate(item) {
      this.templateContent = item.content;
    },

    orderOptionChange(item) {
      this.form.capacity = item.col6;
      this.form.productName = item.col4;
      this.form.productCode = item.col3;
      this.form.sapCode = item.col2;
      this.form.specification = item.col5;
    },
    loadMetaData() {
      this.listLoading = true;
      var param = { type: "zebra" };
      loadMetaData(param).then((response) => {
        console.log(response.data.machines.length);
        this.commodities = response.data.commodities;
        this.machines = response.data.machines;
        this.templates = response.data.templates;
      });
    },
  },
};
</script>

<style scope lang="scss">
.line {
  text-align: center;
}

.previewContainer {
  font-size: 18px;
  line-height: 24px;
  color: #303133;
  text-align: center;
  margin: 0px auto;
  table {
    border-collapse: collapse;
    table-layout: fixed;
  }

  td {
    border: 1px solid #ddd;
    padding: 2px 6px;
    font-size: 18px;
    height: 48px;
  }

  th {
    border: 1px solid #ddd;
    padding: 2px 6px;
    font-size: 18px;
    height: 48px;
  }
}
</style>

