<template>
  <div class="app-container">
    <!-- 搜索栏 -->
    <div class="search-container">
      <!-- 搜索工作栏 -->
      <el-form  ref="queryFormRef" :model="queryParams" :inline="true" label-width="68px">
        <el-form-item label="操作人" prop="userId">
          <el-select
            v-model="queryParams.userId"
            clearable
            filterable
            placeholder="请输入操作人员"
            class="!w-240px"
          >
            <el-option
              v-for="user in userList"
              :key="user.id"
              :label="user.nickname"
              :value="user.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="操作分类" prop="type">
          <el-input
            v-model="queryParams.type"
            placeholder="请输入操作模块"
            clearable
            @keyup.enter="handleQuery"
            class="!w-240px"
          />
        </el-form-item>
        <el-form-item label="操作模块" prop="subType">
          <el-input
            v-model="queryParams.module"
            placeholder="请输入操作模块"
            clearable
            @keyup.enter="handleQuery"
            class="!w-240px"
          />
        </el-form-item>
        <el-form-item label="操作内容" prop="action">
          <el-input
            v-model="queryParams.content"
            placeholder="请输入操作名"
            clearable
            @keyup.enter="handleQuery"
            class="!w-240px"
          />
        </el-form-item>
        <el-form-item label="操作时间" prop="createTime">
          <el-date-picker
            v-model="queryParams.createTime"
            value-format="YYYY-MM-DD HH:mm:ss"
            type="daterange"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            :default-time="[new Date('1 00:00:00'), new Date('1 23:59:59')]"
            class="!w-220px"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleQuery"> <i-ep-search/> 搜索</el-button>
          <el-button @click="resetQuery"><i-ep-refresh/> 重置</el-button>
          <el-button type="success" plain @click="handleExport" :loading="exportLoading">
            <i-ep-download/> 导出
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <!-- 数据表格 && 分页条 -->
    <el-card shadow="never" class="table-container">
      <el-table v-loading="loading" :data="pageData">
        <el-table-column label="日志编号" align="center" prop="id" />
        <el-table-column label="操作模块" align="center" prop="module" />
        <el-table-column label="操作名" align="center" prop="name" width="180" />
        <el-table-column label="操作类型" align="center" prop="type">
          <template v-slot="scope">
            <el-tag v-if="scope.row.type === 0" type="default">其它</el-tag>
            <el-tag v-if="scope.row.type === 1" type="info">查询</el-tag>
            <el-tag v-if="scope.row.type === 2" type="primary">新增</el-tag>
            <el-tag v-if="scope.row.type === 3" type="warning">修改</el-tag>
            <el-tag v-if="scope.row.type === 4" type="danger">删除</el-tag>
            <el-tag v-if="scope.row.type === 5" type="default">导出</el-tag>
            <el-tag v-if="scope.row.type === 6" type="default">导入</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作人" align="center" prop="userNickname" />
        <el-table-column label="操作结果" align="center" prop="status">
          <template v-slot="scope">
            <span>{{ scope.row.resultCode === 0 ? '成功' : '失败' }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作日期" align="center" prop="startTime" width="180">
          <template v-slot="scope">
            <span>{{ scope.row.startTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="执行时长" align="center" prop="startTime">
          <template v-slot="scope">
            <span>{{ scope.row.duration }}  ms</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" fixed="right" width="60">
          <template #default="scope">
            <el-button
              link
              type="primary"
              @click="openDetail(scope.row)"
            >
              详情
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页条 -->
      <pagination
        v-if="total > 0"
        v-model:total="total"
        v-model:page="queryParams.pageNumber"
        v-model:limit="queryParams.pageSize"
        @pagination="handleQuery"
      />
    </el-card>
  </div>

</template>

<script setup lang="ts">
import {OperateLogPageVO} from "@/api/logger/type";
import {getOperateLogPage} from "@/api/logger";
import {getSimpleUserList} from "@/api/user";

const loading = ref(false); // 列表的加载中
const queryFormRef = ref() // 搜索的表单
const exportLoading = ref(false) // 导出的加载中
const total = ref(0); // 数据总数
const pageData = ref<OperateLogPageVO[]>();
const userList = ref<[]>(); // 用户列表

const queryParams = reactive({
  pageNumber: 1,
  pageSize: 10,
  userId: undefined,
  type: undefined,
  subType: undefined,
  action: undefined,
  createTime: [] as any
});

/** 搜索按钮操作 */
function handleQuery(){
  loading.value = true;
  getOperateLogPage(queryParams)
    .then(({data}) => {
      pageData.value = data.list;
      total.value = data.total;
    })
    .finally(() => {
      loading.value = false;
    });
}

/** 重置按钮操作 */
function resetQuery(){
  queryFormRef.value.resetFields()
  handleQuery()
}

/** 详情操作 */
const detailRef = ref()
function openDetail(row: OperateLogPageVO) {
  detailRef.value.open(row);
}

/** 导出按钮操作 */
function handleExport() {

}

onMounted(async () => {
  await handleQuery();
  // 获得用户列表
  userList.value = await getSimpleUserList()
});
</script>
