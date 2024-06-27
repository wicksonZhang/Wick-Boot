<template>
  <el-dialog v-model="dialogVisible" title="详情" width="800">
    <el-descriptions :column="1" border>
      <el-descriptions-item label="日志编号" min-width="120">
        {{ detailData.id }}
      </el-descriptions-item>
      <el-descriptions-item label="操作类型">
        <el-tag v-if="detailData.logType === 100" type="primary">账号登录</el-tag>
        <el-tag v-if="detailData.logType === 101" type="info">社交登录</el-tag>
        <el-tag v-if="detailData.logType === 103" type="default">短信登录</el-tag>
        <el-tag v-if="detailData.logType === 200" type="primary">主动登出</el-tag>
        <el-tag v-if="detailData.logType === 202" type="danger">强制登出</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="用户名称">
        {{ detailData.username }}
      </el-descriptions-item>
      <el-descriptions-item label="登录地址">
        {{ detailData.userIp }}
      </el-descriptions-item>
      <el-descriptions-item label="浏览器">
        {{ detailData.userAgent }}
      </el-descriptions-item>
      <el-descriptions-item label="登陆结果">
          <el-tag v-if="detailData.result === 0" type="success">成功</el-tag>
          <el-tag v-if="detailData.result === 10" type="info">账号或密码不正确</el-tag>
          <el-tag v-if="detailData.result === 20" type="default">用户被禁用</el-tag>
          <el-tag v-if="detailData.result === 30" type="primary">验证码不存在</el-tag>
          <el-tag v-if="detailData.result === 31" type="danger">验证码不正确</el-tag>
          <el-tag v-if="detailData.result === 100" type="danger">未知异常</el-tag>
      </el-descriptions-item>
      <el-descriptions-item label="登录日期">
        {{ detailData.createTime }}
      </el-descriptions-item>
    </el-descriptions>
  </el-dialog>
</template>
<script lang="ts" setup>

import {LoginLogPageVO} from "@/api/logger/type";

defineOptions({ name: 'SystemLoginLogDetail' })

const dialogVisible = ref(false) // 弹窗的是否展示
const detailLoading = ref(false) // 表单的加载中
const detailData = ref<LoginLogPageVO[]>();// 详情数据

/** 打开弹窗 */
const open = async (data: LoginLogPageVO) => {
  console.log(data);
  dialogVisible.value = true
  // 设置数据
  detailLoading.value = true
  try {
    detailData.value = data
  } finally {
    detailLoading.value = false
  }
}
defineExpose({ open }) // 提供 open 方法，用于打开弹窗
</script>
