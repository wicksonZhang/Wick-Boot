<template>
  <el-dialog v-model="dialogVisible" title="详情" width="800">
    <el-descriptions :column="1" border>
      <el-descriptions-item label="日志主键" min-width="120">
        {{ detailData.id }}
      </el-descriptions-item>
      <el-descriptions-item label="链路追踪" v-if="detailData.traceId">
        {{ detailData.traceId }}
      </el-descriptions-item>
      <el-descriptions-item label="操作人编号">
        {{ detailData.userId }}
      </el-descriptions-item>
      <el-descriptions-item label="操作人名字">
        {{ detailData.userNickname }}
      </el-descriptions-item>
      <el-descriptions-item label="操作人 IP">
        {{ detailData.userIp }}
      </el-descriptions-item>
      <el-descriptions-item label="操作人 UA">
        {{ detailData.userAgent }}
      </el-descriptions-item>
      <el-descriptions-item label="操作模块">
        {{ detailData.module }}
      </el-descriptions-item>
      <el-descriptions-item label="操作名">
        {{ detailData.name }}
      </el-descriptions-item>
      <el-descriptions-item v-if="detailData.extra" label="操作拓展参数">
        {{ detailData.extra }}
      </el-descriptions-item>
      <el-descriptions-item label="请求 URL">
        {{ detailData.requestMethod }} {{ detailData.requestUrl }}
      </el-descriptions-item>
      <el-descriptions-item label="Java 方法名">
        {{ detailData.javaMethod }}
      </el-descriptions-item>
      <el-descriptions-item label="Java 方法参数">
        {{ detailData.javaMethodArgs }}
      </el-descriptions-item>
      <el-descriptions-item label="操作时间">
        {{ detailData.startTime }}
      </el-descriptions-item>
      <el-descriptions-item label="执行时长">
        {{ detailData.duration }}
      </el-descriptions-item>
      <el-descriptions-item label="操作结果">
        {{ detailData.resultMsg }}
      </el-descriptions-item>
    </el-descriptions>
  </el-dialog>
</template>


<script setup lang="ts">
import {OperateLogPageVO} from "@/api/logger/type";

defineOptions({name: 'SystemOperateLogDetail'})

const dialogVisible = ref(false) // 弹窗的是否展示
const detailLoading = ref(false) // 表单的加载中
const detailData = ref<OperateLogPageVO[]>();// 详情数据

/** 打开弹窗 */
const open = async (data: OperateLogPageVO) => {
  dialogVisible.value = true
  // 设置数据
  detailLoading.value = true
  try {
    detailData.value = data
  } finally {
    detailLoading.value = false
  }
}
defineExpose({open}) // 提供 open 方法，用于打开弹窗
</script>
