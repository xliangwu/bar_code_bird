<template>
  <div class="app-container">
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-form-item label="供应商">
        <el-input v-model="form.name" style="width: 35%;" disabled />
      </el-form-item>
      <el-form-item label="模板" prop="templateId">
        <el-select v-model="form.templateId" style="width: 25%;" placeholder="选择模板" :filterable="true" value-key="id"
          @change="changeTemplate">
          <el-option v-for="item in templates" :key="item.id" :label="item.title" :value="item" />
        </el-select>
      </el-form-item>
      <el-form-item label="订单号" v-if="enableOrderCode">
        <el-input v-model="form.orderCode" style="width: 35%;" />
      </el-form-item>

      <el-form-item label="机器编号" prop="machineName">
        <el-cascader v-model="form.machineName" style="width: 25%;" :options="machines"
          :props="{ expandTrigger: 'hover' }"></el-cascader>
      </el-form-item>
      <el-form-item label="接单卡号" prop="orderNo">
        <el-select v-model="form.orderNo" style="width: 25%;" placeholder="选择接单卡号" :filterable="true" value-key="id"
          @change="orderOptionChange">
          <el-option v-for="item in commodities" :key="item.id" :label="item.col1" :value="item" />
        </el-select>
      </el-form-item>
      <el-form-item label="品名">
        <el-input v-model="form.productCode" style="width: 35%;" disabled />
      </el-form-item>
      <el-form-item label="产品名">
        <el-input v-model="form.productName" style="width: 35%;" disabled />
      </el-form-item>
      <el-form-item label="规格">
        <el-input v-model="form.specification" style="width: 35%;" disabled />
      </el-form-item>
      <el-form-item label="纸箱编号">
        <el-input v-model="form.boxCode" style="width: 35%;" disabled />
      </el-form-item>
      <el-form-item label="SAP代码">
        <el-input v-model="form.sapCode" style="width: 35%;" disabled />
      </el-form-item>
      <el-form-item label="日期" prop="selectedDate">
        <el-col :span="24">
          <el-date-picker v-model="form.selectedDate" type="date" format="yyyy年MM月dd日" value-format="yyyyMMdd"
            placeholder="选择日期" style="width: 35%;" />
        </el-col>
      </el-form-item>
      <el-form-item label="箱容量">
        <el-input v-model="form.capacity" style="width: 35%;" class="input-with-select">
          <el-select v-model="form.capacityLabel" style="width:100px;padding-left:6px" slot="prepend" placeholder="请选择">
            <el-option label="枚/卷" value="枚/卷"></el-option>
            <el-option label="枚/箱" value="枚/箱"></el-option>
          </el-select>
        </el-input>
      </el-form-item>

      <el-form-item label="起始页数">
        <el-input-number v-model="form.startIndex" :step="1" size="medium" style="width:150px" :min="1" :max="5000" />
      </el-form-item>
      <el-form-item label="结束页数" prop="printCount">
        <el-input-number v-model="form.printCount" :step="1" size="medium" style="width:150px" :min="1" :max="5000" />
      </el-form-item>
      <el-form-item label="打印布局">
        <el-select v-model="form.printType" style="width: 150px;" placeholder="选择机器编号" value-key="value">
          <el-option v-for="item in printTypeOptions" :key="item.id" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-col :span="2" />
        <el-col :span="2">
          <el-button icon="el-icon-printer" type="primary" @click="printPdf('form')">打印</el-button>
        </el-col>
      </el-form-item>
    </el-form>

    <el-dialog title="预览" :visible.sync="previewDialogVisible" :close-on-click-modal="false"
      :before-close="beforeClosePreviewDialog" :center="true" style="padding:0px 8px">
      <div class="previewContainer" v-html="templateHtml" />
    </el-dialog>
  </div>
</template>

<script>
import { loadMetaData, preview, print } from "@/api/print";
import printJS from "print-js";

export default {
  data() {
    var validatePrintCount = (rule, value, callback) => {
      if (value === "" || value < this.form.startIndex) {
        callback(new Error("结束页数需要大于起始页数"));
      } else {
        callback();
      }
    };
    var today = new Date().toISOString().split("T")[0].split("-").join("");
    console.log('today:' + today);
    return {
      form: {
        name: "上海福助工业有限公司",
        orderNo: "",
        machineName: [],
        selectedDate: today,
        startIndex: 1,
        printCount: 1,
        capacity: "",
        productCode: "",
        productName: "",
        boxCode: '',
        sapCode: "",
        specification: "",
        templateId: null,
        printType: 0,
        orderCode: "",
        capacityLabel: "枚/卷",
      },
      templateHtml: "",
      templateContent: "",
      p1CodeFontSize: -1,
      p2CodeFontSize: -1,
      enableOrderCode: false,
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
        printCount: [
          { required: true, message: "请输入结束页数", trigger: "change" },
          { validator: validatePrintCount, trigger: "change" },
        ],
        selectedDate: [
          {
            required: true,
            message: "请选择日期",
            trigger: "change",
          },
        ],
      },
      printTypeOptions: [
        {
          label: "每页4个(2x2)",
          value: 0,
          id: 0,
        },
        {
          label: "每页6个(2x3)",
          value: 1,
          id: 1,
        },
      ],
      commodities: [],
      machines: [],
      templates: [],
      previewDialogVisible: false,
    };
  },

  created() {
    console.log('created');
    this.loadMetaData();
  },

  methods: {
    printPdf(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          var parmas = {
            templateContent: this.templateContent,
            specification: this.form.specification,
            machineCode: this.form.machineName[1],
            selectedDate: this.form.selectedDate,
            startIndex: this.form.startIndex,
            printCount: this.form.printCount,
            capacity: this.form.capacity
              ? this.form.capacity + " " + this.form.capacityLabel
              : "",
            sapCode: this.form.sapCode,
            productCode: this.form.productCode,
            productName: this.form.productName,
            printType: this.form.printType,
            p1CodeFontSize: this.p1CodeFontSize,
            p2CodeFontSize: this.p2CodeFontSize,
            orderCode: this.form.orderCode,
          };

          print(parmas).then((response) => {
            var pdfBase64 = response.data;
            printJS({
              printable: pdfBase64,
              type: "pdf",
              showModal: true,
              base64: true,
              modalMessage: "正在生成待打印的内容",
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
            machineCode: this.form.machineName[1],
            selectedDate: this.form.selectedDate,
            startIndex: this.form.startIndex,
            printCount: this.form.printCount,
            capacity: this.form.capacity
              ? this.form.capacity + " " + this.form.capacityLabel
              : "",
            sapCode: this.form.sapCode,
            productCode: this.form.productCode,
            productName: this.form.productName,
            p1CodeFontSize: this.p1CodeFontSize,
            p2CodeFontSize: this.p2CodeFontSize,
          };
          preview(parmas).then((response) => {
            this.previewDialogVisible = true;
            this.templateHtml = response.data;
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
      this.p1CodeFontSize = item.p1CodeFontSize;
      this.p2CodeFontSize = item.p2CodeFontSize;
      this.enableOrderCode = item.enableOrderCode;
      if (item.title.indexOf("卷") != -1) {
        this.form.capacityLabel = "枚/卷";
      } else if (item.title.indexOf("箱") != -1) {
        this.form.capacityLabel = "枚/箱";
      }
    },

    orderOptionChange(item) {
      var excelData = item.col6 == null ? "" : item.col6;
      this.form.capacity = excelData
        .replace(/枚\/卷/i, "")
        .replace(/枚\/箱/i, "");
      this.form.productName = item.col4;
      this.form.productCode = item.col3;
      this.form.sapCode = item.col2;
      this.form.boxCode = item.col7;
      this.form.specification = item.col5;
    },
    loadMetaData() {
      this.listLoading = true;
      var param = { type: "common" };
      loadMetaData(param).then((response) => {
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
