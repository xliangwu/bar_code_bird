<template>
  <div class="dashboard-container">
    <div class="dashboard-text">
      <p>欢迎使用二维码定制打印系统!</p>
      <div>
        <span>操作说明:</span>
        <ol>
          <li>打印</li>
          <ul>
            <li>
              <el-link type="primary" href="#/print/index">打印</el-link>
            </li>
            <li>
              <el-link type="primary" href="#/zplPrint/index">斑马打印</el-link>
            </li>
          </ul>
          <li>
            <el-link type="primary" href="#/product/commodity">品项信息</el-link>
          </li>
          <li>
            <el-link type="primary" href="#/product/template">配置模板</el-link>
          </li>
          <li>
            <el-link type="primary" href="#/product/machine">机器管理</el-link>
          </li>
        </ol>
      </div>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  computed: {
    ...mapGetters(['name', 'roles'])
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
