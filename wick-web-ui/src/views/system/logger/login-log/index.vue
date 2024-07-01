<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <div class="search-container">
      <el-form ref="queryFormRef" :model="queryParams" :inline="true">
        <el-form-item label="用户名称" prop="username">
          <el-input
            v-model="queryParams.username"
            placeholder="请输入用户名称"
            clearable
            @keyup.enter="handleQuery"
            class="!w-240px"
          />
        </el-form-item>
        <el-form-item label="登录地址" prop="userIp">
          <el-input
            v-model="queryParams.userIp"
            placeholder="请输入登录地址"
            clearable
            @keyup.enter="handleQuery"
            class="!w-240px"
          />
        </el-form-item>
        <el-form-item label="登录日期" prop="createTime">
          <el-date-picker
            v-model="queryParams.createTime"
            type="daterange"
            value-format="YYYY-MM-DD HH:mm:ss"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
            class="!w-240px"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery">
            <i-ep-search/>
            搜索
          </el-button>
          <el-button @click="resetQuery">
            <i-ep-refresh/>
            重置
          </el-button>
          <el-button type="success" @click="handleExport">
            <i-ep-download/>
            导出
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 数据表格 -->
    <el-card shadow="never" class="table-container">
      <el-table v-loading="loading" :data="pageData">
        <el-table-column label="日志编号" align="center" prop="id"/>
        <el-table-column label="操作类型" align="center">
          <template #default="scope">
            <el-tag v-if="scope.row.logType === 100" type="primary">账号登录</el-tag>
            <el-tag v-if="scope.row.logType === 101" type="info">社交登录</el-tag>
            <el-tag v-if="scope.row.logType === 103" type="default">短信登录</el-tag>
            <el-tag v-if="scope.row.logType === 200" type="primary">主动登出</el-tag>
            <el-tag v-if="scope.row.logType === 202" type="danger">强制登出</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="用户名称" align="center" prop="username" width="180"/>
        <el-table-column label="登录地址" align="center" prop="userIp" width="180"/>
        <el-table-column label="浏览器" align="center" prop="userAgent"/>
        <el-table-column label="登陆结果" align="center" prop="result">
          <template #default="scope">
            <el-tag v-if="scope.row.result === 0" type="success">成功</el-tag>
            <el-tag v-if="scope.row.result === 10" type="info">账号或密码不正确</el-tag>
            <el-tag v-if="scope.row.result === 20" type="default">用户被禁用</el-tag>
            <el-tag v-if="scope.row.result === 30" type="primary">验证码不存在</el-tag>
            <el-tag v-if="scope.row.result === 31" type="danger">验证码不正确</el-tag>
            <el-tag v-if="scope.row.result === 100" type="danger">未知异常</el-tag>
          </template>
        </el-table-column>
        <el-table-column
          label="登录日期"
          align="center"
          prop="createTime"
          width="180"
          value-format="YYYY-MM-DD HH:mm:ss"
        />
        <el-table-column label="操作" align="center">
          <template #default="scope">
            <el-button
              type="primary"
              link
              @click="openDetail(scope.row)"
            > 详情
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--   分页条   -->
      <pagination
        v-if="total > 0"
        v-model:total="total"
        v-model:page="queryParams.pageNumber"
        v-model:limit="queryParams.pageSize"
        @pagination="handleQuery"
      />
    </el-card>

    <!-- 表单弹窗 -->
    <login-log-detail ref="detailRef"/>
  </div>
</template>

<script setup lang="ts">
import {exportLoginLog, getLoginLogPage} from "@/api/logger"
import {LoginLogPageVO} from "@/api/logger/type";

const queryFormRef = ref(ElForm);
const loading = ref(false);
const createTime = ref([]);
const queryParams = reactive({
  pageNumber: 1,
  pageSize: 10,
  username: undefined,
  userIp: undefined,
  createTime: [] as any
});
const total = ref(0); // 数据总数
const pageData = ref<LoginLogPageVO[]>();

/** 查询 */
function handleQuery() {
  loading.value = true;
  getLoginLogPage(queryParams)
    .then(({data}) => {
      pageData.value = data.list;
      total.value = data.total;
    })
    .finally(() => {
      loading.value = false;
    });
}

/**
 * 重置查询
 */
function resetQuery() {
  queryFormRef.value.resetFields();
  createTime.value = []
  queryParams.startTime = undefined;
  queryParams.endTime = undefined;
  handleQuery();
}

/** 详情操作 */
const detailRef = ref()
function openDetail(row: LoginLogPageVO) {
  detailRef.value.open(row);
}

/**
 * 导出
 */
function handleExport() {
  exportLoginLog(queryParams).then((response: any) => {
      const fileData = response.data;
      const fileName = decodeURI(
        response.headers["content-disposition"].split(";")[1].split("=")[1]
      );
      const fileType =
        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8";

      const blob = new Blob([fileData], { type: fileType });
      const downloadUrl = window.URL.createObjectURL(blob);

      const downloadLink = document.createElement("a");
      downloadLink.href = downloadUrl;
      downloadLink.download = fileName;

      document.body.appendChild(downloadLink);
      downloadLink.click();

      document.body.removeChild(downloadLink);
      window.URL.revokeObjectURL(downloadUrl);
    });
}

onMounted(() => {
  handleQuery();
});

</script>
